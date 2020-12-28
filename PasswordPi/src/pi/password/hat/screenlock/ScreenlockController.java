package pi.password.hat.screenlock;

import com.waveshare.keyboard.HatKey;

import pi.password.hat.AbstractController;
import pi.password.hat.splash.SplashController;

public class ScreenlockController extends AbstractController {

	private ScreenlockView view;
	private ScreenlockModel model;
	
	@Override
	public void activateHandler() {
		view = new ScreenlockView();
		model = new ScreenlockModel(view);
	}

	@Override
	public void handleButtonAPressed() {
	}

	@Override
	public void handleButtonBPressed() {
	}

	@Override
	public void handleButtonCPressed() {
	}

	@Override
	public void handleJoystickUpPressed() {
	}

	@Override
	public void handleJoystickDownPressed() {
	}

	@Override
	public void handleJoystickLeftPressed() {
	}

	@Override
	public void handleJoystickRightPressed() {
	}

	@Override
	public void handleJoystickCenterPressed() {
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
		new SplashController().activate();	
	}

}
