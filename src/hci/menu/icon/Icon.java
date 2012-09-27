package hci.menu.icon;

import java.awt.*;

public abstract class Icon {
	
	private Image i;
	
	public abstract String getFileName();
	public abstract String getIconName();
	
	public void setImage(Image i)
	{
		this.i = i;
	}
	
	public Image getImage()
	{
		return i;
	}
	
}
