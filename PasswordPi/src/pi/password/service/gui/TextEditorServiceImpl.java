package pi.password.service.gui;

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
	public String editPlaintext(String title, String description, String text) {
		TextEditorCallbackResultHolder result = new TextEditorCallbackResultHolder();
		TextEditorController controller = new TextEditorController(title, description, text, TextEditorType.PLAINTEXT, result, ALPHABET);
		controller.activate();
		
		result.waitForResult();
		
		return result.getResult();
	}

	@Override
	public String editPassword(String title, String description, String password) {
		TextEditorCallbackResultHolder result = new TextEditorCallbackResultHolder();
		TextEditorController controller = new TextEditorController(title, description, password, TextEditorType.PASSWORD, result, ALPHABET);
		controller.activate();
		
		result.waitForResult();
		
		return result.getResult();
	}
	
	private class TextEditorCallbackResultHolder implements TextEditorCallback {

		private String result = null;
		
		@Override
		public void handleTextEditorResult(String result) {
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
		
		public String getResult() {
			return result;
		}
	}

}
