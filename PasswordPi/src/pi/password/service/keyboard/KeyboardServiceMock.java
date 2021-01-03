package pi.password.service.keyboard;

import pi.password.service.settings.SettingsService;

public class KeyboardServiceMock extends KeyboardService {
	
	public KeyboardServiceMock(SettingsService settings) {
		super(settings);
	}

	@Override
	public boolean sendText(String text) {
		System.out.println(text);
		return true;
	}

}
