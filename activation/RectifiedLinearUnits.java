package activation;

import corefunc.Activation;

public class RectifiedLinearUnits implements Activation{
	
	/** 
	 * Used with CNN: Leaky ReLU, Randomized Leaky ReLU, Parameterized ReLU
	 * Exponential Linear Units (ELU), Scaled Exponential Linear Units
	 * Tanh, hardtanh, softtanh, softsign, softmax, softplus
	 **/
	
	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		return Math.max(0, input);
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		return input > 0 ? 0 : 1;
	} 

}
