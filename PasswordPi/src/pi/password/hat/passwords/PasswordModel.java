package pi.password.hat.passwords;

import pi.password.hat.AbstractModel;
import pi.password.manager.PasswordVault;

public class PasswordModel extends AbstractModel {

	private final PasswordView VIEW;
	private final PasswordVault PASSWORDS;
	private int selected;
	
	public PasswordModel(PasswordVault passwords, PasswordView view) {
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
