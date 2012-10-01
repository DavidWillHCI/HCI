package hci.menu;

import java.awt.*;
import java.awt.event.*;

public class MainMenu implements MouseListener, MouseMotionListener {

	private static final int MENU_SIZE = 400;
	
	private boolean visible = true;
	
	public void show()
	{
		visible = true;
	}
	
	public void hide()
	{
		visible = false;
	}
	
	public boolean showing()
	{
		return visible;
	}
	
	public void draw(Graphics g, int width, int height)
	{
		
		Graphics2D g2d = (Graphics2D)g;
		Composite c = g2d.getComposite();
		int type = AlphaComposite.SRC_OVER;
		g2d.setComposite(AlphaComposite.getInstance(type, (0.4f)));
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g2d.setComposite(AlphaComposite.getInstance(type, (0.8f)));
		g.setColor(Color.WHITE);
		
		int x, y, w, h, r;
		
		r = MENU_SIZE/10;
		
		w = MENU_SIZE;
		h = MENU_SIZE;
		
		x = width/2 - w/2;
		y = height/2 - h/2;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		g2d.setComposite(c);
		
		// draw buttons
		int space = MENU_SIZE/35;
		
		w = (MENU_SIZE - 3*space)/2;
		h = (MENU_SIZE - 4*space)/3;
		
		// top left
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + space;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		// top right
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + space;
		
		g.fillRoundRect(x, y, w, h, r, r);

		// middle left
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + 2*space + h;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		// middle right
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + 2*space + h;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		// middle left
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + 3*space + 2*h;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		// middle right
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + 3*space + 2*h;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		Rectangle rrr;
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
