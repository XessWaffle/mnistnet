package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import corefunc.ConnectionType;
import data.CSVMain;
import data.CSVManager;

public class DataPostprocessing {
	private File toRead;
	private CSVManager toWrite;
	
	private String[] toPush;
	private boolean isNet;
	
	public DataPostprocessing(String toRead, String toWrite, boolean isNet) {
		this.toRead = new File(toRead);
		this.toWrite = new CSVManager(toWrite);
		
		this.isNet = isNet;
	}
	
	public boolean read() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(toRead));
			
			String[] init = br.readLine().split(",");
			
			ArrayList<Integer> indices = new ArrayList<>();
			
			init = br.readLine().split(",");
			
			indices.add(init.length - 2);
			indices.add(init.length - 1);
			
			ArrayList<String> fin = new ArrayList<>();
			
			while(init != null) {
				System.out.println(init.length);
				
				for(int j = 0; j < indices.size(); j++) {
					fin.add(init[indices.get(j)]);
				}
				
				toPush = new String[fin.size()];
				
				for(int i = 0; i < fin.size(); i++) {
					toPush[i] = fin.get(i);
				}
				
				System.out.println("Pushing:" + Arrays.toString(toPush));
				
				write();
				
				fin.clear();
				
				init = br.readLine().split(",");
				
			}
			
			return true;
			
		} catch(Exception e) {
			return false;
		}
	}
	
	public void write() {
		toWrite.pushData(toPush);
		toWrite.write();
	}
	
	public static void main(String[] args) {
		CSVMain.main(args);
		
		Thread one = new Thread() {
			public void run() {
				DataPostprocessing dps = new DataPostprocessing("data\\" + ConnectionType.MLP + "NetworkData.csv", "findat\\" + ConnectionType.MLP + "NetworkError.csv", true);
				
				dps.read();
			}
		};
		
		Thread two = new Thread() {
			public void run() {
				DataPostprocessing dps = new DataPostprocessing("data\\" + ConnectionType.ELM + "NetworkData.csv", "findat\\" + ConnectionType.ELM + "NetworkError.csv", true);
				
				dps.read();
			}
		};
		
		Thread three = new Thread() {
			public void run() {
				DataPostprocessing dps = new DataPostprocessing("data\\" + ConnectionType.DRN + "NetworkData.csv", "findat\\" + ConnectionType.DRN + "NetworkError.csv", true);
				
				dps.read();
				
			}
		};
		
		Thread four = new Thread() {
			public void run() {
				DataPostprocessing dps = new DataPostprocessing("data\\" + ConnectionType.EncLM + "NetworkData.csv", "findat\\" + ConnectionType.EncLM + "NetworkError.csv", true);
				
				dps.read();
			}
		};
		
		one.start();
		two.start();
		three.start();
		four.start();
		
		
	}
}
