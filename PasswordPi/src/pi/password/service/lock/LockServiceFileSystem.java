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
	
	private boolean isMasterLocked;
	
	private String masterKey = null;
	
	public LockServiceFileSystem(EncryptionService encryption) {
		this.ENCRYPTION = encryption;
		isMasterLocked = isMasterLocked();
	}
	
	@Override
	public boolean isLockSet() {
		return false;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public void lock() {
	}
	
	@Override
	public boolean unlock(String key) {
		return true;
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
		}
		
		return ret;
	}

	@Override
	public boolean updateLockKey(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMasterKey(String oldKey, String newKey) {
		boolean ret = false;
		
		if (unlockMaster(oldKey)) {
			File tmp = getMasterLockFile().get();
			
			tmp.delete();
			
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
	
	private Optional<File> getMasterLockFile() {
		Optional<File> ret = Arrays.asList(LOCK_DIR.listFiles()).stream()
			.filter(f -> f.isFile())
			.filter(f -> f.getName().endsWith(MASTER_LOCK_FILE_POSTFIX))
			.findAny();
		
		return ret;
	}

}
