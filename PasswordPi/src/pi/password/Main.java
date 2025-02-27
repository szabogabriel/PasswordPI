package pi.password;

import com.jdi.services.ServiceFactory;
import com.jdi.services.impl.ServiceFactoryImpl;

import pi.password.config.JdiConfigService;
import pi.password.entity.SettingsEntity;
import pi.password.entity.SettingsEntity.Keys;
import pi.password.enums.LocalizedTexts;
import pi.password.gui.lock.MasterLockController;
import pi.password.gui.lock.MasterLockSetController;
import pi.password.service.hat.DisplayService;
import pi.password.service.hat.KeyInputService;
import pi.password.service.lock.LockService;
import pi.password.service.settings.SettingsService;
import pi.password.service.webserver.WebserverService;

public class Main {
	
	private static final ServiceFactory DI = new ServiceFactoryImpl(new JdiConfigService());
	
	public static <T> T getInstance(Class<T> clss) {
		return DI.getServiceImpl(clss).orElse(null);
	}
	
	public static void lock() {
		getInstance(LockService.class).masterLock();
		getInstance(MasterLockController.class).activate();
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		init();
		getInstance(MasterLockSetController.class).activate();
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
		SettingsService settings = getInstance(SettingsService.class);
		WebserverService web = getInstance(WebserverService.class);
		
		web.setPort(settings.getSetting(Keys.WEBSERVER_PORT).get().getValueInteger());
		web.setStrict(settings.getSetting(Keys.WEBSERVER_STRICT).get().getValueBoolean());
		if (settings.getSetting(Keys.WEBSERVER_ENABLED).get().getValueBoolean()) {
			web.startWebserver();
		}
		
		LocalizedTexts.loadLocale(settings.getSetting(SettingsEntity.Keys.LOCALE).get().getValue());
	}
	
}
