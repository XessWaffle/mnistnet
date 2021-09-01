package img.view;

import java.awt.image.BufferedImage;

import img.ImageFilter;
import img.label.LabeledImage;

public class ImageParser {
	
	private ImageFilter[] buffer;
	
	public ImageParser() {
		
	}

	public ImageFilter[] getBuffer() {
		return buffer;
	}

	public void setBuffer(ImageFilter[] toParse) {
		this.buffer = toParse;
	}
	
	public void parse(LabeledImage toParse){
		
		BufferedImage pMed = toParse.getImage();
		
		//System.out.println("PMED:" + pMed.getHeight());
		
		ImageFilter red = new ImageFilter(pMed.getWidth(), pMed.getHeight());
		ImageFilter blue = new ImageFilter(pMed.getWidth(), pMed.getHeight());
		ImageFilter green = new ImageFilter(pMed.getWidth(), pMed.getHeight());
		
		for(int i = 0; i < pMed.getWidth(); i++) {
			for(int j = 0; j < pMed.getHeight(); j++) {
				int pRed = (pMed.getRGB(i, j) >> 16) & 0xff;
			    int pGreen = (pMed.getRGB(i, j) >> 8) & 0xff;
			    int pBlue = (pMed.getRGB(i, j)) & 0xff;
			    
			    red.updateNext(map(pRed, 0.0, 256.0, 0.0, 1.0));
			    blue.updateNext(map(pBlue, 0.0, 256.0, 0.0, 1.0));
			    green.updateNext(map(pGreen, 0.0, 256.0, 0.0, 1.0));
			    
			}
		}
		
		ImageFilter[] ret = new ImageFilter[3];
		ret[0] = red;
		ret[1] = green;
		ret[2] = blue;
		
		buffer = ret;
	}
	
	public static double map(double x, double in_min, double in_max, double out_min, double out_max)
	{
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
}
