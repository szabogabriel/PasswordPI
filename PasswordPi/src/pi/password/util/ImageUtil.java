package pi.password.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pi.password.config.Config;

public class ImageUtil {
	
	private static BufferedImage SPLASH;
	private static BufferedImage BACKGROUND;
	private static BufferedImage VAULT;
	private static BufferedImage SETTINGS;
	
	public static BufferedImage getSplashScreen() {
		if (SPLASH == null) {
			SPLASH = ImageUtil.loadImage(Config.BACKGROUND_SCREEN_LOCEKD.toString());
		}
		return SPLASH;
	}
	
	public static BufferedImage getMainBackground() {
		if (BACKGROUND == null) {
			BACKGROUND = ImageUtil.loadImage(Config.BACKGROUND_SCREEN_PASSWORD.toString());
		}
		return BACKGROUND;
	}
	
	public static BufferedImage getVaultBackground() {
		if (VAULT == null) {
			VAULT = ImageUtil.loadImage(Config.BACKGROUND_SCREEN_VAULT.toString());
		}
		return VAULT;
	}
	
	public static BufferedImage getSettingsBackground() {
		if (SETTINGS == null) {
			SETTINGS = ImageUtil.loadImage(Config.BACKGROUND_SCREEN_SETTINGS.toString());
		}
		return SETTINGS;
	}
	
	public static BufferedImage loadImage(String image) {
		try {
			BufferedImage ret = ImageIO.read(new File(image));
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
