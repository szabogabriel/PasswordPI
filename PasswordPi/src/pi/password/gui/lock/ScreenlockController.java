package pi.password.gui.lock;

import com.waveshare.keyboard.HatKey;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.splash.SplashController;
import pi.password.service.gui.DialogService;
import pi.password.service.gui.TextEditorService;
import pi.password.service.lock.LockService;

public class ScreenlockController extends AbstractController {

	private ScreenlockView view;
	private ScreenlockModel model;
	
	private final DialogService DIALOG_SERVICE;
	private final TextEditorService TEXT_EDITOR_SERVICE;
	private final LockService LOCK_SERVICE;
	
	public ScreenlockController(DialogService dialogService, TextEditorService textEditorService, LockService lockService) {
		this.DIALOG_SERVICE = dialogService;
		this.TEXT_EDITOR_SERVICE = textEditorService;
		this.LOCK_SERVICE = lockService;
	}
	
	@Override
	public void activateHandler() {
		view = new ScreenlockView();
		model = new ScreenlockModel(view);
		
		unlockScreen();
		
		activateSplashScreen();
	}
	
	@Override
	public void reactivateHandler() {
		view.paint();
	}

	@Override
	public void handleButtonAReleased() {
		model.addSequence(HatKey.KEY_A);
	}

	@Override
	public void handleButtonBReleased() {
		model.addSequence(HatKey.KEY_B);
	}

	@Override
	public void handleButtonCReleased() {
		model.addSequence(HatKey.KEY_C);
	}

	@Override
	public void handleJoystickUpReleased() {
		model.addSequence(HatKey.JOYSTICK_UP);
	}

	@Override
	public void handleJoystickDownReleased() {
		model.addSequence(HatKey.JOYSTICK_DOWN);
	}

	@Override
	public void handleJoystickLeftReleased() {
		model.addSequence(HatKey.JOYSTICK_LEFT);
	}

	@Override
	public void handleJoystickRightReleased() {
		model.addSequence(HatKey.JOYSTICK_RIGHT);
	}

	@Override
	public void handleJoystickCenterReleased() {
		activateSplashScreen();
	}
	
	private void unlockScreen() {
		String lockPassword = "";
		
		do {
			if (LOCK_SERVICE.isLocked()) {
				//TODO try unlocking it.
			}
		} while (!LOCK_SERVICE.unlock(lockPassword));
	}
	
	private void activateSplashScreen() {
		Main.getInstance(SplashController.class).activate();
	}
	
}
