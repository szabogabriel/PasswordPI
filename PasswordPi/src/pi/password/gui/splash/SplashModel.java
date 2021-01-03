package pi.password.gui.splash;

import pi.password.gui.AbstractModel;

public class SplashModel extends AbstractModel {
	
	private final SplashView VIEW;
	
	private boolean backlight;
	
	public SplashModel(SplashView view) {
		this.VIEW = view;
		this.VIEW.paint();
	}
	
	public void setBacklight(boolean backlight) {
		this.backlight = backlight;
		VIEW.setBacklight(backlight);
	}
	
	public boolean getBacklight() {
		return this.backlight;
	}

}
