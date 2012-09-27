package hci.polygon;

import hci.util.Point;

import java.awt.*;

public class TaggedPolygon extends Polygon {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	public void removeLastPoint()
	{
		
		if (size() > 0)
		{
			npoints--;
		}
		
	}
	
	public void complete()
	{
		complete = true;
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
	
	/*
	public String toString()
	{
		String s = "[Polygon";
		
		if (size() == 0)
		{
			return s + "]";
		}
		
		s = s + "\n" + points.get(0).toString();
		
		for (int i = 1; i < points.size(); i++)
		{
			s = s + ", " + points.get(i).toString();
		}
		
		return s + "]";
	}
	*/
	
}
