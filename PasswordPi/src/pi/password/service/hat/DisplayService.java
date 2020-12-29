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
	
	int getMaxAmountOfBodyRows();
	
	void flipBacklight();
	
	void drawSelection(int row);
	
}
