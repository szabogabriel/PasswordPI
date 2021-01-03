package pi.password.service.hat;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import com.waveshare.display.LcdDisplay;

import pi.password.Main;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.SystemUtil;

public class DisplayServiceWaveshareHat implements DisplayService {

	private final int WIDTH = 128;
	private final int HEIGHT = 128;
	private final int TITLE_BAR_HEIGHT = 16;
	private final int BODY_ELEMENT_BORDER = 3;

	private final Font TITLE_FONT = new Font("Verdana", Font.BOLD, 8);
	private final Font BODY_FONT = new Font("Verdana", Font.PLAIN, 12);

	public enum TextPosition {
		TOP, MIDDLE, BOTTOM
	};

	public enum TextAlign {
		LEFT, CENTER, RIGHT;
	};

	private LcdDisplay lcd = Main.getInstance(LcdDisplay.class);

	private final Canvas CANVAS = new Canvas();

	public DisplayServiceWaveshareHat() {
		lcd.setBackground(Color.BLACK);
	}

	@Override
	public void clearScreen() {
		lcd.clear();
	}

	@Override
	public void print(JComponent component) {
		lcd.print(component);
	}

	@Override
	public void displayImage(BufferedImage image) {
		lcd.displayBitmap(image, 0, 0);
	}

	@Override
	public void displayTitle(String text) {
		int y = (TITLE_BAR_HEIGHT + TITLE_FONT.getSize() - 1) / 2;

		lcd.drawRectangle(0, 0, WIDTH, TITLE_BAR_HEIGHT, Color.BLACK, true, 1);
		BufferedImage image = Main.getInstance(SystemUtil.class).getIpAddress()
				.map(a -> Main.getInstance(ImageUtilService.class).getWifiOn())
				.orElse(Main.getInstance(ImageUtilService.class).getWifiOff());
		
		lcd.displayBitmap(image, WIDTH - image.getWidth(), 0);
		
		int centerPosition = centerStartPosition(TITLE_FONT, text);
		
		lcd.displayString(text, centerPosition, y, Color.GREEN, TITLE_FONT);
	}

	@Override
	public void displayText(String text, Color textColor, int row, TextAlign align, int fontSelector) {
		int x = 0;
		int y = 0;

		// Compute the position on the X axis based on the alignment
		switch (align) {
		case CENTER:
			x = centerStartPosition(BODY_FONT, text);
			break;
		case LEFT:
			x = leftStartPosition();
			break;
		case RIGHT:
			x = rightStartPosition(text);
			break;
		}

		// Compute the position on the Y axis based on the row number
		y = rowStartPosition(row);

		lcd.displayString(text, x, y, textColor, BODY_FONT);

		if (fontSelector >= 0) {
			drawCharSelector(text, row, fontSelector);
		}
	}

	@Override
	public void displayKeyValue(String key, String value, Color keyColor, Color valueColor, int row) {
		int y = rowStartPosition(row);
		
		int keyX = leftStartPosition();
		int valueX = rightStartPosition(value);
		
		lcd.displayString(key, keyX, y, keyColor, BODY_FONT);
		lcd.displayString(value, valueX, y, valueColor, BODY_FONT);
	}

	@Override
	public int getMaxAmountOfBodyRows() {
		int ret = ((HEIGHT - TITLE_BAR_HEIGHT) / (BODY_FONT.getSize() + (2 * BODY_ELEMENT_BORDER)));
		return ret;
	}

	@Override
	public void flipBacklight() {
		// XXX: Not supported currently
//		lcd.flipBacklight();
	}

	@Override
	public void drawSelection(int row) {
		int w = getRowOffsetEnd();
		int h = getRowHeight();
		int x = getRowOffsetStart();
		int y = getRowY(row);

		lcd.drawRectangle(x, y, w, h, Color.RED, false, 2);
	}
	
	@Override
	public boolean isFullyVisibleLine(String text) {
		return textWidth(BODY_FONT, text) < (WIDTH - BODY_ELEMENT_BORDER * 2);
	}

	private int getRowOffsetStart() {
		return 2;
	}

	private int getRowOffsetEnd() {
		return WIDTH - 4;
	}

	private int getRowY(int row) {
		return TITLE_BAR_HEIGHT + (row * (BODY_FONT.getSize() + 2 * BODY_ELEMENT_BORDER));
	}

	private int getRowHeight() {
		return BODY_FONT.getSize() + (2 * BODY_ELEMENT_BORDER);
	}
	
	private int centerStartPosition(Font font, String text) {
		return (int) ((WIDTH / 2) - (textWidth(font, text) / 2));
	}

	private int textWidth(Font font, String text) {
		return CANVAS.getFontMetrics(font).charsWidth(text.toCharArray(), 0, text.length());
	}
	
	private void drawCharSelector(String text, int row, int fontSelector) {
		String substringA, substringB;

		if (fontSelector == 0) {
			substringA = "";
			substringB = text.substring(0, 1);
		} else {
			substringA = text.substring(0, fontSelector);
			substringB = text.substring(0, fontSelector + 1);
		}

		int startx, width;
		startx = textWidth(BODY_FONT, substringA);
		width = textWidth(BODY_FONT, substringB) - startx;

		lcd.drawRectangle(startx + getRowOffsetStart() + 1, getRowY(row), width, getRowHeight(), Color.RED, false, 1);
	}
	
	private int leftStartPosition() {
		return BODY_ELEMENT_BORDER;
	}
	
	private int rightStartPosition(String text) {
		return WIDTH - textWidth(BODY_FONT, text) - BODY_ELEMENT_BORDER;
	}
	
	private int rowStartPosition(int row) {
		return ((BODY_FONT.getSize() + (BODY_ELEMENT_BORDER * 2)) * row) + BODY_ELEMENT_BORDER + TITLE_BAR_HEIGHT
				+ BODY_FONT.getSize();
	}

}
