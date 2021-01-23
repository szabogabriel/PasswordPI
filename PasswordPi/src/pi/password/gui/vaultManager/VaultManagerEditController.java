package pi.password.gui.vaultManager;

import java.util.ArrayList;
import java.util.List;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.splash.SplashController;
import pi.password.gui.vaultManager.VaultManagerEditDisplayable.Type;
import pi.password.service.gui.TextEditorService;
import pi.password.service.password.PasswordVaultService;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.ThreadUtil;

public class VaultManagerEditController extends AbstractController {
	
	private final PasswordVaultService PASSWORDS;
	private final TextEditorService TEXT_EDITOR_SERVICE;
	
	private final AbstractController PARENT;
	
	private ListView<VaultManagerEditDisplayable> view;
	private ListModel<VaultManagerEditDisplayable> model;
	
	private final String ORIGINAL_NAME;
	
	private String password;
	
	private VaultManagerEditDisplayable displayableName;
	private VaultManagerEditDisplayable displayablePassword;
	private VaultManagerEditDisplayable displayableSave;
	private VaultManagerEditDisplayable displayableCancel;
	
	public VaultManagerEditController(String name, PasswordVaultService passwords, TextEditorService textEditorService, AbstractController parentController) {
		this.PASSWORDS = passwords;
		this.PARENT = parentController;
		this.TEXT_EDITOR_SERVICE = textEditorService;
		this.ORIGINAL_NAME = name;
		this.password = (name.trim().length() < 1) ? "" : PASSWORDS.findPasswordEntity(name).get().getPassword();
		this.model = new ListModel<>();
		this.view = new ListView<>("Vault", Main.getInstance(ImageUtilService.class).getVaultBackground(), model);
		
		initModelData();
	}

	@Override
	public void activateHandler() {
		view.paint();
	}

	@Override
	public void reactivateHandler() {
		view.paint();
	}
	
	@Override
	public void handleButtonAReleased() {
		Main.getInstance(SplashController.class).activate();
	}
	
	@Override
	public void handleButtonBReleased() {
		//TODO: delete
	}
	
	@Override
	public void handleButtonCReleased() {
		Main.lock();
	}
	
	@Override
	public void handleJoystickUpReleased() {
		model.decreaseSelection();
	}

	@Override
	public void handleJoystickDownReleased() {
		model.increaseSelection();
	}
	
	@Override
	public void handleJoystickCenterReleased() {
		VaultManagerEditDisplayable selected = model.getSelectedValue();
		
		if (displayableName.equals(selected)) {
			ThreadUtil.createBackgroundJob(() -> {
				String newName = TEXT_EDITOR_SERVICE.editPlaintext("Change", "Enter new name", displayableName.getValue());
				displayableName.setValue(newName);
				model.setData(displayableName, 1);	
			});
		} else
		if (displayablePassword.equals(selected)) {
			ThreadUtil.createBackgroundJob(() -> {
				String newPsswd = TEXT_EDITOR_SERVICE.editPassword("Change", "New password for " + displayableName.getValue(), displayablePassword.getValue());
				displayablePassword.setValue(newPsswd);
				model.setData(displayablePassword, 3);
			});
		} else 
		if (displayableSave.equals(selected)) {
			ThreadUtil.createBackgroundJob(() -> {
				if (ORIGINAL_NAME.trim().length() > 0) {
					PASSWORDS.remove(ORIGINAL_NAME);
				}
				PASSWORDS.add(displayableName.getValue(), displayablePassword.getValue());
				PARENT.reactivate();
			});
		} else
		if (displayableCancel.equals(selected)) {
			PARENT.reactivate();
		}
	}
	
	private void initModelData() {
		List<VaultManagerEditDisplayable> data = new ArrayList<>();

		data.add(new VaultManagerEditDisplayable("Name", Type.KEY));
		displayableName = new VaultManagerEditDisplayable(ORIGINAL_NAME, Type.VALUE);
		data.add(displayableName);
		data.add(new VaultManagerEditDisplayable("Password", Type.KEY));
		displayablePassword = new VaultManagerEditDisplayable(password, Type.PASSWORD);
		data.add(displayablePassword);
		displayableSave = new VaultManagerEditDisplayable("Save", Type.OPTION); 
		data.add(displayableSave);
		displayableCancel = new VaultManagerEditDisplayable("Cancel", Type.OPTION);
		data.add(displayableCancel);
		
		model.setData(data);
	}

}
