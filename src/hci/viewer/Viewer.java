package hci.viewer;

import hci.polygon.*;
import hci.util.Point;
import hci.menu.*;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Viewer extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final String TITLE = "HCI VIEWER";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TaggedImage image;
	
	private JFrame container;
	
	private PolygonManager polman;
	
	private RadialMenu menu;
	
	public Viewer(int w, int h, String file) throws IOException, FileNotFoundException
	{
		
		container = new JFrame(TITLE);
		
		container.getContentPane().add(this);
		
		container.setSize(w + container.getInsets().left + container.getInsets().right, h + container.getInsets().top + container.getInsets().bottom + 25);
		
		polman = new PolygonManager();
		
		image = new TaggedImage(w, h, file, polman);
		
		menu = new RadialMenu();
		
		container.setResizable(false);
		
		container.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		
		  		// save polygon data
		  		polman.savePolygons();
		  		
		    	System.exit(0);
		    	
		  	}
		});
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		container.setVisible(true);
		
	}
	
	public void paintComponent(Graphics g)
	{
		
		// draw image and polygons
		image.draw(g);
		
		// draw menu
		menu.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
		switch (me.getButton())
		{
		case MouseEvent.BUTTON1:
			
			if (!menu.showing())
			{
				polman.addNewPoint(new Point(me.getX(), me.getY()));
				repaint();
			}
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		
		switch (me.getButton())
		{
		case MouseEvent.BUTTON3:
			menu.show(new Point(me.getX(), me.getY()));
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
		            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new java.awt.Point(0, 0),
		            "null"));
			repaint();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
		switch (me.getButton())
		{
		case MouseEvent.BUTTON3:
			menu.close();
			
			// attempt to move the mouse back to where it was 
			// this should probably be adapted to put the mouse to a more meaningful position
			try
			{
				
				Robot r = new Robot();
				r.mouseMove((int)(menu.getPosition().getX() + this.getLocationOnScreen().getX()), (int)(menu.getPosition().getY() + this.getLocationOnScreen().getY()));
				
			}
			catch (Exception e)
			{
				
			}
			
			setCursor(Cursor.getDefaultCursor());
			repaint();
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		
		if (menu.showing())
		{
			if (menu.updateMousePosition(new Point(me.getX(), me.getY())))
			{
				repaint();
			}
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		
		
		
	}
	
}
