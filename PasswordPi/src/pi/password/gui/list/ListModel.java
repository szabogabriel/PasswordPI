package pi.password.gui.list;

import pi.password.gui.AbstractModel;

public class ListModel extends AbstractModel {
	
	private String[] values = new String [] {};
	private int selected = 0;
	
	private ListModelUpdateListener listener;
	
	public ListModel() {
		this.selected = 0;
	}
	
	public void setListener(ListModelUpdateListener listener) {
		this.listener = listener;
	}
	
	public String[] getValues() {
		return values;
	}
	
	public void setValues(String [] values) {
		if (values == null) {
			this.values = new String [] {};
		} else {
			this.values = values;
		}
		
		if (listener != null) {
			listener.contentChanged();
		}
	}
	
	public int getCurrentSelected() {
		return this.selected;
	}
	
	public void increaseSelection() {
		if (selected + 1 < values.length) {
			selected++;
			
			if (listener != null) {
				listener.selectionChanged();
			}
		}
	}
	
	public void decreaseSelection() {
		if (selected > 0) {
			selected--;
			
			if (listener != null) {
				listener.selectionChanged();
			}
		}
	}

}
