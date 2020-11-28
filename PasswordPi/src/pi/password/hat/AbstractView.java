package pi.password.hat;

import java.awt.image.BufferedImage;

public abstract class AbstractView {
	
	public final Display DISPLAY;
	private final BufferedImage BACKGROUND;
	
	public AbstractView(BufferedImage background) {
		this.BACKGROUND = background;
		this.DISPLAY = Display.INSTANCE;
	}
	
	protected BufferedImage getBackground() {
		return BACKGROUND;
	}
	
	public abstract void paint();

}
