package pi.password.service.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pi.password.config.RuntimeConfig;

public class ImageUtilServiceFileSystem implements ImageUtilService {
	
	private static BufferedImage SPLASH;
	private static BufferedImage BACKGROUND;
	private static BufferedImage VAULT;
	private static BufferedImage SETTINGS;
	private static BufferedImage WIFI_ON;
	private static BufferedImage WIFI_OFF;
	
	@Override
	public BufferedImage getSplashScreen() {
		if (SPLASH == null) {
			SPLASH = loadImage(RuntimeConfig.BACKGROUND_SCREEN_LOCEKD.toString());
		}
		return SPLASH;
	}
	
	@Override
	public BufferedImage getMainBackground() {
		if (BACKGROUND == null) {
			BACKGROUND = loadImage(RuntimeConfig.BACKGROUND_SCREEN_PASSWORD.toString());
		}
		return BACKGROUND;
	}
	
	@Override
	public BufferedImage getVaultBackground() {
		if (VAULT == null) {
			VAULT = loadImage(RuntimeConfig.BACKGROUND_SCREEN_VAULT.toString());
		}
		return VAULT;
	}
	
	@Override
	public BufferedImage getSettingsBackground() {
		if (SETTINGS == null) {
			SETTINGS = loadImage(RuntimeConfig.BACKGROUND_SCREEN_SETTINGS.toString());
		}
		return SETTINGS;
	}
	
	@Override
	public BufferedImage getWifiOn() {
		if (WIFI_ON == null) {
			WIFI_ON = loadImage(RuntimeConfig.ICON_WIFI_ON.toString());
		}
		return WIFI_ON;
	}
	
	@Override
	public BufferedImage getWifiOff() {
		if (WIFI_OFF == null) {
			WIFI_OFF = loadImage(RuntimeConfig.ICON_WIFI_OFF.toString());
		}
		return WIFI_OFF;
	}
	
	@Override
	public BufferedImage loadImage(String image) {
		try {
			BufferedImage ret = ImageIO.read(new File(image));
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
