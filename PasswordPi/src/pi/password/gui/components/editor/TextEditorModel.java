package pi.password.gui.components.editor;

import java.util.Optional;
import java.util.function.Consumer;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.AbstractModel;
import pi.password.service.alphabet.AlphabetService;
import pi.password.service.hat.KeyInputService;

public class TextEditorModel extends AbstractModel {
	
	private final TextEditorType TYPE;
	private final TextEditorCallback CALLBACK;
	
	private final AlphabetService ALPHABET;
	
	private final boolean CLOSABLE;
	
	private char[] content;
	
	private int currentEditChar = 0;
	
	private AbstractController previousController = Main.getInstance(KeyInputService.class).getCurrentButtonHandler();
	
	private TextEditorChangeListener listener;
	
	public TextEditorModel(String initValue, TextEditorType type, TextEditorCallback callback, AlphabetService alphabet) {
		this(initValue, type, callback, alphabet, true);
	}
	
	public TextEditorModel(String initValue, TextEditorType type, TextEditorCallback callback, AlphabetService alphabet, boolean closable) {
		this.CLOSABLE = closable;
		this.TYPE = type;
		this.CALLBACK = callback;
		this.ALPHABET = alphabet;
		
		if (initValue ==  null || initValue.length() == 0) {
			this.content = new char [] {getFirstChar()};
		} else {
			this.content = initValue.toCharArray();
			currentEditChar = initValue.length() - 1;
		}
	}
	
	public void setListener(TextEditorChangeListener listener) {
		this.listener = listener;
	}
	
	public String getCurrentContent() {
		return new String(content);
	}
	
	public int getCurrentSelectedCharPosition() {
		return currentEditChar;
	}
	
	public TextEditorType getType() {
		return TYPE;
	}
	
	private char getFirstChar() {
		char ret = ' ';
		switch (TYPE) {
		case PASSWORD:
			ret = ALPHABET.getPasswordAlphabet()[0];
			break;
		case PLAINTEXT:
			ret = ALPHABET.getRegularAlphabet()[0];
			break;
		}
		return ret;
	}
	
	public void moveToNextCharPosition() {
		currentEditChar++;
		
		if (currentEditChar == content.length) {
			content = (new String(content) + getFirstChar()).toCharArray();
		}
		
		castEvent(l -> l.selectionChanged());
	}
	
	public void moveToPrevCharPosition() {
		if (currentEditChar > 0) {
			currentEditChar--;
			
			castEvent(l -> l.selectionChanged());
		}
	}
	
	public void deleteCurrentChar() {
		StringBuilder sb = new StringBuilder();
		
		if (content.length > 1) {
			sb.append(new String(content));
			sb.deleteCharAt(currentEditChar);
			content = sb.toString().toCharArray();
			
			if (currentEditChar == content.length) {
				currentEditChar--;
			}
		}
		
		castEvent(l -> l.contentChanged());
	}
	
	public void increaseCurrentCharacter() {
		char current = content[currentEditChar];
		current = getNextChar(current);
		content[currentEditChar] = current;
		
		castEvent(l -> l.contentChanged());
	}
	
	public void decreaseCurrentCharacter() {
		char current = content[currentEditChar];
		current = getPrevChar(current);
		content[currentEditChar] = current;
		
		castEvent(l -> l.contentChanged());
	}
	
	private char getNextChar(char currentChar) {
		return (TYPE == TextEditorType.PLAINTEXT) ? ALPHABET.getRegularNextChar(currentChar) : ALPHABET.getPasswordNextChar(currentChar);
	}
	
	private char getPrevChar(char current) {
		return (TYPE == TextEditorType.PLAINTEXT) ? ALPHABET.getRegularPrevChar(current) : ALPHABET.getPasswordPrevChar(current);
	}
	
	public void confirmCurrentStatus() {
		closeEditor(Optional.of(new String(content)));
	}
	
	public void closeEditor() {
		closeEditor(Optional.empty());
	}
	
	private void closeEditor(Optional<String> result) {
		CALLBACK.handleTextEditorResult(result);
		Main.getInstance(KeyInputService.class).setButtonHandler(previousController);
		if (CLOSABLE) {
			previousController.reactivate();
		}
	}
	
	private void castEvent(Consumer<TextEditorChangeListener> event) {
		if (listener != null) {
			event.accept(listener);
		}
	}

}
