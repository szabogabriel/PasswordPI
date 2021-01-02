package pi.password.service.settings;

import java.util.Optional;
import java.util.Set;

import pi.password.entity.SettingsEntity;
import pi.password.service.webserver.WebserverService;

public abstract class SettingsService {
	
	private final WebserverService WEBSERVER_SERVICE;
	
	protected SettingsService(WebserverService webserverService) {
		this.WEBSERVER_SERVICE = webserverService;
	}
	
	protected void init() {
		getSettings().stream().forEach(this::handleValueChange);
	}
	
	protected void handleValueChange(SettingsEntity entity) {
		switch (entity.getKey()) {
		case WEBSERVER_ENABLED:
			if (entity.getValueBoolean()) {
				WEBSERVER_SERVICE.startWebserver();
			}
			else {
				WEBSERVER_SERVICE.stopWebserver();
			}
			break;
		case WEBSERVER_PORT:
			WEBSERVER_SERVICE.setPort(entity.getValueInteger());
			break;
		default:
			break;
		}
	}
	
	public abstract Set<SettingsEntity> getSettings();
	
	public abstract Optional<SettingsEntity> getSetting(String key);
	
	public abstract void setValue(SettingsEntity setting);
	
}
