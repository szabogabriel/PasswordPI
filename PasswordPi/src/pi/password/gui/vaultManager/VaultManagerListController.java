package pi.password.gui.vaultManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.components.list.StringDisplayable;
import pi.password.gui.splash.SplashController;
import pi.password.service.gui.DialogService;
import pi.password.service.gui.TextEditorService;
import pi.password.service.password.PasswordVaultService;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.ThreadUtil;

public class VaultManagerListController extends AbstractController {
	
	private static final String NEW_PASSWORD_STRING = "[New]";

	private ListView<StringDisplayable> view;
	private ListModel<StringDisplayable> model;
	
	private PasswordVaultService PASSWORDS;
	private DialogService DIALOG;
	private TextEditorService TEXT_EDITOR_SERVICE;
	
	public VaultManagerListController(PasswordVaultService passwords, DialogService dialog, TextEditorService editor) {
		this.PASSWORDS = passwords;
		this.DIALOG = dialog;
		this.TEXT_EDITOR_SERVICE = editor;
		model = new ListModel<>();
		view = new ListView<>("Vault", Main.getInstance(ImageUtilService.class).getVaultBackground(), model);
		updateModelData();
	}
	
	@Override
	public void activateHandler() {
		view.paint();
	}
	
	@Override
	public void reactivateHandler() {
		updateModelData();
	}

	@Override
	public void handleButtonAReleased() {
		Main.getInstance(SplashController.class).activate();			
	}
	
	@Override
	public void handleButtonBReleased() {
		StringDisplayable selected = model.getSelectedValue();
		
		if (selected != null && !NEW_PASSWORD_STRING.equals(selected.getValue())) {
			removePassword();
		}
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
	public void handleJoystickLeftReleased() {
		for (int i = 0; i < view.MAX_ROW_NUM; i++) {
			model.decreaseSelection();
		}
	}

	@Override
	public void handleJoystickRightReleased() {
		for (int i = 0; i < view.MAX_ROW_NUM; i++) {
			model.increaseSelection();
		}		
	}

	@Override
	public void handleJoystickCenterReleased() {
		ThreadUtil.createBackgroundJob(() -> {
			String currentName = getCurrentSelectedName();
			
			if (!NEW_PASSWORD_STRING.equals(currentName)) {
				Optional<Boolean> ret = DIALOG.showEditDeleteCancelDialog("Choose action for " + currentName);
				
				if (ret.isPresent()) {
					if (ret.get()) {
						new VaultManagerEditController(currentName, PASSWORDS, TEXT_EDITOR_SERVICE, this).activate();
					} else {
						removePassword();
					}
				}
			} else {
				new VaultManagerEditController("", PASSWORDS, TEXT_EDITOR_SERVICE, this).activate();
			}
		});

	}
	
	private void removePassword() {
		ThreadUtil.createBackgroundJob(() -> {
			String name = getCurrentSelectedName();
			
			if (DIALOG.showYesNoDialog("Do you really want to remove " + name + " ?")) {
				PASSWORDS.remove(name);
				updateModelData();
			}
		});
	}
	
	private String getCurrentSelectedName() {
		String nameSelected = model.getSelectedValue().getValue();
		return nameSelected;
	}
	
	private List<String> getSortedPasswordKeys() {
		return PASSWORDS.listPasswordEntityNames().stream().sorted().collect(Collectors.toList());
	}
	
	private void updateModelData() {
		List<StringDisplayable> data = new ArrayList<>();
		data.add(new StringDisplayable(NEW_PASSWORD_STRING));
		data.addAll(getSortedPasswordKeys().stream().map(d -> new StringDisplayable(d)).collect(Collectors.toList()));
		model.setData(data);
	}

}
