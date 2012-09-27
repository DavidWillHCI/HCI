package hci;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;



import javax.swing.JFrame;


import hci.utils.*;

/**
 * Handles image editing panel
 * @author Michal
 *
 */
public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {
	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * image to be tagged
	 */
	BufferedImage image = null;

	/**
	 * list of current polygon's vertices 
	 */
	ArrayList<Point> currentPolygon = null;

	/**
	 * list of polygons
	 */
	ArrayList<ArrayList<Point>> polygonsList = null;

	/**
	 * My own actual arraylist of polygons
	 */
	ArrayList<Polygon> myPolygonList = null;

	/**
	 * default constructor, sets up the window properties
	 */
	public ImagePanel() {


		currentPolygon = new ArrayList<Point>();
		polygonsList = new ArrayList<ArrayList<Point>>();
		myPolygonList = new ArrayList<Polygon>();

		this.setVisible(true);

		Dimension panelSize = new Dimension(800, 600);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * extended constructor - loads image to be labelled
	 * @param imageName - path to image
	 * @throws Exception if error loading the image
	 */
	public ImagePanel(String imageName) throws Exception{
		this();
		image = ImageIO.read(new File(imageName));
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800 : (image.getWidth() * 600)/image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600 : (image.getHeight() * 800)/image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight );
			Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}
	}

	/**
	 * Displays the image
	 */
	public void ShowImage() {
		Graphics g = this.getGraphics();

		if (image != null) {
			g.drawImage(
					image, 0, 0, null);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		//display iamge
		ShowImage();

		//display all the completed polygons
		for(ArrayList<Point> polygon : polygonsList) {
			drawPolygon(polygon);
			finishPolygon(polygon);
		}

		//display current polygon
		drawPolygon(currentPolygon);
	}

	/**
	 * displays a polygon without last stroke
	 * @param polygon to be displayed
	 */
	public void drawPolygon(ArrayList<Point> polygon) {
		Graphics2D g = (Graphics2D)this.getGraphics();
		g.setColor(Color.GREEN);
		for(int i = 0; i < polygon.size(); i++) {
			Point currentVertex = polygon.get(i);
			if (i != 0) {
				Point prevVertex = polygon.get(i - 1);
				g.drawLine(prevVertex.getX(), prevVertex.getY(), currentVertex.getX(), currentVertex.getY());
			}
			g.fillOval(currentVertex.getX() - 5, currentVertex.getY() - 5, 10, 10);
		}
	}

	/**
	 * displays last stroke of the polygon (arch between the last and first vertices)
	 * @param polygon to be finished
	 */
	public void finishPolygon(ArrayList<Point> polygon) {
		//if there are less than 3 vertices than nothing to be completed
		if (polygon.size() >= 3) {
			Point firstVertex = polygon.get(0);
			Point lastVertex = polygon.get(polygon.size() - 1);

			Graphics2D g = (Graphics2D)this.getGraphics();
			g.setColor(Color.GREEN);
			g.drawLine(firstVertex.getX(), firstVertex.getY(), lastVertex.getX(), lastVertex.getY());
		}
	}

	/**
	 * moves current polygon to the list of polygons and makes pace for a new one
	 */
	public void addNewPolygon() {
		//finish the current polygon if any
		if (currentPolygon != null ) {
			finishPolygon(currentPolygon);
			polygonsList.add(currentPolygon);

			// Add the polygon as an actual polygon object to my own data structure
			// currentPolygon is just an ArrayList of point objects
			int[] listOfX = new int[100];
			int[] listOfY = new int[100];
			int numberOfPoints = 0;
			for (int i = 0; i < currentPolygon.size(); i++){

				numberOfPoints += 1;
				listOfX[i] = currentPolygon.get(i).getX();
				listOfY[i] = currentPolygon.get(i).getY();


			}
			Polygon newPolygon = new Polygon(listOfX, listOfY, numberOfPoints);
			myPolygonList.add(newPolygon);
		}

		currentPolygon = new ArrayList<Point>();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		//check if the cursos withing image area
		if (x > image.getWidth() || y > image.getHeight()) {
			//if not do nothing
			return;
		}

		Graphics2D g = (Graphics2D)this.getGraphics();

		//if the left button than we will add a vertex to poly
		if (e.getButton() == MouseEvent.BUTTON1) {
			g.setColor(Color.GREEN);
			if (currentPolygon.size() != 0) {
				Point lastVertex = currentPolygon.get(currentPolygon.size() - 1);
				g.drawLine(lastVertex.getX(), lastVertex.getY(), x, y);
			}
			g.fillOval(x-5,y-5,10,10);

			currentPolygon.add(new Point(x,y));
			System.out.println(x + " " + y);
		} 
	}

	//add a mouselistener that outputs the coordinates of the pointer when it is inside of a region
	public void mouseMoved(MouseEvent e) {

		Graphics g = this.getGraphics();

		int currentX = e.getX();
		int currentY = e.getY();

		for (int i = 0; i < myPolygonList.size(); i++){

			if (myPolygonList.get(i).contains(currentX, currentY)){

				g.setColor(Color.BLACK);
				// You colour it in
				g.fillPolygon(myPolygonList.get(i));

			}


		}

		for (int i = 0; i < myPolygonList.size(); i++){

			/*	if (!myPolygonList.get(i).contains(currentX, currentY)){

				g.clearRect(0, 0, getWidth(), getHeight());
				paint(getGraphics());

			}*/	


		}



	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		Graphics g = this.getGraphics();

		g.clearRect(0, 0, getWidth(), getHeight());
		paint(getGraphics());




	}




	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
