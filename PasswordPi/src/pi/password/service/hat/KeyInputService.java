package pi.password.service.hat;

import pi.password.gui.AbstractController;

public interface KeyInputService {

	void setButtonHandler(AbstractController buttonHandler);
	
	AbstractController getCurrentButtonHandler();
}
