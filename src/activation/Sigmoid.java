package activation;

import corefunc.Activation;

public class Sigmoid implements Activation{

	
	public Sigmoid() {}
	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		
		//System.out.println("Exp:" + (1.0/(1 + Math.exp(-1 * input))));
		
		return (1.0/(1 + Math.exp(-1 * input)));
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		return input * (1.0 - input);
	}

}
