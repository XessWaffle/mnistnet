package core.factory;

import activation.ExponentialLinearUnits;
import activation.Gaussian;
import activation.HyperbolicTangent;
import activation.Linear;
import activation.RectifiedLinearUnits;
import activation.Sigmoid;
import corefunc.Activation;
import corefunc.ActivationType;

public class ActivationFactory {
	public static Activation create(ActivationType at) {
		if(at == ActivationType.GAUSSIAN) {
			return new Gaussian();
		} else if(at == ActivationType.HYPERBOLIC_TANGENT) {
			return new HyperbolicTangent();
 		} else if(at == ActivationType.ELU){
 			return new ExponentialLinearUnits();
 		} else if(at == ActivationType.LINEAR) {
 			return new Linear();
 		} else if(at == ActivationType.ReLU) {
 			return new RectifiedLinearUnits();
 		} else {
 			return new Sigmoid();
 		}
	}
}
