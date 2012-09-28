package hci.viewer;

import hci.util.Point;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class FeedbackHandler implements ActionListener, Painter {
	
	private static final int FRAME_DRAW_TIME = 50;
	private static final String ICON_FILE = "icons/Delete.png";
	
	private Viewer parent;
	private Timer timer;
	private double alphaMod = 1;
	private Point position;
	private Image image;
	
	public FeedbackHandler(Viewer parent) throws IOException, FileNotFoundException
	{
		image = ImageIO.read(new File(ICON_FILE));
		this.parent = parent;
		this.timer = new Timer(FRAME_DRAW_TIME,this);
		timer.stop();
	}
	
	public boolean isRunning()
	{
		return timer.isRunning();
	}
	
	public void reset(Point p)
	{
		alphaMod = 1;
		position = p;
		if (!isRunning())
		{
			timer.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// we always know the even incoming
		// to be the timer event to just 
		// handle that
		
		parent.repaint(this);
		
		alphaMod -= 0.1;
		if (alphaMod < 0)
		{
			timer.stop();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		
		if (alphaMod < 0)
			return;
		
		Graphics2D g2d = (Graphics2D)g;
		Composite c = g2d.getComposite();
		int type = AlphaComposite.SRC_OVER;
		g2d.setComposite(AlphaComposite.getInstance(type, ((float)alphaMod)));
		g.drawImage(image, position.getX() - image.getWidth(null) / 2, position.getY() - image.getHeight(null) / 2, null);
		g2d.setComposite(c);
		
	}
	
	
	
}
