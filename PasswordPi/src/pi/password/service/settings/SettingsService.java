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
		getSettings()
			.stream()
			.sorted((k1, k2) -> k1.getKey().ordinal() - k2.getKey().ordinal())
			.forEach(this::handleValueChange);
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
		case KEYSTROKE_LENGTH:
			break;
		case TYPE_DELAY_MAX:
			break;
		case TYPE_DELAY_MIN:
			break;
		default:
			break;
		}
	}
	
	public abstract Set<SettingsEntity> getSettings();
	
	public abstract Optional<SettingsEntity> getSetting(SettingsEntity.Keys key);
	
	public abstract void setValue(SettingsEntity setting);
	
}
