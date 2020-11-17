package pi.password;

import pi.password.hat.handlers.ButtonHandler;

public class Main {
	
//	private static final String PASSWORD_VAULT = "./passwords.properties";
	
//	private PasswordManager passwordManager;
	
	private Main() {
//		passwordManager = new PasswordManager(new FilesystemPasswordVault(PASSWORD_VAULT));

		ButtonHandler.SPLASH_SCREEN.activate();
		
		try {
			while (true)
				Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
