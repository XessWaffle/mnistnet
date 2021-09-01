package img;


public class ImageFilter {
	
	private double[][] filter;
	
	private int xPos = 0, yPos = 0;
	private int xTiles, yTiles;
	
	public ImageFilter(int xSize, int ySize) {
		this.setFilter(new double[xSize][ySize]);
		
		xTiles = xSize;
		yTiles = ySize;
	}
	
	public ImageFilter() {}

	public double[][] getFilter() {
		return filter;
	}

	public void setFilter(double[][] filter) {
		this.filter = filter;
	}

	public boolean hasValues() {
		return xPos != 0;
	}
	
	
	public int getXSize() {
		return xTiles;
	}

	public void setXTiles(int xTiles) {
		this.xTiles = xTiles;
	}
	
	
	public int getYSize() {
		return yTiles;
	}

	public void setYTiles(int yTiles) {
		this.yTiles = yTiles;
	}
	
	public double getValue(int x, int y) {
		return filter[x][y];
	}
	
	public ConvolutionalFilter getRectangle(int xStart, int yStart, int xEnd, int yEnd){
		int sx = xEnd - xStart + 1;
		int sy = yEnd - yStart + 1;
		
		double[][] ret = new double[sx][sy];
		
		int xT = xStart, yT = yStart;
		
		for(int i = 0; i < sx; i++) {
			for(int j = 0; j < sy; j++) {
				
				if(xT >= 0 && xT < filter.length && yT >= 0 && yT < filter[0].length) {
					ret[i][j] = filter[xT][yT];
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
	
	public int updateNext(double toWrite) {
		if(xPos >= filter.length && yPos >= filter[0].length) {
			return -1;
		} 
		
		if(yPos > filter[xPos].length - 1) {
			yPos = 0;
			xPos++;
		}
		
		filter[xPos][yPos++] = toWrite;
		
		return 0;
		
	}

	
	
}
