package pi.password;

import com.jdi.ServiceFactory;
import com.jdi.ServiceFactoryImpl;

import pi.password.config.JdiConfigService;
import pi.password.hat.screenlock.ScreenlockController;
import pi.password.manager.PasswordVault;

public class Main {
	
	public static final ServiceFactory SERVICE_FACTORY = new ServiceFactoryImpl(new JdiConfigService());
	
	public static PasswordVault VAULT;
	
	private Main() {
		new ScreenlockController().activate();
		
		try {
			while (true)
				Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		new Main();
	}

}
