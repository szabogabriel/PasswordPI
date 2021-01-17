package pi.password.service.gui;

import java.util.Optional;

public interface DialogService {
	
	boolean showYesNoDialog(String message);
	
	Optional<Boolean> showYesNoCancelDialog(String message);
	
	Optional<Boolean> showEditDeleteCancelDialog(String message);
	
	void showMessage(String message);

}
