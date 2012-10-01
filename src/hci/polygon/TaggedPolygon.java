package hci.polygon;

import hci.util.Point;

import java.awt.*;

public class TaggedPolygon extends Polygon {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean editing = false;
	private boolean complete = false;
	private boolean highlighted = false;
	private String nameOfPolygon;
	
	private int selectedPointIdx = -1;
	
	
	public String getNameOfPolygon() {
		return nameOfPolygon;
	}

	public void setNameOfPolygon(String nameOfPolygon) {
		this.nameOfPolygon = nameOfPolygon;
	}

	public int size()
	{
		return npoints;
	}
	
	public boolean isHighlighted()
	{
		return highlighted;
	}
	
	private double euclidDistance(Point p1, Point p2)
	{
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(),2) + Math.pow(p1.getY() - p2.getY(),2));
	}
	
	public void selectPoint(Point p)
	{
		
		// if p is close to point, select this point to move
		for (int i = 0; i < npoints; i++)
		{
			if (euclidDistance(p,new Point(xpoints[i],ypoints[i])) < 10)
			{
				selectedPointIdx = i;
				return;
			}
		}
		
		selectedPointIdx = -1;
		
	}
	
	public void clearSelectedPoint()
	{
		selectedPointIdx = -1;
	}
	
	public void updateSelectedPoint(Point p)
	{
		if (selectedPointIdx != -1)
		{
			xpoints[selectedPointIdx] = p.getX();
			ypoints[selectedPointIdx] = p.getY();
			invalidate();
		}
	}
	
	public int getSelectedPointIdx()
	{
		return selectedPointIdx;
	}
	
	public void addPoint(Point p)
	{
		
		addPoint(p.getX(), p.getY());
		
	}
	
	public void addPoint(int x, int y)
	{
		
		if (size() > 0)
		{
			if (xpoints[size()-1] == x && ypoints[size()-1] == y)
			{
				return;
			}
		}
		
		super.addPoint(x, y);
		
	}
	
	public boolean removeLastPoint()
	{
		
		if (size() > 0)
		{
			npoints--;
			
			if (editing)
			{
				complete = false;
			}
			
			return true;
		}
		return false;
		
	}
	
	public void complete()
	{
		complete = true;
		editing = false;
	}
	
	public Point[] getPoints()
	{
		Point[] pArr = new Point[size()];
		for (int i = 0; i < size(); i++)
		{
			pArr[i] = new Point(xpoints[i],ypoints[i]);
		}
		return pArr;
	}
	
	public boolean isComplete()
	{
		return complete;
	}
	
	public boolean movePoint(Point p1, Point p2)
	{
		if (!editing)
		{
			return false;
		}
		
		for (int i = 0; i < size(); i++)
		{
			if (xpoints[i] == p1.getX() && ypoints[i] == p1.getY())
			{
				xpoints[i] = p2.getX();
				ypoints[i] = p2.getY();
				return true;
			}
		}
		return false;
	}
	
	public boolean updateHighlight(Point p)
	{
		
		boolean oldHighlighted = highlighted;
		
		highlighted = contains(p.getX(),p.getY());
		
		return oldHighlighted != highlighted;
		
	}
	
	public boolean resetHighlight()
	{
		boolean oldHighlighted = highlighted;
		highlighted = false;
		return oldHighlighted != highlighted;
	}
	
	public void edit()
	{
		editing = true;
	}
	
	public boolean isEditing()
	{
		return editing;
	}
	
}
