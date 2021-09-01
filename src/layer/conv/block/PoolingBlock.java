package layer.conv.block;

import core.Neuron;
import layer.conv.PoolingLayer;

public class PoolingBlock extends Block {
	
	private int strideSize;
	
	public PoolingBlock(int strideSize, Block prevBlock) {
		//numFilters is arbitrary for pooling but necessary for depth
		super(prevBlock.getDepth(), prevBlock.getWidth()/strideSize, prevBlock.getHeight()/strideSize, prevBlock);
		
		this.block = new PoolingLayer[this.getDepth()];
		this.strideSize = strideSize;
		
		System.out.println("Creating PBL " + numBlocks + ": " + "(" + this.getWidth() + ", " + this.getHeight() + ", " + this.getDepth() + ")");
		
		numBlocks++;
		
		initialize();
	
	}

	private void initialize() {
		// TODO Auto-generated method stub
		
		int xSize = (int)(prevBlock.getWidth()/strideSize);
		int ySize = (int)(prevBlock.getHeight()/strideSize);
		
		for(int z = 0; z < this.getDepth(); z++) {
			this.block[z] = new PoolingLayer(xSize, ySize, strideSize);
			this.addAllNeurons(((PoolingLayer)this.block[z]).getAllNeurons());
			
			System.out.println("Neuron Count: " + this.getAllNeurons().size());
			
		}
	}
	
	public Neuron getNeuronAt(int x, int y, int z) {
		return ((PoolingLayer)this.block[z]).getNeuronAt(x, y);
	}
	
	public void calculate() {
		for(PoolingLayer pl: (PoolingLayer[]) this.getBlock()) {
			pl.calculate();
		}
	}
	
	public String toString() {
		return "PBL: " + "(" + this.getWidth() + ", " + this.getHeight() + ", " + this.getDepth() + ")";
	}
}
