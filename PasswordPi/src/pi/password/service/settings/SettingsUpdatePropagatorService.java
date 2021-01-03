package pi.password.service.settings;

import pi.password.entity.SettingsEntity;

public interface SettingsUpdatePropagatorService {
	
	void handleSettingUpdate(SettingsEntity entity);

}
