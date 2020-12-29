package pi.password.gui;

import java.awt.image.BufferedImage;

import pi.password.Main;
import pi.password.service.hat.DisplayService;

public abstract class AbstractView {
	
	public final DisplayService DISPLAY;
	private final BufferedImage BACKGROUND;
	
	public AbstractView(BufferedImage background) {
		this.BACKGROUND = background;
		this.DISPLAY = Main.getInstance(DisplayService.class);
	}
	
	protected BufferedImage getBackground() {
		return BACKGROUND;
	}
	
	public abstract void paint();

}
