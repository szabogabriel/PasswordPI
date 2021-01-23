package pi.password.gui.lock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.waveshare.keyboard.HatKey;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.SystemUtil;

public class ScreenlockView extends AbstractView {

	private List<String> keys = new ArrayList<>();

	public ScreenlockView() {
		super(Main.getInstance(ImageUtilService.class).getMainBackground());
	}

	public void sequenceChanged(List<HatKey> keys) {
		this.keys.clear();
		int counter = 0;
		String tmp = "";
		for (HatKey it : keys) {
			tmp += keyToString(it);
			if (++counter == 8) {
				this.keys.add(tmp.trim());
				tmp = "";
				counter = 0;
			}
		}
		if (tmp.length() > 0) {
			this.keys.add(tmp.trim());
		}
	}

	private String keyToString(HatKey key) {
		String ret;
		switch (key) {
		case JOYSTICK_DOWN:
			ret = "D ";
			break;
		case JOYSTICK_LEFT:
			ret = "L ";
			break;
		case JOYSTICK_RIGHT:
			ret = "R ";
			break;
		case JOYSTICK_UP:
			ret = "U ";
			break;
		case KEY_A:
			ret = "A ";
			break;
		case KEY_B:
			ret = "B ";
			break;
		case KEY_C:
			ret = "C ";
			break;
		default:
			ret = "";
			break;
		}
		return ret;
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(Main.getInstance(SystemUtil.class).getIpAddress().orElse("Offline"));

		DISPLAY.displayText("Locked", Color.GREEN, 1, TextAlign.CENTER, -1);
		for (int i = 0; i < keys.size(); i++) {
			DISPLAY.displayText(keys.get(i), Color.GREEN, 3 + i, TextAlign.CENTER, -1);
		}
	}

}
