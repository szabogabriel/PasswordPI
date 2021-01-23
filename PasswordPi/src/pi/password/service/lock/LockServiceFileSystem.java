package pi.password.service.lock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

import pi.password.config.RuntimeConfig;
import pi.password.service.crypto.EncryptionService;

public class LockServiceFileSystem implements LockService {
	
	private final String LOCK_FILE_POSTFIX = ".lock";
	private final String MASTER_LOCK_FILE_POSTFIX = ".master";
	private final File LOCK_DIR = new File(RuntimeConfig.WORKING_DIRECTORY.toString());
	
	private final EncryptionService ENCRYPTION;
	
	private String masterKey = null;
	
	private boolean isScreenLocked;
	
	public LockServiceFileSystem(EncryptionService encryption) {
		this.ENCRYPTION = encryption;
		isScreenLocked = isLockSet();
	}
	
	@Override
	public boolean isLockSet() {
		return getScreenLockFile().isPresent();
	}

	@Override
	public boolean isLocked() {
		return isScreenLocked;
	}

	@Override
	public void lock() {
		if (isLockSet()) {
			isScreenLocked = true;
		}
	}
	
	@Override
	public boolean unlock(String key) {
		boolean ret = false;
		
		if (isLockSet()) {
			try {
				String storedData = new String(Files.readAllBytes(getScreenLockFile().get().toPath()));
				String computed = ENCRYPTION.encodeToBase64(ENCRYPTION.encrpyt(key.getBytes(), masterKey));
				
				if (storedData != null && storedData.equals(computed)) {
					isScreenLocked = false;
					ret = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ret = true;
		}
		
		return ret;
	}

	@Override
	public boolean isMasterLockSet() {
		return getMasterLockFile().isPresent();
	}

	@Override
	public boolean isMasterLocked() {
		return masterKey == null;
	}

	@Override
	public void masterLock() {
		masterKey = null;
	}

	@Override
	public boolean unlockMaster(String key) {
		//TODO: the logic here only changes the password, but we still need to migrate the stored data!!!
		boolean ret = false;
		masterKey = null;
		Optional<File> masterLockFile = getMasterLockFile();
		
		if (masterLockFile.isPresent()) {
			File tmp = masterLockFile.get();
			
			try {
				// salt derived by the file name
				String salt = tmp.getName().substring(0, tmp.getName().indexOf(MASTER_LOCK_FILE_POSTFIX));
				String savedHash = new String(Files.readAllBytes(tmp.toPath()));
				String countedHash = ENCRYPTION.encodeToBase64(ENCRYPTION.hashData(combinePasswordWithSalt(key, salt)));
				
				if (savedHash != null && savedHash.equals(countedHash)) {
					ret = true;
					masterKey = key;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ret = true;
		}
		
		return ret;
	}

	@Override
	public boolean updateLockKey(String oldKey, String newKey) {
		File fileToWrite = null;
		
		if (isLockSet()) {
			if (unlock(oldKey) && newKey != null) {
				fileToWrite = getScreenLockFile().get();
			}
		} else {
			fileToWrite = new File(LOCK_DIR.getAbsoluteFile() + "/" + ENCRYPTION.generateSalt() + LOCK_FILE_POSTFIX);
		}
		
		if (fileToWrite != null) {
			String encryptedNewKey = ENCRYPTION.encodeToBase64(ENCRYPTION.encrpyt(newKey.getBytes(), masterKey));
			try {
				Files.write(fileToWrite.toPath(), encryptedNewKey.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return fileToWrite != null;
	}

	@Override
	public boolean updateMasterKey(String oldKey, String newKey) {
		boolean ret = false;
		
		if (unlockMaster(oldKey)) {
			Optional<File> tmp = getMasterLockFile();
			
			if (tmp.isPresent()) {
				tmp.get().delete();
			}
			
			String salt = ENCRYPTION.generateSalt();
			
			File newMasterFile = new File(LOCK_DIR.getAbsoluteFile() + "/" + salt + MASTER_LOCK_FILE_POSTFIX);
			
			String newHash = ENCRYPTION.encodeToBase64(ENCRYPTION.hashData(combinePasswordWithSalt(newKey, salt)));
			
			try {
				Files.write(newMasterFile.toPath(), newHash.getBytes());
				masterKey = newKey;
				ret = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			masterKey = null;
		}
		
		return ret;
	}
	
	private String combinePasswordWithSalt(String password, String salt) {
		return password + salt;
	}
	
	private Optional<File> getScreenLockFile() {
		return getLockFile(LOCK_FILE_POSTFIX);
	}
	
	private Optional<File> getMasterLockFile() {
		return getLockFile(MASTER_LOCK_FILE_POSTFIX);
	}
	
	private Optional<File> getLockFile(String postfix) {
		Optional<File> ret = Arrays.asList(LOCK_DIR.listFiles()).stream()
				.filter(f -> f.isFile())
				.filter(f -> f.getName().endsWith(postfix))
				.findAny();
			
			return ret;
	}

}
