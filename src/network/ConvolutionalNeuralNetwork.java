package network;

import java.util.ArrayList;
import java.util.Arrays;

import core.NeuralLayer;
import core.NeuralLink;
import core.NeuralNetwork;
import core.Neuron;
import core.factory.ActivationFactory;
import corefunc.ActivationType;
import corefunc.ConnectionType;
import data.CSVManager;
import img.ImageFilter;
import img.label.LabeledImage;
import img.label.LabeledImageLoader;
import img.view.ImageParser;
import layer.conv.ConvolutionalLayer;
import layer.conv.PoolingLayer;
import layer.conv.block.Block;
import layer.conv.block.ConvolutionalBlock;
import layer.conv.block.InputBlock;
import layer.conv.block.PoolingBlock;
import learning.Backpropagation;
import neuron.ConvolutionalNeuron;
import neuron.OutputNeuron;
import neuron.PoolingNeuron;
import visualization.ConvNetVisualization;


public class ConvolutionalNeuralNetwork extends NeuralNetwork{
	
	public static final int EPOCH_PRESET = 100;
	public static final int CONV_RELU_POOL = 0;
	public static final int CONV_RELU_CONV = 1;
	
	private Block inputBlock;
	
	private ArrayList<NeuralLayer> fcLayer;
	private ConnectionType ctFc;
	
	private LabeledImageLoader limLoad;
	private LabeledImage current;
	private ImageParser limParse;
	
	private ConvNetVisualization cnv;
	
	private CSVManager neuronDat;
	private CSVManager networkDat;
	
	private double accuracy;
	
	public ConvolutionalNeuralNetwork(int[][] numFilters, int initBlockWidth, int initBlockDepth, int initBlockHeight, int[] numHiddenLayers, ConnectionType ct) {
		
		super();
		
		this.limLoad = new LabeledImageLoader(false);
		this.limParse = new ImageParser();
		this.fcLayer = new ArrayList<>();
		
		inputBlock = new InputBlock(initBlockDepth, initBlockWidth, initBlockHeight, null);
		
		this.setInput(inputBlock);
		
		initialize(numFilters);
		initialize(numHiddenLayers);
				
		ctFc = ct;
		
		this.outputBuffer = new double[10];
		
		neuronDat = new CSVManager("data\\" + ctFc + "NeuronData.csv");
		networkDat = new CSVManager("data\\" + ctFc + "NetworkData.csv");
		
		initialize();
		
	}
	
	private void initialize() {
		ArrayList<NeuralLayer> all = new ArrayList<>();
		
		all.add(this.getInputs());
		all.addAll(this.getHiddenLayers());
		all.add(this.getOutputs());
		
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> namesNeu = new ArrayList<>();
		
		names.add("Epoch");
		namesNeu.add("Epoch");
		for(NeuralLayer nl: all) {
			for(Neuron n: nl.getAllNeurons()) {
				names.add(n.getLabel() + " Bias");
				namesNeu.add(n.getLabel() + " Activation");
				namesNeu.add(n.getLabel() + " Input");
				for(NeuralLink forProp: n.getForwardPropConnections()) {
					names.add(n.getLabel() + " " + forProp.label + "Weight");
				}
			}
		}
		
		names.add("Network Error");
		names.add("Accuracy");
		
		String[] push = new String[names.size()];
		String[] pushNeu = new String[namesNeu.size()];
		
		
		System.out.println(push.length);
		
		int pushLoc = 0;
		int pushNeuLoc = 0;
		
		for(String add: names) {
			push[pushLoc++] = add;
		}
		
		for(String add: namesNeu) {
			pushNeu[pushNeuLoc++] = add;
		}
		
		neuronDat.pushData(pushNeu);
		
		neuronDat.write();
		
		networkDat.pushData(push);
		
		networkDat.write();
	}
	
	private void initialize(int[][] numFilters) {
		// TODO Auto-generated method stub
		
		System.out.println("\nInitializing ConvNet...");
		
		for(int[] filt: numFilters) {
			
			Block prevBlock = (Block) this.getNetwork().get(this.getNetwork().size() - 1);
			
			ConvolutionalBlock cbl = new ConvolutionalBlock(filt[0], filt[1], filt[2], prevBlock);
			
			this.addHiddenLayer(cbl);
			
			PoolingBlock pbl = new PoolingBlock(2, cbl);
			
			this.addHiddenLayer(pbl);
			
		}
		Block prevBlock = (Block) this.getNetwork().get(this.getNetwork().size() - 1);
		
		PoolingBlock pbl = new PoolingBlock(2, prevBlock);
		
		this.addHiddenLayer(pbl);
		
		System.out.println("ConvNet initialization complete.\n");
		
		for(NeuralLayer nl: this.getNetwork()) {
			System.out.println((Block) nl);
		}
	}

	private void initialize(int[] numHiddenLayers) {
		
		System.out.println("Initializing fcNet..." + this.ctFc);
		
		//this.fcLayer.add(this.getHiddenLayers().get(this.getHiddenLayers().size() - 1));
		
		for(int length: numHiddenLayers) {
			NeuralLayer hidden = new NeuralLayer(ActivationFactory.create(ActivationType.HYPERBOLIC_TANGENT));
			hidden.addNeuron(length);
			
			//this.addHiddenLayer(hidden);
			this.fcLayer.add(hidden);
			
			System.out.println("New HL: " + length);
		
		}
		
		NeuralLayer output = new NeuralLayer();
		
		for(int i = 0; i < 10; i++) {
			
			Neuron tA = new OutputNeuron();
			
			output.addNeuron(tA);
		}
		
		for(int i = 0; i < 10; i++) {
			output.getNeuronAt(i).setFunction(ActivationFactory.create(ActivationType.SIGMOID));
		}
		
		this.setOutput(output);
		this.fcLayer.add(output);
		
		System.out.println("Finalizing Initialization...");
		
	}
	
	public LabeledImage getCurrentImage() {
		return current;
	}

	public void setCurrentImage(LabeledImage current) {
		this.current = current;
	}

	public ArrayList<NeuralLayer> getFcLayer() {
		return fcLayer;
	}

	public void setFcLayer(ArrayList<NeuralLayer> fcLayer) {
		this.fcLayer = fcLayer;
	}

	public ImageParser getLimParse() {
		return limParse;
	}

	public void setLimParse(ImageParser limParse) {
		this.limParse = limParse;
	}
	
	public LabeledImageLoader getLimLoad() {
		return limLoad;
	}

	public void setLimLoad(LabeledImageLoader limLoad) {
		this.limLoad = limLoad;
	}

	
	@Override
	public void connect() {
		
		for(NeuralLayer nl: this.getNetwork()) {
			
			Block process;
			process = (Block) nl;
			
			if(process instanceof ConvolutionalBlock) {
				
				System.out.println("Connecting Conv Block to " + process.getPrevBlock().getClass());
				
				connectConvBlock((ConvolutionalBlock)process);
			} else if(process instanceof PoolingBlock) {
				
				System.out.println("Connecting Pool Block to " + process.getPrevBlock().getClass());
				
				connectPoolBlock((PoolingBlock)process);
			}
		}
		
		connectFC();
		
		
	}

	private void connectConvFC() {
		// TODO Auto-generated method stub
		NeuralLayer toConnect = this.getHiddenLayers().get(this.getHiddenLayers().size() - 1);
		NeuralLayer tCto = this.fcLayer.get(0);
		
		for(Neuron tC: toConnect.getAllNeurons()) {
			for(Neuron tCat: tCto.getAllNeurons()) {
				tCat.inputConnect(tC, false);
				tC.outputConnect(tCat, true);
				
				System.out.println("Conv-FC Connection: " + tCat + "---" + tC);
			}
		}
		
	}

	private void connectFC() {
		// TODO Auto-generated method stub
		
		if(ctFc == ConnectionType.DRN) {
			connectConvFC();
			
			int layerDepth = 2;
			
			for(int i = 0; i < this.fcLayer.size() - 1; i++) {
				for(Neuron toConnect: this.fcLayer.get(i).getAllNeurons()) {
					for(Neuron tCto: this.fcLayer.get(i + 1).getAllNeurons()) {
						tCto.inputConnect(toConnect, false);
						
						toConnect.outputConnect(tCto, true);
						System.out.println("FC Connection: " + tCto + "---" + toConnect);
						
					}
				}
			}
			
			for(int i = layerDepth - 1; i < this.fcLayer.size(); i++) {
				
				NeuralLayer lTc = null;
				
				try {
					lTc = this.fcLayer.get(i - layerDepth);
				} catch(Exception e) {
					lTc = this.getHiddenLayers().get(this.getHiddenLayers().size() - 1);
				}
				
				Neuron tInput = this.fcLayer.get(i).getFirstNeuron();
				Neuron tOutput = lTc.getFirstNeuron();
				
				Neuron bInput = this.getFcLayer().get(i).getLastNeuron();
				Neuron bOutput = lTc.getLastNeuron();
				
				tInput.inputConnect(tOutput, false);
				tOutput.outputConnect(tInput, true);
				
				bInput.inputConnect(bOutput, false);
				bOutput.outputConnect(bInput, true);
			
			}
			
			
		} else if(ctFc == ConnectionType.ELM) {
			connectELM();
		} else if(ctFc == ConnectionType.EncLM) {
			connectConvFC();
			
			ArrayList<ArrayList<Integer>> layerCalc = new ArrayList<ArrayList<Integer>>();
			
			for(int i = 0; i < this.fcLayer.size(); i++) {
				layerCalc.add(new ArrayList<Integer>());
			}
			
			for(int i = 0; i < this.fcLayer.size(); i++) {
				
				for(Neuron in: this.fcLayer.get(i).getAllNeurons()) {
					int numConnections = (int)(Math.random() * 16);
					
					System.out.println("Connection Process initiated: " + in + ", " + numConnections);
					
					for(int j = 0; j < numConnections; j++) {
						
						Integer randLayer, randIndex;
						
						while(true) {
							randLayer = (int)(Math.random() * i);
							randIndex = (int)(Math.random() * this.fcLayer.get(randLayer).getAllNeurons().size());
							
							System.out.println("Testing Connection: " + "(" + randLayer + ", " + randIndex + ")");
						
							if(!layerCalc.get(randLayer).contains(randIndex) || Math.random() > 0.5) {
								layerCalc.get(randLayer).add(randIndex);
								
								System.out.println("Connection Succeeded:" + "(" + randLayer + ", " + randIndex + ")");
								
								break;
							}
							
							System.out.println("Failed Connection:" + "(" + randLayer + ", " + randIndex + ")");
							
							if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
								break;
							}
							
						}
						
						Neuron get = this.fcLayer.get(randLayer).getNeuronAt(randIndex);
						
						in.inputConnect(get, false);
						get.outputConnect(in, true);
						
						if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
							break;
						}
					}
					
				}
			
				
				for(Neuron in: this.getOutputs().getAllNeurons()) {
					int numConnections = (int)(Math.random() * 16);
					
					System.out.println("Connection Process initiated: " + in + ", " + numConnections);
					
					for(int j = 0; j < numConnections; j++) {
						
						Integer randLayer, randIndex;
						
						while(true) {
							randLayer = (int)(Math.random() * i);
							randIndex = (int)(Math.random() * this.fcLayer.get(randLayer).getAllNeurons().size());
							
							System.out.println("Testing Connection: " + "(" + randLayer + ", " + randIndex + ")");
							
							if(this.fcLayer.get(randLayer).getAllNeurons().get(randIndex).getBackPropConnections().size() < 2 || Math.random() > 0.5 ) {
								System.out.println("Connection Succeeded:" + "(" + randLayer + ", " + randIndex + ")");
								
								break;
							}
							
							System.out.println("Failed Connection:" + "(" + randLayer + ", " + randIndex + ")");
							
							if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
								break;
							}
						}
						
						Neuron get = this.fcLayer.get(randLayer).getNeuronAt(randIndex);
						
						in.inputConnect(get, false);
						get.outputConnect(in, true);
						
						if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
							break;
						}
					}
				}
				
			}

			
		} else {
			
			for(int i = 0; i < this.fcLayer.size() - 1; i++) {
				for(Neuron toConnect: this.fcLayer.get(i).getAllNeurons()) {
					for(Neuron tCto: this.fcLayer.get(i + 1).getAllNeurons()) {
						tCto.inputConnect(toConnect, false);
						
						toConnect.outputConnect(tCto, true);
						System.out.println("FC Connection: " + tCto + "---" + toConnect);
						
					}
				}
			}
			
			connectConvFC();
		}
		
		
	}

	private void connectELM() {
		// TODO Auto-generated method stub
		NeuralLayer toConnect = this.getHiddenLayers().get(this.getHiddenLayers().size() - 1);
		NeuralLayer tCto = this.fcLayer.get(0);
		
		ArrayList<Integer> stored = new ArrayList<Integer>();
		
		for(Neuron tCat: tCto.getAllNeurons()) {
			int numConnections = (int) ((Math.random() * toConnect.getAllNeurons().size())/tCto.getAllNeurons().size()) * 2;
			
			for(int i = 0; i < numConnections; i++) {
				
				Neuron tC = null;
				
				System.out.println("Connection Process initiated: " + tCat);
				
				while(true) {
					
					Integer neuronLoc = (int)(Math.random() * toConnect.getAllNeurons().size());
					
					tC = toConnect.getNeuronAt(neuronLoc);
					
					System.out.println("Testing Connection: " + tC);
					
					if(!stored.contains(neuronLoc) || Math.random() > 0.5){
						stored.add(neuronLoc);
						
						System.out.println("Connection Succeeded:" + tC + " at I " + neuronLoc);
						
						break;
					}
					
					if(stored.size() == toConnect.getAllNeurons().size()) {
						break;
					}
					
					System.out.println("Failed Connection:" + tC + " at I " + neuronLoc);
					
				}
				
				tCat.inputConnect(tC, false);
				tC.outputConnect(tCat, true);
				
				if(stored.size() == toConnect.getAllNeurons().size()) {
					break;
				}
				
			}
			
		}
		
		ArrayList<ArrayList<Integer>> layerCalc = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < this.fcLayer.size(); i++) {
			layerCalc.add(new ArrayList<Integer>());
		}
		
		for(int i = 0; i < this.fcLayer.size(); i++) {
			
			for(Neuron in: this.fcLayer.get(i).getAllNeurons()) {
				int numConnections = (int)(Math.random() * 16);
				
				System.out.println("Connection Process initiated: " + in + ", " + numConnections);
				
				for(int j = 0; j < numConnections; j++) {
					
					Integer randLayer, randIndex;
					
					while(true) {
						randLayer = (int)(Math.random() * i);
						randIndex = (int)(Math.random() * this.fcLayer.get(randLayer).getAllNeurons().size());
						
						System.out.println("Testing Connection: " + "(" + randLayer + ", " + randIndex + ")");
					
						if(!layerCalc.get(randLayer).contains(randIndex) || Math.random() > 0.5) {
							layerCalc.get(randLayer).add(randIndex);
							
							System.out.println("Connection Succeeded:" + "(" + randLayer + ", " + randIndex + ")");
							
							break;
						}
						
						System.out.println("Failed Connection:" + "(" + randLayer + ", " + randIndex + ")");
						
						if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
							break;
						}
						
					}
					
					Neuron get = this.fcLayer.get(randLayer).getNeuronAt(randIndex);
					
					in.inputConnect(get, false);
					get.outputConnect(in, true);
					
					if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
						break;
					}
				}
				
			}
		
			
			for(Neuron in: this.getOutputs().getAllNeurons()) {
				int numConnections = (int)(Math.random() * 16);
				
				System.out.println("Connection Process initiated: " + in + ", " + numConnections);
				
				for(int j = 0; j < numConnections; j++) {
					
					Integer randLayer, randIndex;
					
					while(true) {
						randLayer = (int)(Math.random() * i);
						randIndex = (int)(Math.random() * this.fcLayer.get(randLayer).getAllNeurons().size());
						
						System.out.println("Testing Connection: " + "(" + randLayer + ", " + randIndex + ")");
						
						if(this.fcLayer.get(randLayer).getAllNeurons().get(randIndex).getBackPropConnections().size() < 2 || Math.random() > 0.5 ) {
							System.out.println("Connection Succeeded:" + "(" + randLayer + ", " + randIndex + ")");
							
							break;
						}
						
						System.out.println("Failed Connection:" + "(" + randLayer + ", " + randIndex + ")");
						
						if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
							break;
						}
					}
					
					Neuron get = this.fcLayer.get(randLayer).getNeuronAt(randIndex);
					
					in.inputConnect(get, false);
					get.outputConnect(in, true);
					
					if(layerCalc.get(randLayer).size() == this.fcLayer.get(randLayer).getAllNeurons().size()) {
						break;
					}
				}
			}
			
		}
	}

	private void connectPoolBlock(PoolingBlock process) {
		// TODO Auto-generated method stub
		Block prevBlock = process.getPrevBlock();
		
		int depth = 0;
		
		for(PoolingLayer layer: (PoolingLayer[]) process.getBlock()) {
			
			System.out.println("Connecting Layer: " + "(" + layer.getXSize() + ", " + layer.getYSize() + ")");
			
			for(int x = 0; x < layer.getXSize(); x++) {
				for(int y = 0; y < layer.getYSize(); y++) {
					
					System.out.println("Connecting: " + layer.getNeuronAt(x, y));
					
					int startX = x * 2;
					int endX = (x + 1) * 2;
					int startY = y * 2;
					int endY = (y + 1) * 2;
					
					for(int i = startX; i < endX; i++) {
						for(int j = startY; j < endY; j++) {
							Neuron toConnectto = prevBlock.getBlock()[depth].getNeuronAt(i, j);
							
							if(toConnectto != null) {
								((PoolingNeuron)layer.getNeuronAt(x, y)).inputConnect(toConnectto, true);
								
								toConnectto.outputConnect(layer.getNeuronAt(x, y), true);
								
								System.out.println("Success: " + toConnectto);
							} else {
								System.out.println("Failure: " + toConnectto);
							}
						}
					}
				
					
					
				}
			}
			depth++;
		}
	}

	private void connectConvBlock(ConvolutionalBlock process) {
		// TODO Auto-generated method stub
		
		Block prevBlock = process.getPrevBlock();
		
		for(ConvolutionalLayer layer: (ConvolutionalLayer[]) process.getBlock()) {
			
			System.out.println("Connecting Layer: " + "(" + layer.getXSize() + ", " + layer.getYSize() + ")" + ", " + "(" + layer.getRgbFilter().getXSize() + ", " + layer.getRgbFilter().getYSize() + ")");
			
			for(int x = 0; x < layer.getXSize(); x++) {
				for(int y = 0; y < layer.getYSize(); y++) {
					
					System.out.println("Connecting: " + layer.getNeuronAt(x, y));
					
					int startX = x - layer.getRgbFilter().getXZeroPadding();
					int endX = x + layer.getRgbFilter().getXZeroPadding();
					int startY = y - layer.getRgbFilter().getYZeroPadding();
					int endY = y + layer.getRgbFilter().getYZeroPadding();
					
					int distX = endX - startX;
					int distY = endY - startY;
					
					for(int z = 0; z < prevBlock.getDepth(); z++) {
						for(int i = 0; i < distX; i++) {
							for(int j = 0; j < distY; j++) {
								Neuron toConnectto = prevBlock.getBlock()[z].getNeuronAt(i + startX, j + startY);
								
								if(toConnectto != null) {
									((ConvolutionalNeuron)layer.getNeuronAt(x, y)).inputConnect(toConnectto, i, j, z);
									
									toConnectto.outputConnect(layer.getNeuronAt(x, y), true);
									
									System.out.println("Success: " + toConnectto);
								} else {
									System.out.println("Failure: " + toConnectto);
								}
							}
						}
					}
					
					
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		fcLayer.remove(this.fcLayer.size()-1);
		
		this.hiddenLayers.addAll(fcLayer);
		
		ArrayList<NeuralLayer> net = new ArrayList<>();
		
		net.add(this.getInputs());
		net.addAll(hiddenLayers);
		net.add(this.getOutputs());
		
		((Backpropagation) this.rule).setNetwork(net);
		
		while(limLoad.getCurrentIndex() < limLoad.getFileSize()) {
			
			double accuracy = 0;
			
			for(int i = 0; i < EPOCH_PRESET; i++) {
				current = limLoad.getBuffer();
				
				cnv.getDrawer().setImage(current);
				
				limParse.parse(current);
				
				double[] preferredValues = preparePreferred(current);
				createInput(limParse.getBuffer());
				
				this.getInputs().calculate();
				
				for(NeuralLayer nl: this.getHiddenLayers()) {
					
					//System.out.println("Calculating Neural Layer -- " + nl);
					
					nl.calculate();
				}
				
				int activationViewLoc = 0;
				
				for(ConvolutionalLayer cl: this.getConvLayers()) {
					cnv.getActivationArray()[activationViewLoc++].setLayer(cl.getLayer());	
				}
				
				
				this.getOutputs().calculate();
				
				for(int k = 0; k < outputBuffer.length; k++) {
					outputBuffer[k] = this.getOutputs().getNeuronAt(k).getNetOutput();
				}
				
				int maxIndex = 0;
				
				for(int k = 0; k < outputBuffer.length; k++) {
					if(outputBuffer[maxIndex] < outputBuffer[k]) {
						maxIndex = k;
					}
				}
				
				if(preferredValues[maxIndex] == 1.0) {
					System.out.println("CORRECT");
					accuracy += 1.0;
				}
				
				cnv.getNetworkOutput().setBufferToDraw(outputBuffer);
				
				
				this.error += this.calc.compute(outputBuffer, preferredValues);
			
				
				this.rule.computeAndPush(preferredValues);
		
				try {
					cnv.repaintValues();
					Thread.sleep(0);
					this.writeNeuronData();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			error /= EPOCH_PRESET;
			this.accuracy = accuracy/EPOCH_PRESET;
			
			cnv.getErrorDisplay().pushNext(new Double(error));
			cnv.getAccuracyDisplay().pushNext(new Double(this.accuracy));
			
			int filterViewLoc = 0;
			
			for(ConvolutionalLayer cl: this.getConvLayers()) {
				cnv.getFilterViewArray()[filterViewLoc++].setView(cl.getRgbFilter());
			}
			
			this.update();
			
			System.out.println("Updating..." + error);
			this.writeNetworkData();
			cnv.repaintValues();
			limLoad.loadNextEpoch(1);
		}
	}

	private void createInput(ImageFilter[] buffer) {
		// TODO Auto-generated method stub
		for(int i = 0; i < inputBlock.getBlock().length; i++) {	
			for(int x = 0; x < buffer[i].getXSize(); x++) {
				for(int y = 0; y < buffer[i].getYSize(); y++) {
					inputBlock.getBlock()[i].getNeuronAt(x, y).setNetOutput(buffer[i].getValue(x, y));
				}
			}
				
		}
	}

	private double[] preparePreferred(LabeledImage toTest) {
		// TODO Auto-generated method stub
		double[] ret = new double[10];
		
		for(int i = 0; i < 10; i++) {
			if(i == Integer.parseInt(toTest.getLabel())) {
				ret[i] = 1.0;
			} else {
				ret[i] = 0.0;
			}
		}
		
		System.out.println(Arrays.toString(ret));
		
		return ret;
	}
	
	public int getNumConvLayers() {
		
		int ret = 0;
		
		for(NeuralLayer nl: this.getHiddenLayers()) {
			if(nl instanceof ConvolutionalBlock) {
				ret += ((ConvolutionalBlock) nl).getBlock().length;
			}
		}
		
		return ret;
	}
	
	public int getNumPoolLayers() {
		int ret = 0;
		
		for(NeuralLayer nl: this.getHiddenLayers()) {
			if(nl instanceof PoolingBlock) {
				ret += ((PoolingBlock) nl).getBlock().length;
			}
		}
		
		return ret;
	}
	
	public ConvolutionalLayer[] getConvLayers() {
		ConvolutionalLayer[] ret = new ConvolutionalLayer[this.getNumConvLayers()];
		
		int loc = 0;
		
		for(NeuralLayer nl: this.getHiddenLayers()) {
			if(nl instanceof ConvolutionalBlock) {
				for(int i = 0; i < ((ConvolutionalBlock) nl).getBlock().length ; i++) {
					ret[loc++] = (ConvolutionalLayer) ((ConvolutionalBlock) nl).getBlock()[i];
				}
			}
		}
		
		return ret;
	}
	
	public PoolingLayer[] getPoolingLayers() {
		PoolingLayer[] ret = new PoolingLayer[this.getNumConvLayers()];
		
		int loc = 0;
		
		for(NeuralLayer nl: this.getHiddenLayers()) {
			if(nl instanceof PoolingBlock) {
				for(int i = 0; i < ((PoolingBlock) nl).getBlock().length ; i++) {
					ret[loc++] = (PoolingLayer) ((PoolingBlock) nl).getBlock()[i];
				}
			}
		}
		
		return ret;
	}
	
	
	public void writeNetworkData() {
		ArrayList<NeuralLayer> all = new ArrayList<>();
		
		all.add(this.getInputs());
		all.addAll(this.getHiddenLayers());
		all.add(this.getOutputs());
		
		ArrayList<String> neuron = new ArrayList<String>();
		neuron.add(epoch + "");
		
		for(NeuralLayer nl: all) {
			for(Neuron n: nl.getAllNeurons()) {
				neuron.add(n.getBias() + "");
				for(NeuralLink forProp: n.getForwardPropConnections()) {
					neuron.add(forProp.getWeight() + "");
				}
			}
		}
		
		neuron.add(this.getError() + "");
		
		neuron.add(this.accuracy + "");
		
		String[] push = new String[neuron.size()];
		
		int pushLoc = 0;
		
		for(String add: neuron) {
			push[pushLoc++] = add;
		}
		
		networkDat.pushData(push);
		
		networkDat.write();
	}
	

	public void writeNeuronData() {
		ArrayList<NeuralLayer> all = new ArrayList<>();
		
		all.add(this.getInputs());
		all.addAll(this.getHiddenLayers());
		all.add(this.getOutputs());
		
		ArrayList<String> neuron = new ArrayList<String>();
		neuron.add(epoch + "");
		
		for(NeuralLayer nl: all) {
			for(Neuron n: nl.getAllNeurons()) {
				neuron.add(n.getNetOutput() + "");
				neuron.add(n.getNetInput() + "");
			}
		}
		
		String[] push = new String[neuron.size()];
		
		int pushLoc = 0;
		
		for(String add: neuron) {
			push[pushLoc++] = add;
		}
		
		neuronDat.pushData(push);
		
		neuronDat.write();
	}

	public ConvNetVisualization getVisualization() {
		return cnv;
	}

	public void setVisualization(ConvNetVisualization cnv) {
		this.cnv = cnv;
	}
	

}
