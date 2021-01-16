package pi.password.gui.components.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import pi.password.gui.AbstractModel;

public class ListModel<T extends ListBodyDisplayable> extends AbstractModel {
	
	private List<T> elements = new ArrayList<>();
	
	private int selected = 0;
	
	private ListModelUpdateListener listener;
	
	public ListModel() {
		this.selected = 0;
	}
	
	public void setListener(ListModelUpdateListener listener) {
		this.listener = listener;
	}
	
	public void addData(T data) {
		if (data != null) {
			elements.add(data);
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public void setData(T data, int position) {
		if (position >= 0 && position < elements.size()) {
			elements.set(position, data);
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public void removeData(T data) {
		if (data != null && elements.contains(data)) {
			elements.remove(data);
			
			castEvent(l -> l.dataChanged());
		}
	}
	
	public void setData(List<T> data) {
		if (data != null) {
			elements.clear();
			elements.addAll(data);
			
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
		if (selected + 1 < elements.size()) {
			selected++;
			
			castEvent(l -> l.selectionChanged());
		}
	}
	
	public void decreaseSelection() {
		if (selected > 0) {
			selected--;
			
			castEvent(l -> l.selectionChanged());
		}
	}
	
	private void castEvent(Consumer<ListModelUpdateListener> event) {
		if (listener != null) {
			event.accept(listener);
		}
	}

}
