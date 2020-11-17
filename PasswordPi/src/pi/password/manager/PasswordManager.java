package pi.password.manager;

import java.util.List;
import java.util.stream.Collectors;

import pi.password.manager.vault.PasswordVault;

public class PasswordManager {
	
	private final PasswordVault VAULT;
	
	public PasswordManager(PasswordVault vault) {
		this.VAULT = vault;
	}

	public List<String> listPasswordKeys() {
		return VAULT.listPasswordEntities().stream().map(e -> e.getName()).sorted().collect(Collectors.toList());
	}
	
	
}
