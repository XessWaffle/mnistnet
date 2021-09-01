package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class WriteCSV {
	
	public static final int NUM_TO_GENERATE = 100000;
	
	private static Random ran = new Random();
	
	public static ArrayList<File> allProgramFiles = new ArrayList<>();
	
	public static void createNewFile(String fileName) throws IOException {
		allProgramFiles.add(new File(fileName));
		
		allProgramFiles.get(allProgramFiles.size() - 1).createNewFile();
		
	}
	
	public static void writeToFile(String fileName, String[] toWrite) {
		File toWriteTo = null;
		
		for(File check: allProgramFiles) {
			
			System.out.println(check.getName());
			
			if(check.getName().equals(fileName)) {
				toWriteTo = check;
				break;
			}
		}
		
		try {
			PrintWriter pw = new PrintWriter(toWriteTo);
			
			for(int i = 0; i < toWrite.length; i++) {
				pw.append(toWrite[i]);
				
				if(i < toWrite.length - 1) {
					pw.append(",");
				}
			}
			
			pw.append("\n");
			
			pw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void generateData() {
		
		File XORtwo = new File("data\\XOR2.csv"), XORthree = new File("data\\XOR3.csv");
		File ANDtwo = new File("data\\AND2.csv"), ANDthree = new File("data\\AND3.csv");
		File ORtwo = new File("data\\OR2.csv"), ORthree = new File("data\\OR3.csv");
		File quad = new File("data\\QUAD.csv");
		writeXORData(XORtwo, XORthree);
		
		writeANDData(ANDtwo, ANDthree);
		
		writeORData(ORtwo, ORthree);
		
		writeQuadData(quad);
	}

	private static void writeQuadData(File quad) {
		// TODO Auto-generated method stub
		Stack<double[]> or2 = new Stack<>();
		
		for(int i = 0; i < NUM_TO_GENERATE; i++) {
			double[] two = new double[5];
			
			boolean pass = false;
			
			while(!pass) {
				try {
					two[0] = (int)((Math.random() - 0.5) * NUM_TO_GENERATE / 1000);
					two[1] = (int)((Math.random() - 0.5) * NUM_TO_GENERATE / 1000);
					two[2] = (int)((Math.random() - 0.5) * NUM_TO_GENERATE / 1000);
					
					two[3] = (-1 * two[1] + Math.sqrt(Math.pow(two[1], 2) - 4 * two[0] * two[2]))/(2 * two[0]);
		
					two[4] = (-1 * two[1] - Math.sqrt(Math.pow(two[1], 2) - 4 * two[0] * two[2]))/(2 * two[0]);
					
					if(!(Double.isNaN(two[3]) || Double.isNaN(two[4])))
						pass = true;
					
				} catch (Exception e) {
					System.out.println("Bad Data");
				}
			}
			or2.push(two);
			
		}
		
		try {
			
			quad.createNewFile();
			
			PrintWriter pw1 = new PrintWriter(quad);
			
			while(!or2.isEmpty()) {
				double[] two = or2.pop();
				
				for(int i = 0; i < two.length; i++) {
					pw1.append(two[i] + "");
					
					if(i != two.length - 1)
						pw1.append(",");
				}
				
				pw1.append("\n");
				
			}
			
			pw1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void writeORData(File ORtwo, File ORthree) {
		// TODO Auto-generated method stub
		Stack<double[]> or2 = new Stack<>();
		Stack<double[]> or3 = new Stack<>();
		
		boolean ran1, ran2, ran3;
		
		for(int i = 0; i < NUM_TO_GENERATE; i++) {
			double[] two = new double[3];
			double[] three = new double[4];
			
			ran1 = ran.nextBoolean();
			ran2 = ran.nextBoolean();
			ran3 = ran.nextBoolean();
		
			two[0] = ran1 || ran2 ? 1.0 : 0.0;
			two[1] = ran1 ? 1.0 : 0.0;
			two[2] = ran2  ? 1.0 : 0.0;
			
			three[0] = ran1 || ran2 || ran3 ? 1.0 : 0.0;
			three[1] = ran1 ? 1.0 : 0.0;
			three[2] = ran2 ? 1.0 : 0.0;
			three[3] = ran3 ? 1.0 : 0.0;
			
			or2.push(two);
			or3.push(three);
		}
		
		try {
			
			ORtwo.createNewFile();
			ORthree.createNewFile();
			
			PrintWriter pw1 = new PrintWriter(ORtwo);
			PrintWriter pw2 = new PrintWriter(ORthree);
			
			while(!or2.isEmpty()) {
				double[] two = or2.pop();
				double[] three = or3.pop();
				
				for(int i = 0; i < two.length; i++) {
					pw1.append(two[i] + "");
					
					if(i != two.length - 1)
						pw1.append(",");
				}
				
				pw1.append("\n");
				
				
				for(int i = 0; i < three.length; i++) {
					pw2.append(three[i] + "");
					
					if(i != three.length - 1)
						pw2.append(",");
				}
				
				pw2.append("\n");
			}
			
			pw1.close();
			pw2.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private static void writeANDData(File ANDtwo, File ANDthree) {
		// TODO Auto-generated method stub
		Stack<double[]> or2 = new Stack<>();
		Stack<double[]> or3 = new Stack<>();
		
		boolean ran1, ran2, ran3;
		
		for(int i = 0; i < NUM_TO_GENERATE; i++) {
			double[] two = new double[3];
			double[] three = new double[4];
			
			ran1 = ran.nextBoolean();
			ran2 = ran.nextBoolean();
			ran3 = ran.nextBoolean();
		
			two[0] = ran1 && ran2 ? 1.0 : 0.0;
			two[1] = ran1 ? 1.0 : 0.0;
			two[2] = ran2  ? 1.0 : 0.0;
			
			three[0] = ran1 && ran2 && ran3 ? 1.0 : 0.0;
			three[1] = ran1 ? 1.0 : 0.0;
			three[2] = ran2 ? 1.0 : 0.0;
			three[3] = ran3 ? 1.0 : 0.0;
			
			or2.push(two);
			or3.push(three);
		}
		
		try {
			
			ANDtwo.createNewFile();
			ANDthree.createNewFile();
			
			PrintWriter pw1 = new PrintWriter(ANDtwo);
			PrintWriter pw2 = new PrintWriter(ANDthree);
			
			while(!or2.isEmpty()) {
				double[] two = or2.pop();
				double[] three = or3.pop();
				
				for(int i = 0; i < two.length; i++) {
					pw1.append(two[i] + "");
					
					if(i != two.length - 1)
						pw1.append(",");
				}
				
				pw1.append("\n");
				
				
				for(int i = 0; i < three.length; i++) {
					pw2.append(three[i] + "");
					
					if(i != three.length - 1)
						pw2.append(",");
				}
				
				pw2.append("\n");
			}
			
			pw1.close();
			pw2.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void writeXORData(File XORtwo, File XORthree) {
		// TODO Auto-generated method stub
		Stack<double[]> or2 = new Stack<>();
		Stack<double[]> or3 = new Stack<>();
		
		boolean ran1, ran2, ran3;
		
		for(int i = 0; i < NUM_TO_GENERATE; i++) {
			double[] two = new double[3];
			double[] three = new double[4];
			
			ran1 = ran.nextBoolean();
			ran2 = ran.nextBoolean();
			ran3 = ran.nextBoolean();
		
			two[0] = (ran1 || ran2) && !(ran1 && ran2)? 1.0 : 0.0;
			two[1] = ran1 ? 1.0 : 0.0;
			two[2] = ran2  ? 1.0 : 0.0;
			
			three[0] = (ran1 || ran2 || ran3) && !(ran1 && ran2 && ran3) ? 1.0 : 0.0;
			three[1] = ran1 ? 1.0 : 0.0;
			three[2] = ran2 ? 1.0 : 0.0;
			three[3] = ran3 ? 1.0 : 0.0;
			
			or2.push(two);
			or3.push(three);
		}
		
		try {
			
			XORtwo.createNewFile();
			XORthree.createNewFile();
			
			PrintWriter pw1 = new PrintWriter(XORtwo);
			PrintWriter pw2 = new PrintWriter(XORthree);
			
			while(!or2.isEmpty()) {
				double[] two = or2.pop();
				double[] three = or3.pop();
				
				for(int i = 0; i < two.length; i++) {
					pw1.append(two[i] + "");
					
					if(i != two.length - 1)
						pw1.append(",");
				}
				
				pw1.append("\n");
				
				
				for(int i = 0; i < three.length; i++) {
					pw2.append(three[i] + "");
					
					if(i != three.length - 1)
						pw2.append(",");
				}
				
				pw2.append("\n");
			}
			
			pw1.close();
			pw2.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
