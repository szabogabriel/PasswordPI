package pi.password.gui.components.dialog;

public enum DialogResult {
	
	YES ("Yes"),
	NO ("No"),
	OK ("Ok"),
	CANCEL ("Cancel"),
	;
	
	private final String PRINTABLE_VALUE;
	
	private DialogResult(String printableValue) {
		this.PRINTABLE_VALUE = printableValue;
	}
	
	public String getPrintableValue() {
		return PRINTABLE_VALUE;
	}

}
