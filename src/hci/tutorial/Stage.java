package hci.tutorial;

import hci.menu.icon.*;

import java.awt.*;

public abstract class Stage 
{
	
	protected IconManager iconman;
	
	public Stage(IconManager iconman)
	{
		this.iconman = iconman;
	}
	
	public abstract void draw(Graphics g);
	public abstract void click(Point p);
	public abstract boolean complete();
	
}
