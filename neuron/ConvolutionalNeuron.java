package neuron;

import core.NeuralLink;
import core.Neuron;
import core.factory.ActivationFactory;
import corefunc.ActivationType;
import corefunc.UpdateState;
import img.ConvolutionalFilter;
import layer.conv.ConvolutionalLayer;

public class ConvolutionalNeuron extends Neuron{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NeuralLink[][][] inputWeights;
	private ConvolutionalLayer cLayerIn;

	private int cfX, cfY, cfZ;
	
	public ConvolutionalNeuron(int cfX, int cfY, int cfZ, ConvolutionalLayer in) {
		super(ActivationFactory.create(ActivationType.ELU));
		
		inputWeights = new NeuralLink[cfX][cfY][cfZ];
		
		this.setXSize(cfX);
		this.setYSize(cfY);
		this.setZSize(cfZ);
		
		this.cLayerIn = in;
		
		initialize();
	
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		for(int i = 0; i < cfX; i++) {
			for(int j = 0; j < cfY; j++) {
				for(int k = 0; k < cfZ; k++) {
					inputWeights[i][j][k] = new NeuralLink(null, this, false);
					this.forwardPropNeurons.add(inputWeights[i][j][k]);
				}
			}
		}
	}

	public void setWeightValues(ConvolutionalFilter cf) {
		int cX = 0, cY = 0;
		
		for(int z = 0; z < this.getZSize(); z++) {
			for(int x = 0; x < this.getXSize(); x++) {
				for(int y = 0; y < this.getXSize(); y++) {
					inputWeights[x][y][z].setWeight(cf.getFilter()[cX][cY]);
					cY++;
				}
				cX++;
				cY = 0;
			}
			cX = 0;
			
		}
	}
	
	public ConvolutionalFilter getWeightUpdateMatrix() {
		ConvolutionalFilter toRet = new ConvolutionalFilter(0, cfX, cfY);
		
		for(int z = 0; z < cfZ; z++) {
			for(int x = 0; x < cfX; x++) {
				for(int y = 0; y < cfY; y++) {
					toRet.getFilter()[x][y] += inputWeights[x][y][z].getEpochWeightUpdates();
				}
			}
		}
		
		return toRet;
	}
	
	public void inputConnect(Neuron from, int x, int y, int z) {
		
		if(x >= 0 && y >= 0 && z >= 0)
			inputWeights[x][y][z].setFromNeuron(from);
		
	}

	public NeuralLink[][][] getLinkMatrix() {
		return inputWeights;
	}

	public void setLinkMatrix(NeuralLink[][][] inputWeights) {
		this.inputWeights = inputWeights;
	}

	public ConvolutionalLayer getInConvLayer() {
		return cLayerIn;
	}

	public void setInConvLayer(ConvolutionalLayer cLayerIn) {
		this.cLayerIn = cLayerIn;
	}

	public int getXSize() {
		return cfX;
	}

	public void setXSize(int cfX) {
		this.cfX = cfX;
	}

	public int getYSize() {
		return cfY;
	}

	public void setYSize(int cfY) {
		this.cfY = cfY;
	}
	
	private int getZSize() {
		// TODO Auto-generated method stub
		return cfZ;
	}
	
	private void setZSize(int cfZ) {
		// TODO Auto-generated method stub
		this.cfZ = cfZ;
	}
	
	public void adjust() {
		
		bias += biasUpdates/(double)numUpdates;
		
		cLayerIn.rgbFilter.add(this.getWeightUpdateMatrix());
		
		biasUpdates = 0;
		numUpdates = 0;
		
		this.setActive(false);
		
		state = UpdateState.TO_UPDATE;
				
		
	}
}
