package pi.password;

import java.io.IOException;

import com.waveshare.Hat;
import com.waveshare.display.LcdFactories;
import com.waveshare.keyboard.KeyboardFactories;

import pi.password.hat.screenlock.ScreenlockController;
import pi.password.keyboard.Keyboard;
import pi.password.keyboard.mock.MockKeyboard;
import pi.password.keyboard.us.KeyboardUs;
import pi.password.manager.PasswordVault;
import pi.password.manager.filesystem.FilesystemPasswordVault;

public class Main {
	
	private static final String PASSWORD_VAULT = "./passwords.properties";
	
	public static PasswordVault VAULT;
	
	public static Hat hat;
	public static Keyboard keyboard;
	
	private Main() {
		new ScreenlockController().activate();
		
		try {
			while (true)
				Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if (args != null && args.length > 0 && "dev".equalsIgnoreCase(args[0])) {
			try {
				hat = com.waveshare.mock.Main.createHat();
			} catch (IOException e) {
				e.printStackTrace();
			}
			keyboard = new MockKeyboard();
		} else {
			hat = new Hat(KeyboardFactories.HARDWARE, LcdFactories.BUFFERED);
			keyboard = new KeyboardUs();
		}
		VAULT = new FilesystemPasswordVault(PASSWORD_VAULT);
		
		new Main();
	}

}
