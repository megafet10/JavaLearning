/**
 * 
 */

/**
 * @author minhbq
 *
 */
import java.io.*;
import java.util.Scanner;
//import java.util.*;

public class FileStreamIO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dirname = ".\\output\\";
		String filename[] = {"test.txt", "file.txt", "example.txt"};
		String filelist[];
		for (int i = 0; i<3; i++) {
			filename[i] = dirname + filename[i];
		}
			
		try {
			String sWrite [] = {"Hello world!", "This is message from CEO", "File stream example"};
			File d = new File(dirname);
			d.mkdirs();
			
			for (int i = 0; i<3; i++) {
				FileWriter f = new FileWriter(filename[i]);
				BufferedWriter out = new BufferedWriter(f);
				out.write(sWrite[i]);		
				out.close();
			}
			
			filelist = d.list();
			
			for (int i = 0; i<filelist.length; i++) {
				System.out.println("["+ (i+ 1) +"] " + filelist[i]);
			}
			
			Scanner reader = new Scanner(System.in);

			char c;
			int b;
			String s;
			for (;;) {
				System.out.println("Select a file to open, enter 0 to exit");
				c = reader.next().charAt(0);

				if (Character.isDigit(c)) {
					b = Character.getNumericValue(c);
				} else continue;
				
				if (b == 0) {
					break;
				} else if (b > filelist.length) {
					continue;
				} else {
					String fi= dirname + filelist[b-1];
					FileReader f = new FileReader(fi);
					BufferedReader pr = new BufferedReader(f);
					s= pr.readLine();
					System.out.println("The content: " + s);
					pr.close();	
				} 
				
			}
			reader.close();

			
			//char c = reader.next().charAt(0);
			
			//InputStreamReader cin = new InputStreamReader(System.in);
			/*OutputStream os = new FileOutputStream(filename);
			for (char b : bWrite) {
				os.write(b);
				 FileUtils.writeStringToFile()
			}
			os.close();
			
			InputStream is = new FileInputStream(filename);
			int size = is.available();
			
			for (int i = 0; i<size; i++) {
				System.out.println((char) is.read());
			}
			is.close();*/
		} catch (IOException e) {
			System.out.println("Exception " + e);
		}
	}

}
