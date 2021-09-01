package visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;


public class NetworkOutput extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double[] bufferToDraw;
	
	public NetworkOutput() {
		bufferToDraw = new double[10];
		
		for(int i = 0; i < 10; i++) {
			bufferToDraw[i] = Math.random();
		}
	}

	public double[] getBufferToDraw() {
		return bufferToDraw;
	}

	public void setBufferToDraw(double[] bufferToDraw) {
		this.bufferToDraw = bufferToDraw;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		double maxVal = Double.MIN_VALUE;
		double minVal = Double.MAX_VALUE;
		
		for(int i = 0; i < bufferToDraw.length; i++) {
			if(maxVal < bufferToDraw[i]) {
				maxVal = bufferToDraw[i];
			} 
			
			if(minVal > bufferToDraw[i]) {
				minVal = bufferToDraw[i];
			}
		}
		
		
		for(int i = 0; i < bufferToDraw.length; i++) {
			
			int draw = detColor(bufferToDraw[i], maxVal, minVal); 
			
			g2d.setColor(new Color((int)(draw), (int)(draw), (int)(draw)));
			
			g2d.fillRect(0, i * 20, 50, 20);
			
			g2d.setColor(Color.BLACK);
			this.drawCenteredString(g2d, bufferToDraw[i] > 1.0 || bufferToDraw[i] < 0.0 ? "Err: " + bufferToDraw[i]  : bufferToDraw[i] + "", new Rectangle(80, i * 20, 80, 20), new Font("sansserif", Font.BOLD, (int)(12)));
		}
	}
	
	public void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    
	    g.drawString(text, x, y);
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
