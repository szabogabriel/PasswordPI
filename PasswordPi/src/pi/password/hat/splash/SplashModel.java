package pi.password.hat.splash;

import pi.password.hat.AbstractModel;

public class SplashModel extends AbstractModel {
	
	private final SplashView VIEW;
	
	public SplashModel(SplashView view) {
		this.VIEW = view;
		this.VIEW.paint();
	}

}
