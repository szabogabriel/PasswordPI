package pi.password.service.gui;

import java.util.Optional;

import pi.password.gui.components.dialog.DialogCallback;
import pi.password.gui.components.dialog.DialogController;
import pi.password.gui.components.dialog.DialogResult;
import pi.password.gui.components.dialog.DialogType;

public class DialogServiceImpl implements DialogService {

	@Override
	public boolean showYesNoDialog(String message) {
		DialogCallbackResultHolder result = new DialogCallbackResultHolder();
		DialogController controller = new DialogController(message, DialogType.YES_NO, result);
		controller.activate();
		
		result.waitForResult();
		
		return result.getResult().get();
	}

	@Override
	public Optional<Boolean> showYesNoCancelDialog(String message) {
		DialogCallbackResultHolder result = new DialogCallbackResultHolder();
		DialogController controller = new DialogController(message, DialogType.YES_NO_CANCEL, result);
		controller.activate();
		
		return result.getResult();
	}

	@Override
	public void showMessage(String message) {
		DialogController controller = new DialogController(message, DialogType.OK, t -> {});
		controller.activate();
	}
	
	@Override
	public Optional<Boolean> showEditDeleteCancelDialog(String message) {
		DialogCallbackResultHolder result = new DialogCallbackResultHolder();
		DialogController controller = new DialogController(message, DialogType.EDIT_DELETE_CANCEL, result);
		controller.activate();
		
		result.waitForResult();
		
		return result.getResult();
	}
	
	private class DialogCallbackResultHolder implements DialogCallback {

		private DialogResult result = null;
		
		@Override
		public void handleDialogResult(DialogResult result) {
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
		
		public Optional<Boolean> getResult() {
			Optional<Boolean> ret = Optional.empty();
			switch (result) {
			case CANCEL:
				break;
			case NO:
				ret = Optional.of(Boolean.FALSE);
				break;
			case OK:
				ret = Optional.of(Boolean.TRUE);
				break;
			case YES:
				ret = Optional.of(Boolean.TRUE);
				break;
			case DELETE:
				ret = Optional.of(Boolean.FALSE);
				break;
			case EDIT:
				ret = Optional.of(Boolean.TRUE);
			default:
				break;
			}
			return ret;
		}
	}

}
