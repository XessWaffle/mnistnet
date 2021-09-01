package core;

import java.util.ArrayList;

import core.factory.ActivationFactory;
import corefunc.Activation;
import corefunc.ActivationType;

public class NeuralLayer {
	
	/**
	 * Used to set the label.
	 */
	private static int labelSetter = 0;
	
	/**
	 * The list of neurons that form the layer.
	 */
	protected ArrayList<Neuron> neurons;
	
	/**
	 * Name of the layer.
	 */
	protected String label;
	
	protected Activation nAct = ActivationFactory.create(ActivationType.SIGMOID);
	
	public NeuralLayer() {
		neurons = new ArrayList<>();
		
		label = "Layer" + ++labelSetter;
	}
	
	public NeuralLayer(ArrayList<Neuron> neurons) {
		this.neurons = neurons;
		label = "Layer" + ++labelSetter;
	}
	
	public NeuralLayer(Activation act) {
		neurons = new ArrayList<>();
		label = "Layer" + ++labelSetter;
		
		this.nAct = act;
		
	}
	
	public void addNeuron(Neuron n){
		neurons.add(n);
	}
	
	public void addNeuron(int numNeurons) {
		for(int i = 0; i < numNeurons; i++) {
			neurons.add(new Neuron(nAct));
		}
	}
	
	public void addAllNeurons(ArrayList<Neuron> neurons) {
		this.neurons.addAll(neurons);
	}
	
	public void calculate() {
		for(Neuron n: neurons) {
			n.calculate();
		}
	}
	
	public Neuron getNeuronAt(int index) {
		return neurons.get(index);
	}
	
	public ArrayList<Neuron> getAllNeurons() {
		return neurons;
	}
	
	public Neuron getFirstNeuron() {
		return this.getNeuronAt(0);
	}
	
	public Neuron getLastNeuron() {
		return this.getNeuronAt(neurons.size() - 1);
	}
	
	public String toString() {
		return "LAYER";
	}
}
