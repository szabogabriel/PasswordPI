package pi.password.gui.splash;

import pi.password.gui.AbstractView;
import pi.password.util.ImageUtil;
import pi.password.util.SystemUtil;

public class SplashView extends AbstractView {

	public SplashView() {
		super(ImageUtil.getSplashScreen());
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(SystemUtil.getIpAddress().orElse("Offline"));
	}

}
