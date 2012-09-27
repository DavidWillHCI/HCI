package hci.menu.icon;

import java.awt.Image;
import java.io.*;
import java.util.*;

import javax.imageio.*;

public class IconManager {
	
	private static final int ICON_WIDTH = 30, ICON_HEIGHT = 30;
	
	private ArrayList<Icon> icons = new ArrayList<Icon>();
	
	public IconManager(Icon[] icons) throws IOException, FileNotFoundException
	{
		
		for (int i = 0; i < icons.length; i++)
		{
			Image originalImage = null, scaledImage;
			try
			{
				originalImage = ImageIO.read(new File(icons[i].getFileName()));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			if (originalImage.getWidth(null) > ICON_WIDTH || originalImage.getHeight(null) > ICON_HEIGHT) {
				int newWidth = originalImage.getWidth(null) > ICON_WIDTH ? ICON_WIDTH : (originalImage.getWidth(null) * ICON_WIDTH)/originalImage.getHeight(null);
				int newHeight = originalImage.getHeight(null) > ICON_HEIGHT ? ICON_HEIGHT : (originalImage.getHeight(null) * ICON_HEIGHT)/originalImage.getWidth(null);
				scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			}
			else
			{
				scaledImage = originalImage;
			}
			
			icons[i].setImage(scaledImage);
			this.icons.add(icons[i]);
			
		}
		
	}
	
	public Icon getIconByName(String name)
	{
		for (int i = 0; i < icons.size(); i++)
		{
			if (icons.get(i).getIconName().equals(name))
			{
				return icons.get(i);
			}
		}
		return null;
	}
	
}
