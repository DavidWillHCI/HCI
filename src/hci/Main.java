package hci;

import hci.viewer.*;

public class Main {
	
	public static void main (String[] args)
	{
		
		try
		{
			
			if (args.length > 0)
			{
				@SuppressWarnings("unused")
				Viewer v = new Viewer(800,600,args[0]);
			}
			else
			{
				@SuppressWarnings("unused")
				Viewer v = new Viewer(800,600,"");
			}
			
		}
		catch (Exception e)
		{
			System.err.println("Exception:");
			e.printStackTrace();
		}
		
	}
	
}
