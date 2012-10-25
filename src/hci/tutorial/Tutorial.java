package hci.tutorial;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import hci.menu.*;
import hci.menu.icon.*;
import hci.viewer.*;
import hci.util.Point;

public class Tutorial implements Painter, MouseListener
{
	
	private Viewer viewer;
	private IconManager iconman;
	private int stage = 0;
	private boolean enabled = false;
	private Stage[] stages;
	private RadialMenu menu;
	
	public Tutorial(Viewer viewer, RadialMenu menu) throws FileNotFoundException, IOException
	{
		
		this.menu = menu;
		
		this.viewer = viewer;
		
		// load icons
		Icon[] icons = {
				new ExitIcon(),
				new LeftClickIcon(),
				new RightClickIcon(),
				new TutorialKeyboardIcon(),
		};
		
		iconman = new IconManager(icons,40,40);
		
		stages = new Stage[]
			{
				new Stage1(iconman),
				new Stage2(iconman),
				new Stage3(iconman),
				new Stage4(iconman),
				new Stage5Undo(iconman),
				new Stage6(iconman),
				new Stage7Complete(iconman),
				new Stage8Keyboard(iconman),
				new Stage9Final(iconman),
			};
		
	}

	private void enable(boolean enable)
	{
		this.enabled = enable;
	}
	
	public void reset()
	{
		
		for (Stage s : stages)
		{
			s.reset();
		}
		
		stage = 0;
		enable(true);
		viewer.repaint(this);
		
		viewer.removeMouseListener(viewer);
		viewer.addMouseListener(this);
	}
	
	public void stop()
	{
		enable(false);
		viewer.repaint(this);
		
		viewer.removeMouseListener(this);
		viewer.addMouseListener(viewer);
	}
	
	private Stage getCurrentStage()
	{
		
		do
		{
		
			if (stage >= stages.length)
			{
				stop();
				return null;
			}
			
			if (stages[stage].complete())
				stage++;
			else
				break;
		
		} while(true);
		
		return stages[stage];
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if (!enabled)
			return;
		
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
	
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
		//System.out.println("(" + me.getX() + "," + me.getY() + ")");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	public void keyPressed()
	{
		
		Stage s = getCurrentStage();
		if (s != null)
		{
			s.keyPressed();
			viewer.repaint(this);
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		
		Stage s = getCurrentStage();
		
		switch(me.getButton())
		{
		case MouseEvent.BUTTON1:
			
			if (!enabled)
			{
				viewer.mouseClicked(me);
				return;
			}
			
			if (me.getX() > viewer.getDimensions().getX() - 45 && me.getY() < 43)
			{
				stop();
				return;
			}
			
			s.click(new Point(me.getX(),me.getY()));
			if (s.complete())
			{
				stage++;
				if (stage >= stages.length)
				{
					stop();
				}
				viewer.mousePressed(me);
				return;
			}
			
			return;
		case MouseEvent.BUTTON3:
			if (s != null && s.openContextMenu())
				viewer.mousePressed(me);
			break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
		Stage s = getCurrentStage();
		
		if (s != null)
		{
		
			if (menu.showing())
			{
				if (!s.closeContextMenu(menu.getState()))
				{
					menu.setState(-1);
				}
			}
		
		}
		
		viewer.mouseReleased(me);
		
	}
	
}
