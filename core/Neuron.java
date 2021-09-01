package core;

import java.io.Serializable;
import java.util.ArrayList;

import core.factory.ActivationFactory;
import corefunc.Activation;
import corefunc.ActivationType;
import corefunc.UpdateState;

public class Neuron implements Serializable{
	
	/**
	 * The serial ID for writing to files.
	 */
	private static final long serialVersionUID = -1207656982028030308L;

	/**
	 * Used to set the label depending on the number of neurons. 
	 */
	private static int labelSetter = 0;
	
	/**
	 * The total input to this neuron when calculating.
	 * Usually calculated by the weighted sum of the previous layer's neurons.
	 */
	protected transient double forwardPropTotalInput;
	
	/**
	 * The total output of this neuron, calculated with its activation, or transfer, function.
	 */
	protected transient double forwardPropOutput;
	
	/**
	 * The local error of this neuron depending on the total error of the network.
	 */
	protected double delta;
	
	/**
	 * The local bias of this neuron shifting the activation to the right and left..
	 */
	protected double bias;
	
	/**
	 * The list of changes to the bias of the neuron which are then averaged out.
	 */
	protected double biasUpdates;
	protected int numUpdates;
	
	/**
	 * The activation or transfer function of the neuron.
	 */
	protected Activation function;
	
	/**
	 * The neurons that this neuron reads to calculate its final output.
	 */
	protected ArrayList<NeuralLink> forwardPropNeurons;
	
	/**
	 * A set of arbitrary connections that link the network both backwards and forwards.
	 */
	protected ArrayList<NeuralLink> backPropNeurons;
	
	/**
	 * Determines the update state of the neuron
	 */
	protected UpdateState state;
	
	/**
	 * The neuron's name.
	 */
	protected String label;
	
	/**
	 * The boolean that determines activity or not
	 */
	private boolean isActive;
	
	public Neuron() {
		this.function = ActivationFactory.create(ActivationType.HYPERBOLIC_TANGENT);
		
		forwardPropNeurons = new ArrayList<>();
		backPropNeurons = new ArrayList<>();
		
		label = "Neuron " + ++labelSetter;
		

		this.bias = Math.random() - 0.5;
	}
	
	public Neuron(Activation e) {
		this.function = e;
		
		forwardPropNeurons = new ArrayList<>();
		backPropNeurons = new ArrayList<>();
	

		label = "Neuron " + ++labelSetter;
		

		this.bias = Math.random() - 0.5;
	}
	
	public void inputConnect(Neuron from, boolean fixed) {
		forwardPropNeurons.add(new NeuralLink(from, this, fixed));
	}
	
	public void outputConnect(Neuron to, boolean fixed) {
		backPropNeurons.add(new NeuralLink(this, to, fixed));
	}
	
	public double getNetInput() {
		return this.forwardPropTotalInput;
	}
	
	public double getInactiveNetInput() {
		double sum = 0.0;
		
		for(NeuralLink forward: this.getForwardPropConnections()) {
			sum += forward.getWeight() * forward.getFromOutput(); 
		}
		
		return sum;
	}
	
	public double getNetOutput() {
		return this.forwardPropOutput;
	}
	
	public void setNetOutput(double out) {
		this.forwardPropOutput = out;
	}
	
	public double getDeltaError() {
		return delta;
	}
	
	public void setDeltaError(double delta) {
		this.delta = delta;
	}
	
	public double getBias() {
		return this.bias;
	}
	
	public void setBias(double bias) {
		this.bias = bias;
	}
	
	public ArrayList<NeuralLink> getBackPropConnections(){
		return this.backPropNeurons;
	}
	
	public ArrayList<NeuralLink> getForwardPropConnections(){
		return this.forwardPropNeurons;
	}
	
	public static void resetLabel() {
		labelSetter = 0;
	}
	
	public NeuralLink findConnectionTo(Neuron n) {
		for(NeuralLink forwardLinkTo: n.getForwardPropConnections()) {
			if(Neuron.equals(forwardLinkTo.getFromNeuron(), this)) {
				return forwardLinkTo;
			}
		}
		
		return null;
	}
	
	public NeuralLink findConnectionFrom(Neuron n) {
		for(NeuralLink forwardLinkTo: this.getForwardPropConnections()) {
			if(Neuron.equals(forwardLinkTo.getFromNeuron(), n)) {
				return forwardLinkTo;
			}
		}
		
		return null;
	}
	
	public boolean hasConnectionFrom(Neuron n) {
		for(NeuralLink forwardLinkTo: this.getForwardPropConnections()) {
			if(Neuron.equals(forwardLinkTo.getFromNeuron(), n)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasConnectionTo(Neuron n) {
		for(NeuralLink forwardLinkTo: this.getForwardPropConnections()) {
			if(Neuron.equals(forwardLinkTo.getFromNeuron(), n)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasInputConnections() {
		return !this.forwardPropNeurons.isEmpty();
	}
	
	public boolean hasOutputConnections() {
		return !this.backPropNeurons.isEmpty();
	}
	
	public Activation getFunction() {
		return this.function;
	}
	
	public void setFunction(Activation a) {
		// TODO Auto-generated method stub
		this.function = a;
	}
	
	
	public void calculate() {
		this.forwardPropTotalInput = 0.0;
		
		for(NeuralLink forward: this.getForwardPropConnections()) {
			
			if(forward.getFromNeuron() != null && forward.getFromNeuron().isActive())
				forwardPropTotalInput += forward.getWeight() * forward.getFromOutput(); 
		}
		
		this.forwardPropOutput = function.compute(forwardPropTotalInput + bias);
		
		this.setActive(true);
	}
	
	public void pushBiasAdjustment(double biasAdjustment) {
		biasUpdates += biasAdjustment;
		numUpdates++;
	}
	
	public void adjust() {
		bias += biasUpdates/(double)numUpdates;
		
		for(NeuralLink in: this.forwardPropNeurons) {
			in.adjust();
			in.setState(UpdateState.TO_UPDATE);
		}
		
		biasUpdates = 0;
		numUpdates = 0;
		
		this.setActive(false);
		
		state = UpdateState.TO_UPDATE;
		
	}
	
	public static boolean equals(Neuron one, Neuron two) {
		
		if(one != null && two != null) 
			return (one.label).equals(two.label);
		
		return false;
	
	}
	
	public static int getNumNeurons() {
		return labelSetter;
	}

	public String getLabel() {
		return this.label;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UpdateState getState() {
		return state;
	}

	public void setState(UpdateState state) {
		this.state = state;
	}


}
