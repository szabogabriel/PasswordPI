package pi.password.service.password;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Timer;

import pi.password.config.RuntimeConfig;
import pi.password.entity.PasswordEntity;
import pi.password.service.crypto.EncryptionService;
import pi.password.service.lock.LockService;

public class PasswordVaultServiceFileSystem implements PasswordVaultService {

	private final EncryptionService ENCRYPTION_SERVICE;
	private final LockService LOCK_SERVICE;
	private final File VAULT;

	private Properties PROPERTIES;
	
	private long lastLoaded;
	
	public PasswordVaultServiceFileSystem(EncryptionService encryptionService, LockService lockService) {
		this.ENCRYPTION_SERVICE = encryptionService;
		this.LOCK_SERVICE = lockService;
		VAULT = new File(RuntimeConfig.VAULT_FILESYSTEM_FILE.toString());
		PROPERTIES = new Properties();
		loadPasswords();
		new Timer(300000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkForUpdates();
			}
		});
	}
	
	private void checkForUpdates() {
		long lastModified = VAULT.lastModified();
		if (lastModified > lastLoaded) {
			loadPasswords();
		}
	}
	
	private void loadPasswords() {
		try {
			PROPERTIES.load(new FileInputStream(VAULT));
			lastLoaded = System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveProperties() {
		try {
			PROPERTIES.store(new FileOutputStream(VAULT), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<PasswordEntity> listPasswordEntities() {
		return PROPERTIES.keySet().stream()
				.map(k -> createPasswordEntity(k.toString(), PROPERTIES.get(k).toString()))
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<PasswordEntity> findPasswordEntity(String name) {
		Optional<PasswordEntity> ret = Optional.empty();
		if (isKnownPasswordEntity(name)) {
			String psswd = PROPERTIES.getProperty(name);
			PasswordEntity tmp = createPasswordEntity(name, psswd);
			ret = Optional.of(tmp);
		}
		return ret;
	}
	
	@Override
	public boolean isKnownPasswordEntity(String name) {
		return PROPERTIES.containsKey(name);
	}

	@Override
	public void update(String name, String password) {
		checkForUpdates();
		
		PROPERTIES.put(name, encryptPassword(password));
		
		saveProperties();
	}

	@Override
	public List<String> listPasswordEntityNames() {
		return PROPERTIES.keySet().stream()
				.map(o -> o.toString())
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	public void remove(String name) {
		if (PROPERTIES.containsKey(name)) {
			PROPERTIES.remove(name);
			saveProperties();
		}
	}

	@Override
	public boolean add(String name, String password) {
		boolean ret = false;
		if (!PROPERTIES.contains(name)) {
			PROPERTIES.put(name, encryptPassword(password));
			saveProperties();
			ret = true;
		}
		return ret;
	}
	
	private PasswordEntity createPasswordEntity(String name, String password) {
		String encryptedPassword = decryptPassword(password);
		PasswordEntity ret = new PasswordEntity(name, encryptedPassword);
		return ret;
	}
	
	private String decryptPassword(String encryptedPassword) {
		String ret = new String(ENCRYPTION_SERVICE.decrypt(ENCRYPTION_SERVICE.decodeFromBase64(encryptedPassword), masterKey()));
		return ret;
	}
	
	private String encryptPassword(String password) {
		String ret = ENCRYPTION_SERVICE.encodeToBase64(ENCRYPTION_SERVICE.encrpyt(password.getBytes(), masterKey()));
		return ret;
	}
	
	private String masterKey() {
		return LOCK_SERVICE.getMasterKeyForEncryption();
	}

}
