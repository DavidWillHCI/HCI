package hci.menu.filedialog;

import java.io.File;

import javax.swing.filechooser.*;

public class DialogFileFilter extends FileFilter {
	
	private final String[] allowedExts = 
		{
			"jpg",
			"jpeg",
			"png",
			"gif",
		};
	
	@Override
	public boolean accept(File f) {
		
		if (f.isDirectory())
			return true;
		
		String name = f.getName();
		
		for (int i = 0; i < allowedExts.length; i++)
		{
			if (name.endsWith(allowedExts[i]))
				return true;
		}
		
		return false;
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Image Files";
	}
	
}