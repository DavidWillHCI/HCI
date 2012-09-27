package hci;

import hci.viewer.*;

public class Main {
	
	public static void main (String[] args)
	{
		
		try
		{
		
			@SuppressWarnings("unused")
			Viewer v = new Viewer(800,600,"/home/will/uni/hci/images/U1003_0000.jpg");
			
		}
		catch (Exception e)
		{
			System.err.println("Exception:");
			e.printStackTrace();
		}
		
	}
	
}
