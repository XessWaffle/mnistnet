package corefunc;

public interface Error {
	
	public double compute(double[] input, double[] preferredValues);
	
	public double[] derive(double[] input, double[] preferredValues);

	
}
