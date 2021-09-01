package corefunc;

import core.Neuron;
import img.ConvolutionalFilter;

public interface Convolutable {
	public double getValue(int x, int y);
	
	public Neuron getNeuronAt(int x, int y);
	
	public int getXSize();
	public int getYSize();
	
	public ConvolutionalFilter getRectangle(int xStart, int yStart, int xEnd, int yEnd);
	public void update();
	public void calculate();
	
	
	
}
