package img.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import img.ConvolutionalFilter;
import img.ImageConvolution;
import img.ImageFilter;
import img.label.LabeledImageDrawer;
import img.label.LabeledImageLoader;

import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ImageViewer {

	private JFrame frame;
	
	private LabeledImageLoader creator;
	private LabeledImageDrawer drawer, convolutionDrawer;
	
	private ImageParser imgp;
	private ImageCreator imgc;
	
	private ImageConvolution imgconv;
	private ConvolutionalFilter convFilt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageViewer window = new ImageViewer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImageViewer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 800, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		creator = new LabeledImageLoader(false);
		imgp = new ImageParser();
		imgc = new ImageCreator();
		imgconv = new ImageConvolution(1);
		convFilt = new ConvolutionalFilter(3, 3);
		
		drawer = new LabeledImageDrawer();
		drawer.setBounds(0,0,333, 310);
		frame.getContentPane().add(drawer);
		
		convolutionDrawer = new LabeledImageDrawer();
		convolutionDrawer.setBounds(frame.getWidth() - 333, 0, 333, 310);
		frame.getContentPane().add(convolutionDrawer);
		
		JLabel lblLabel = new JLabel();
		lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabel.setFont(new Font("Courier New", Font.PLAIN, 15));
		lblLabel.setBounds(350, 285, 90, 25);
		frame.getContentPane().add(lblLabel);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				creator.loadNext();
				
				fConv();
				
				lblLabel.setText(creator.getBuffer().getLabel());
				
				drawer.repaint();
				convolutionDrawer.repaint();
			}
		});
		btnNext.setBounds(350, 60, 90, 25);
		frame.getContentPane().add(btnNext);
		
		JButton btnPrev = new JButton("Previous");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creator.loadPrevious();
				
				fConv();
				
				lblLabel.setText(creator.getBuffer().getLabel());
				
				drawer.repaint();
				convolutionDrawer.repaint();
			}
		});
		btnPrev.setBounds(350, 150, 90, 25);
		frame.getContentPane().add(btnPrev);
		
		JSlider location = new JSlider();
		location.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				creator.loadIndex(location.getValue());
				
				fConv();
				
				lblLabel.setText(creator.getBuffer().getLabel());
				
				drawer.repaint();
				convolutionDrawer.repaint();
			}
		});
		location.setMaximum(creator.getFileSize() - 1);
		location.setMinimum(0);
		location.setBounds(10, 331, 764, 50);
		frame.getContentPane().add(location);
		
		
		
	}
	
	public void fConv() {
		drawer.setImage(creator.getBuffer());

	}
	
}
