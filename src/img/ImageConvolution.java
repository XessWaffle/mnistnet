package img;

public class ImageConvolution {
	
	private int stride;
	
	private ImageFilter toConv;
	
	public ImageConvolution(int stride) {
		this.stride = stride;
	}

	public int getStride() {
		return stride;
	}

	public void setStride(int stride) {
		this.stride = stride;
	}
	
	public ImageFilter getFilter() {
		return toConv;
	}

	public void setFilter(ImageFilter toConv) {
		this.toConv = toConv;
	}
	
	public ImageFilter convolute(ConvolutionalFilter cf) throws IllegalArgumentException{
		if(toConv.hasValues()) {
			//System.out.println(toConv.getXTiles());
			
			int xTiles = (int)((toConv.getXSize() - cf.getXSize() + 2 * cf.getXZeroPadding())/this.getStride()) + 1;
			int yTiles = (int)((toConv.getYSize() - cf.getYSize() + 2 * cf.getYZeroPadding())/this.getStride()) + 1;
			
			ImageFilter convFilt = new ImageFilter(xTiles, yTiles);
			
			try {
				for(int i = 0; i < convFilt.getXSize(); i++) {
					for(int j = 0; j < convFilt.getYSize(); j++) {
						ConvolutionalFilter imgRect = toConv.getRectangle(i - cf.getXZeroPadding(), j - cf.getYZeroPadding(), i + cf.getXZeroPadding(), j + cf.getYZeroPadding());
						
						double dot = cf.dot(imgRect);
					
						convFilt.updateNext(dot);
					}
				}
			} catch(IllegalArgumentException e) {
				e.printStackTrace();
				System.out.println("Convolutional Filter Failure");
				throw new IllegalArgumentException();
			}
			
			return convFilt;
			
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	
}
