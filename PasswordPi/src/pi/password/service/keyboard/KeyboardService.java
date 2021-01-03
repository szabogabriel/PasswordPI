package pi.password.service.keyboard;

import java.util.Optional;

import pi.password.entity.SettingsEntity;
import pi.password.service.settings.SettingsService;

public abstract class KeyboardService {
	
	private final SettingsService SETTINGS;

	public abstract boolean sendText(String text);
	
	public KeyboardService(SettingsService settings) {
		this.SETTINGS = settings;
	}
	
	protected void keystrokeLength() {
		Optional<SettingsEntity> setting = SETTINGS.getSetting(SettingsEntity.Keys.KEYSTROKE_LENGTH);
		if (setting.isPresent()) {
			sleep(setting.get().getValueInteger());
		}
	}
	
	protected void keystrokeDelay() {
		int minDelay = SETTINGS.getSetting(SettingsEntity.Keys.TYPE_DELAY_MIN).get().getValueInteger();
		int maxDelay = SETTINGS.getSetting(SettingsEntity.Keys.TYPE_DELAY_MAX).get().getValueInteger();
		
		int deltaDelay = maxDelay - minDelay;
		
		int delay = minDelay + (int)(Math.random() * (double)deltaDelay);
		
		sleep(delay);
	}
	
	protected void sleep(int millies) {
		try {
			Thread.sleep(millies);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
