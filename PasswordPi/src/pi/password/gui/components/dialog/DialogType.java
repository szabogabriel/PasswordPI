package pi.password.gui.components.dialog;

public enum DialogType {
	YES_NO (DialogResult.YES, DialogResult.NO),
	YES_NO_CANCEL (DialogResult.YES, DialogResult.NO, DialogResult.CANCEL),
	EDIT_DELETE_CANCEL (DialogResult.EDIT, DialogResult.DELETE, DialogResult.CANCEL),
	OK (DialogResult.OK),
	;
	
	private final DialogResult [] OPTIONS;
	
	private DialogType(DialogResult... options) {
		this.OPTIONS = options;
	}
	
	public DialogResult[] getResultOptions() {
		return OPTIONS;
	}
}
