package pi.password.service.lock;

public interface LockService {
	
	boolean isLockSet();
	
	boolean isLocked();
	
	void lock();
	
	boolean unlock(String key);
	
	boolean setInitialKey(String key, boolean overwrite);
	
}
