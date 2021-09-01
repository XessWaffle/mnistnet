package layer.conv;

import core.NeuralLayer;
import core.Neuron;
import corefunc.Convolutable;
import img.ConvolutionalFilter;
import neuron.PoolingNeuron;

public class PoolingLayer extends NeuralLayer implements Convolutable{
	
	private Neuron[][] layer;
	
	private int xSize, ySize;
	private int strideSize;
	
		
	public PoolingLayer(int xSize, int ySize, int strideSize) {
		this.setXSize(xSize);
		this.setYSize(ySize);
		this.setStrideSize(strideSize);
		
		layer = new PoolingNeuron[xSize][ySize];
		initialize();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				layer[i][j] = new PoolingNeuron();
				this.addNeuron(layer[i][j]);
			}
		}
	}

	public PoolingNeuron[][] getLayer() {
		return (PoolingNeuron[][]) layer;
	}

	public void setLayer(PoolingNeuron[][] layer) {
		this.layer = layer;
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

	public int getStrideSize() {
		return strideSize;
	}


	public void setStrideSize(int strideSize) {
		this.strideSize = strideSize;
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

	@Override
	public double getValue(int x, int y) {
		// TODO Auto-generated method stub
		return this.layer[x][y].getNetOutput();
	}

	public Neuron getNeuronAt(int x, int y) {
		return x >= 0 && y >= 0 && x < this.getXSize() && y < this.getYSize()? this.layer[x][y] : null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	


	
	
	
}
