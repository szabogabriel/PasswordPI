package pi.password.gui.vaultManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pi.password.entity.PasswordEntity;
import pi.password.gui.AbstractModel;
import pi.password.service.dialog.DialogService;
import pi.password.service.password.PasswordAlphabetService;
import pi.password.service.password.PasswordVaultService;

public class VaultManagerModel extends AbstractModel {
	
	private static final String TEXT_NEW_PASSWORD = "[New]";
	
	private static final int EDIT_MODE_NAME = 1;
	private static final int EDIT_MODE_PSSWD = 4;
	
	private final DialogService DIALOG;
	private final PasswordVaultService PASSWORDS;
	private final PasswordAlphabetService ALPHABET;
	
	private final VaultManagerListView VIEW_LIST;
	private final VaultManagerEditView VIEW_EDIT;
	private int selected;
	
	private String currentlyEditedText;
	private int currentlyEditedChar = 0;
	private boolean editMode = false;
	private int currentEditMode = 0;
	private PasswordEntity oldPasswordEntity;
	private PasswordEntity newPasswordEntity;
		
	public VaultManagerModel(DialogService dialog, PasswordVaultService vault, PasswordAlphabetService alphabetService, VaultManagerListView viewList, VaultManagerEditView viewEdit) {
		this.DIALOG = dialog;
		this.PASSWORDS = vault;
		this.ALPHABET = alphabetService;
		this.VIEW_LIST = viewList;
		this.VIEW_EDIT = viewEdit;
		
		selected = 0;
		
		this.VIEW_LIST.setKeys(getSortedPasswordKeys());
		this.VIEW_LIST.setSelection(selected);
		this.VIEW_LIST.paint();
	}
	
	public void increaseSelection() {
		if (editMode) {
			increaseSelectedCharacter();
		} else {
			movePasswordListSelectionDown();
		}
	}
	
	public void decreaseSelection() {
		if (editMode) {
			descreaseSelectedCharacter();
		} else {
			movePasswordListSelectionUp();
		}
	}
	
	public void nextCharacter() {
		if (editMode) {
			moveToNextCharacter();
		} else {
			startEditMode();
		}
	}
	
	public void prevCharacter() {
		if (editMode) {
			moveToPreviousCharacter();
		}
	}
	
	public void confirmSelection() {
		if (editMode) {
			editModeConfirm();
		} else {
			startEditMode();
		}
	}
	
	public String[] getSortedPasswordKeys() {
		List<String> ret = new ArrayList<>();
		
		ret.add(TEXT_NEW_PASSWORD);
		
		PASSWORDS.listPasswordEntityNames().stream()
		.sorted()
		.forEach(e -> ret.add(e));
		
		return ret.stream().toArray(String[]::new);
	}
	
	public int getCurrentSelected() {
		return this.selected;
	}
	
	public int currentlyEditedRow() {
		return currentEditMode;
	}
	
	private void movePasswordListSelectionDown() {
		if (selected + 1 < getSortedPasswordKeys().length) {
			selected++;
		}
		
		updateSelection();
	}
	
	private void movePasswordListSelectionUp() {
		if (selected > 0) {
			selected--;
		}
		
		updateSelection();
	}
	
	private void updateSelection() {
		VIEW_LIST.setSelection(selected);
		VIEW_LIST.paint();
	}
	
	private void descreaseSelectedCharacter() {
		char[] tmp = currentlyEditedText.toCharArray();
		char currentChar = tmp[currentlyEditedChar];
		char newChar = ALPHABET.getNextChar(currentChar);
		tmp[currentlyEditedChar] = newChar;
		currentlyEditedText = new String(tmp);
		
		updateEditView();
	}
	
	private void increaseSelectedCharacter() {
		char[] tmp = currentlyEditedText.toCharArray();
		char currentChar = tmp[currentlyEditedChar];
		char newChar = ALPHABET.getPrevChar(currentChar);
		tmp[currentlyEditedChar] = newChar;
		currentlyEditedText = new String(tmp);
		
		updateEditView();
	}
	
	private void moveToNextCharacter() {
		currentlyEditedChar++;
		if (currentlyEditedChar == currentlyEditedText.length()) {
			currentlyEditedText += " ";
		}
		
		updateEditView();
	}
	
	private void moveToPreviousCharacter() {
		if (currentlyEditedChar > 0) {
			currentlyEditedChar--;
		}
		
		updateEditView();
	}
	
	private boolean editModeConfirm() {
		boolean ret = false;
		if (currentEditMode == EDIT_MODE_NAME) {
			currentEditMode = EDIT_MODE_PSSWD;
			
			newPasswordEntity = new PasswordEntity(currentlyEditedText, null);
			
			String psswd = " ";
			if (oldPasswordEntity != null && oldPasswordEntity.getPassword() != null) {
				psswd = oldPasswordEntity.getPassword();
			}
			currentlyEditedText = psswd;
			
			updateEditView();
		} else 
		if (currentEditMode == EDIT_MODE_PSSWD) {
			new Thread(() -> {
				String name = newPasswordEntity.getName();
				String password = currentlyEditedText;
				
				if (oldPasswordEntity == null) {
					String message = "Add new password " + name + "?";
					if (DIALOG.showYesNoDialog(message)) {
						PASSWORDS.add(name, password);
					}
				} else {
					String message = "Change password " + oldPasswordEntity.getName() + "?";
					
					if (DIALOG.showYesNoDialog(message)) {
						PASSWORDS.remove(oldPasswordEntity.getName());
						PASSWORDS.add(name, password);					
					}
				}
				
				exitEditMode();
			}).start();
			
			ret = true;
		}
		return ret;
	}
	
	private void exitEditMode() {
		if (editMode) {
			editMode = false;
			currentEditMode = 0;
			currentlyEditedChar = -1;
			currentlyEditedText = "";
			oldPasswordEntity = null;
			newPasswordEntity = null;
			VIEW_LIST.paint();
		}
	}
	
	private void startEditMode() {
		String nameSelected = getSortedPasswordKeys()[selected];
		String name = "";
		String password = "";
		
		oldPasswordEntity = null;
		
		if (!TEXT_NEW_PASSWORD.equals(nameSelected)) {
			name = nameSelected;
			Optional<PasswordEntity> psswd = PASSWORDS.findPasswordEntity(name);
			if (psswd.isPresent()) {
				password = psswd.get().getPassword();
			}
			
			oldPasswordEntity = new PasswordEntity(name, password);
		}
		
		editMode = true;
		currentEditMode = EDIT_MODE_NAME;
		currentlyEditedText = name;
		currentlyEditedChar = name.length() - 1;
		
		VIEW_EDIT.setAccountName(name);
		VIEW_EDIT.setPassword(password);
		VIEW_EDIT.setCurrentlyEditedRow(1);
		VIEW_EDIT.setCurrentlyEditedChar(currentlyEditedChar);
		VIEW_EDIT.paint();
	}
	
	private void updateEditView() {
		if (currentEditMode == EDIT_MODE_NAME) {
			VIEW_EDIT.setAccountName(currentlyEditedText);
		} else
		if (currentEditMode == EDIT_MODE_PSSWD) {
			VIEW_EDIT.setPassword(currentlyEditedText);
		}
		
		VIEW_EDIT.setCurrentlyEditedChar(currentlyEditedChar);
		VIEW_EDIT.setCurrentlyEditedRow(currentEditMode);
		VIEW_EDIT.paint();
	}
	
}
