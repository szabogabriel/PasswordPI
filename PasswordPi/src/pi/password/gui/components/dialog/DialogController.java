package pi.password.gui.components.dialog;

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
	public void handleJoystickUpReleased() {
		model.handleSelectionDown();
	}

	@Override
	public void handleJoystickDownReleased() {
		model.handleSelectionUp();
	}

	@Override
	public void handleJoystickCenterReleased() {
		model.handleOk();
	}

}
