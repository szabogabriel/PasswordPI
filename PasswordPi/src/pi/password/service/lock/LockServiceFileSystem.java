package pi.password.service.lock;

import java.io.File;

import pi.password.config.RuntimeConfig;

public class LockServiceFileSystem implements LockService {
	
	private static final String LOCK_FILE_POSTFIX = ".lock";
	
	private final File LOCK_DIR = new File(RuntimeConfig.WORKING_DIRECTORY.toString());
	
	public LockServiceFileSystem() {
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
		return false;
	}

	@Override
	public boolean isMasterLockSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMasterLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void masterLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean unlockMaster(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateLockKey(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMasterKey(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
