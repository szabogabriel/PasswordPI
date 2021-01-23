package pi.password.gui.screenlock;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.components.background.BackgroundView;
import pi.password.gui.splash.SplashController;
import pi.password.service.gui.TextEditorService;
import pi.password.service.lock.LockService;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.ThreadUtil;

/**
 * This is a dummy controller. It is mainly used as a tool to unify the GUI
 * flow. Since the dialogs and input fields are operating on the
 * {@link pi.password.gui.AbstractController#reactivateHandler} method upon
 * finishing the work, they need some object to be stored as parent and the
 * notify when the dialog finished.
 * 
 * @author g
 *
 */
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
		if (LOCK_SERVICE.isMasterLockSet()) {
			unlockScreen();
		} else {
			setMasterLock();
			activateSplashScreen();
		}
	}

	private void unlockScreen() {
		String lockPassword = "";

		do {
			unlockMaster();

			if (LOCK_SERVICE.isLocked()) {
				activateScreenLock();
			} else {
				activateSplashScreen();
			}
		} while (!LOCK_SERVICE.unlock(lockPassword));
	}

	private void unlockMaster() {
		String masterPassword;
		do {
			masterPassword = TEXT_EDITOR_SERVICE.editPassword("LOCKED", "Enter master password", "");
		} while (!LOCK_SERVICE.unlockMaster(masterPassword));
	}

	private void activateScreenLock() {
		Main.getInstance(ScreenlockController.class).activate();
	}

	private void activateSplashScreen() {
		Main.getInstance(SplashController.class).activate();
	}

	private void setMasterLock() {
		String master1, master2;

		do {
			master1 = TEXT_EDITOR_SERVICE.editPassword("MASTER", "Set master password", "");
			master2 = TEXT_EDITOR_SERVICE.editPassword("MASTER", "Repeat master password", "");
		} while (master1 == null || !master1.equals(master2));

		LOCK_SERVICE.updateMasterKey("", master1);
	}

}
