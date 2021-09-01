package neuron;

import activation.Linear;
import core.NeuralLink;
import core.Neuron;
import corefunc.UpdateState;

public class PoolingNeuron extends Neuron{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PoolingNeuron() {
		super(new Linear());
	}
	
	public void calculate() {
		this.forwardPropTotalInput = Integer.MIN_VALUE;
		
		for(NeuralLink forward: this.getForwardPropConnections()) {
			if(forward.getFromNeuron().isActive()) {
				double toCheck = forward.getWeight() * forward.getFromOutput();
				
				if(toCheck > this.forwardPropTotalInput) {
					this.forwardPropTotalInput = toCheck;
				}
			}
		}
		
		this.forwardPropOutput = function.compute(forwardPropTotalInput);
		
		this.setActive(true);
	}
	
	public void adjust() {
		biasUpdates = 0;
		numUpdates = 0;
		
		for(NeuralLink in: this.forwardPropNeurons) {
			in.adjust();
			in.setState(UpdateState.TO_UPDATE);
		}
		
		this.setActive(false);
		this.state = UpdateState.TO_UPDATE;
	}

	
}
