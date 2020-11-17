package pi.password.manager.filesystem;

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

import pi.password.manager.PasswordEntity;
import pi.password.manager.PasswordVault;

public class FilesystemPasswordVault implements PasswordVault {

	private final File VAULT;

	private Properties PROPERTIES;
	
	private long lastLoaded;

	public FilesystemPasswordVault(String fileName) {
		VAULT = new File(fileName);
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

	@Override
	public Set<PasswordEntity> listPasswordEntities() {
		return PROPERTIES.keySet().stream().map(k -> new PasswordEntity(k.toString(), PROPERTIES.get(k).toString()))
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<PasswordEntity> findPasswordEntity(String name) {
		Optional<PasswordEntity> ret = Optional.empty();
		if (isKnownPasswordEntity(name)) {
			Optional.of(PROPERTIES.getProperty(name));
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
		
		PROPERTIES.put(name, password);
		
		try {
			PROPERTIES.store(new FileOutputStream(VAULT), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> listPasswordEntityNames() {
		return PROPERTIES.keySet().stream().map(o -> o.toString()).sorted().collect(Collectors.toList());
	}

}
