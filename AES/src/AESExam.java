import java.security.Key;
import java.util.Base64;
import java.util.logging.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESExam {
   
    private static final String TAG = "AESCrypt";
    private static final String AES_MODE = "AES/CBC/PKCS5Padding";
    private static final String CHARSET = "UTF-8";
    
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final byte[] IV_BYTE = 
    	{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    
    private static final String PASS = "123456a@"; 
    private static String MESG = "Hello cryptographic world!";
    
//    Generate SHA256 hash of the password which is used as key
    private static SecretKeySpec genKey (final String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	final MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
    	byte[] bytes = password.getBytes("UTF-8");
    	digest.update(bytes, 0, bytes.length);
    	byte[] key = digest.digest();
    	
    	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
    	    	
    	return secretKey;
    }
    
//    Convert byte array to Hex
    private static String byteToHex (byte[] bytes) {
    	final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    	char[] hexChar = new char [bytes.length*2];
    	int i;
    	for (int j=0; j<bytes.length; j++) {
    		i = bytes[j] & 0xFF;
    		hexChar [j*2] = hexArray [i >>> 4];
    		hexChar [j*2 + 1] = hexArray [i  & 0x0F];
    	}
    	return new String (hexChar);
    }
    
//	Encrypt message using ARS-256
//    Input: AES key-256bit, init vector, plain text
//    Output: Encrypted text
    public static byte[] encrypt (final SecretKeySpec key, final byte[] iv, final byte[] message)
    		throws GeneralSecurityException {
    	final Cipher cipher = Cipher.getInstance(AES_MODE);
    	IvParameterSpec ivSpec = new IvParameterSpec(iv);

    	cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
    	byte[] cypherText = cipher.doFinal(message);
    	
       	System.out.println("Input Plain text: " + byteToHex(message));
    	System.out.println("Cipher text: " + byteToHex(cypherText));
    	
    	return cypherText;
    			
    }
    
    /*
     * Decrypt message
     * Input:
     * Output:
     */
    public static byte[] decrypt(final SecretKeySpec key, final byte[] iv, final byte[] encryptedMesg)
    		throws GeneralSecurityException {
    	final Cipher cipher = Cipher.getInstance(AES_MODE);
    	IvParameterSpec ivSpec = new IvParameterSpec(iv);
    		
    	cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
    	byte[] plainText = cipher.doFinal(encryptedMesg);
    	
    	
    	System.out.println("Input encrypted text: " + byteToHex(encryptedMesg));
    	System.out.println("Plant Text: " + byteToHex(plainText));
    	
    	return plainText;
    }
    
    
//    Main app
    public static void main (String args[]) {
    	try {
    		SecretKeySpec sKey = genKey(PASS);
    		byte[] bMesg = MESG.getBytes();
    		byte[] encryptMesg = encrypt (sKey, IV_BYTE, bMesg);
    		
    		SecretKeySpec dKey = genKey(PASS);
    		byte[] decryptMesg = decrypt(dKey, IV_BYTE, encryptMesg);
    		
    		//System.out.println(decryptMesg);
    		
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	} catch (GeneralSecurityException e) {
    		e.printStackTrace();
    	}
    	
    }
}
