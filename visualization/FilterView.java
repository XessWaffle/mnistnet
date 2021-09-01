package visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.DecimalFormat;

import javax.swing.JComponent;


import img.ConvolutionalFilter;

public class FilterView extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConvolutionalFilter view;
	
	public FilterView() {
		setView(new ConvolutionalFilter(5,5));
	}

	public ConvolutionalFilter getView() {
		return view;
	}

	public void setView(ConvolutionalFilter view) {
		this.view = view;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		int xTileWidth = this.getWidth()/view.getXSize();
		int yTileHeight = this.getHeight()/view.getYSize();
		
		
		int xPos = 0;
		int yPos = 0;
		
		double layerMaxVal = Double.MIN_VALUE;
		double layerMinVal = Double.MAX_VALUE;
		
		for(int i = 0; i < view.getFilter().length; i++) {
			for(int j = 0; j < view.getFilter()[0].length; j++) {
				if(layerMaxVal < view.getFilter()[i][j]) {
					layerMaxVal = view.getFilter()[i][j];
				}
				
				if(layerMinVal > view.getFilter()[i][j]) {
					layerMinVal = view.getFilter()[i][j];
				}
			}
		}
		
		for(int i = 0; i < view.getXSize(); i++) {
			for(int j = 0; j < view.getYSize(); j++) {
				
				int color = this.detColor(view.getFilter()[i][j], layerMaxVal, layerMinVal);
				
				g2d.setColor(new Color(color, color, color));
				
				Rectangle write = new Rectangle(xPos, yPos, xTileWidth, yTileHeight);
				
				g2d.fill((Shape) write);
				
				g2d.setColor(new Color(255 - color, 255 - color, 255 - color));
				
				//this.drawCenteredString(g2d, (new DecimalFormat("#.###")).format(view.getFilter()[i][j]) + "", write, new Font("sansserif", Font.BOLD, (int)(4)));
				
				
				yPos += yTileHeight;
			}
			xPos += xTileWidth;
			yPos = 0;
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
}
