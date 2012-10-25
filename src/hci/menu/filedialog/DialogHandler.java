package hci.menu.filedialog;

import javax.swing.*;

public class DialogHandler {
	
	private String filename = null;
	private JFileChooser fc = new JFileChooser();
	private JComponent parent;
	
	public DialogHandler(JComponent parent)
	{
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new DialogFileFilter());
		
		this.parent = parent;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public boolean showLoadDialog()
	{
		
		int result = fc.showOpenDialog(parent);
		
		if (result == JFileChooser.APPROVE_OPTION)
		{
			filename = fc.getSelectedFile().getAbsolutePath();
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public boolean showSaveDialog()
	{
		
		int result = fc.showSaveDialog(parent);
		
		if (result == JFileChooser.APPROVE_OPTION)
		{
			filename = fc.getSelectedFile().getAbsolutePath();
			
			if (!filename.contains("."))
			{
				filename += ".jpg";
			}
			
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
}
