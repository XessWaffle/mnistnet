package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ActivationDeterminer {
	public static void main(String[] args) {
		beginCalculations();
	}
	
	public static void beginCalculations() {
		// TODO Auto-generated method stub
		String direc = "data//";
		
		File dir = new File(direc);
		

		for(File calcCsv: dir.listFiles()) {
			if(calcCsv.getName().contains("NeuronData")) {
				addActivations(calcCsv);
			}
		}
	
	}

	private static void addActivations(File calcCsv) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(calcCsv));
			
			String cal = br.readLine();
			String[] calS = cal.split(",");
			
			File actCsv = new File("findat//Activations" + calcCsv.getName());
			
			ArrayList<Integer> locations = new ArrayList<>();
			
			BufferedWriter calc = new BufferedWriter(new FileWriter(actCsv,true));
			
			if(!actCsv.exists()) {
				actCsv.createNewFile();
			}
			
			for(int i = calS.length - 1; i >= 0; i--) {
				
				if(calS[i].contains("Activation") && locations.size() < 10) {
					locations.add(i);
					
					System.out.println("Writing: " + calS[i]);
					
					calc.write(calS[i] + ",");
				}
				
			}
			
			calc.write("\n");
			
			String line;
			
			
			
			while((line = br.readLine()) != null){	
				String[] dat = line.split(",");
				
				for(int i = 0; i < locations.size(); i++) {
					calc.write(dat[locations.get(i)] + ",");
					System.out.println("Writing: " + dat[locations.get(i)]);
					
				}
				
				calc.write("\n");
			
			}
			
			br.close();
		
			calc.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}