package img.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import img.ConvolutionalFilter;
import img.ImageFilter;
import img.label.LabeledImage;

public class ImageCreator {
	
	private LabeledImage buffer;
	
	public ImageCreator() {
		
	}
	
	public LabeledImage getBuffer() {
		return buffer;
	}

	public void setBuffer(LabeledImage buffer) {
		this.buffer = buffer;
	}

	public void create(ImageFilter[] imf, ConvolutionalFilter sCheck) {
		
		int sx = imf[0].getXSize();
		int sy = imf[0].getYSize();
		
		BufferedImage toAdd = new BufferedImage(sx, sy, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = toAdd.createGraphics();
		
		for(int i = 0; i < sx; i++) {
			for(int j = 0; j < sy; j++) {
				
				double size = sCheck.getXSize() * sCheck.getYSize();
				
				double red = ImageParser.map(imf[0].getValue(i, j), 0, size, 0, 1.0);
				double green = ImageParser.map(imf[1].getValue(i, j), 0, size, 0, 1.0);
				double blue = ImageParser.map(imf[2].getValue(i, j), 0, size, 0, 1.0);
				
				g2d.setColor(new Color((int)(red * 255), (int)(green * 255), (int)(blue * 255)));
				g2d.fillRect(i, j, 1, 1);
				
			}
		}
		
		
		this.buffer = new LabeledImage(toAdd, "NULL");
		
	}
	
}
