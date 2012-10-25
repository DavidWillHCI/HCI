package hci.tutorial;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

import hci.menu.icon.*;

public class Stage7Complete extends Stage {

	private IconManager customIconman;
	
	public Stage7Complete(IconManager iconman) throws FileNotFoundException, IOException {
		
		super(iconman);
		
		customIconman = new IconManager(
				new Icon[] {
					new TutorialCompleteIcon(),
				},
				75,
				75);
		
	}
	
	public void draw(Graphics g)
	{
		
		g.drawImage(customIconman.getIconByName("TutorialCompleteIcon").getImage(), 400 - (iconman.getSize().getX()/2), 300 - (iconman.getSize().getY()/2), null);
		g.drawImage(iconman.getIconByName("RightClickIcon").getImage(), 370 - (iconman.getSize().getX()/2), 320 - (iconman.getSize().getY()/2), null);
		
	}
	
	public boolean openContextMenu()
	{
		return true;
	}
	
	public boolean closeContextMenu(int state)
	{
		
		if (state == 3)
		{
			complete = true;
			return true;
		}
		
		return false;
		
	}

}
