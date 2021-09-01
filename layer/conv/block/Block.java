package layer.conv.block;

import core.NeuralLayer;
import corefunc.Convolutable;

public class Block extends NeuralLayer{
	
	public static int numBlocks = 0;
	
	protected Convolutable[] block;
	protected Block prevBlock;

	private int numFilters;
	
	private int width, height, depth;
	
	public Block(int numFilters, int width, int height, Block prevBlock) {
		this.setNumFilters(numFilters);
		
		this.setDepth(numFilters);
		this.setWidth(width);
		this.setHeight(height);
		
		this.prevBlock = prevBlock;
	}
	
	public Block getPrevBlock() {
		return prevBlock;
	}
	
	public double getValueAt(int x, int y, int z) {
		return block[z].getValue(x, y);
	}

	public Convolutable[] getBlock() {
		return block;
	}

	public void setBlock(Convolutable[] block) {
		this.block = block;
	}

	public int getNumFilters() {
		return numFilters;
	}

	public void setNumFilters(int numFilters) {
		this.numFilters = numFilters;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public void update() {
		for(Convolutable obj: block) {
			obj.update();
		}
	}
	
	public void calculate() {
		for(Convolutable obj: block) {
			obj.calculate();
		}
	}
	
	public String toString() {
		return "BLOCK";
	}
}
