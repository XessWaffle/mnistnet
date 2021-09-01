package img.label;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import network.ConvolutionalNeuralNetwork;

public class LabeledImageLoader {
	
	public static final int SCALED_IMAGE_SIZE = 24;
	public static final File MNIST_TEST_FILE = new File("img\\mnist_test.csv");
	public static final File MNIST_TRAIN_FILE = new File("img\\mnist_train.csv");
	
	private File mnistFile;
	private int fileSize;
	private int currentIndex = 0;
	private int prevIndex = 0;
	
	private Stack<LabeledImage> buffer;
	
	public LabeledImageLoader(boolean testFile) {
		
		if(testFile) {
			mnistFile = MNIST_TEST_FILE;
			fileSize = 10000;
		} else {
			mnistFile = MNIST_TRAIN_FILE;
			fileSize = 60000;
		}
		
		buffer = new Stack<>();
		
		loadNextEpoch(1);
		
		
	}

	public File getMNISTFile() {
		return mnistFile;
	}

	public void setMNISTFile(File mnistFile) {
		this.mnistFile = mnistFile;
	}

	public LabeledImage getBuffer() {
		return buffer.pop();
	}
	
	public Stack<LabeledImage> getActBuffer(){
		return buffer;
	}

	public void setBuffer(Stack<LabeledImage> buffer) {
		this.buffer = buffer;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public int getFileSize() {
		return fileSize;
	}
	
	public void loadNextEpoch(int cnt) {
		
		for(int m = 0; m < cnt; m++)
			try {
				
				prevIndex = currentIndex;
				
				BufferedReader br = new BufferedReader(new FileReader(this.getMNISTFile()));
				
				String label;
				BufferedImage img = new BufferedImage(28,28, BufferedImage.TYPE_INT_ARGB);
				
				for(int i = 0; i < currentIndex + 1; i++) {
					br.readLine();
				}
				
				for(int k = 0; k < ConvolutionalNeuralNetwork.EPOCH_PRESET; k++) {
					System.out.println("Next Image " + ++currentIndex);
					
					String[] line = br.readLine().split(",");
					
					label = line[0];
					
					Graphics2D imgGr = img.createGraphics();
					
					int lineLoc = 1;
					
					for(int i = 0; i < 28; i++) {
						for(int j = 0; j < 28; j++) {
							int col = Integer.parseInt(line[lineLoc++]);
							
							imgGr.setColor(new Color(col,col,col));
							imgGr.fillRect(j, i, 1, 1);
							
						}
					}
					
					BufferedImage upscaledImg = new BufferedImage(SCALED_IMAGE_SIZE,SCALED_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2d = upscaledImg.createGraphics();
					
					g2d.drawImage(img, 0, 0, SCALED_IMAGE_SIZE, SCALED_IMAGE_SIZE, null);
					g2d.dispose();
					g2d.setComposite(AlphaComposite.Src);
					g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					
					
					buffer.push(new LabeledImage(upscaledImg, label));
				}
				
				
				br.close();
				
			} catch(Exception e) {
				System.out.println(e.getStackTrace() + "\nIO Error");
			}
	}
	
	public void loadNext() {
		try {
			
			prevIndex = currentIndex;
			
			BufferedReader br = new BufferedReader(new FileReader(this.getMNISTFile()));
			
			String label;
			BufferedImage img = new BufferedImage(28,28, BufferedImage.TYPE_INT_ARGB);
			
			for(int i = 0; i < currentIndex + 1; i++) {
				br.readLine();
			}
			
			System.out.println("Next Image " + ++currentIndex);
			
			String[] line = br.readLine().split(",");
			
			label = line[0];
			
			Graphics2D imgGr = img.createGraphics();
			
			int lineLoc = 1;
			
			for(int i = 0; i < 28; i++) {
				for(int j = 0; j < 28; j++) {
					int col = Integer.parseInt(line[lineLoc++]);
					
					imgGr.setColor(new Color(col,col,col));
					imgGr.fillRect(j, i, 1, 1);
					
				}
			}
			
			BufferedImage upscaledImg = new BufferedImage(SCALED_IMAGE_SIZE,SCALED_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = upscaledImg.createGraphics();
			
			g2d.drawImage(img, 0, 0, SCALED_IMAGE_SIZE, SCALED_IMAGE_SIZE, null);
			g2d.dispose();
			g2d.setComposite(AlphaComposite.Src);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			
			buffer.push(new LabeledImage(upscaledImg, label));
			
			br.close();
			
		} catch(Exception e) {
			System.out.println(e.getStackTrace() + "\nIO Error");
		}
	}
	
	public void loadPrevious() {
		try {
			
			prevIndex = currentIndex;
			
			BufferedReader br = new BufferedReader(new FileReader(this.getMNISTFile()));
			
			String label;
			BufferedImage img = new BufferedImage(28,28, BufferedImage.TYPE_INT_ARGB);
			
			for(int i = 0; i < currentIndex - 1; i++) {
				br.readLine();
			}
			
			System.out.println("Previous Image " + --currentIndex);
			
			String[] line = br.readLine().split(",");
			
			label = line[0];
			
			Graphics2D imgGr = img.createGraphics();
			
			int lineLoc = 1;
			
			for(int i = 0; i < 28; i++) {
				for(int j = 0; j < 28; j++) {
					int col = Integer.parseInt(line[lineLoc++]);
					
					imgGr.setColor(new Color(col,col,col));
					imgGr.fillRect(j, i, 1, 1);
					
				}
			}
			
			BufferedImage upscaledImg = new BufferedImage(SCALED_IMAGE_SIZE, SCALED_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = upscaledImg.createGraphics();
			
			g2d.drawImage(img, 0, 0, SCALED_IMAGE_SIZE, SCALED_IMAGE_SIZE, null);
			g2d.dispose();
			g2d.setComposite(AlphaComposite.Src);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			
			buffer.push(new LabeledImage(upscaledImg, label));
			
			br.close();
			
		} catch(Exception e) {
			System.out.println(e.getStackTrace() + "\nIO Error");
		}
	}
	
	public void loadIndex(int index) {
		try {
			
			prevIndex = currentIndex;
			
			System.out.println("Image at index " + index);
			
			BufferedReader br = new BufferedReader(new FileReader(this.getMNISTFile()));
			
			String label;
			BufferedImage img = new BufferedImage(28,28, BufferedImage.TYPE_INT_ARGB);
			
			for(int i = 0; i < index; i++) {
				br.readLine();
			}
			
			String[] line = br.readLine().split(",");
			
			label = line[0];
			
			Graphics2D imgGr = img.createGraphics();
			
			int lineLoc = 1;
			
			for(int i = 0; i < 28; i++) {
				for(int j = 0; j < 28; j++) {
					int col = Integer.parseInt(line[lineLoc++]);
					
					imgGr.setColor(new Color(col,col,col));
					imgGr.fillRect(j, i, 1, 1);
					
				}
			}
			
			BufferedImage upscaledImg = new BufferedImage(SCALED_IMAGE_SIZE, SCALED_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = upscaledImg.createGraphics();
			
			g2d.drawImage(img, 0, 0, SCALED_IMAGE_SIZE, SCALED_IMAGE_SIZE, null);
			g2d.dispose();
			g2d.setComposite(AlphaComposite.Src);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			
			buffer.push(new LabeledImage(upscaledImg, label));
			
			br.close();
			
			currentIndex = index;
			
		} catch(Exception e) {
			System.out.println(e.getStackTrace() + "\nIO Error");
		}
	}

	public int getPrevIndex() {
		return prevIndex;
	}

	public void setPrevIndex(int prevIndex) {
		this.prevIndex = prevIndex;
	}
}
