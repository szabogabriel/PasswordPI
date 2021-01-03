package pi.password.gui.dialog;

import java.util.Arrays;

import pi.password.Main;
import pi.password.gui.AbstractModel;
import pi.password.gui.splash.SplashController;

public class DialogModel extends AbstractModel {

	private final DialogView VIEW;
	private final String MESSAGE;
	private final DialogType TYPE;
	private final DialogCallback CALLBACK;
	
	private int selection = 0;
	
	public DialogModel(String message, DialogType type, DialogView view, DialogCallback callback) {
		this.VIEW = view;
		this.MESSAGE = message;
		this.TYPE = type;
		this.CALLBACK = callback;
		VIEW.setMessage(message);
		VIEW.setValues(getOptions());
		VIEW.setSelection(selection);
	}

	public String getMessage() {
		return MESSAGE;
	}

	private String[] getOptions() {
		return Arrays.asList(TYPE.getResultOptions())
				.stream()
				.map(E -> E.getPrintableValue())
				.toArray(String[]::new);
	}
	
	public void handleSelectionUp() {
		if (TYPE.getResultOptions().length - 1 > selection) {
			selection++;
		}
		
		VIEW.setSelection(selection);
		VIEW.paint();
	}
	
	public void handleSelectionDown() {
		if (selection > 0) {
			selection--;
		}
		VIEW.setSelection(selection);
		VIEW.paint();
	}
	
	public void handleOk() {
		CALLBACK.handleDialogResult(TYPE.getResultOptions()[selection]);
		Main.getInstance(SplashController.class).activate();
	}

}
