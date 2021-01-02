package pi.password.entity;

import java.util.Arrays;
import java.util.Optional;

import com.sun.corba.se.impl.io.TypeMismatchException;

public class SettingsEntity {
	
	public enum Type {
		OPTIONS,
		PLAINTEXT,
		NUMBERS,
		;
	}
	
	public enum Keys {
		WEBSERVER_PORT(Type.NUMBERS, "Web port", "80", null),
		WEBSERVER_ENABLED(Type.OPTIONS, "Webserver", "Off", new String [] {"Off", "On"}),
		;

		private final Type type;
		private final String printableName;
		private final String defaultValue;
		private final String[] options;
		
		private Keys(Type type, String printableName, String defaultValue, String [] options) {
			this.type = type;
			this.printableName = printableName;
			this.defaultValue = defaultValue;
			this.options = options;
		}
		
		public Type getType() {
			return type;
		}
		
		public String getPrintableName() {
			return printableName;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
		
		public String [] getValues() {
			return options;
		}
		
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
		throw new TypeMismatchException("The setting " + key.getPrintableName() + " is not of type " + Type.NUMBERS + ".");
	}
}
