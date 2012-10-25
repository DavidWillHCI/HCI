package hci.menu.icon;

import java.awt.Image;
import java.io.*;
import java.util.*;
import hci.util.Point;

import javax.imageio.*;

public class IconManager {
	
	private ArrayList<Icon> icons = new ArrayList<Icon>();
	private Point position;
	
	public IconManager(Icon[] icons, int width, int height) throws IOException, FileNotFoundException
	{
		
		position = new Point(width, height);
		
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
			
			if (originalImage.getWidth(null) > width || originalImage.getHeight(null) > height) {
				int newWidth = originalImage.getWidth(null) > width ? width : (originalImage.getWidth(null) * width)/originalImage.getHeight(null);
				int newHeight = originalImage.getHeight(null) > height ? height : (originalImage.getHeight(null) * height)/originalImage.getWidth(null);
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
	
	public Point getSize()
	{
		return position;
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
