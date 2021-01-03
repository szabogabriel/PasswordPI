package pi.password.service.dialog;

import java.util.Optional;

public interface DialogService {
	
	boolean showYesNoDialog(String message);
	
	Optional<Boolean> showYesNoCancelDialog(String message);
	
	void showMessage(String message);

}
