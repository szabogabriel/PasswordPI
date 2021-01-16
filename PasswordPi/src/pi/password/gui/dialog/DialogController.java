package pi.password.gui.dialog;

import pi.password.gui.AbstractController;

public class DialogController extends AbstractController {
	
	private DialogView view;
	private DialogModel model;
	
	public DialogController(String message, DialogType type, DialogCallback callback) {
		view = new DialogView();
		model = new DialogModel(message, type, view, callback);
	}

	@Override
	public void activateHandler() {
		view.paint();
	}
	
	@Override
	public void reactivateHandler() {
		view.paint();
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
	}

	@Override
	public void handleButtonBReleased() {
	}

	@Override
	public void handleButtonCReleased() {
	}

	@Override
	public void handleJoystickUpReleased() {
		model.handleSelectionDown();
	}

	@Override
	public void handleJoystickDownReleased() {
		model.handleSelectionUp();
	}

	@Override
	public void handleJoystickLeftReleased() {
	}

	@Override
	public void handleJoystickRightReleased() {
	}

	@Override
	public void handleJoystickCenterReleased() {
		model.handleOk();
	}

}
