package pi.password.gui.splash;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.SystemUtil;

public class SplashView extends AbstractView {

	public SplashView() {
		super(Main.getInstance(ImageUtilService.class).getSplashScreen());
	}
	
	public void setBacklight(boolean backlight) {
		DISPLAY.setBacklight(backlight);
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(Main.getInstance(SystemUtil.class).getIpAddress().orElse("Offline"));
	}

}
