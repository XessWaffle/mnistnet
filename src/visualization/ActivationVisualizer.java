package visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import core.Neuron;

public class ActivationVisualizer extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Neuron[][] layer;
	
	public ActivationVisualizer() {
		setLayer(null);
	}

	public Neuron[][] getLayer() {
		return layer;
	}

	public void setLayer(Neuron[][] layer) {
		this.layer = layer;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		if(this.layer != null) {
			int tileWidth = this.getWidth()/this.layer.length;
			int tileHeight = this.getHeight()/this.layer[0].length;
			
			int xPos = 0, yPos = 0;
			
			double layerMaxVal = Double.MIN_VALUE;
			double layerMinVal = Double.MAX_VALUE;
			
			for(int i = 0; i < layer.length; i++) {
				for(int j = 0; j < layer[0].length; j++) {
					if(layerMaxVal < layer[i][j].getNetOutput()) {
						layerMaxVal = layer[i][j].getNetOutput();
					}
					
					if(layerMinVal > layer[i][j].getNetOutput()) {
						layerMinVal = layer[i][j].getNetOutput();
					}
				}
			}
			
			
			for(int i = 0; i < layer.length; i++) {
				for(int j = 0; j < layer[0].length; j++) {
					int colorVal = detColor(layer[i][j].getNetOutput(), layerMaxVal, layerMinVal);
					g2d.setColor(new Color(colorVal, colorVal, colorVal));
					g2d.fillRect(xPos, yPos, tileWidth, tileHeight);
					yPos += tileHeight;
				}
				xPos += tileWidth;
				yPos = 0;
			}
			
			
		}
		
		
	}

	private int detColor(double netOutput, double maxVal, double minVal) {
		// TODO Auto-generated method stub
		
		double ratio = 0;
		
		if(netOutput > 0) {
			ratio = netOutput/maxVal;
		} else {
			ratio = -netOutput/minVal;
		}
		
		ratio = (ratio + 1)/2.0;
		
		return (int)(ratio * 255);
	}
	
	
	
}
