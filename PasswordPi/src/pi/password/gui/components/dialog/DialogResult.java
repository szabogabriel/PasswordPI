package pi.password.gui.components.dialog;

import pi.password.enums.LocalizedTexts;

public enum DialogResult {
	
	YES (LocalizedTexts.DIALOG_RESULT_YES.toString()),
	NO (LocalizedTexts.DIALOG_RESULT_NO.toString()),
	OK (LocalizedTexts.DIALOG_RESULT_OK.toString()),
	EDIT (LocalizedTexts.DIALOG_RESULT_EDIT.toString()),
	DELETE (LocalizedTexts.DIALOG_RESULT_DELETE.toString()),
	CANCEL (LocalizedTexts.DIALOG_RESULT_CANCEL.toString()),
	;
	
	private final String PRINTABLE_VALUE;
	
	private DialogResult(String printableValue) {
		this.PRINTABLE_VALUE = printableValue;
	}
	
	public String getPrintableValue() {
		return PRINTABLE_VALUE;
	}

}
