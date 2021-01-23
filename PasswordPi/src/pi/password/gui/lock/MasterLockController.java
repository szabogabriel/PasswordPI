package pi.password.gui.lock;

import java.util.Optional;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.components.background.BackgroundView;
import pi.password.gui.splash.SplashController;
import pi.password.service.gui.TextEditorService;
import pi.password.service.lock.LockService;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.ThreadUtil;


public class MasterLockController extends AbstractController {

	private final ImageUtilService IMAGE_UTIL;
	private final LockService LOCK_SERVICE;
	private final TextEditorService TEXT_EDITOR_SERVICE;

	private final BackgroundView BACKGROUND;

	public MasterLockController(ImageUtilService imageUtil, LockService lockService,
			TextEditorService textEditorService) {
		this.IMAGE_UTIL = imageUtil;
		this.LOCK_SERVICE = lockService;
		this.TEXT_EDITOR_SERVICE = textEditorService;
		this.BACKGROUND = new BackgroundView(IMAGE_UTIL.getMainBackground(), "");
	}

	@Override
	public void activateHandler() {
		BACKGROUND.paint();
		ThreadUtil.createBackgroundJob(() -> handleMasterUnlock());
	}

	@Override
	public void reactivateHandler() {
		BACKGROUND.paint();
	}
	
	private void handleMasterUnlock() {
		Optional<String> masterPassword;
		do {
			masterPassword = TEXT_EDITOR_SERVICE.editPassword("LOCKED", "Enter master password", "", false);
		} while (!masterPassword.isPresent() || !LOCK_SERVICE.unlockMaster(masterPassword.get()));
		
		if (LOCK_SERVICE.isLockSet()) {
			activateScreenLock();
		} else {
			activateSplashScreen();
		}
	}

	private void activateScreenLock() {
		Main.getInstance(ScreenlockController.class).activate();
	}

	private void activateSplashScreen() {
		Main.getInstance(SplashController.class).activate();
	}

}
