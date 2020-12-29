package pi.password.gui.passwords;

import pi.password.gui.AbstractModel;
import pi.password.service.password.PasswordVaultService;

public class PasswordModel extends AbstractModel {

	private final PasswordView VIEW;
	private final PasswordVaultService PASSWORDS;
	private int selected;
	
	public PasswordModel(PasswordVaultService passwords, PasswordView view) {
		this.PASSWORDS = passwords;
		this.VIEW = view;
		this.selected = 0;
		this.VIEW.setKeys(getSortedPasswordKeys());
		this.VIEW.setSelection(selected);
		this.VIEW.paint();
	}
	
	public String[] getSortedPasswordKeys() {
		return PASSWORDS.listPasswordEntityNames().stream().sorted().toArray(String[]::new);
	}
	
	public int getCurrentSelected() {
		return this.selected;
	}
	
	public String getSelectedPassword() {
		String nameSelected = getSortedPasswordKeys()[selected];
		String ret = PASSWORDS
				.listPasswordEntities()
				.parallelStream()
				.filter(e -> e.getName().equals(nameSelected))
				.map(e -> e.getPassword())
				.findAny()
				.orElse(null);
		return ret;
	}
	
	public void increaseSelection() {
		if (selected + 1 < getSortedPasswordKeys().length) {
			selected++;
		}
		
		VIEW.setSelection(selected);
		VIEW.paint();
	}
	
	public void decreaseSelection() {
		if (selected > 0) {
			selected--;
		}
		
		VIEW.setSelection(selected);
		VIEW.paint();
	}
	

}
