package hci.polygon;

import hci.util.Point;

import java.util.*;

public class PolygonManager {
	
	private ArrayList<TaggedPolygon> polygons = new ArrayList<TaggedPolygon>();
	
	public void loadPolygons(int hash)
	{
		
		
		
	}
	
	public void savePolygons()
	{
		
	}
	
	public void finishPolygon()
	{
		
		if (polygons.size() == 0)
			return;
		
		TaggedPolygon p = polygons.get(polygons.size() - 1);
		
		if (!p.isComplete())
		{
			p.complete();
		}
		
	}
	
	public void resetHighlights()
	{
		for (int i = 0; i < polygons.size(); i++)
		{
			polygons.get(i).resetHighlight();
		}
	}
	
	public boolean updateHighlights(Point p)
	{
		
		boolean highlightedPolygon = false,
				repaint = false;
		
		for (int i = polygons.size()-1; i >= 0; i--)
		{
			
			if (!highlightedPolygon)
			{
				if (polygons.get(i).updateHighlight(p))
				{
					repaint = true;
				}
				
				if (polygons.get(i).isHighlighted())
				{
					highlightedPolygon = true;
				}
			}
			else
			{
				if (polygons.get(i).resetHighlight())
				{
					repaint = true;
				}
			}
			
		}
		
		return repaint;
		
	}
	
	public boolean openPolygon()
	{
		if (polygons.size() == 0)
		{
			return false;
		}
		
		return !polygons.get(polygons.size() - 1).isComplete();
	}
	
	public void addNewPoint(Point p)
	{
		
		// if last polygon is complete, start new one
		TaggedPolygon polygon;
		if (polygons.size() > 0)
		{
			polygon = polygons.get(polygons.size()-1);
		}
		else
		{
			polygon = new TaggedPolygon();
			polygons.add(polygon);
		}
		
		if (polygon.isComplete())
		{
			polygon = new TaggedPolygon();
			polygons.add(polygon);
		}
		
		polygon.addPoint(p);
		
	}
	
	public TaggedPolygon[] getPolygons()
	{
		TaggedPolygon[] pArr = new TaggedPolygon[polygons.size()];
		
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
