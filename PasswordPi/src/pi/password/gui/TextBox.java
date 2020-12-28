package pi.password.gui;

import java.awt.Graphics2D;

public class TextBox {
	
	private String content = "";
	private boolean selected = false;
	private int position = 0;
	
	public TextBox() {
		
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void paint(Graphics2D g) {
		
	}

}
