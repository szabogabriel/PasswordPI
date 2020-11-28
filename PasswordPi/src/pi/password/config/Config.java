package pi.password.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Config {
	
	BACKGROUND_SCREEN_LOCEKD("background.screen.locked", "img/lock.bmp"),
	BACKGROUND_SCREEN_PASSWORD("background.screen.password", "img/background.bmp"),
	BACKGROUND_SCREEN_SETTINGS("background.screen.settings", "img/settings.bmp"),
	BACKGROUND_SCREEN_VAULT("background.screen.vault", "img/vault.bmp"),
	
	VAULT_FILESYSTEM_FILE("vault.filesystem.file", "passwords.properties"),
	;
	
	private static final File PROPERTIES_FILE = new File("./settings.properties");
	private static final Properties CONFIG_VALUES = new Properties();
	
	static {
		if (PROPERTIES_FILE.exists() && PROPERTIES_FILE.isFile()) {
			try (InputStream in = new FileInputStream(PROPERTIES_FILE)) {
				CONFIG_VALUES.load(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private final String key;
	private final String defaultValue;
	
	private Config(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String toString() {
		return CONFIG_VALUES.getProperty(key, defaultValue);
	}

}
