package hci.tutorial;

import hci.menu.icon.*;

import java.awt.*;
import hci.util.Point;

public abstract class Stage 
{
	
	protected String icon = "";
	protected Point position = new Point(0,0);
	protected boolean complete = false;
	
	protected IconManager iconman;
	
	public Stage(IconManager iconman)
	{
		this.iconman = iconman;
		
		
	}
	
	public void draw(Graphics g)
	{
		
		if (icon == "")
			return;
		
		g.drawImage(iconman.getIconByName(icon).getImage(), position.getX() - (iconman.getSize().getX()/2), position.getY() - (iconman.getSize().getY()/2), null);
		
	}
	
	public void click(Point p)
	{
		
		int x1 = position.getX() - (iconman.getSize().getX()/2),
				y1 = position.getY() - (iconman.getSize().getY()/2),
				x2 = position.getX() + (iconman.getSize().getX()/2),
				y2 = position.getY() + (iconman.getSize().getY()/2);
		
		complete |= (p.getX() > x1 && p.getX() < x2 && p.getY() > y1 && p.getY() < y2);
		return;
		
	}
	
	public void keyPressed()
	{
		
	}
	
	public boolean openContextMenu()
	{
		return false;
	}
	
	public boolean closeContextMenu(int state)
	{
		return true;
	}
	
	public boolean complete()
	{
		return complete;
	}
	
	public void reset()
	{
		complete = false;
	}
	
}
