package pi.password.entity;

import java.util.Arrays;
import java.util.Optional;

import pi.password.enums.LocalizedTexts;

public class SettingsEntity {
	
	public enum Type {
		OPTIONS,
		PLAINTEXT,
		NUMBERS,
		;
	}
	
	public enum Keys {
		LOCALE(Type.OPTIONS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_LOCALE.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return "En";
			}
			
			@Override
			public String[] getValues() {
				return LocalizedTexts.getAvailableLocales();
			}
		},
		KEYSTROKE_LENGTH(Type.NUMBERS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_KEYSTROKE.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return "50";
			}
			
			@Override
			public String[] getValues() {
				return null;
			}
		},
		TYPE_DELAY_MIN(Type.NUMBERS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_DELAY_MIN.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return "30";
			}
			
			@Override
			public String[] getValues() {
				return null;
			}
		},
		TYPE_DELAY_MAX(Type.NUMBERS) {
			@Override
			public String getPrintableName() {
				return  LocalizedTexts.SETTINGS_KEY_DELAY_MAX.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return "120";
			}
			
			@Override
			public String[] getValues() {
				return null;
			}
		},
		WEBSERVER_STRICT(Type.OPTIONS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_WEBSERVER_STRICT.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return LocalizedTexts.SETTINGS_VALUE_OFF.toString();
			}
			
			@Override
			public String[] getValues() {
				return new String [] {LocalizedTexts.SETTINGS_VALUE_OFF.toString(), LocalizedTexts.SETTINGS_VALUE_ON.toString()};
			}
		},
		WEBSERVER_PORT(Type.NUMBERS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_WEBSERVER_PORT.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return "80";
			}
			
			@Override
			public String[] getValues() {
				return null;
			}
		},
		WEBSERVER_ENABLED(Type.OPTIONS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_WEBSERVER_ENABLED.toString();
			}
			
			@Override
			public String getDefaultValue() {
				return LocalizedTexts.SETTINGS_VALUE_OFF.toString();
			}
			
			@Override
			public String[] getValues() {
				return new String [] {LocalizedTexts.SETTINGS_VALUE_OFF.toString(), LocalizedTexts.SETTINGS_VALUE_ON.toString()};
			}
		},
		DEVELOPER_MODE(Type.OPTIONS) {
			@Override
			public String getPrintableName() {
				return LocalizedTexts.SETTINGS_KEY_DEVELOPER_MODE.toString();
			}

			@Override
			public String getDefaultValue() {
				return LocalizedTexts.SETTINGS_VALUE_OFF.toString();
			}

			@Override
			public String[] getValues() {
				return new String [] {LocalizedTexts.SETTINGS_VALUE_OFF.toString(), LocalizedTexts.SETTINGS_VALUE_ON.toString()};
			}
		},
		;

		private final Type type;
		
		private Keys(Type type) {
			this.type = type;
		}
		
		public Type getType() {
			return type;
		}
		
		public abstract String getPrintableName();

		public abstract String getDefaultValue();
		
		public abstract String [] getValues();
		
		public static Optional<Keys> getKey(String value) {
			return Arrays.asList(values()).stream().filter(e -> e.name().equals(value)).findAny();
		}
	}
	
	private Keys key;
	
	private  String value;
	
	public SettingsEntity(Keys key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public Keys getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean getValueBoolean() {
		return "true".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value);
	}
	
	public int getValueInteger() {
		if (key.getType() == Type.NUMBERS) {
			int ret = 0;
			try {
				ret = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return ret;
		}
		throw new IllegalArgumentException("The setting " + key.getPrintableName() + " is not of type " + Type.NUMBERS + ".");
	}
}
