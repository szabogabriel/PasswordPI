package pi.password.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	private static BufferedImage SPLASH;
	private static BufferedImage BACKGROUND;
	private static BufferedImage VAULT;
	private static BufferedImage SETTINGS;
	
	public static BufferedImage getSplashScreen() {
		if (SPLASH == null) {
			SPLASH = ImageUtil.loadImage("img/lock.bmp");
		}
		return SPLASH;
	}
	
	public static BufferedImage getMainBackground() {
		if (BACKGROUND == null) {
			BACKGROUND = ImageUtil.loadImage("img/background.bmp");
		}
		return BACKGROUND;
	}
	
	public static BufferedImage getVaultBackground() {
		if (VAULT == null) {
			VAULT = ImageUtil.loadImage("img/vault.bmp");
		}
		return VAULT;
	}
	
	public static BufferedImage getSettingsBackground() {
		if (SETTINGS == null) {
			SETTINGS = ImageUtil.loadImage("img/settings.bmp");
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
