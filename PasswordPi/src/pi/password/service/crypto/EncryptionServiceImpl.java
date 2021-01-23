package pi.password.service.crypto;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionServiceImpl implements EncryptionService {
	
	private static final String ALGORITHM = "AES";
	private static final int SALT_LENGTH = 32;
	private static final char [] SALT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	private final Encoder ENCODER = Base64.getEncoder();
	private final Decoder DECODER = Base64.getDecoder();
	
	private final MessageDigest DIGEST;
	private final Cipher CIPHER_ENCRYPT;
	private final Cipher CIPHER_DECRYPT;
	
	public EncryptionServiceImpl() {
		MessageDigest digest = null;
		Cipher cEnc = null;
		Cipher cDec = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			cEnc = Cipher.getInstance(ALGORITHM);
			cDec = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		DIGEST = digest;
		CIPHER_ENCRYPT = cEnc;
		CIPHER_DECRYPT = cDec;
	}
	
	@Override
	public String generateSalt() {
		String ret = "";
		
		for (int i = 0; i < SALT_LENGTH; i++) {
			ret += SALT_ALPHABET[((int)(Math.random() * SALT_ALPHABET.length))];
		}
		
		return ret;
	}

	@Override
	public byte[] hashData(String data) {
		byte[] ret = DIGEST.digest(data.getBytes());
		return ret;
	}

	@Override
	public byte[] encrpyt(byte[] data, String key) {
		byte[] ret = new byte[] {};

		try {
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), 0, key.getBytes().length, ALGORITHM);
			CIPHER_ENCRYPT.init(Cipher.ENCRYPT_MODE, secretKey);
			ret = CIPHER_ENCRYPT.doFinal(data);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public byte[] decrypt(byte[] data, String key) {
		byte [] ret = new byte[] {};
		
		try {
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), 0, key.getBytes().length, ALGORITHM);
			CIPHER_DECRYPT.init(Cipher.DECRYPT_MODE, secretKey);
			ret = CIPHER_DECRYPT.doFinal(data);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public String encodeToBase64(byte[] data) {
		return ENCODER.encodeToString(data);
	}

	@Override
	public byte[] decodeFromBase64(String data) {
		return DECODER.decode(data);
	}
	
}
