package pi.password.gui.components.background;

import java.awt.image.BufferedImage;

import pi.password.gui.AbstractView;

public class BackgroundView extends AbstractView {
	
	private final String TITLE;

	public BackgroundView(BufferedImage background, String title) {
		super(background);
		this.TITLE = title;
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(TITLE);
	}

}
