package pi.password.manager.vault;

import java.util.Optional;
import java.util.Set;

import pi.password.manager.PasswordEntity;

public interface PasswordVault {
	
	Set<PasswordEntity> listPasswordEntities();
	
	Optional<PasswordEntity> findPasswordEntity(String name);
	
	boolean isKnownPasswordEntity(String name);
	
	void update(String name, String password);

}
