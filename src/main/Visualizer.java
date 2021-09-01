package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import network.ConvolutionalNeuralNetwork;
import visualization.ConvNetVisualization;

import corefunc.ConnectionType;
import data.CSVMain;


public class Visualizer {

	private ConvNetVisualization frame;
	
	private ConvolutionalNeuralNetwork cnn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		CSVMain.main(args);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualizer window = new Visualizer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Visualizer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		int[][] filters = {{10, 3, 3}, {5, 5, 5}};
		int[] numHiddenLayers = {32, 20, 16};
		
		cnn = new ConvolutionalNeuralNetwork(filters, 24, 1, 24, numHiddenLayers, ConnectionType.MLP);
		
		frame = new ConvNetVisualization(cnn);
		frame.setBounds(0, 0, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				frame.initialize();
				frame.repaintValues();
				
			}
		});
		
		
		Thread netRun = new Thread() {
			public void run() {

				cnn = new ConvolutionalNeuralNetwork(filters, 24, 1, 24, numHiddenLayers, ConnectionType.MLP);

				cnn.setVisualization(frame);
				frame.setNetwork(cnn);
				cnn.connect();
				
				cnn.run();
				
				
				//cnn = new ConvolutionalNeuralNetwork(filters, 24, 1, 24, numHiddenLayers, ConnectionType.ELM);

				//cnn.setVisualization(frame);
				//frame.setNetwork(cnn);
				//cnn.connect();
				
				//cnn.run();
				
				//frame.finish();
				
				//cnn = new ConvolutionalNeuralNetwork(filters, 24, 1, 24, numHiddenLayers, ConnectionType.DRN);

				//cnn.setVisualization(frame);
				//frame.setNetwork(cnn);
				//cnn.connect();
				
				//cnn.run();
				
				//frame.finish();
				
				//cnn = new ConvolutionalNeuralNetwork(filters, 24, 1, 24, numHiddenLayers, ConnectionType.EncLM);

				//cnn.setVisualization(frame);
				//frame.setNetwork(cnn);
				//cnn.connect();
				
				//cnn.run();
				
			}
		};
		
		
		netRun.start();
		
		
	}
}
