package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Stack;

public class CSVManager{
	
	
	private Stack<String[]> toWrite;
	private File fileToManage;
	private PrintWriter forFile;
	
	public CSVManager(String fileToManage) {
		toWrite = new Stack<>();
		
		this.fileToManage = new File(fileToManage);
		
		try {
			forFile = new PrintWriter(this.fileToManage);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void write() {
		// TODO Auto-generated method stub
		while(true) {
			if(!toWrite.isEmpty()) {
				
				String[] dat = toWrite.pop();
				
				try {
					
					for(int i = 0; i < dat.length; i++) {
						forFile.append(dat[i]);
						
						if(i < dat.length - 1) {
							forFile.append(",");
						}
					}
					
					forFile.append("\n");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			} else {
				break;
			}
		}
		
		
		forFile.flush();
	}

	public Stack<String[]> getToWrite() {
		return toWrite;
	}


	public void setToWrite(Stack<String[]> toWrite) {
		this.toWrite = toWrite;
	}
	
	public void pushData(String[] data) {
		toWrite.push(data);
	}
	
}
