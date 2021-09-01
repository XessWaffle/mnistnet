package error;



import corefunc.Error;

public class MeanSquaredError implements Error{
	
	public MeanSquaredError() {}
	
	@Override
	public double compute(double[] input, double[] preferredValues) {
		// TODO Auto-generated method stub
		
		double sum = 0.0;
		
		for(int i = 0; i < input.length; i++) {
			sum += preferredValues[i] - input[i];
		}
		
		return 0.5 * (Math.pow(sum, 2));
	}

	@Override
	public double[] derive(double[] input, double[] preferredValues) {
		// TODO Auto-generated method stub
		double[] ret = new double[input.length];
		
		for(int i = 0; i < input.length; i++) {
			ret[i] = input[i] - preferredValues[i];
		}
		
		return ret;
	}
}
