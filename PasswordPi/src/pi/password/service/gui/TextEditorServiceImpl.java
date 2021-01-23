package pi.password.service.gui;

import java.util.Optional;

import pi.password.gui.components.editor.TextEditorCallback;
import pi.password.gui.components.editor.TextEditorController;
import pi.password.gui.components.editor.TextEditorType;
import pi.password.service.alphabet.AlphabetService;

public class TextEditorServiceImpl implements TextEditorService {
	
	private final AlphabetService ALPHABET;
	
	public TextEditorServiceImpl(AlphabetService alphabet) {
		this.ALPHABET = alphabet;
	}
	
	@Override
	public Optional<String> editPlaintext(String title, String description, String text) {
		return editPlaintext(title, description, text, true);
	}
	
	@Override
	public Optional<String> editPlaintext(String title, String description, String text, boolean closable) {
		TextEditorCallbackResultHolder result = new TextEditorCallbackResultHolder();
		TextEditorController controller = new TextEditorController(title, description, text, TextEditorType.PLAINTEXT, result, ALPHABET, closable);
		controller.activate();
		
		result.waitForResult();
		
		return result.getResult();
	}

	@Override
	public Optional<String> editPassword(String title, String description, String password) {
		return editPassword(title, description, password, true);
	}
	
	@Override
	public Optional<String> editPassword(String title, String description, String password, boolean closable) {
		TextEditorCallbackResultHolder result = new TextEditorCallbackResultHolder();
		TextEditorController controller = new TextEditorController(title, description, password, TextEditorType.PASSWORD, result, ALPHABET, closable);
		controller.activate();
		
		result.waitForResult();
		
		return result.getResult();
	}
	
	private class TextEditorCallbackResultHolder implements TextEditorCallback {

		private Optional<String> result = null;
		
		@Override
		public void handleTextEditorResult(Optional<String> result) {
			this.result = result;
		}
		
		public void waitForResult() {
			while (result == null) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public Optional<String> getResult() {
			return result;
		}
	}

}
