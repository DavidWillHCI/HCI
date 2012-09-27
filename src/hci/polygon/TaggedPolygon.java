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
	
	public int size()
	{
		return npoints;
	}
	
	public boolean isHighlighted()
	{
		return highlighted;
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
