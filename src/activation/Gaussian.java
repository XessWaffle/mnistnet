package activation;

import corefunc.Activation;

public class Gaussian implements Activation{

	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		return Math.exp(-1 * Math.pow(input, 2));
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		return -2 * (input) * this.compute(input);
	}

}
