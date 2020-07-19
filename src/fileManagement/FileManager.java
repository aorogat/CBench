
package fileManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

	public static String readFileLineByLine(String fileDirectory) {
		FileReader in;
		String fileContents = "";
		try {
			in = new FileReader(fileDirectory);
			BufferedReader br = new BufferedReader(in);
			String line;
		    while((line = br.readLine()) != null) {
		    	fileContents += line + "\n";
		    }
		    br.close();
		    in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileDirectory);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading the file " + fileDirectory);
			e.printStackTrace();
		}
		return fileContents;
	}
	
	public static String readWholeFile(String fileDirectory) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(fileDirectory));
			return new String(encoded, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
