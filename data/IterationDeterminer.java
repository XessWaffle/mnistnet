package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class IterationDeterminer {
	private ArrayList<Integer> divergenceMap;
	
	public IterationDeterminer(){
		setDivergenceMap(new ArrayList<>());
		
		
	}

	public void beginCalculations() {
		// TODO Auto-generated method stub
		String direc = "data\\mmadata";
		
		File dir = new File(direc);
		
		for(String subDir: dir.list()) {
			File sub = new File(direc + "//" + subDir);
			for(File calcCsv: sub.listFiles()) {
				if(calcCsv.getName().contains("NetworkData")) {
					calculateIterationValue(calcCsv);
				} else {
					calculateIterationValueNeuronData(calcCsv);
				}
			}
		}
	}

	private void calculateIterationValueNeuronData(File calcCsv) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(calcCsv));
			
			br.readLine();
			
			int iteration = 0;
			
			double prevOutput = 0, threshold = 0.7;
			
			String line;
			
			
			while((line = br.readLine()) != null){
				
				String[] dat = line.split(",");
				
				double output = Double.parseDouble(dat[dat.length - 2]);
				
				if((iteration != 0 || iteration != 1) && Math.abs(prevOutput - output) > threshold) {
					break;
				}
				
				prevOutput = output;
				iteration++;
			}
			
			br.close();
			
			BufferedWriter calc = new BufferedWriter(new FileWriter(calcCsv,true));
			
			calc.write("\nMax Error Drop," + iteration);
			
			calc.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void calculateIterationValue(File calcCsv) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(calcCsv));
			
			br.readLine();
			
			int iteration = 0, maxIteration = 0;
			
			double avgNetworkErrorDrop, maxNetworkErrorDrop = Integer.MIN_VALUE, prevNetworkError = 0;
			
			String line;
			
			
			while((line = br.readLine()) != null){
				
				String[] dat = line.split(",");
				
				double networkError = Double.parseDouble(dat[dat.length - 1]);
				
				avgNetworkErrorDrop = prevNetworkError - networkError;
				
				if(avgNetworkErrorDrop > maxNetworkErrorDrop && iteration < 1000 && iteration > 1) {
					maxIteration = iteration;
					maxNetworkErrorDrop = avgNetworkErrorDrop;
					
					System.out.println(maxNetworkErrorDrop);
					
				}
				
		
				prevNetworkError = networkError;
				
				iteration++;
			}
			
			br.close();
			
			BufferedWriter calc = new BufferedWriter(new FileWriter(calcCsv,true));
			
			calc.write("\nMax Error Drop," + maxIteration);
			
			calc.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public ArrayList<Integer> getDivergenceMap() {
		return divergenceMap;
	}

	public void setDivergenceMap(ArrayList<Integer> divergenceMap) {
		this.divergenceMap = divergenceMap;
	}
}
