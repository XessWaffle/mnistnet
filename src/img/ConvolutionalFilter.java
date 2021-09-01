package img;


public class ConvolutionalFilter{
	private double[][] filter;
	private int xZeroPadding;
	private int yZeroPadding;
	
	private int xSize, ySize;
	
	public ConvolutionalFilter(int xSize, int ySize) {
		filter = new double[xSize][ySize];
		
		// NEED TO CHANGE: ASSUMES THAT FILTER IS SQUARE
		setXZeroPadding((xSize - 1)/2);
		setYZeroPadding((ySize - 1)/2);
		
		this.xSize = xSize;
		this.ySize = ySize;
		
		
		initialize();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				filter[i][j] = Math.random() - 0.5;
			}
		}
	}

	public ConvolutionalFilter(double fill, int xSize, int ySize) {
		filter = new double[xSize][ySize];
		
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				filter[i][j] = fill;
			}
		}
		
		this.xSize = xSize;
		this.ySize = ySize;
		
		setXZeroPadding((xSize - 1)/2);
		setYZeroPadding((ySize - 1)/2);
	}
	
	public double[][] getFilter() {
		return filter;
	}

	public void setFilter(double[][] filter) {
		this.filter = filter;
	}
	
	public void setFilterValue(double value, int x, int y) {
		this.filter[x][y] = value;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
	}
	
	public int getXZeroPadding() {
		return xZeroPadding;
	}

	public void setXZeroPadding(int xZeroPadding) {
		this.xZeroPadding = xZeroPadding;
	}

	public int getYZeroPadding() {
		return yZeroPadding;
	}

	public void setYZeroPadding(int yZeroPadding) {
		this.yZeroPadding = yZeroPadding;
	}

	public double dot(ConvolutionalFilter next) throws IllegalArgumentException{
		
		double answer = 0;
		
		if(this.getXSize() == next.getXSize() && this.getYSize() == next.getYSize()) {
			for(int i = 0; i < this.getXSize(); i++) {
				for(int j = 0; j < this.getYSize(); j++) {
					answer += next.filter[i][j] * this.filter[i][j];
					
					//System.out.println(next.filter[i][j] + " * " + this.filter[i][j]);
					
				}
			}
			
			return answer;
		} else {throw new IllegalArgumentException();}
	}
	
	public ConvolutionalFilter sum(ConvolutionalFilter... cfs) throws IllegalArgumentException, CloneNotSupportedException{
		
		ConvolutionalFilter sum = (ConvolutionalFilter) this.clone();
		
		for(ConvolutionalFilter cf: cfs) {
			if(cf.getXSize() == this.getXSize() && cf.getYSize() == this.getYSize()) {
				for(int i = 0; i < this.getXSize(); i++) {
					for(int j = 0; j < this.getYSize(); j++) {
						sum.filter[i][j] += cf.getFilter()[i][j];
					}
				}
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		
		return sum;
		
	}
	
	public void add(ConvolutionalFilter... cfs) throws IllegalArgumentException{
		
		for(ConvolutionalFilter cf: cfs) {
			
			System.out.println(cf.getXSize() + ", " + this.getXSize() + "; " + cf.getYSize() + ", " + this.getYSize());
			
			if(cf.getXSize() == this.getXSize() && cf.getYSize() == this.getYSize()) {
				for(int i = 0; i < this.getXSize(); i++) {
					for(int j = 0; j < this.getYSize(); j++) {
						this.filter[i][j] += cf.getFilter()[i][j];
					}
				}
			} else {
				throw new IllegalArgumentException();
			}
		}

	}
	
	public String toString() {
		
		String ret = "[\n";
		
		for(int i = 0; i < this.getXSize(); i++) {
			
			for(int j = 0; j < this.getYSize(); j++) {
				ret += this.filter[i][j] + " ";
			}
			ret += "\n";
		}
		
		ret += "]";
		
		return ret;
		
	}
}
