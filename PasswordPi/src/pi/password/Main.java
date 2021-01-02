package pi.password;

import com.jdi.ServiceFactory;
import com.jdi.ServiceFactoryImpl;

import pi.password.config.JdiConfigService;
import pi.password.gui.screenlock.ScreenlockController;
import pi.password.service.hat.DisplayService;
import pi.password.service.hat.KeyInputService;
import pi.password.service.settings.SettingsService;

public class Main {
	
	private static final ServiceFactory DI = new ServiceFactoryImpl(new JdiConfigService());
	
	public static <T> T getInstance(Class<T> clss) {
		return DI.getServiceImpl(clss).orElse(null);
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		init();
		getInstance(ScreenlockController.class).activate();
	}
	
	private static void init() {
		createHatInstance();		
		startBackgroundJobs();
	}
	
	private static void createHatInstance() {
		// This isn't really necessary, it would be called transitively anyway
		// but at least we are making clear, what is essential to be loaded.
		getInstance(DisplayService.class);
		getInstance(KeyInputService.class);
	}
	
	private static void startBackgroundJobs() {
		// The settings service takes care of the background jobs via callback.
		getInstance(SettingsService.class);
	}
	
}
