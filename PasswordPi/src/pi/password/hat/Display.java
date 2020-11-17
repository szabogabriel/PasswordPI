package pi.password.hat;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.waveshare.Hat;
import com.waveshare.display.LcdGui;
import com.waveshare.display.enums.DotPixel;
import com.waveshare.display.enums.DrawFill;
import com.waveshare.display.font.Font;
import com.waveshare.display.font.Font12;
import com.waveshare.display.font.Font16;
import com.waveshare.display.font.Font20;
import com.waveshare.display.font.Font24;
import com.waveshare.display.font.Font8;

public enum Display {
	
	INSTANCE;
	
	public enum TextPosition { TOP, MIDDLE, BOTTOM };
	public enum TextAlign { LEFT, CENTER, RIGHT; };

	public enum TextFormat {
		SIZE_8_LINE_0(0, new Font8()), SIZE_8_LINE_1(8, new Font8()), 
		SIZE_8_LINE_2(16, new Font8()), SIZE_8_LINE_3(24, new Font8()),
		SIZE_8_LINE_4(32, new Font8()),	SIZE_8_LINE_5(40, new Font8()),
		SIZE_8_LINE_6(48, new Font8()), SIZE_8_LINE_7(56, new Font8()),
		SIZE_8_LINE_8(64, new Font8()),	SIZE_8_LINE_9(72, new Font8()),
		SIZE_8_LINE_10(80, new Font8()), SIZE_8_LINE_11(88, new Font8()),
		SIZE_8_LINE_12(96, new Font8()), SIZE_8_LINE_13(104, new Font8()),
		SIZE_8_LINE_14(112, new Font8()),SIZE_8_LINE_15(120, new Font8()),
		
		SIZE_12_LINE_0(0, new Font12()), SIZE_12_LINE_1(12, new Font12()),
		SIZE_12_LINE_2(24, new Font12()), SIZE_12_LINE_3(36, new Font12()),
		SIZE_12_LINE_4(48, new Font12()), SIZE_12_LINE_5(60, new Font12()),
		SIZE_12_LINE_6(72, new Font12()), SIZE_12_LINE_7(84, new Font12()),
		SIZE_12_LINE_8(96, new Font12()), SIZE_12_LINE_9(108, new Font12()),
		SIZE_12_LINE_10(120, new Font12()),
		
		SIZE_16_LINE_0(0, new Font16()), SIZE_16_LINE_1(16, new Font16()),
		SIZE_16_LINE_2(32, new Font16()), SIZE_16_LINE_3(48, new Font16()),
		SIZE_16_LINE_4(64, new Font16()), SIZE_16_LINE_5(80, new Font16()),
		SIZE_16_LINE_6(96, new Font16()), SIZE_16_LINE_7(112, new Font16()),
		
		SIZE_20_LINE_0(0, new Font20()), SIZE_20_LINE_1(20, new Font20()),
		SIZE_20_LINE_2(40, new Font20()), SIZE_20_LINE_3(60, new Font20()),
		SIZE_20_LINE_4(80, new Font20()), SIZE_20_LINE_5(100, new Font20()),
		
		SIZE_24_LINE_0(0, new Font24()), SIZE_24_LINE_1(24, new Font24()),
		SIZE_24_LINE_2(48, new Font24()), SIZE_24_LINE_3(72, new Font24()),
		SIZE_24_LINE_4(96, new Font24())
		;
		private final int startPosition;
		private final Font font;
		private TextFormat(int startPosition, Font font) {
			this.startPosition = startPosition;
			this.font = font;
		}
	}
	
	private LcdGui lcd = Hat.INSTANCE.lcdHat;
	
	public void clearScreen() {
		lcd.clear(Color.BLACK);
	}
	
	public void displayImage(BufferedImage image) {
		lcd.displayBitmap(0, 0, image);
	}
	
	public void displayText(String text, Color textColor, Color textBackground, TextFormat textFormat, TextAlign align) {
		try {
			int x = 0;
			
			switch (align) {
			case CENTER: x = 64 - ((text.length() * textFormat.font.getWidth()) / 2); break;
			case LEFT: x = 0; break;
			case RIGHT: x = 128 - ((text.length() + 1) * textFormat.font.getWidth()); break;
			}
			
			int y = textFormat.startPosition;
			
			lcd.displayString(x, y, text, textFormat.font, textBackground, textColor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void flipBacklight() {
		lcd.flipBacklight();
	}
	
	public void drawSelection(int x, int y) {
		try {
			lcd.drawCircle(x, y, 5, Color.RED, DrawFill.FILLED, DotPixel.DOT_PIXEL_1_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
