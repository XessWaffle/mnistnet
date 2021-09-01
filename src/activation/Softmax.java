package activation;

import core.NeuralLayer;
import core.Neuron;
import corefunc.Activation;

public class Softmax implements Activation{
	
	private NeuralLayer outputs;
	
	
	public Softmax(NeuralLayer output) {
		super();
		this.outputs = output;
	}
	
	@Override
	public double compute(double input) {
		// TODO Auto-generated method stub
		double sum = 0;
		
		double largest = Double.MIN_VALUE;
		
		boolean strange = true;
		
		for(Neuron n: outputs.getAllNeurons()) {
			
			if(largest < n.getInactiveNetInput()) {
				largest = n.getInactiveNetInput();
			}
			
			System.out.println(n.getForwardPropConnections().size());
			
			if(n.getInactiveNetInput() == input) {
				strange = false;
			}
		}
		
		for(Neuron n: outputs.getAllNeurons()) {
			
			if(Math.exp(n.getInactiveNetInput() - largest) < 0){
				System.out.println("ERROR");
			}
			
			sum += Math.exp(n.getInactiveNetInput() - largest);
			
		}
		
		double ans = Math.exp(input - largest)/sum;
		
		boolean ch = ans < 0 || ans > 1;
		
		System.out.print(ch + " " + strange);
		
		if(ch) {
			System.out.println(Math.exp(input - largest) + ", " + sum);
		} else {
			System.out.print("\n");
		}
		
		
		return ans;
		
	}

	@Override
	public double derive(double input) {
		// TODO Auto-generated method stub
		double sum = 0;
		double numSum = 0;
			
		double largest = Double.MIN_VALUE;
		
		for(Neuron n: outputs.getAllNeurons()) {
			
			if(largest < n.getInactiveNetInput()) {
				largest = n.getInactiveNetInput();
			}
		}
		
		for(Neuron n: outputs.getAllNeurons()) {
			sum += Math.exp(n.getInactiveNetInput() - largest);
		}
		
		numSum = sum - Math.exp(input - largest);
		
		return (Math.exp(input - largest) * (numSum))/(sum * sum);	
		
	}

	public NeuralLayer getOutputs() {
		return outputs;
	}

	public void setOutputs(NeuralLayer outputs) {
		this.outputs = outputs;
	}
	
}
