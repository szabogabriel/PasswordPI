package pi.password;

import pi.password.hat.handlers.ButtonHandler;
import pi.password.manager.PasswordVault;
import pi.password.manager.filesystem.FilesystemPasswordVault;

public class Main {
	
	private static final String PASSWORD_VAULT = "./passwords.properties";
	
	public static PasswordVault VAULT;
	
	private Main() {
		ButtonHandler.SPLASH_SCREEN.activate();
		
		try {
			while (true)
				Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VAULT = new FilesystemPasswordVault(PASSWORD_VAULT);
		new Main();
	}

}
