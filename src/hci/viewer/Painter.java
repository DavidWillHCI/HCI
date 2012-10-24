package hci.viewer;

import java.awt.Graphics;

public interface Painter {
	
	public void draw(Graphics g);
	public boolean requeue();
	
}
