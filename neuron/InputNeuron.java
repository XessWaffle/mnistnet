package neuron;

import core.Neuron;

public class InputNeuron extends Neuron{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3941979150936586627L;

	public InputNeuron() {
		super();
	}
	
	public void calculate() {
		this.setActive(true);
	}
	
}
