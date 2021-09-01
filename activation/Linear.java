package activation;

import corefunc.Activation;

public class Linear implements Activation{

	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		return input;
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
