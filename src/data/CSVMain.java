package data;

import java.io.IOException;

import corefunc.ConnectionType;

public class CSVMain {
	public static void main(String[] args) {
		try {
			//WriteCSV.createNewFile("findat\\" + ConnectionType.MLP + "NeuronData.csv");
			WriteCSV.createNewFile("findat\\" + ConnectionType.MLP + "NetworkError.csv");
	
			//WriteCSV.createNewFile("findat\\" + ConnectionType.DRN + "NeuronData.csv");
			WriteCSV.createNewFile("findat\\" + ConnectionType.DRN + "NetworkError.csv");
	
			//WriteCSV.createNewFile("findat\\" + ConnectionType.ELM + "NeuronData.csv");
			WriteCSV.createNewFile("findat\\" + ConnectionType.ELM + "NetworkError.csv");
	
			//WriteCSV.createNewFile("findat\\" + ConnectionType.EncLM + "NeuronData.csv");
			WriteCSV.createNewFile("findat\\" + ConnectionType.EncLM + "NetworkError.csv");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		/* 
		 * IterationDeterminer id = new IterationDeterminer();
		 * id.beginCalculations();
		 */
	}
}
