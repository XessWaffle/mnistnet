package core;

import corefunc.UpdateState;

public class NeuralLink {
	
	/**
	 * Used to set the label of the link by number of links.
	 */
	private static int labelSetter = 0;
	
	/**
	 * The weight of the connection between the neurons.
	 */
	protected double weight;
	
	/**
	 * The neuron the connection is traveling to.
	 */
	protected Neuron to;
	
	/**
	 * The neuron the connection has been initiated from.
	 */
	protected Neuron from;
	
	/**
	 * Identifies pure connections rather than learned values
	 */
	protected boolean isFixed;
	
	/**
	 * The pushed weights to the neuron
	 */
	protected double weightUpdates;
	protected int numUpdates = 0;
	
	/**
	 * Determines the update state of the neuron
	 */
	protected UpdateState state;
	
	/**
	 * The name of the link
	 */
	public String label;

	public NeuralLink(Neuron from, Neuron to, boolean fixed) {
		this.from = from;
		this.to= to;
		
		this.label = "Link" + ++labelSetter;
		
		if(!fixed) {
			this.weight = Math.random() - 0.5;
		} else {
			this.weight = 1;
		}
		
		this.isFixed = fixed;
	}
	
	public Neuron getFromNeuron() {
		return this.from;
	}
	
	public void setFromNeuron(Neuron from) {
		this.from = from;
	}
	
	public Neuron getToNeuron() {
		return this.to;
	}
	
	public void setToNeuron(Neuron to) {
		this.to = to;
	}
	
	public double getFromOutput() {
		return from == null ? 0 : from.getNetOutput();
	}
	
	public double getToOutput() {
		return to == null ? 0 : to.getNetOutput();
	}
	
	public UpdateState getState() {
		return state;
	}

	public void setState(UpdateState state) {
		this.state = state;
	}
	
	public double getWeight() {		
		return this.isFixed ? 1.0 : this.weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public void pushWeightAdjustment(double weightAdjustment) {
		weightUpdates += weightAdjustment;
		numUpdates++;
	}
	
	public double getEpochWeightUpdates() {	
		double ans = weightUpdates;
		
		this.weightUpdates = 0;
		this.numUpdates = 0;
		
		return ans;
	}
	
	public int getNumUpdates() {
		return numUpdates;
	}
	
	public boolean isFixed() {
		return isFixed;
	}
	
	public void adjust() {
		if(!this.isFixed) {
			this.weight += this.weightUpdates / (double)this.numUpdates;
			
			this.weightUpdates = 0;
			this.numUpdates = 0;
		}
	}
	
	public static int getNumConnections() {
		return labelSetter;
	}

	

}
