package activation;

import corefunc.Activation;

public class ExponentialLinearUnits implements Activation{

	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		return input > 0 ? input : (Math.exp(input) - 1);
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		return input > 0 ? 1.0 : input;
	}
	
}
