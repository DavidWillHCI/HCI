package hci.menu;

import hci.menu.icon.*;
import hci.viewer.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu implements MouseListener, MouseMotionListener {

	private static final int MENU_SIZE = 200;
	private static final int LOAD = 1, SAVE = 2, EXIT = 3, TUTORIAL = 4, MENU = 5;
	
	private int selected = 0; // top left = 1, clockwise
	private boolean visible = true;
	private Viewer parent;
	private int width = 0, height = 0;
	private IconManager iconman;
	
	public MainMenu(Viewer parent) throws FileNotFoundException, IOException
	{
		this.parent = parent;
		
		// load icons
		Icon[] icons = {
				new SaveIcon(),
				new LoadIcon(),
				new TutorialIcon(),
				new ExitIcon()
		};
		
		iconman = new IconManager(icons,50,50);
	}
	
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
		
		this.width = width;
		this.height = height;
		
		Graphics2D g2d = (Graphics2D)g;
		Composite c = g2d.getComposite();
		int type = AlphaComposite.SRC_OVER;
		g2d.setComposite(AlphaComposite.getInstance(type, (0.4f)));
		
		if (selected == 0)
		{
			g.setColor(Color.WHITE);
		}
		else
		{
			g.setColor(Color.BLACK);
			
		}
		g.fillRect(0, 0, width, height);
		
		
		
		g2d.setComposite(AlphaComposite.getInstance(type, (0.7f)));
		g.setColor(Color.WHITE);
		
		int x, y, w, h, r;
		
		r = MENU_SIZE/10;
		
		w = MENU_SIZE;
		h = MENU_SIZE;
		
		x = width/2 - w/2;
		y = height/2 - h/2;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		//g2d.setComposite(c);
		
		// draw buttons
		int space = MENU_SIZE/35;
		
		w = (MENU_SIZE - 3*space)/2;
		h = (MENU_SIZE - 3*space)/2;
		
		// top left
		if (selected == LOAD)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.GRAY);
		
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + space;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		// draw load img
		
		g.drawImage(iconman.getIconByName("LoadIcon").getImage(), x + (w/2 - 25), y + (h/2 - 25), null);
		
		
		
		// top right
		if (selected == SAVE)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.GRAY);
		
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + space;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		// draw save img
		
		g.drawImage(iconman.getIconByName("SaveIcon").getImage(), x + (w/2 - 25), y + (h/2 - 25), null);
		
		
		

		// bottom left
		if (selected == TUTORIAL)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.GRAY);
		
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + 2*space + h;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		g.drawImage(iconman.getIconByName("TutorialIcon").getImage(), x + (w/2 - 27), y + (h/2 - 25), null);
		
		
		
		// middle right
		if (selected == EXIT)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.GRAY);
		
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + 2*space + h;
		
		g.fillRoundRect(x, y, w, h, r, r);
		
		g.drawImage(iconman.getIconByName("ExitIcon").getImage(), x + (w/2 - 25), y + (h/2 - 25), null);
		
		
		
		g2d.setComposite(c);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		switch (selected)
		{
		case SAVE:
			break;
		case LOAD:
			break;
		case TUTORIAL:
			break;
		case EXIT:
			System.exit(0);
			break;
		case MENU:
			break;
		default:
			parent.hideMainMenu();
			break;
		}
		
	}
	
	private boolean contains(int l, int t, int w, int h, Point p)
	{
		
		int x = (int) p.getX(),
			y = (int) p.getY();
		
		boolean b1 = x > l,
			b2 = x < (l+w),
			b3 = y > t,
			b4 = y < (t+h);
		
		return ((b1) && (b2) && (b3) && (b4));
		
	}
	
	private boolean updatePosition(Point p)
	{
		
		int x, y, w, h, 
			r = MENU_SIZE/10,
			oldSelected = selected,
			space = MENU_SIZE/35;
		
		boolean highlight = false;
		
		w = MENU_SIZE;
		h = MENU_SIZE;
		
		x = width/2 - w/2;
		y = height/2 - h/2;
		
		if (contains(x,y,w,h,p))
		{
			selected = MENU;
			highlight = true;
		}
		
		w = (MENU_SIZE - 3*space)/2;
		h = (MENU_SIZE - 3*space)/2;
		
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + space;
		
		if (contains(x,y,w,h,p))
		{
			selected = LOAD;
			highlight = true;
		}
		
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + space;
		
		if (contains(x,y,w,h,p))
		{
			selected = SAVE;
			highlight = true;
		}
		
		x = width/2 - MENU_SIZE/2 + space;
		y = height/2 - MENU_SIZE/2 + 2*space + h;
		
		if (contains(x,y,w,h,p))
		{
			selected = TUTORIAL;
			highlight = true;
		}
		
		x = width/2 - MENU_SIZE/2 + 2*space + w;
		y = height/2 - MENU_SIZE/2 + 2*space + h;
		
		if (contains(x,y,w,h,p))
		{
			selected = EXIT;
			highlight = true;
		}
		
		if (!highlight)
		{
			selected = 0;
		}
		
		return (oldSelected != selected);
		
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
		
		// check for change,
		if (updatePosition(arg0.getPoint()))
			// if so redraw
			parent.repaint();
		
	}
	
}
