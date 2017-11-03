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
    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String CHARSET = "UTF-8";
    
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final byte[] IV_BYTE = 
    	{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    
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
    

}
