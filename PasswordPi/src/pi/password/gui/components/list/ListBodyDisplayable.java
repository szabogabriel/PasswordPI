package pi.password.gui.components.list;

import pi.password.service.hat.DisplayService;

public interface ListBodyDisplayable {
	
	void display(DisplayService display, int row, int fontSelect);
	
	boolean isSelectable();
	
}
