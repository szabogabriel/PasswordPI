package pi.password.service.hat;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;

public interface DisplayService {
	
	void clearScreen();
	
	void print(JComponent component);
	
	void displayImage(BufferedImage image);
	
	void displayTitle(String text);
	
	void displayText(String text, Color textColor, int row, TextAlign align, int fontSelector);
	
	void displayKeyValue(String key, String value, Color keyColor, Color valueColor, int row);
	
	int getMaxAmountOfBodyRows();
	
	void setBacklight(boolean backlight);
	
	void drawSelection(int row);
	
	boolean isFullyVisibleLine(String text);
	
}
