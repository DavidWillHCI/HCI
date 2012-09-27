package hci.polygon;

import hci.util.Point;

import java.util.*;

public class Polygon {
	
	private ArrayList<Point> points = new ArrayList<Point>();
	private boolean complete = false;
	
	public void addPoint(Point p)
	{
		points.add(p);
		System.out.println("New point: " + p.toString());
	}
	
	public void removeLastPoint()
	{
		points.remove(points.size()-1);
	}
	
	public void complete()
	{
		complete = true;
	}
	
	public Point[] getPoints()
	{
		Point[] pArr = new Point[points.size()];
		for (int i = 0; i < points.size(); i++)
		{
			pArr[i] = points.get(i);
		}
		return pArr;
	}
	
	public boolean isComplete()
	{
		return complete;
	}
	
	public boolean movePoint(Point p1, Point p2)
	{
		Point q;
		for (int i = 0; i < points.size(); i++)
		{
			q = points.get(i);
			if (q.getX() == p1.getX() && q.getY() == p1.getY())
			{
				points.get(i).setPoint(p2.getX(), p2.getY());
				return true;
			}
		}
		return false;
	}
	
	public String toString()
	{
		String s = "[Polygon";
		
		if (points.size() == 0)
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
	
}
