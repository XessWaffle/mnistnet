package visualization;


import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import network.ConvolutionalNeuralNetwork;
import img.label.LabeledImageDrawer;

public class ConvNetVisualization extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JProgressBar jpb;
	private JProgressBar epoch;
	private JTextField timer;
	
	private long startTime;
	
	private ActivationVisualizer[] av;
	private ErrorDisplay ed;
	private ErrorDisplay ad;
	private FilterView[] fv;
	private NetworkOutput no;
	private LabeledImageDrawer lid;
	
	private ConvolutionalNeuralNetwork cnn;
	
	public ConvNetVisualization(ConvolutionalNeuralNetwork cnn) {
		this.cnn = cnn;
		
		setFilterViewArray(new FilterView[cnn.getNumConvLayers()]);
		setActivationArray(new ActivationVisualizer[cnn.getNumConvLayers()]);
			
		ed = new ErrorDisplay(50, true);
		setAccuracyDisplay(new ErrorDisplay(50, false));
		no = new NetworkOutput();
		
		lid = new LabeledImageDrawer();
		jpb = new JProgressBar();
		epoch = new JProgressBar();
		timer = new JTextField();
		
		step();
		
		initialize();
		
		startTime = System.nanoTime();
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		
		int offset = 200;
		
		int midPoint = fv.length/2;
		
		int spacing = 10;
		
		int cH = (this.getHeight() - 20 - spacing * midPoint)/(midPoint * 1);
		int cW = cH;
		
		for(int i = 0; i < fv.length; i++) {
			if(i >= midPoint) {
				fv[i].setBounds(0, (i - midPoint) * (spacing + cH), cW, cH);
			} else {
				fv[i].setBounds(this.getWidth() - cW - 20, i * (spacing + cH), cW, cH);
			}
			
			this.getContentPane().add(fv[i]);
		}
		
		midPoint = av.length/2;
		
		int startX = cW + 10;
		int endX = this.getWidth() - startX;
		int startY = offset * 2 + 10;
		int endY = this.getHeight() - 20;
		
		int aW = (endX - startX)/midPoint;
		int aH = (endY - startY)/2;
		
		for(int i = 0; i < av.length; i++) {
			if(i >= midPoint) {
				av[i].setBounds(startX + (i - midPoint) * (aW) , endY - aH, aW, aH);
			} else {
				av[i].setBounds(endX - (i + 1) * (aW), startY, aW, aH);
			}
			
			this.getContentPane().add(av[i]);
		}

		lid.setBounds(cW, 0, offset , offset);
		no.setBounds((this.getWidth() - 20 - cW - offset), 0, offset, offset);
		
		jpb.setBounds(lid.getX() + lid.getWidth() + 10, 10, no.getX() - (lid.getX() + lid.getWidth() + 20), 50);
		
		jpb.setMaximum(cnn.getLimLoad().getFileSize());
		jpb.setMinimum(0);
		jpb.setValue(cnn.getLimLoad().getCurrentIndex());
		jpb.setStringPainted(true);
		jpb.setString("Training");
		
		epoch.setBounds(lid.getX() + lid.getWidth() + 10, jpb.getY() + 10 + jpb.getHeight(), no.getX() - (lid.getX() + lid.getWidth() + 20), jpb.getHeight());
		
		epoch.setMaximum(ConvolutionalNeuralNetwork.EPOCH_PRESET);
		epoch.setMinimum(0);
		epoch.setValue(epoch.getMaximum() - cnn.getLimLoad().getActBuffer().size());
		epoch.setStringPainted(true);
		epoch.setString("Epoch");
		
		timer = new JTextField();
		timer.setBounds(epoch.getX(), epoch.getY() + epoch.getHeight() + 10, epoch.getWidth(), 50);
		timer.setHorizontalAlignment(JTextField.CENTER);
		
		this.getContentPane().add(lid);
		this.getContentPane().add(no);
		this.getContentPane().add(jpb);
		this.getContentPane().add(epoch);
		this.getContentPane().add(timer);
		
		ed.setBounds(lid.getX(), lid.getY() + lid.getHeight() + 5, no.getX() - lid.getX() + offset, 100);
		ad.setBounds(ed.getX(), ed.getY()  + ed.getHeight() + 5, ed.getWidth(), ed.getHeight());
		
		this.getContentPane().add(ed);
		this.getContentPane().add(ad);
	}

	public void step() {
		
		lid.setImage(cnn.getCurrentImage());
		
		for(int i = 0; i < fv.length; i++) {
			fv[i] = new FilterView();
			fv[i].setView(cnn.getConvLayers()[i].getRgbFilter());
			av[i] = new ActivationVisualizer();
			av[i].setLayer(cnn.getConvLayers()[i].getLayer());
		}
		
		no.setBufferToDraw(cnn.getOutputBuffer());
	}
	
	public void repaintValues() {
		for(int i = 0; i < fv.length; i++) {
			fv[i].repaint();
		}
		
		for(int i = 0; i < av.length; i++) {
			av[i].repaint();
		}
		
		ed.repaint();
		no.repaint();
		ad.repaint();
		lid.repaint();
		
		epoch.setValue(epoch.getMaximum() - cnn.getLimLoad().getActBuffer().size());		
		epoch.setString("Image: " + (int)(epoch.getPercentComplete() * epoch.getMaximum()));
		epoch.repaint();
	
		jpb.setValue((int)(epoch.getPercentComplete() * epoch.getMaximum() + cnn.getLimLoad().getPrevIndex()));
		jpb.setString("Training: " + (int)(epoch.getPercentComplete() * epoch.getMaximum() + cnn.getLimLoad().getPrevIndex()));
		jpb.repaint();
	
		long nanoseconds = System.nanoTime() - startTime;
		long microseconds = nanoseconds / 1000;
		long miliseconds = microseconds / 1000;
		long seconds = miliseconds / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
	
		timer.setText((new DecimalFormat("##")).format(hours) + ":" + (new DecimalFormat("##")).format(minutes - hours * 60) + ":" + (new DecimalFormat("##")).format(seconds - minutes * 60));
		
	}


	public ActivationVisualizer[] getActivationArray() {
		return av;
	}


	public void setActivationArray(ActivationVisualizer[] av) {
		this.av = av;
	}


	public ErrorDisplay getErrorDisplay() {
		return ed;
	}


	public void setErrorDisplay(ErrorDisplay ed) {
		this.ed = ed;
	}


	public FilterView[] getFilterViewArray() {
		return fv;
	}


	public void setFilterViewArray(FilterView[] fv) {
		this.fv = fv;
	}


	public NetworkOutput getNetworkOutput() {
		return no;
	}


	public void setNetworkOutput(NetworkOutput no) {
		this.no = no;
	}

	public ErrorDisplay getAccuracyDisplay() {
		return ad;
	}

	public void setAccuracyDisplay(ErrorDisplay ad) {
		this.ad = ad;
	}

	public LabeledImageDrawer getDrawer() {
		return lid;
	}

	public void setDrawer(LabeledImageDrawer lid) {
		this.lid = lid;
	}
	
	public void setNetwork(ConvolutionalNeuralNetwork cnn) {
		this.cnn = cnn;
	}
	
	public void finish() {
		ed.clear();
		ad.clear();
	}
}
