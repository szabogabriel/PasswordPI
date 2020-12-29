package pi.password.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import com.jdi.ServiceClassType;
import com.waveshare.display.buffered.BufferedLcdDisplay;
import com.waveshare.keyboard.hat.HatKeyboardImpl;

import pi.password.keyboard.us.KeyboardUs;
import pi.password.manager.filesystem.FilesystemPasswordVault;

public enum Config {
	
	DI_IMPL_LCD_DISPLAY("impl.com.waveshare.display.LcdDisplay", BufferedLcdDisplay.class.getCanonicalName()),
	DI_IMPL_HAT_KEYBOARD("impl.com.waveshare.keyboard.HatKeyboard", HatKeyboardImpl.class.getCanonicalName()),
	DI_IMPL_KEYBOARD_SERVICE("impl.pi.password.keyboard.KeyboardService", KeyboardUs.class.getCanonicalName()),
	DI_TYPE_KEYBOARD_SERVICE("type.pi.password.keyboard.KeyboardService", ServiceClassType.MULTITON.toString()),
	DI_IMPL_PASSWORD_VAULT("impl.pi.password.manager.PasswordVault", FilesystemPasswordVault.class.getCanonicalName()),
	
	BACKGROUND_SCREEN_LOCEKD("background.screen.locked", "img/lock.bmp"),
	BACKGROUND_SCREEN_PASSWORD("background.screen.password", "img/background.bmp"),
	BACKGROUND_SCREEN_SETTINGS("background.screen.settings", "img/settings.bmp"),
	BACKGROUND_SCREEN_VAULT("background.screen.vault", "img/vault.bmp"),
	
	ICON_WIFI_ON("icon.wifi.on", "img/icon_wifi_on.png"),
	ICON_WIFI_OFF("icon.wifi.off", "img/icon_wifi_off.png"),
	
	VAULT_TYPE("vault.type", "filesystem"),
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

	public static Optional<String> get(String key) {
		Optional<String> ret = Optional.empty();
		if (key != null && CONFIG_VALUES.containsKey(key)) {
			ret = Optional.of(CONFIG_VALUES.getProperty(key));
		}
		return ret;
	}
	
}
