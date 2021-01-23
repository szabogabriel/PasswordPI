package pi.password.gui.lock;

import java.awt.Color;
import java.util.Optional;

import pi.password.Main;
import pi.password.enums.LocalizedTexts;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.components.list.StringDisplayable;
import pi.password.service.gui.TextEditorService;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.lock.LockService;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.ThreadUtil;

public class MasterLockSetController extends AbstractController {
	
	private final int TEXT_BOX_1_POSITION = 2;
	private final int TEXT_BOX_2_POSITION = 4;
	private final int OK_BUTTON_POSITION = 5;
	
	private final ImageUtilService IMAGE_UTIL;
	private final LockService LOCK_SERVICE;
	private final TextEditorService TEXT_EDITOR_SERVICE;
	
	private ListModel<StringDisplayable> model;
	private ListView<StringDisplayable> view;

	public MasterLockSetController(ImageUtilService imageUtil, LockService lockService,
			TextEditorService textEditorService) {
		this.IMAGE_UTIL = imageUtil;
		this.LOCK_SERVICE = lockService;
		this.TEXT_EDITOR_SERVICE = textEditorService;
	}
	
	@Override
	public void activateHandler() {
		if (LOCK_SERVICE.isMasterLockSet()) {
			activateNextScreen();
		} else {
			model = new ListModel<StringDisplayable>();
			view = new ListView<StringDisplayable>(LocalizedTexts.VIEW_NEW_MASTER_TITLE.toString(), IMAGE_UTIL.getMainBackground(), model);
			view.paint();
			
			model.addData(new StringDisplayable(LocalizedTexts.VIEW_NEW_MASTER_LINE_1.toString(), Color.WHITE, false, TextAlign.CENTER));
			model.addData(new StringDisplayable(LocalizedTexts.VIEW_NEW_MASTER_LINE_2.toString(), Color.WHITE, false, TextAlign.CENTER));
			model.addData(new StringDisplayable("", Color.GRAY, true, TextAlign.LEFT, this::toPassword));
			model.addData(new StringDisplayable(LocalizedTexts.VIEW_NEW_MASTER_REPEAT.toString(), Color.WHITE, false, TextAlign.CENTER));
			model.addData(new StringDisplayable("", Color.GRAY, true, TextAlign.LEFT, this::toPassword));
			model.addData(new StringDisplayable(LocalizedTexts.VIEW_NEW_MASTER_CONFIRM.toString(), Color.GREEN, true, TextAlign.CENTER));
		}
	}

	@Override
	public void reactivateHandler() {
		view.paint();
	}
	
	public void handleJoystickUpReleased() {
		model.decreaseSelection();
	}
	
	public void handleJoystickDownReleased() {
		model.increaseSelection();
	}
	
	public void handleJoystickCenterReleased() {
		int selection = model.getCurrentSelection();
		
		if (selection == TEXT_BOX_1_POSITION) {
			ThreadUtil.createBackgroundJob(() -> {
				Optional<String> ret = TEXT_EDITOR_SERVICE.editPassword(LocalizedTexts.VIEW_NEW_MASTER_DIALOG_HEADER.toString(), LocalizedTexts.VIEW_NEW_MASTER_ENTER_MASTER.toString(), getTextBoxContent(TEXT_BOX_1_POSITION));
				if (ret.isPresent()) {
					setTextBox(ret.get(), TEXT_BOX_1_POSITION);
				}
			});
		} else 
		if (selection == TEXT_BOX_2_POSITION) {
			ThreadUtil.createBackgroundJob(() -> {
				Optional<String> ret = TEXT_EDITOR_SERVICE.editPassword(LocalizedTexts.VIEW_NEW_MASTER_DIALOG_HEADER.toString(), LocalizedTexts.VIEW_NEW_MASTER_REPEAT_MASTER.toString(), getTextBoxContent(TEXT_BOX_2_POSITION));
				if (ret.isPresent()) {
					setTextBox(ret.get(), TEXT_BOX_2_POSITION);
				}
			});
		} else
		if (selection == OK_BUTTON_POSITION) {
			String text1 = getTextBoxContent(TEXT_BOX_1_POSITION);
			String text2 = getTextBoxContent(TEXT_BOX_2_POSITION);
			
			if (text1 != null && text1.length() > 1 && text1.equals(text2)) {
				LOCK_SERVICE.updateMasterKey("", text1);
				activateNextScreen();
			}
		}
	}
	
	private String getTextBoxContent(int position) {
		String tmp = model.getValues().get(position).getValue();
		
		return tmp;
	}
	
	private void setTextBox(String content, int position) {
		StringDisplayable tmp = new StringDisplayable(content, Color.GRAY, true, TextAlign.LEFT, this::toPassword);
		model.setData(tmp, position);
	}

	private void activateNextScreen() {
		Main.getInstance(MasterLockController.class).activate();
	}
	
	private String toPassword(String content) {
		return content.replaceAll(".", "*");
	}
}
