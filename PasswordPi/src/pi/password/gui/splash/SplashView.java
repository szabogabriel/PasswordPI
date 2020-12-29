package pi.password.gui.splash;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.SystemUtil;

public class SplashView extends AbstractView {

	public SplashView() {
		super(Main.DI.getServiceImpl(ImageUtilService.class).get().getSplashScreen());
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(Main.DI.getServiceImpl(SystemUtil.class).get().getIpAddress().orElse("Offline"));
	}

}
