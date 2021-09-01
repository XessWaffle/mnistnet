package core.factory;

import java.util.ArrayList;

import core.NeuralLayer;
import core.NeuralNetwork;
import core.Neuron;
import corefunc.ConnectionType;

public class ConnectionFactory {
	
	public static void connect(NeuralLayer a, NeuralLayer b, ConnectionType ct, boolean fixed) {
		for(Neuron inA: a.getAllNeurons()) {
			for(Neuron inB:b.getAllNeurons()) {
				inA.outputConnect(inB, fixed);
			}
		}
		
		for(Neuron inB: b.getAllNeurons()) {
			for(Neuron inA:a.getAllNeurons()) {
				inB.inputConnect(inA, fixed);
			}
		}
	}
	
	public static void connect(ArrayList<NeuralLayer> nn, ConnectionType ct) {
			
			for(int i = 0; i < nn.size() - 1; i++) {
				for(Neuron n: nn.get(i).getAllNeurons()) {
					for(Neuron tCto: nn.get(i + 1).getAllNeurons()) {
						
					}
				}
			}
	}
	
	
	public static void connect(Neuron a, Neuron b, boolean fixed) {
		a.outputConnect(b, fixed);
		b.inputConnect(a, fixed);
	}
	
	public static void connect(NeuralLayer a, boolean fixed) {
		for(Neuron inA: a.getAllNeurons()) {
			for(Neuron inAAlso: a.getAllNeurons()) {
				if(!Neuron.equals(inA, inAAlso)) {
					ConnectionFactory.connect(inA, inAAlso, fixed);	
				}
			}
		}	
		
	}
	
	
}
