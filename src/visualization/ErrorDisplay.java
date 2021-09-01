package visualization;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Stack;

import javax.swing.JComponent;

public class ErrorDisplay extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack<Double> toDraw;
	private int dispAmt;
	
	private double initial;
	private boolean isFirst = true;
	private Double error;
	private boolean avgMax;
	
	public ErrorDisplay(int amntToShow, boolean avgMax) {
		setToDraw(new Stack<Double>());
		
		this.setDispAmt(amntToShow);
		
		this.avgMax = avgMax;
	}

	public Stack<Double> getToDraw() {
		return toDraw;
	}

	public void setToDraw(Stack<Double> toDraw) {
		this.toDraw = toDraw;
	}

	public int getDispAmt() {
		return dispAmt;
	}

	public void setDispAmt(int dispAmt) {
		this.dispAmt = dispAmt;
	}
	
	public void pushNext(Double dub) {
		toDraw.push(dub);
		
		error = dub;
		
		if(isFirst) {
			this.initial = dub;
			isFirst = false;
		}
		
		if(toDraw.size() > this.dispAmt) {
			toDraw.remove(0);
		}
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 20, this.getWidth(), this.getHeight());
		
		int drawWidth = this.getWidth()/this.dispAmt;
		
		int xPos = 0;
		
		if(avgMax) {
			initial = average();
		} else {
			initial = max();
		}
		
		
		for(int i = 0; i < toDraw.size(); i++) {
			g2d.setColor(Color.BLACK);
			
			int drawHeight = detHeight(toDraw.get(i).doubleValue());
			
			System.out.println(drawHeight);
			
			g2d.fillRect(xPos, this.getHeight() - drawHeight + 20, drawWidth, drawHeight);
			
			xPos += drawWidth;
			
		}
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), 20);
		
		g2d.setColor(Color.BLACK);
		this.drawCenteredString(g2d, error + "", new Rectangle(0, 0, this.getWidth(), 20), new Font("sansserif", Font.BOLD, (int)(12)));
	}

	private double max() {
		// TODO Auto-generated method stub
		
		double ret = Double.MIN_VALUE;
		
		for(Double d: toDraw) {
			if(ret < d.doubleValue()) {
				ret = d.doubleValue();
			}
		}
		
		return ret;
	}
	

	private double average() {
		// TODO Auto-generated method stub
		
		double sum = 0;
		
		for(Double d: toDraw) {
			sum += d.doubleValue();
		}
		
		sum /= toDraw.size();
		
		return sum;
	}

	private int detHeight(double d) {
		// TODO Auto-generated method stub
		double ratio = d/this.initial;
		
		return (int)(ratio * this.getHeight());
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
	
	public void clear() {
		toDraw.clear();
	}
	
}
