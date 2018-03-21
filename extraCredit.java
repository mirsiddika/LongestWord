package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;




public class extraCredit {

	
		
		public void createNewFile(String fileName) {
			PrintWriter outStream = null;//notice this is not inside the try block	
			try {
				outStream = new PrintWriter(fileName);//create a new file
			}catch (FileNotFoundException e) {
				System.err.println("Could not create the file "+fileName+ " MESSAGE: "+e.getMessage());
			}
			finally{
				if(outStream != null){
					outStream.close();
				}
			}
			System.out.println("DONE CREATING NEW FILE"+fileName);
		}
	
public void writeToNewFile(String fileName, String text) {
	PrintWriter outStream = null;
	try{
		outStream = new PrintWriter(fileName);//create a new file
		outStream.println(text);//write to the new file
	}catch(FileNotFoundException e){
		System.err.println("Could not create the file "+fileName+ " MESSAGE: "+e.getMessage());
	}
	finally{//do clean up
		if(outStream != null){
			outStream.close();
		}
	}
	System.out.println("DONE CREATING new file and writing to it "+fileName);
}

	
	public static void appendToFile(String fileName, String text) {
		PrintWriter outStream = null;
		try{
			outStream = new PrintWriter(new FileOutputStream(fileName, true));//true tells computer we want to keep previous content
			outStream.println(text);//append to the file
		}catch(FileNotFoundException e){
			System.err.println("Could not append to the file "+fileName+ " MESSAGE: "+e.getMessage());
		}
		finally{//do clean up
			if(outStream != null){
				outStream.close();
			}
		}
	}
}
		
	


