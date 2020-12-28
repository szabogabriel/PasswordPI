package pi.password;

import com.jdi.ServiceFactory;
import com.jdi.ServiceFactoryImpl;

import pi.password.config.JdiConfigService;
import pi.password.hat.screenlock.ScreenlockController;

public class Main {
	
	public static final ServiceFactory SERVICE_FACTORY = new ServiceFactoryImpl(new JdiConfigService());
	
	public static void main(String[] args) throws ClassNotFoundException {
		new ScreenlockController().activate();
	}

}
