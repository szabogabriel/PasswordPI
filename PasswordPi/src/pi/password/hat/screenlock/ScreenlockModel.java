package pi.password.hat.screenlock;

import java.util.ArrayList;
import java.util.List;

import com.waveshare.keyboard.HatKey;

import pi.password.hat.AbstractModel;

public class ScreenlockModel extends AbstractModel {
	
	private final ScreenlockView VIEW;
	private List<HatKey> UNBLOCK_SEQUENCE = new ArrayList<>();
	
	public ScreenlockModel(ScreenlockView view) {
		this.VIEW = view;
		this.VIEW.paint();
	}
	
	public void addSequence(HatKey key) {
		UNBLOCK_SEQUENCE.add(key);
		VIEW.sequenceChanged(UNBLOCK_SEQUENCE);
		VIEW.paint();
	}
	
	public void clearSequence() {
		UNBLOCK_SEQUENCE.clear();
		VIEW.sequenceChanged(UNBLOCK_SEQUENCE);
		VIEW.paint();
	}
	
	

}
