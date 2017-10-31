import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class MD5Exam {

	public static void main (String args[]) {
		String passwordToHash = "password";
		//String generatedPass = null;
		final byte [] salt;
		BufferedReader in;
		String PassEnterred;
		boolean bCheckPass=false;
		//byte [] salt = null;
		
		try {
			salt = genSalt();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			md.update(salt);
			byte[] bytePass = md.digest(passwordToHash.getBytes());
			
			//Convert to Hex
/*			StringBuilder sb = new StringBuilder();
			for (int i=0; i<bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff), 16));

			}
			generatedPass = sb.toString();
			
			System.out.println(generatedPass);*/
			
			in = new BufferedReader(new InputStreamReader(System.in));
			do {
				//Enter from command line
				System.out.println("Enter password to Exit");
				PassEnterred = in.readLine();
								
				//Hashed new password entered to check
				MessageDigest mdE = MessageDigest.getInstance("SHA-256");
				mdE.update(salt);
				byte [] bytePassEnterred = mdE.digest(PassEnterred.getBytes());
				
				//Check hash
				if (Arrays.equals(bytePass, bytePassEnterred)) {
				//if (Arrays.hashCode(bytePass) == Arrays.hashCode(bytePassEnterred)) {
					bCheckPass = true;
					System.out.println("Right! now quite.");
				} else {
					System.out.println("Wrong!");
				}
				
			} while (!bCheckPass);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	//Generate Salt (just optional to MD5)
	public static byte[] genSalt() throws NoSuchAlgorithmException {
		//SHA1PRNG algorithm is used as cryptographically strong pseudo-random number generator based on the SHA-1
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		
		byte [] salt = new byte [16];
		sr.nextBytes(salt);
		return salt;
		
	}
	
/*	public static char getHex (byte b) {
		Byte bObj = new Byte (b);
		if (b<10) {
			return bObj.toString().charAt(0);
		} else {
			char hexChar = (char) (bObj.toString().charAt(1) + 'A' -'0');
			return hexChar;
		}
	}*/
	//str += Integer.toHexString(bytes[i]);
	//System.out.println(bytes[i]);
	//sb.append(Integer.toString(bytes[i]));
}
