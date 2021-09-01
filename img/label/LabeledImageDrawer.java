package img.label;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class LabeledImageDrawer extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LabeledImage toDraw;
	
	public LabeledImageDrawer() {}
	
	public LabeledImage getImage() {
		return toDraw;
	}

	public void setImage(LabeledImage toDraw) {
		this.toDraw = toDraw;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(toDraw != null) {
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.drawImage(toDraw.getImage(), 0, 0, this.getWidth(), this.getHeight(), 
					0, 0, toDraw.getImage().getWidth(), toDraw.getImage().getWidth(), null);
			
			System.out.println(toDraw.getImage().getWidth() + ", " + toDraw.getImage().getHeight());
			
		}
	}

}
