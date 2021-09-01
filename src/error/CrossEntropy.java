package error;

import corefunc.Error;

public class CrossEntropy implements Error{

	@Override
	public double compute(double[] input, double[] preferredValues) {
		// TODO Auto-generated method stub
		double sum = 0;
		
		for(int i = 0; i < input.length; i++) {
			sum += preferredValues[i] * Math.log(input[i]);
		}
		
		return -1 * sum;
	}

	@Override
	public double[] derive(double[] input, double[] preferredValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
