package img;

public class ImagePool {
	
	private ImageFilter toPool;
	private int xSize;
	private int ySize;
	
	public ImagePool(ImageFilter toPool, int xSize, int ySize) {
		this.toPool = toPool;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public ImageFilter getFilter() {
		return toPool;
	}

	public void setFilter(ImageFilter toPool) {
		this.toPool = toPool;
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
	
	public ImageFilter pool() {
		
		int nXs = toPool.getXSize()/this.xSize;
		int nYs = toPool.getYSize()/this.ySize;
		
		ImageFilter pooled = new ImageFilter(nXs, nYs);
		
		for(int i = 0; i < toPool.getXSize(); i+=this.xSize) {
			for(int j = 0; j < toPool.getYSize(); j+=this.ySize) {
				//Calculates average of pool rectangle
				//May need to update to reflect maxpool
				pooled.updateNext((toPool.getRectangle(i, j, i + xSize, j + ySize).dot(new ConvolutionalFilter(this.xSize, this.ySize)))/(this.xSize * this.ySize));
			}
		}
		
		return pooled;
	}
	
}
