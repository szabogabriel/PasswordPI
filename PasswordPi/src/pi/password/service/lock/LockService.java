package pi.password.service.lock;

public interface LockService {
	
	boolean isLockSet();
	
	boolean isMasterLockSet();
	
	boolean isLocked();
	
	boolean isMasterLocked();
	
	void lock();
	
	void masterLock();
	
	boolean unlock(String key);
	
	boolean unlockMaster(String key);
	
	boolean updateLockKey(String oldKey, String newKey);
	
	boolean updateMasterKey(String oldKey, String newKey);
	
}
