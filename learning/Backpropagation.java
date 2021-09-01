	package learning;

import java.util.ArrayList;

import activation.Gaussian;
import activation.Softmax;
import core.NeuralLayer;
import core.NeuralLink;
import core.NeuralNetwork;
import core.Neuron;
import neuron.InputNeuron;
import neuron.InputOutputNeuron;
import corefunc.Learning;
import corefunc.UpdateState;


public class Backpropagation implements Learning{
	
	private static final double BIAS_LEARNING_RATE = 0.05;
	private static final double WEIGHT_LEARNING_RATE = 0.05;
	/**
	 * The neuron that this learning format is assigned to
	 */
	private ArrayList<NeuralLayer> all = new ArrayList<NeuralLayer>();
	
	protected NeuralNetwork teaching;
	
	public Backpropagation(NeuralNetwork neuralNetwork) {
		this.teaching = neuralNetwork;
		
		all.add(this.teaching.getInputs());
		all.addAll(this.teaching.getHiddenLayers());
		all.add(this.teaching.getOutputs());
	}
	
	public void setNetwork(ArrayList<NeuralLayer> all) {
		this.all = all;
	}

	@Override
	public void computeAndPush(double[] preferredValues) {
		
		int inputErrorLoc = 0;
		
		for(Neuron output: all.get(all.size() - 1).getAllNeurons()) {
			if(output.getFunction() instanceof Gaussian || output.getFunction() instanceof Softmax) {
				
				System.out.println(output.getFunction().getClass());
				
				if(preferredValues[inputErrorLoc] == 0.0) {
					output.setDeltaError(-1 * (preferredValues[inputErrorLoc++] - output.getNetOutput()) * output.getFunction().derive(output.getInactiveNetInput()));	
				} else {	
					output.setDeltaError(-1 * (preferredValues[inputErrorLoc++] - output.getNetOutput()) * output.getFunction().derive(output.getInactiveNetInput()));
				}
			} else {
				if(preferredValues[inputErrorLoc] == 0.0) {
					output.setDeltaError(-1 * (preferredValues[inputErrorLoc++] - output.getNetOutput()) * output.getFunction().derive(output.getNetOutput()));	
				} else {	
					output.setDeltaError(-1 * (preferredValues[inputErrorLoc++] - output.getNetOutput()) * output.getFunction().derive(output.getNetOutput()));
				}
			}
		}
		
		for(int hLayer = all.size() - 2; hLayer >= 0; hLayer--) {
			for(Neuron hidden: all.get(hLayer).getAllNeurons()) {
				
				double delta = 0.0;
				
				for(NeuralLink linFront: hidden.getBackPropConnections()) {
					delta += linFront.getToNeuron().getDeltaError() * hidden.findConnectionTo(linFront.getToNeuron()).getWeight();
				}
				
				delta *= hidden.getFunction().derive(hidden.getNetOutput());
				
				hidden.setDeltaError(delta);
				
			}
		}
		
		for(Neuron output: all.get(all.size() - 1).getAllNeurons()) {
			recursiveCompute(output);
		}
	}
	
	public void computeAndPush(double error) {
		
		for(Neuron output: all.get(all.size() - 1).getAllNeurons()) {
			if(output.getFunction() instanceof Gaussian || output.getFunction() instanceof Softmax) {
				output.setDeltaError(-1 * (error - output.getNetOutput()) * output.getFunction().derive(output.getInactiveNetInput()));	
			} else {
				output.setDeltaError(-1 * (error - output.getNetOutput()) * output.getFunction().derive(output.getNetOutput()));	
			}
		}
		
		for(int hLayer = all.size() - 2; hLayer >= 0; hLayer--) {
			for(Neuron hidden: all.get(hLayer).getAllNeurons()) {
				
				double delta = 0.0;
				
				for(NeuralLink linFront: hidden.getBackPropConnections()) {
					delta += linFront.getToNeuron().getDeltaError() * hidden.findConnectionTo(linFront.getToNeuron()).getWeight();
				}
				
				delta *= hidden.getFunction().derive(hidden.getNetOutput());
				
				hidden.setDeltaError(delta);
				
			}
		}
		
		for(Neuron output: all.get(all.size() - 1).getAllNeurons()) {
			recursiveCompute(output);
		}
	}

	private void recursiveCompute(Neuron toCompute) {
		// TODO Auto-generated method stub
		
		
		if(toCompute == null) {
			return;
		}
		
		if(toCompute.getState() == UpdateState.UPDATED) {
			return;
		}
		
		toCompute.pushBiasAdjustment(toCompute.getDeltaError() * -1 * BIAS_LEARNING_RATE * teaching.getError());
		
		toCompute.setState(UpdateState.UPDATED);
		
		
		if(toCompute instanceof InputNeuron || toCompute instanceof InputOutputNeuron) {		
			return;
		}
		
		for(NeuralLink forProp: toCompute.getForwardPropConnections()) {
			
			if(forProp.getState() != UpdateState.UPDATED && !forProp.isFixed()) {
				double wAdj = forProp.getFromOutput() * -1 * WEIGHT_LEARNING_RATE * toCompute.getDeltaError() * teaching.getError();
				
				forProp.pushWeightAdjustment(wAdj);
				
				forProp.setState(UpdateState.UPDATED);
			}
				
			recursiveCompute(forProp.getFromNeuron());
		}
		
	}

	@Override
	public void adjust() {
		// TODO Auto-generated method stub
		
		for(int i = all.size() - 1; i >= 0; i--) {
			for(Neuron n : all.get(i).getAllNeurons()) {
				
				if(n.isActive() && n.getState() == UpdateState.UPDATED) {	
					n.adjust();
				}
				
			}
		}
	}

}
