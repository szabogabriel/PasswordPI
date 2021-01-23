package pi.password.service.settings;

import pi.password.entity.SettingsEntity;
import pi.password.enums.LocalizedTexts;
import pi.password.service.webserver.WebserverService;

public class SettingsUpdatePropagatorServiceImpl implements SettingsUpdatePropagatorService {
	
	private final WebserverService WEB;
	
	public SettingsUpdatePropagatorServiceImpl(WebserverService web) {
		this.WEB = web;
	}

	@Override
	public void handleSettingUpdate(SettingsEntity entity) {
		switch (entity.getKey()) {
		case LOCALE:
			LocalizedTexts.loadLocale(entity.getValue());
			break;
		case KEYSTROKE_LENGTH:
			break;
		case TYPE_DELAY_MAX:
			break;
		case TYPE_DELAY_MIN:
			break;
		case WEBSERVER_ENABLED:
			if (entity.getValueBoolean()) {
				WEB.startWebserver();
			} else {
				WEB.stopWebserver();
			}
			break;
		case WEBSERVER_PORT:
			WEB.setPort(entity.getValueInteger());
			break;
		case WEBSERVER_STRICT:
			WEB.setStrict(entity.getValueBoolean());
			break;
		default:
			break;
		
		}
	}

}
