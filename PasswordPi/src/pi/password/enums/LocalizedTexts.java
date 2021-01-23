package pi.password.enums;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public enum LocalizedTexts {
	
	SETTINGS_KEY_LOCALE("settings.key.locale", "Language"),
	SETTINGS_KEY_KEYSTROKE("settings.key.keystroke", "Keystroke"),
	SETTINGS_KEY_DELAY_MIN("settings.key.delay.min", "Delay min"),
	SETTINGS_KEY_DELAY_MAX("settings.key.delay.max", "Delay max"),
	SETTINGS_KEY_WEBSERVER_STRICT("settings.key.webserver.strict", "Web strict"),
	SETTINGS_KEY_WEBSERVER_PORT("settings.key.webserver.port", "Web port"),
	SETTINGS_KEY_WEBSERVER_ENABLED("settings.key.webserver.enabled", "Webserver"),
	SETTINGS_VALUE_ON("settings.value.on", "On"),
	SETTINGS_VALUE_OFF("settings.value.off", "Off"),
	
	DIALOG_RESULT_YES("dialog.result.yes", "Yes"),
	DIALOG_RESULT_NO("dialog.result.no", "No"),
	DIALOG_RESULT_OK("dialog.result.ok", "Ok"),
	DIALOG_RESULT_EDIT("dialog.result.edit", "Edit"),
	DIALOG_RESULT_DELETE("dialog.result.delete", "Delete"),
	DIALOG_RESULT_CANCEL("dialog.result.cancel", "Cancel"),
	
	VIEW_NEW_MASTER_LINE_1("view.new_master.line_1", "Set new master"),
	VIEW_NEW_MASTER_LINE_2("view.new_master.line_2", "Password"),
	VIEW_NEW_MASTER_REPEAT("view.new_master.repeat", "Repeat"),
	VIEW_NEW_MASTER_CONFIRM("view.new_master.confirm", "Ok"),
	VIEW_NEW_MASTER_ENTER_MASTER("view.new_master.enter_master", "Enter master password"),
	VIEW_NEW_MASTER_REPEAT_MASTER("view.new_master.repeat_master", "Repeat master password"),
	VIEW_NEW_MASTER_DIALOG_HEADER("view.new_master.dialog.header", "Master"),
	VIEW_NEW_MASTER_TITLE("view.new_master.title", "LOCKED"),
	
	VIEW_PASSWORDS_TITLE("view.passwords.title", "Passwords"),
	
	VIEW_SETTINGS_TITLE("view.settings.title", "Settings"),
	
	VIEW_SPLASH_TITLE_OFFLINE("view.splash.title.offline", "Offline"),
	VIEW_SPLASH_BODY_PASSWORD("view.splash.body.passwords", "Passwords"),
	VIEW_SPLASH_BODY_VAULT("view.splash.body.vault", "Vault"),
	VIEW_SPLASH_BODY_SETTINGS("view.splash.body.settings", "Settings"),
	
	VIEW_VAULT_EDIT_TITLE("view.vault.edit.title", "Vault"),
	VIEW_VAULT_EDIT_DIALOG_TITLE("view.vault.edit.dialog.title", "Change"),
	VIEW_VAULT_EDIT_DIALOG_ENTER_NAME("view.vault.edit.dialog.enter_name", "Enter new name"),
	VIEW_VAULT_EDIT_DIALOG_ENTER_PASSWORD("view.vault.edit.dialog.enter_password", "New password for "),
	VIEW_VAULT_EDIT_BODY_NAME("view.vault.edit.body.name", "Name"),
	VIEW_VAULT_EDIT_BODY_PASSWORD("view.vault.edit.body.password", "Password"),
	VIEW_VAULT_EDIT_BODY_SAVE("view.vault.edit.body.save", "Save"),
	VIEW_VAULT_EDIT_BODY_CANCEL("view.vault.edit.body.cancel", "Cancel"),
	
	VIEW_VAULT_LIST_TITLE("view.vault.list.title", "Vault"),
	VIEW_VAULT_LIST_NEW("view.vault.list.new", "[New]"),
	VIEW_VAULT_LIST_DIALOG_TEXTS("view.vault.list.dialog.text", "Choose action for "),
	VIEW_VAULT_LIST_DIALOG_REMOVE("view.vault.list.dialog.remove", "Do you really want to remove "),
	;
	
	private static final File LOCALE_FOLDER = new File("./locale");
	
	private static Properties LOCALE = new Properties();
	
	private final String KEY;
	private final String DEFAULT_VALUE;
	
	private LocalizedTexts(String key, String defaultValue) {
		this.KEY = key;
		this.DEFAULT_VALUE = defaultValue;
	}
	
	@Override
	public String toString() {
		return LOCALE.getProperty(KEY, DEFAULT_VALUE);
	}
	
	public static void loadLocale(String localeName) {
		try (InputStream in = new FileInputStream(new File(LOCALE_FOLDER.getAbsoluteFile() + "/" + localeName + ".properties"))) {
			LOCALE.clear();
			LOCALE.load(in);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getAvailableLocales() {
		return Arrays.asList(LOCALE_FOLDER.listFiles()).stream()
			.filter(f -> f.isFile())
			.filter(f -> f.getName().endsWith(".properties"))
			.map(f -> f.getName())
			.map(f -> f.substring(0, f.indexOf(".properties")))
			.sorted((a, b) -> a.compareTo(b))
			.toArray(String[]::new);
	}

}
