package img.label;

import java.awt.image.BufferedImage;

public class LabeledImage {
	private BufferedImage lImage;
	private String label;
	
	public LabeledImage(BufferedImage img, String label) {
		this.setImage(img);
		this.setLabel(label);
	}

	public BufferedImage getImage() {
		return lImage;
	}

	public void setImage(BufferedImage lImage) {
		this.lImage = lImage;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
