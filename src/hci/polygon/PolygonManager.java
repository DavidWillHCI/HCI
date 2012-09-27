package hci.polygon;

import hci.util.Point;

import java.util.*;

public class PolygonManager {
	
	private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	
	public void loadPolygons(int hash)
	{
		
		
		
	}
	
	public void savePolygons()
	{
		
	}
	
	public void addNewPoint(Point p)
	{
		
		// if last polygon is complete, start new one
		Polygon polygon;
		if (polygons.size() > 0)
		{
			polygon = polygons.get(polygons.size()-1);
		}
		else
		{
			polygon = new Polygon();
			polygons.add(polygon);
		}
		
		if (polygon.isComplete())
		{
			polygon = new Polygon();
			polygons.add(polygon);
		}
		
		polygon.addPoint(p);
		
	}
	
	public Polygon[] getPolygons()
	{
		Polygon[] pArr = new Polygon[polygons.size()];
		
		for (int i = 0; i < polygons.size(); i++)
		{
			pArr[i] = polygons.get(i);
		}
		
		return pArr;
	}
	
	public String toString()
	{
		String s = "[PolygonManager";
		
		if (polygons.size() == 0)
		{
			return s + "]";
		}
		
		s = s + "\n" + polygons.get(0).toString();
		
		for (int i = 1; i < polygons.size(); i++)
		{
			s = s + ", " + polygons.get(i).toString();
		}
		
		return s + "]";
	}
	
}
