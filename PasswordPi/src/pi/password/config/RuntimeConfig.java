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
import com.waveshare.display.LcdDisplay;
import com.waveshare.display.buffered.BufferedLcdDisplay;
import com.waveshare.keyboard.HatKeyboard;
import com.waveshare.keyboard.hat.HatKeyboardImpl;

import pi.password.gui.passwords.PasswordController;
import pi.password.gui.screenlock.ScreenlockController;
import pi.password.gui.splash.SplashController;
import pi.password.gui.vaultManager.VaultManagerListController;
import pi.password.service.alphabet.AlphabetService;
import pi.password.service.alphabet.AlphabetServiceImpl;
import pi.password.service.gui.DialogService;
import pi.password.service.gui.DialogServiceImpl;
import pi.password.service.gui.TextEditorService;
import pi.password.service.gui.TextEditorServiceImpl;
import pi.password.service.hat.DisplayService;
import pi.password.service.hat.DisplayServiceWaveshareHat;
import pi.password.service.hat.KeyInputService;
import pi.password.service.hat.KeyInputServiceWaveshareHat;
import pi.password.service.keyboard.KeyboardService;
import pi.password.service.keyboard.KeyboardServiceUs;
import pi.password.service.lock.LockService;
import pi.password.service.lock.LockServiceFileSystem;
import pi.password.service.password.PasswordVaultService;
import pi.password.service.password.PasswordVaultServiceFileSystem;
import pi.password.service.settings.SettingsService;
import pi.password.service.settings.SettingsServiceFilesystem;
import pi.password.service.settings.SettingsUpdatePropagatorService;
import pi.password.service.settings.SettingsUpdatePropagatorServiceImpl;
import pi.password.service.template.TemplateService;
import pi.password.service.template.TemplateServiceMustache;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.ImageUtilServiceFileSystem;
import pi.password.service.webserver.WebserverService;
import pi.password.service.webserver.WebserverServiceImpl;

public enum RuntimeConfig {
	
	DI_IMPL_ALPHABET("impl." + AlphabetService.class.getCanonicalName(), AlphabetServiceImpl.class.getCanonicalName()),
	DI_IMPL_LCD_DISPLAY("impl." + LcdDisplay.class.getCanonicalName(), BufferedLcdDisplay.class.getCanonicalName()),
	DI_IMPL_HAT_KEYBOARD("impl." + HatKeyboard.class.getCanonicalName(), HatKeyboardImpl.class.getCanonicalName()),
	DI_IMPL_DIALOG_SERVICE("impl." + DialogService.class.getCanonicalName(), DialogServiceImpl.class.getCanonicalName()),
	DI_IMPL_DISPLAY_SERVICE("impl." + DisplayService.class.getCanonicalName(), DisplayServiceWaveshareHat.class.getCanonicalName()),
	DI_IMPL_GUI_PASSWORD("impl." + PasswordController.class.getCanonicalName(), PasswordController.class.getCanonicalName()),
	DI_TYPE_GUI_PASSWORD("type." + PasswordController.class.getCanonicalName(), ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_SCREENLOCK("impl." + ScreenlockController.class.getCanonicalName(), ScreenlockController.class.getCanonicalName()),
	DI_TYPE_GUI_SCREENLOCK("type." + ScreenlockController.class.getCanonicalName(), ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_SETTINGS("impl." + ScreenlockController.class.getCanonicalName(), ScreenlockController.class.getCanonicalName()),
	DI_TYPE_GUI_SETTINGS("type." + ScreenlockController.class.getCanonicalName(), ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_SPLASH("impl." + SplashController.class.getCanonicalName(), SplashController.class.getCanonicalName()),
	DI_TYPE_GUI_SPLASH("type." + SplashController.class.getCanonicalName(), ServiceClassType.MULTITON.toString()),
	DI_IMPL_GUI_VAULT("impl." + VaultManagerListController.class.getCanonicalName(), VaultManagerListController.class.getCanonicalName()),
	DI_TYPE_GUI_VAULT("type." + VaultManagerListController.class.getCanonicalName(), ServiceClassType.MULTITON.toString()),
	DI_IMPL_IMAGE_UTIL_SERVICE("impl." + ImageUtilService.class.getCanonicalName(), ImageUtilServiceFileSystem.class.getCanonicalName()),
	DI_IMPL_KEY_INPUT_SERVICE("impl." + KeyInputService.class.getCanonicalName(), KeyInputServiceWaveshareHat.class.getCanonicalName()),
	DI_IMPL_KEYBOARD_SERVICE("impl." + KeyboardService.class.getCanonicalName(), KeyboardServiceUs.class.getCanonicalName()),
	DI_TYPE_KEYBOARD_SERVICE("type." + KeyboardService.class.getCanonicalName(), ServiceClassType.MULTITON.toString()),
	DI_IMPL_LOCK_SERVICE("impl." + LockService.class.getCanonicalName(), LockServiceFileSystem.class.getCanonicalName()),
	DI_IMPL_PASSWORD_VAULT("impl." + PasswordVaultService.class.getCanonicalName(), PasswordVaultServiceFileSystem.class.getCanonicalName()),
	DI_IMPL_SETTINGS_SERVICE("impl." + SettingsService.class.getCanonicalName(), SettingsServiceFilesystem.class.getCanonicalName()),
	DI_IMPL_SETTINGS_UPDATE_SERVICE("impl." + SettingsUpdatePropagatorService.class.getCanonicalName(), SettingsUpdatePropagatorServiceImpl.class.getCanonicalName()),
	DI_IMPL_TEXT_EDITOR_SERVICE("impl." + TextEditorService.class.getCanonicalName(), TextEditorServiceImpl.class.getCanonicalName()),
	DI_IMPL_TEMPLATE_SERVICE("impl." + TemplateService.class.getCanonicalName(), TemplateServiceMustache.class.getCanonicalName()),
	DI_IMPL_WEBSERVER_SERVICE("impl." + WebserverService.class.getCanonicalName(), WebserverServiceImpl.class.getCanonicalName()),
	
	BACKGROUND_SCREEN_LOCEKD("background.screen.locked", "img/lock.bmp"),
	BACKGROUND_SCREEN_PASSWORD("background.screen.password", "img/background.bmp"),
	BACKGROUND_SCREEN_SETTINGS("background.screen.settings", "img/settings.bmp"),
	BACKGROUND_SCREEN_VAULT("background.screen.vault", "img/vault.bmp"),
	
	ICON_WIFI_ON("icon.wifi.on", "img/icon_wifi_on.png"),
	ICON_WIFI_OFF("icon.wifi.off", "img/icon_wifi_off.png"),
	
	ALPHABET_PASSWORD("alphabet.password", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-*/?.,$#@%^&{}[]()<>;:"),
	ALPHABET_REGULAR("alphabet.regular", " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.?()"),
	
	WORKING_DIRECTORY("working.dir", "."),
	
	WEB_DIRECTORY("web.dir", "./web/"),
	
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
