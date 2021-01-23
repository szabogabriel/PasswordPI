package pi.password.gui.components.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import pi.password.gui.AbstractModel;

public class ListModel<T extends ListBodyDisplayable> extends AbstractModel {
	
	private List<T> elements = new ArrayList<>();
	
	private int selected = -1;
	
	private ListModelUpdateListener listener;
	
	public ListModel() {
		this.selected = -1;
	}
	
	public void setListener(ListModelUpdateListener listener) {
		this.listener = listener;
	}
	
	public void addData(T data) {
		if (data != null) {
			elements.add(data);
			
			updateSelection();
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public void setData(T data, int position) {
		if (position >= 0 && position < elements.size()) {
			elements.set(position, data);
			
			updateSelection();
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public void removeData(T data) {
		if (data != null && elements.contains(data)) {
			elements.remove(data);
			
			updateSelection();
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public void setData(List<T> data) {
		if (data != null) {
			elements.clear();
			elements.addAll(data);
			
			updateSelection();
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public T getSelectedValue() {
		return elements.get(selected);
	}
	
	public List<T> getValues() {
		return elements;
	}
	
	public int getCurrentSelection() {
		return this.selected;
	}
	
	public void increaseSelection() {
		increaseSelection(true);
	}
	
	public void increaseSelection(boolean castEvent) {
		if (selected + 1 < elements.size()) {
			int tmp = selected + 1;
			while (tmp < elements.size() && !elements.get(tmp).isSelectable()) {
				tmp++;
			}
			
			if (tmp < elements.size()) {
				selected = tmp;
				
				if (castEvent) {
					castEvent(l -> l.selectionChanged());
				}
			}
		}
	}
	
	public void decreaseSelection() {
		decreaseSelection(true);
	}
	
	private void updateSelection() {
		int oldSelected = selected;
		
		if (selected == -1) {
			if (isSelectableAvailable()) {
				do {
					selected++;
				} while (!elements.get(selected).isSelectable());
			}
		} else {
			if (elements.size() <= selected) {
				selected = getLastSelectable();
			} else {
				if (!elements.get(selected).isSelectable()) {
					int tmp = selected;
					while (tmp < elements.size() && !elements.get(tmp).isSelectable()) tmp++;
					if (tmp < elements.size()) {
						selected = tmp;
					} else {
						while (tmp >= 0 && !elements.get(tmp).isSelectable()) tmp--;
						selected = tmp;
					}
				}
			}
		}
		
		if (oldSelected != selected) {
			castEvent(l -> l.selectionChanged());
		}
	}
	
	private int getLastSelectable() {
		for (int i = elements.size() - 1; i >= 0; i--) {
			if (elements.get(i).isSelectable()) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean isSelectableAvailable() {
		for (T it : elements) {
			if (it.isSelectable()) {
				return true;
			}
		}
		return false;
	}
	
	private void decreaseSelection(boolean castEvent) {
		if (selected > 0) {
			int tmp = selected - 1;
			while (tmp >= 0 && !elements.get(tmp).isSelectable()) {
				tmp--;
			}
			
			if (tmp >= 0) {
				selected = tmp;
				
				if (castEvent) {
					castEvent(l -> l.selectionChanged());
				}
			}
		}
	}
	
	private void castEvent(Consumer<ListModelUpdateListener> event) {
		if (listener != null) {
			event.accept(listener);
		}
	}

}
