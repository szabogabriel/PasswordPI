package pi.password.service.crypto;

public interface EncryptionService {
	
	String generateSalt();
	
	byte[] hashData(String data);
	
	byte[] encrpyt(byte[] data, String key);
	
	byte[] decrypt(byte[] data, String key);
	
	String encodeToBase64(byte[] data);
	
	byte[] decodeFromBase64(String data);

}
