package pi.password.manager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PasswordVault {
	
	List<String> listPasswordEntityNames();
	
	Set<PasswordEntity> listPasswordEntities();
	
	Optional<PasswordEntity> findPasswordEntity(String name);
	
	boolean isKnownPasswordEntity(String name);
	
	void update(String name, String password);

}
