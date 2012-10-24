package hci.viewer;

import hci.polygon.*;
import hci.util.Point;
import hci.menu.*;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Viewer extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private static final String TITLE = "HCI VIEWER";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TaggedImage image;

	private JFrame container;

	private PolygonManager polman;
	
	private RadialMenu contextMenu;
	private MainMenu mainMenu;
	
	private Queue<Painter> painterQueue = new LinkedList<Painter>();

	private FeedbackHandler feedback;
	private LabelBox labelbox;

	public Viewer(int w, int h, String file) throws IOException, FileNotFoundException
	{

		container = new JFrame(TITLE);

		container.getContentPane().add(this);

		container.setSize(w + container.getInsets().left + container.getInsets().right, h + container.getInsets().top + container.getInsets().bottom + 25);

		polman = new PolygonManager();

		image = new TaggedImage(w, h, file, polman);

		contextMenu = new RadialMenu(w,h);
		mainMenu = new MainMenu(this);

		container.setResizable(false);

		labelbox = new LabelBox(this);
		this.add(labelbox);

		labelbox.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent Ke) {
				if (KeyEvent.VK_ENTER == Ke.getKeyCode() && labelbox.isVisible()) {

					try{

						polman.currentlyEditedPolygon().setNameOfPolygon(labelbox.editLabelBox()); // Null pointer because on initial polygon creation it is not being "edited"
						labelbox.setVisible(false);
						polman.finishPolygon();
						repaint();
					}
					catch(NullPointerException e){}
					try{

						polman.getNewPolygon().setNameOfPolygon(labelbox.editLabelBox());
						labelbox.setVisible(false);
						polman.finishPolygon();
						repaint();
					}
					catch(NullPointerException n){}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// The keylistener should only be on the labelbox in the final design
		container.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}



			@Override
			public void keyReleased(KeyEvent Ke) {

				// This is just for testing the file saving. It wont be tied to a keypress in the final version
				if (KeyEvent.VK_S == Ke.getKeyCode()) {

					final JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(container);

					if (returnVal == JFileChooser.APPROVE_OPTION){
						String fileToSave = fc.getSelectedFile().getAbsolutePath();
						try {
							image.saveImage(fileToSave);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				// For testing saving the polygon map
				if (KeyEvent.VK_P == Ke.getKeyCode()) {

					try {
						final JFileChooser fc = new JFileChooser();
						int returnVal = fc.showSaveDialog(container);

						if (returnVal == JFileChooser.APPROVE_OPTION){
							String fileToSave = fc.getSelectedFile().getAbsolutePath();
							FileOutputStream fout = new FileOutputStream(fileToSave);
							ObjectOutputStream oos = new ObjectOutputStream(fout);
							oos.writeObject(polman.getPolygonsArrayList());
							oos.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// Testing loading the polygon map
				if (KeyEvent.VK_L == Ke.getKeyCode()){

					final JFileChooser fc = new JFileChooser();
					int returnVal = fc.showOpenDialog(container);

					if (returnVal == JFileChooser.APPROVE_OPTION){
						String polygonsToLoad = fc.getSelectedFile().getAbsolutePath();
						polman.loadPolygons(polygonsToLoad);
						repaint();	
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
		});

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {

				// save polygon data
				polman.savePolygons();

				System.exit(0);

			}
		});

		labelbox.setVisible(false);

		feedback = new FeedbackHandler(this);

		addMouseListener(this);
		addMouseMotionListener(this);

		addMouseListener(mainMenu);
		addMouseMotionListener(mainMenu);
		
		container.setVisible(true);

	}

	public void paintComponent(Graphics g)
	{

		// draw image and polygons
		image.draw(g);

		// handle painter queue
		Painter p;
		while ((p = painterQueue.poll()) != null)
		{
			p.draw(g);
		}

		// draw menu
		if (mainMenu.showing())
		{
			mainMenu.draw(g, getWidth(), getHeight());
		}
		else
		{
			contextMenu.draw(g);
		}
		
	}
	
	public void hideMainMenu()
	{
		if (image.isImageLoaded())
		{
			mainMenu.hide();
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
		if (mainMenu.showing())
		{
			return;
		}
		
		switch (me.getButton())
		{
		case MouseEvent.BUTTON1:

			if (!polman.isEditing())
			{
				if (!contextMenu.showing())
				{
					polman.addNewPoint(new Point(me.getX(), me.getY()));
					repaint();
				}

				// unhighlight all polygons
				if (polman.openPolygon())
				{

					if (polman.removeHighlights())
					{
						repaint();
					}

				}
			}

		}

	}

	public void repaint(Painter p)
	{
		painterQueue.add(p);
		repaint();
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
		
		if (mainMenu.showing())
		{
			return;
		}

		switch (me.getButton())
		{
		case MouseEvent.BUTTON1:

			if (polman.isEditing())
			{

				polman.highlightPoint(new Point(me.getX(),me.getY()));
				repaint();

			}

			break;
		case MouseEvent.BUTTON3:
			contextMenu.show(new Point(me.getX(), me.getY()));
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new java.awt.Point(0, 0),
					"null"));
			repaint();
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		
		if (mainMenu.showing())
		{
			return;
		}
		

		switch (me.getButton())
		{
		case MouseEvent.BUTTON1:
			polman.resetPointHighlight();
			break;
		case MouseEvent.BUTTON3:

			boolean showFeedback = false;

			
			if (contextMenu.showing())
			{
				
				int state = contextMenu.close();
					

				switch (state)
				{
				case 0:
					break;
				case 1:

					System.out.println("Entered edit mode");

					if(polman.getHighlighted() != null){
						labelbox.setVisible(true);

						Point point = contextMenu.getPosition();
						Point myPoint = new Point(point.getX(), point.getY());

						labelbox.setPosition(myPoint);
						labelbox.requestFocus();
					}

					repaint();

					if (polman.isEditing() || !polman.editHighlighted())
					{
						showFeedback = true;
						labelbox.setVisible(false);
					}
					break;
				case 3:

					if (labelbox.isVisible()){

						try{
							polman.currentlyEditedPolygon().setNameOfPolygon(labelbox.editLabelBox());
							polman.finishPolygon();
						}
						catch(NullPointerException e){}

						labelbox.setVisible(false);

					}

					else if (polman.openPolygon())
					{

						labelbox.setVisible(true);
						Point point = contextMenu.getPosition();

						Point myPoint = new Point(point.getX(), point.getY());
						labelbox.setPosition(myPoint);
						labelbox.requestFocus();




						if (!polman.finishPolygon())
						{
							showFeedback = true;
						}
					}
					else
					{
						showFeedback = true;
					}
					break;
				case 5:
					mainMenu.show();
					repaint();
					break;
				case 7:

					if (!polman.isEditing())
					{
						if (polman.openPolygon())
						{
							if (polman.removeLastPoint())
							{
								repaint();
							}
							else
							{
								showFeedback = true;
							}
						}
						else
						{
							if (polman.removeHighlighted())
							{
								repaint();
							}
							else
							{
								showFeedback = true;
							}
						}
					}
					else
					{
						showFeedback = true;
					}

					break;
				}

				// attempt to move the mouse back to where it was 
				// this should probably be adapted to put the mouse to a more meaningful position
				try
				{

					Robot r = new Robot();
					r.mouseMove((int)(contextMenu.getPosition().getX() + this.getLocationOnScreen().getX()), (int)(contextMenu.getPosition().getY() + this.getLocationOnScreen().getY()));
					

				}
				catch (Exception e)
				{

				}

				if (showFeedback)
				{
					feedback.reset(contextMenu.getPosition());
				}

				repaint();
			}

			setCursor(Cursor.getDefaultCursor());
		}

	}

	@Override
	public void mouseDragged(MouseEvent me) {

		
		if (mainMenu.showing())
		{
			return;
		}
		
		if (contextMenu.showing())
		{
			if (contextMenu.updateMousePosition(new Point(me.getX(), me.getY())))
			{
				repaint();
			}
		}
		else
		{

			if (polman.updatePoint(new Point(me.getX(), me.getY())))
			{
				repaint();
			}

		}

	}

	@Override
	public void mouseMoved(MouseEvent me) {

		if (mainMenu.showing())
		{
			return;
		}
		
		if (!polman.openPolygon())
		{

			if (polman.updateHighlights(new Point(me.getX(), me.getY())))
			{
				repaint();
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
