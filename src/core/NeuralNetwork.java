package core;

import java.util.ArrayList;
import corefunc.Error;
import corefunc.LayerType;
import corefunc.Learning;
import error.MeanSquaredError;
import learning.Backpropagation;

public abstract class NeuralNetwork {
	
	/**
	 * Used to set the name for the network.
	 */
	private static int labelSetter = 0;
	
	/**
	 * All hidden layers, user specified, of the neural network.
	 */
	protected ArrayList<NeuralLayer> hiddenLayers;
	
	/**
	 * The list of input neurons to this network, in accordance with the type of dataset.
	 */
	protected NeuralLayer input;
	
	/**
	 * The list of outputs, user specified, that the network will have.
	 */
	protected NeuralLayer output;
	
	/**
	 * The error calculation to train the network.
	 */
	protected Error calc;
	
	/**
	 * The learning this network will follow
	 */
	protected Learning rule;
	
	/**
	 * The buffer that stores the output values of the network.
	 */
	protected double[] outputBuffer;
	
	/**
	 * The network name.
	 */
	protected String label;
	
	protected int epoch = 0;
	
	protected double error;

	public NeuralNetwork() {
		this.label = "Network " + ++labelSetter;
		
		this.hiddenLayers = new ArrayList<>();
		this.input = new NeuralLayer();
		this.output = new NeuralLayer();
		
		this.calc = new MeanSquaredError();
		this.rule = new Backpropagation(this);
	}
	
	
	public void initalize() {
		
	}

	public abstract void connect();
	
	public abstract void run();

	public void update() {
		// TODO Auto-generated method stub
		this.rule.adjust();
		
		this.epoch++;
	}

	public ArrayList<NeuralLayer> getHiddenLayers() {
		return hiddenLayers;
	}

	public void setHiddenLayers(ArrayList<NeuralLayer> hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}
	
	public void addHiddenLayer(NeuralLayer nl) {
		this.hiddenLayers.add(nl);
	}

	public NeuralLayer getInputs() {
		return input;
	}

	public void setInput(NeuralLayer input) {
		this.input = input;
	}
	
	public void addInput(Neuron toAdd) {
		this.input.addNeuron(toAdd);
	}

	public NeuralLayer getOutputs() {
		return output;
	}

	public void setOutput(NeuralLayer output) {
		this.output = output;
	}
	
	public void addOutput(Neuron toAdd) {
		this.output.addNeuron(toAdd);
	}
	
	public ArrayList<NeuralLayer> getNetwork(){
		ArrayList<NeuralLayer> toRet = new ArrayList<>();
		
		toRet.add(this.getInputs());
		toRet.addAll(this.getHiddenLayers());
		
		
		return toRet;
	}
	
	public NeuralLayer getLastLayer() {
		return this.getNetwork().get(this.getNetwork().size() - 1);
	}
	
	public void createHiddenLayer(LayerType lt) {
		
	}
	
	public void createInputLayer(LayerType lt) {
		
	}
	
	public void createOutputLayer(LayerType lt) {
		
	}
	

	public Error getCalc() {
		return calc;
	}

	public void setCalc(Error calc) {
		this.calc = calc;
	}

	public Learning getRule() {
		return rule;
	}

	public void setRule(Learning rule) {
		this.rule = rule;
	}

	public double[] getOutputBuffer() {
		return outputBuffer;
	}

	public void setOutputBuffer(double[] outputBuffer) {
		this.outputBuffer = outputBuffer;
	}

	public String getName() {
		return label;
	}

	public void setName(String label) {
		this.label = label;
	}

	public double getError() {
		return error;
	}


	public void setError(double error) {
		this.error = error;
	}
	
}
