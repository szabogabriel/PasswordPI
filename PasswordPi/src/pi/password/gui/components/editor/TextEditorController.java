package pi.password.gui.components.editor;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.service.alphabet.AlphabetService;

public class TextEditorController extends AbstractController {

	private TextEditorView view;
	private TextEditorModel model;
	
	private boolean closable = false;
	
	public TextEditorController(String title, String editorDescription, String initValue, TextEditorType type, TextEditorCallback callback, AlphabetService alphabet) {
	
	}
		
	public TextEditorController(String title, String editorDescription, String initValue, TextEditorType type, TextEditorCallback callback, AlphabetService alphabet, boolean closable) {
		model = new TextEditorModel(initValue, type, callback, alphabet);
		view = new TextEditorView(title, editorDescription, model);
		this.closable = closable;
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
		if (closable) {
			model.closeEditor();
		}
	}
	
	public void handleButtonBReleased() {
		model.deleteCurrentChar();
	}
	
	public void handleButtonCReleased() {
		if (closable) {
			model.closeEditor();
			Main.lock();
		}
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
