package pi.password.service.password;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import pi.password.entity.PasswordEntity;

public interface PasswordVaultService {
	
	List<String> listPasswordEntityNames();
	
	Set<PasswordEntity> listPasswordEntities();
	
	Optional<PasswordEntity> findPasswordEntity(String name);
	
	boolean isKnownPasswordEntity(String name);
	
	void update(String name, String password);

}
