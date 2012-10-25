package hci.tutorial;

import java.awt.Graphics;
import java.io.*;

import hci.menu.icon.*;

import hci.util.Point;

public class Stage8Keyboard extends Stage {

	private IconManager customIconman;
	
	public Stage8Keyboard(IconManager iconman) throws FileNotFoundException, IOException {
		
		super(iconman);
		
		customIconman = new IconManager(
				new Icon[] {
					new TutorialKeyboardIcon(),
				},
				400,
				267);
		
		position = new Point(380,280);
		
	}
	
	public void draw(Graphics g)
	{
		
		g.drawImage(customIconman.getIconByName("TutorialKeyboardIcon").getImage(), 400 - (customIconman.getSize().getX()/2), 300 - (customIconman.getSize().getY()/2), null);
		
	}

}
