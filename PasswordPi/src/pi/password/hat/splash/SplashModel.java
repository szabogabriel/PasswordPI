package pi.password.hat.splash;

import pi.password.hat.AbstractModel;

public class SplashModel extends AbstractModel {
	
	private final SplashView VIEW;
	
	private boolean backlight;
	
	public SplashModel(SplashView view) {
		this.VIEW = view;
		this.VIEW.paint();
	}
	
	public void setBacklight(boolean backlight) {
		this.backlight = backlight;
	}
	
	public boolean getBacklight(boolean backlight) {
		return this.backlight;
	}

}
