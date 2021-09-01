package layer.conv;

import core.NeuralLayer;
import core.Neuron;
import corefunc.Convolutable;
import img.ConvolutionalFilter;
import neuron.ConvolutionalNeuron;
public class ConvolutionalLayer extends NeuralLayer implements Convolutable{
	
	public ConvolutionalFilter rgbFilter;
	
	private Neuron[][] layer;
	
	private int xSize, ySize;
	
	private int prevBlockDepth;
	
	public ConvolutionalLayer(int xSize, int ySize, int filterX, int filterY, int prevBlockDepth) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.prevBlockDepth = prevBlockDepth;
		
		this.layer = new ConvolutionalNeuron[xSize][ySize];
		
		this.setRgbFilter(new ConvolutionalFilter(filterX, filterY));
		
		initialize();

	}

	private void initialize() {
		
		for(int i = 0; i < layer.length; i++) {
			for(int j = 0; j < layer[i].length; j++) {
				layer[i][j] = new ConvolutionalNeuron(this.rgbFilter.getXSize(), this.rgbFilter.getYSize(), this.prevBlockDepth, this);
				this.addNeuron(layer[i][j]);
			}
		}
	}

	public int getXSize() {
		return xSize;
	}

	public void setXSize(int xSize) {
		this.xSize = xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public void setYSize(int ySize) {
		this.ySize = ySize;
	}

	public Neuron[][] getLayer() {
		return layer;
	}

	public void setLayer(Neuron[][] layer) {
		this.layer = layer;
	}
	
	public Neuron getNeuronAt(int x, int y) {
		return x >= 0 && y >= 0 && x < this.getXSize() && y < this.getYSize()? this.layer[x][y] : null;
	}
	
	@Override
	public double getValue(int x, int y) {
		// TODO Auto-generated method stub
		return this.layer[x][y].getNetOutput();
	}
	
	public ConvolutionalFilter getRectangle(int xStart, int yStart, int xEnd, int yEnd){
		int sx = xEnd - xStart + 1;
		int sy = yEnd - yStart + 1;
		
		double[][] ret = new double[sx][sy];
		
		int xT = xStart, yT = yStart;
		
		for(int i = 0; i < sx; i++) {
			for(int j = 0; j < sy; j++) {
				
				if(xT >= 0 && xT < layer.length && yT >= 0 && yT < layer[0].length) {
					ret[i][j] = layer[xT][yT].getNetOutput();
				} else {
					ret[i][j] = 0;
				}
				
				yT++;
			}
			xT++;
		}
		
		ConvolutionalFilter retO = new ConvolutionalFilter(sx, sy);
		
		//System.out.println(retO + " " + ret.length);
		
		retO.setFilter(ret);
		
		return retO;
		
	}

	public ConvolutionalFilter getRgbFilter() {
		return rgbFilter;
	}

	public void setRgbFilter(ConvolutionalFilter rgbFilter) {
		this.rgbFilter = rgbFilter;
	}

	public int getPrevBlockDepth() {
		return prevBlockDepth;
	}

	public void setPrevBlockDepth(int prevBlockDepth) {
		this.prevBlockDepth = prevBlockDepth;
	}
	
	public void calculate() {
		for(int i = 0; i < layer.length; i++) {
			for(int j = 0; j < layer[i].length; j++) {
				((ConvolutionalNeuron)layer[i][j]).setWeightValues(rgbFilter);
			}
		}
		
		super.calculate();
	}
	
	public void update() {
		for(int i = 0; i < this.getXSize(); i++) {
			for(int j = 0; j < this.getYSize(); j++) {
				rgbFilter.add(((ConvolutionalNeuron)this.layer[i][j]).getWeightUpdateMatrix());
			}
		}
	}
}
	
	
	
	
