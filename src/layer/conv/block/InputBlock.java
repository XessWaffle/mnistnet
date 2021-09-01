package layer.conv.block;

import layer.conv.InputLayer;

public class InputBlock extends Block{

	public InputBlock(int numFilters, int width, int height, Block prevBlock) {
		super(numFilters, width, height, prevBlock);
		// TODO Auto-generated constructor stub
		
		System.out.println("Creating IPL " + numBlocks + ": " + "(" + this.getWidth() + ", " + this.getHeight() + ", " + this.getDepth() + ")");
		
		this.block = new InputLayer[this.getDepth()];
		
		numBlocks++;
		
		initialize();
		
	}

	private void initialize() {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.getDepth(); i++) {
			this.block[i] = new InputLayer(this.getWidth(), this.getHeight());
			this.addAllNeurons(((InputLayer)this.block[i]).getAllNeurons());
			
			System.out.println("Neuron Count: " + this.getAllNeurons().size());
		}
	}
	
	public String toString() {
		return "IPL: " + "(" + this.getWidth() + ", " + this.getHeight() + ", " + this.getDepth() + ")";
	}
	
	

}
