package neuron;

import core.Neuron;

public class InputOutputNeuron extends Neuron {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean actingAsInput = true;
	
	public InputOutputNeuron() {
		super();
	}
	
	public void calculate() {
		if(this.isActingAsInput()) {
			this.setActive(true);
			this.setActingAsInput(false);
		} else {
			super.calculate();
			this.setActingAsInput(true);
		}
	}

	public boolean isActingAsInput() {
		return actingAsInput;
	}

	public void setActingAsInput(boolean actingAsInput) {
		this.actingAsInput = actingAsInput;
	}

}
