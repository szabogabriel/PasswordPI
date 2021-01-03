package pi.password.service.settings;

import java.util.Optional;
import java.util.Set;

import pi.password.entity.SettingsEntity;

public interface SettingsService {
	
	public abstract Set<SettingsEntity> getSettings();
	
	public abstract Optional<SettingsEntity> getSetting(SettingsEntity.Keys key);
	
	public abstract void setValue(SettingsEntity setting);
	
}
