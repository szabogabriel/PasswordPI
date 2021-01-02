package pi.password.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import com.jdi.ServiceClassType;
import com.waveshare.display.buffered.BufferedLcdDisplay;
import com.waveshare.keyboard.hat.HatKeyboardImpl;

import pi.password.gui.passwords.PasswordController;
import pi.password.gui.screenlock.ScreenlockController;
import pi.password.gui.splash.SplashController;
import pi.password.gui.vaultManager.VaultManagerController;
import pi.password.service.hat.DisplayServiceWaveshareHat;
import pi.password.service.hat.KeyInputServiceWaveshareHat;
import pi.password.service.keyboard.KeyboardServiceUs;
import pi.password.service.lock.LockServiceFileSystem;
import pi.password.service.password.PasswordVaultServiceFileSystem;
import pi.password.service.settings.SettingsServiceFilesystem;
import pi.password.service.util.ImageUtilServiceFileSystem;
import pi.password.service.webserver.WebserverServiceImpl;

public enum RuntimeConfig {
	
	DI_IMPL_LCD_DISPLAY("impl.com.waveshare.display.LcdDisplay", BufferedLcdDisplay.class.getCanonicalName()),
	DI_IMPL_HAT_KEYBOARD("impl.com.waveshare.keyboard.HatKeyboard", HatKeyboardImpl.class.getCanonicalName()),
	DI_IMPL_DISPLAY_SERVICE("impl.pi.password.service.hat.DisplayService", DisplayServiceWaveshareHat.class.getCanonicalName()),
	DI_IMPL_GUI_PASSWORD("impl.pi.password.gui.passwords.PasswordController", PasswordController.class.getCanonicalName()),
	DI_TYPE_GUI_PASSWORD("type.pi.password.gui.passwords.PasswordController", ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_SCREENLOCK("impl.pi.password.gui.screenlock.ScreenlockController", ScreenlockController.class.getCanonicalName()),
	DI_TYPE_GUI_SCREENLOCK("type.pi.password.gui.screenlock.ScreenlockController", ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_SETTINGS("impl.pi.password.gui.screenlock.ScreenlockController", ScreenlockController.class.getCanonicalName()),
	DI_TYPE_GUI_SETTINGS("type.pi.password.gui.screenlock.ScreenlockController", ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_SPLASH("impl.pi.password.gui.splash.SplashController", SplashController.class.getCanonicalName()),
	DI_TYPE_GUI_SPLASH("type.pi.password.gui.splash.SplashController", ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_VAULT("impl.pi.password.gui.vaultManager.VaultManagerController", VaultManagerController.class.getCanonicalName()),
	DI_TYPE_GUI_VAULT("type.pi.password.gui.vaultManager.VaultManagerController", ServiceClassType.MULTITON.toString()),
	DI_IMPL_IMAGE_UTIL_SERVICE("impl.pi.password.service.util.ImageUtilService", ImageUtilServiceFileSystem.class.getCanonicalName()),
	DI_IMPL_KEY_INPUT_SERVICE("impl.pi.password.service.hat.KeyInputService", KeyInputServiceWaveshareHat.class.getCanonicalName()),
	DI_IMPL_KEYBOARD_SERVICE("impl.pi.password.service.keyboard.KeyboardService", KeyboardServiceUs.class.getCanonicalName()),
	DI_TYPE_KEYBOARD_SERVICE("type.pi.password.service.keyboard.KeyboardService", ServiceClassType.MULTITON.toString()),
	DI_IMPL_LOCK_SERVICE("impl.pi.password.service.lock.LockService", LockServiceFileSystem.class.getCanonicalName()),
	DI_IMPL_SETTINGS_SERVICE("impl.pi.password.service.settings.SettingsService", SettingsServiceFilesystem.class.getCanonicalName()),
	DI_IMPL_PASSWORD_VAULT("impl.pi.password.service.password.PasswordVaultService", PasswordVaultServiceFileSystem.class.getCanonicalName()),
	DI_IMPL_WEBSERVER_SERVICE("impl.pi.password.service.webserver.WebserverService", WebserverServiceImpl.class.getCanonicalName()),
	
	BACKGROUND_SCREEN_LOCEKD("background.screen.locked", "img/lock.bmp"),
	BACKGROUND_SCREEN_PASSWORD("background.screen.password", "img/background.bmp"),
	BACKGROUND_SCREEN_SETTINGS("background.screen.settings", "img/settings.bmp"),
	BACKGROUND_SCREEN_VAULT("background.screen.vault", "img/vault.bmp"),
	
	ICON_WIFI_ON("icon.wifi.on", "img/icon_wifi_on.png"),
	ICON_WIFI_OFF("icon.wifi.off", "img/icon_wifi_off.png"),
	
	WORKING_DIRECTORY("working.dir", "."),
	
	VAULT_TYPE("vault.type", "filesystem"),
	VAULT_FILESYSTEM_FILE("vault.filesystem.file", "passwords.properties"),
	;
	
	private static final File PROPERTIES_FILE = new File("./runtime.properties");
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
	
	private RuntimeConfig(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey() {
		return key;
	}
	
	public String toString() {
		return CONFIG_VALUES.getProperty(key, defaultValue);
	}

	public static Optional<String> get(String key) {
		return Arrays.asList(values())
				.stream()
				.filter(v -> v.key.equals(key))
				.map(v -> v.toString())
				.findAny();
	}
	
}
