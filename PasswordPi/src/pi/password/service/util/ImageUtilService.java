package pi.password.service.util;

import java.awt.image.BufferedImage;

public interface ImageUtilService {
	
	BufferedImage getSplashScreen();
	
	BufferedImage getMainBackground();
	
	BufferedImage getVaultBackground();
	
	BufferedImage getSettingsBackground();
	
	BufferedImage getWifiOn();
	
	BufferedImage getWifiOff();
	
	BufferedImage loadImage(String image);
}
