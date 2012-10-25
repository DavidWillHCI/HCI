package hci.tutorial;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;

import hci.menu.icon.*;

public class Stage5Undo extends Stage {

	private IconManager customIconman;
	
	public Stage5Undo(IconManager iconman) throws FileNotFoundException, IOException {
		
		super(iconman);
		
		customIconman = new IconManager(
				new Icon[] {
					new TutorialUndoIcon(),
				},
				75,
				75);
		
	}
	
	public void draw(Graphics g)
	{
		
		g.drawImage(customIconman.getIconByName("TutorialUndoIcon").getImage(), 400 - (iconman.getSize().getX()/2), 300 - (iconman.getSize().getY()/2), null);
		g.drawImage(iconman.getIconByName("RightClickIcon").getImage(), 370 - (iconman.getSize().getX()/2), 320 - (iconman.getSize().getY()/2), null);
		
	}
	
	public boolean openContextMenu()
	{
		return true;
	}
	
	public boolean closeContextMenu(int state)
	{
		
		if (state == 7)
		{
			complete = true;
			return true;
		}
		
		return false;
		
	}

}
