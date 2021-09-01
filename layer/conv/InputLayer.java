package layer.conv;

import core.NeuralLayer;
import core.Neuron;
import corefunc.Convolutable;
import img.ConvolutionalFilter;
import neuron.InputNeuron;

public class InputLayer extends NeuralLayer implements Convolutable{
	
	private Neuron[][] layer;
	private int xSize, ySize;
	
	public InputLayer(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		
		layer = new Neuron[xSize][ySize];
		
		initialize();
		
	}
	
	private void initialize() {
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				layer[i][j] = new InputNeuron();
				this.addNeuron(layer[i][j]);
			}
		}
	}
	
	
	@Override
	public double getValue(int x, int y) {
		// TODO Auto-generated method stub
		return this.layer[x][y].getNetOutput();
	}

	@Override
	public int getXSize() {
		// TODO Auto-generated method stub
		return xSize;
	}

	@Override
	public int getYSize() {
		// TODO Auto-generated method stub
		return ySize;
	}
	
	public Neuron getNeuronAt(int x, int y) {
		return x >= 0 && y >= 0 && x < this.getXSize() && y < this.getYSize()? this.layer[x][y] : null;
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



	public Neuron[][] getLayer() {
		return layer;
	}


	public void setLayer(Neuron[][] layer) {
		this.layer = layer;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
