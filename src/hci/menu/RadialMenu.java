package hci.menu;

import java.awt.*;
import hci.util.Point;

public class RadialMenu {
	
	private static final int SIZE = 30, BORDER = 2;
	
	/**
	 *  State of menu
	 *  0 = middle
	 *  1...n = segment from 12 o'clock clockwise
	 */
	private int state = 0;
	private boolean visible = false;
	private Point position;
	
	public void show(Point p)
	{
		
		state = 0;
		visible = true;
		position = p;
		
	}
	
	public void hide()
	{
		visible = false;
	}
	
	public void draw(Graphics g)
	{
		
		if (!visible)
			return;
		
		if (state == 1)
		{	
			g.setColor(Color.GRAY);
			g.fillOval(position.getX() - (int)(SIZE/2), position.getY() - (int)(SIZE/2), SIZE, SIZE);
			
			g.setColor(Color.WHITE);
			g.fillOval(position.getX() + SIZE + 10 - (int)(SIZE/2) - BORDER, position.getY() - (int)(SIZE/2) - BORDER, SIZE + 2*BORDER, SIZE + 2*BORDER);
			
			g.setColor(Color.GRAY);
			g.fillOval(position.getX() + SIZE + 10 - (int)(SIZE/2), position.getY() - (int)(SIZE/2), SIZE, SIZE);
		}
		else
		{
			g.setColor(Color.WHITE);
			g.fillOval(position.getX() - (int)(SIZE/2) - BORDER, position.getY() - (int)(SIZE/2) - BORDER, SIZE + 2*BORDER, SIZE + 2*BORDER);
			
			g.setColor(Color.GRAY);
			g.fillOval(position.getX() - (int)(SIZE/2), position.getY() - (int)(SIZE/2), SIZE, SIZE);
			
			g.setColor(Color.GRAY);
			g.fillOval(position.getX() + SIZE + 10 - (int)(SIZE/2), position.getY() - (int)(SIZE/2), SIZE, SIZE);
		}
	}
	
	public boolean showing()
	{
		return visible;
	}
	
	public int close()
	{
		
		// accept current menu item
		System.out.println("Menu closed with state = " + state);
		hide();
		
		return state;
		
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public boolean updateMousePosition(Point p) 
	{
		
		int oldState = state;
		
		// handle mouse movement to change state
		if (Math.sqrt(Math.pow(p.getX() - position.getX(),2) + Math.pow(p.getY() - position.getY(),2)) > SIZE)
		{
			state = 1;
		}
		else
		{
			state = 0;
		}
		
		System.out.println("euclid distance = " + (Math.sqrt(Math.pow(p.getX() - position.getX(),2) + Math.pow(p.getY() - position.getY(),2))));
		
		if (oldState == state)
		{
			return false;
		}
		return true;
		
	}
	
}
