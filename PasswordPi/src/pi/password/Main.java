package pi.password;

import com.jdi.ServiceFactory;
import com.jdi.ServiceFactoryImpl;

import pi.password.config.JdiConfigService;
import pi.password.gui.screenlock.ScreenlockController;

public class Main {
	
	private static final ServiceFactory DI = new ServiceFactoryImpl(new JdiConfigService());
	
	public static <T> T getInstance(Class<T> clss) {
		return DI.getServiceImpl(clss).orElse(null);
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		new ScreenlockController().activate();
	}
	
}
