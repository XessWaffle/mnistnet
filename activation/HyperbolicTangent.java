package activation;

import corefunc.Activation;

public class HyperbolicTangent implements Activation{
	
	public HyperbolicTangent() {}
	
	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		return Math.tanh(input);
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		return 1.0 - (Math.pow(input, 2.0));
	}

}
