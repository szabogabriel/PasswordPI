package pi.password.service.gui;

import java.util.Optional;

public interface TextEditorService {
	
	Optional<String> editPlaintext(String title, String description, String text);
	
	Optional<String> editPlaintext(String title, String description, String text, boolean closable);
	
	Optional<String> editPassword(String title, String description, String password);
	
	Optional<String> editPassword(String title, String description, String password, boolean closable);
	
}
