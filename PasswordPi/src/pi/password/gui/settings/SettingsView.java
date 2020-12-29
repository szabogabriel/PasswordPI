package pi.password.gui.settings;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.util.ImageUtilService;

public class SettingsView extends AbstractView {

	public SettingsView() {
		super(Main.getInstance(ImageUtilService.class).getSettingsBackground());
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
		
	}

}
