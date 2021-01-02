package pi.password.service.lock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

import pi.password.Main;
import pi.password.config.RuntimeConfig;
import pi.password.service.util.RandomUtil;

public class LockServiceFileSystem implements LockService {
	
	private static final String LOCK_FILE_POSTFIX = ".lock";
	
	private final File LOCK_DIR = new File(RuntimeConfig.WORKING_DIRECTORY.toString());
	
	private Optional<File> lockFile = Optional.empty();
	
	private boolean locked = true;
	
	public LockServiceFileSystem() {
		lockFile = Arrays.asList(LOCK_DIR.listFiles())
			.stream()
			.filter(f -> f.getName().endsWith(LOCK_FILE_POSTFIX))
			.findAny();
	}
	
	@Override
	public boolean isLockSet() {
		return lockFile.isPresent();
	}

	@Override
	public boolean isLocked() {
		return isLockSet() && locked;
	}

	@Override
	public void lock() {
		locked = true;
	}
	
	@Override
	public boolean unlock(String key) {
		if (lockFile.isPresent()) {
			File lock = lockFile.get();
			String salt = lock.getName().substring(0, lock.getName().indexOf(LOCK_FILE_POSTFIX));
			String tmpKey = key + salt;
			try {
				String lockFileContent = new String(Files.readAllBytes(lock.toPath()));
				return lockFileContent.contains(tmpKey.hashCode() + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public boolean setInitialKey(String key, boolean overwrite) {
		boolean ret = false;
		
		if (lockFile.isPresent() && overwrite) {
			lockFile.get().delete();
			ret = createLockFile(key);
		} else
		if (!lockFile.isPresent()) {
			ret = createLockFile(key);
		}
		
		return ret;
	}
	
	private boolean createLockFile(String key) {
		String salt = Main.getInstance(RandomUtil.class).getSalt();
		File targetFile = new File(LOCK_DIR.getAbsolutePath() + "/" + salt + LOCK_FILE_POSTFIX);
		
		String dataToBeHashed = key + salt;
		String hashedData = dataToBeHashed.hashCode() + "";
		
		try {
			Files.write(targetFile.toPath(), hashedData.getBytes());
			lockFile = Optional.of(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
