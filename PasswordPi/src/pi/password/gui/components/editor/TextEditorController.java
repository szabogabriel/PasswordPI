package pi.password.gui.components.editor;

import pi.password.gui.AbstractController;
import pi.password.service.alphabet.AlphabetService;

public class TextEditorController extends AbstractController {

	private TextEditorView view;
	private TextEditorModel model;
	
	public TextEditorController(String title, String editorDescription, String initValue, TextEditorType type, TextEditorCallback callback, AlphabetService alphabet) {
		model = new TextEditorModel(initValue, type, callback, alphabet);
		view = new TextEditorView(title, editorDescription, model);
	}
	
	@Override
	public void activateHandler() {
		view.paint();
	}

	@Override
	public void reactivateHandler() {
		view.paint();
	}
	
	public void handleButtonAReleased() {
		//TODO - go to splash screen maybe?
	}
	
	public void handleButtonBReleased() {
		//TODO - go to splash screen maybe?
	}
	
	public void handleButtonCReleased() {
		//TODO - activate settings maybe?
	}
	
	public void handleJoystickUpReleased() {
		model.increaseCurrentCharacter();
	}
	
	public void handleJoystickDownReleased() {
		model.decreaseCurrentCharacter();
	}
	
	public void handleJoystickLeftReleased() {
		model.moveToPrevCharPosition();
	}
	
	public void handleJoystickRightReleased() {
		model.moveToNextCharPosition();
	}
	
	public void handleJoystickCenterReleased() {
		model.confirmCurrentStatus();
	}

}
