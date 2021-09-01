package layer.conv.block;

import core.Neuron;
import layer.conv.ConvolutionalLayer;

public class ConvolutionalBlock extends Block{
	
	private int filterX, filterY;
	
	public ConvolutionalBlock(int numFilters, int filterX, int filterY, Block prevBlock) {
		super(numFilters, prevBlock.getWidth(), prevBlock.getHeight(), prevBlock);
		
		this.block = new ConvolutionalLayer[numFilters];
		
		System.out.println("Creating CBL " + numBlocks + ": " + "(" + this.getWidth() + ", " + this.getHeight() + ", " + this.getDepth() + ")");
		
		numBlocks++;
		
		this.filterX = filterX;
		this.filterY = filterY;
		
		initialize();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.getNumFilters(); i++) {
			this.block[i] = new ConvolutionalLayer(this.getWidth(), this.getHeight(), filterX, filterY, this.prevBlock.getDepth());
			this.addAllNeurons(((ConvolutionalLayer)this.block[i]).getAllNeurons());
			
			System.out.println("Neuron Count: " + this.getAllNeurons().size());
			
		}
	}

	public Neuron getNeuronAt(int x, int y, int z) {
		return ((ConvolutionalLayer)this.block[z]).getNeuronAt(x, y);
	}
	
	public int getFilterX() {
		return filterX;
	}
	
	public int getFilterY() {
		return filterY;
	}
	
	public void calculate() {
		for(ConvolutionalLayer cl: (ConvolutionalLayer[]) this.getBlock()) {
			cl.calculate();
		}
	}
	
	public String toString() {
		return "CBL: " + "(" + this.getWidth() + ", " + this.getHeight() + ", " + this.getDepth() + ")" + ", " + "(fX: " + this.getFilterX() + ", " + "fY: " + this.getFilterY() + ")";

	}
}
