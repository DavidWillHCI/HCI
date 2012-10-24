package hci.tutorial;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import hci.menu.icon.*;
import hci.viewer.*;
import hci.util.Point;

public class Tutorial implements Painter
{
	
	private Viewer viewer;
	private IconManager iconman;
	private int stage = 0;
	private boolean enabled = false;
	private Stage[] stages = 
		{
			new StageBegin(iconman),
		};
	
	public Tutorial(Viewer viewer) throws FileNotFoundException, IOException
	{
		
		this.viewer = viewer;
		
		// load icons
		Icon[] icons = {
				new ExitIcon()
		};
		
		iconman = new IconManager(icons,40,40);
		
	}

	private void enable(boolean enable)
	{
		this.enabled = enable;
	}
	
	public void reset()
	{
		stage = 0;
		enable(true);
		viewer.repaint(this);
	}
	
	public void stop()
	{
		enable(false);
		viewer.repaint(this);
	}
	
	private Stage getCurrentStage()
	{
		
		if (stage >= stages.length)
		{
			enable(false);
			return null;
		}
		
		return stages[stage];
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		Stage s = getCurrentStage();
		
		if (s == null)
		{
			return;
		}
		
		s.draw(g);
		
		// show close tutorial button
		Point size = viewer.getDimensions();
		g.drawImage(iconman.getIconByName("ExitIcon").getImage(), size.getX() - 45, 3, null);
		
	}

	@Override
	public boolean requeue() {
		return enabled;
	}

	public boolean handleClick(MouseEvent me) {
		
		Stage s = getCurrentStage();
		
		if (!enabled)
		{
			return true;
		}
		
		System.out.println("(" + me.getX() + "," + me.getY() + ")");
		System.out.println(" " + (viewer.getDimensions().getX() - 45) + "," + 43 + " ");
		if (me.getX() > viewer.getDimensions().getX() - 45 && me.getY() < 43)
		{
			stop();
			return false;
		}
		
		s.click(me.getPoint());
		if (s.complete())
		{
			stage++;
			if (stage >= stages.length)
			{
				enable(false);
			}
			return true;
		}
		
		return false;
		
	}
	
}
