package main;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordSalt {
	public static int Iteration = 250;
	public static String[] saltPassword(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException{
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
				Iteration, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(
				"PBKDF2WithHmacSHA1");
		
		String[] passwordAndSalt = new String[2];

	    byte[] hash = factory.generateSecret(spec).getEncoded();
	    passwordAndSalt[0] = toHex(salt);
	    passwordAndSalt[1] = toHex(hash);
		return passwordAndSalt;
	}
	
	public static boolean validatePassword(String inputPassword,
										  String salt,
										  String storePassword)
		throws NoSuchAlgorithmException, InvalidKeySpecException{
		
		byte[] saltInByte = fromHex(salt);
		byte[] passwordInByte = fromHex(storePassword);
	    PBEKeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), 
	    		saltInByte, Iteration, passwordInByte.length * 8);
	    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    byte[] testHash = skf.generateSecret(spec).getEncoded();
	        
	    int diff = passwordInByte.length ^ testHash.length;
	    for(int i = 0; (i < passwordInByte.length) && (i < testHash.length); i++)
	    {
	        diff |= passwordInByte[i] ^ testHash[i];
	    }
	    return diff == 0;
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
	    BigInteger bi = new BigInteger(1, array);
	    String hex = bi.toString(16);
	    
	    int paddingLength = (array.length * 2) - hex.length();
	    if(paddingLength > 0)
	    {
	        return String.format("%0"  +paddingLength + "d", 0) + hex;
	    }else{
	        return hex;
	    }
	}
	
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
	    byte[] bytes = new byte[hex.length() / 2];
	    for(int i = 0; i < bytes.length ;i++)
	    {
	        bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return bytes;
	}
}
