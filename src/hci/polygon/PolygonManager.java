package hci.polygon;

import hci.util.Point;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class PolygonManager {
	
	private ArrayList<TaggedPolygon> polygons = new ArrayList<TaggedPolygon>();
	
	
	public ArrayList<TaggedPolygon> getPolygonsArrayList(){
		
		return polygons;
		
	}
	public void loadPolygons(String file)
	{
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);

			// The left hand side of this assignment should instead create the polygon i think
			ArrayList<TaggedPolygon> savedPolygons = new ArrayList<TaggedPolygon>();
			savedPolygons = (ArrayList<TaggedPolygon>) ois.readObject();
			for (int i = 0; i < savedPolygons.size(); i++){
			polygons.add(savedPolygons.get(i));
			}
			System.out.println("Trying to unserialize");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		
		
	}
	
	public void savePolygons()
	{
		
	}
	
	public TaggedPolygon getHighlighted(){
		
		for (int i = 0; i < polygons.size(); i++) {
			
			if (polygons.get(i).isHighlighted()){
				
				return polygons.get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	public void highlightPoint(Point p)
	{
		
		for (int i = 0; i < polygons.size(); i++)
		{
			if (polygons.get(i).isEditing())
			{
				polygons.get(i).selectPoint(p);
			}
		}
		
	}
	
	public void resetPointHighlight()
	{
		for (int i = 0; i < polygons.size(); i++)
		{
			polygons.get(i).clearSelectedPoint();
		}
	}
	
	public boolean updatePoint(Point p)
	{
		
		// if highlighted point, update it
		for (int i = 0; i < polygons.size(); i++)
		{
			if (polygons.get(i).getSelectedPointIdx() != -1)
			{
				polygons.get(i).updateSelectedPoint(p);
				return true;
			}
		}
		
		return false;
		
	}
	
	public TaggedPolygon getNewPolygon(){
		
		TaggedPolygon p = polygons.get(polygons.size() - 1);
		return p;
		
	}
	
	public boolean finishPolygon()
	{
		
		if (polygons.size() == 0)
			return false;
		
		TaggedPolygon p = polygons.get(polygons.size() - 1);
		
		if (p.size() < 3)
		{
			polygons.remove(polygons.size() - 1);
			return false;
		}
		
		// finish all editing
		for (int i = 0; i < polygons.size(); i++)
		{
			polygons.get(i).complete();
		}
		
		return true;
		
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
	
	public boolean removeHighlights()
	{
		
		boolean repaint = false;

		for (int i = polygons.size()-1; i >= 0; i--)
		{
			
			if (polygons.get(i).resetHighlight())
			{
				repaint = true;
			}
			
		}
		
		return repaint;
		
	}
	
	public boolean editHighlighted()
	{
		
		boolean repaint = false;
		
		for (int i = 0; i < polygons.size(); i++)
		{
			
			if (polygons.get(i).isHighlighted())
			{
				polygons.get(i).edit();
				repaint = true;
				break;
			}
			
		}
		
		return repaint;
		
	}
	
	public boolean removeHighlighted()
	{
		boolean repaint = false;
		Iterator<TaggedPolygon> i = polygons.iterator();
		while (i.hasNext())
		{
			TaggedPolygon p = i.next();
			
			if (p.isHighlighted())
			{
				i.remove();
				repaint = true;
			}
		}
		
		return repaint;
	}
	
	/**
	 * Finds out which Polygon is currently being edited
	 * @return the taggedPolygon being edited
	 */
	public TaggedPolygon currentlyEditedPolygon(){
		
		for (int i = 0; i < polygons.size(); i++) {
			
			if (polygons.get(i).isEditing()){
				
				return polygons.get(i);
				
			}
			
		}
		
		//TODO
		return null;
		
	}
	
	public boolean openPolygon()
	{
		if (polygons.size() == 0)
		{
			return false;
		}
		
		return !polygons.get(polygons.size() - 1).isComplete() || isEditing();
	}
	
	public boolean isEditing()
	{
		if (polygons.size() == 0)
		{
			return false;
		}
		
		for (int i = 0; i < polygons.size(); i++)
		{
			if (polygons.get(i).isEditing())
			{
				return true;
			}
		}
		
		return false;
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
		
		if (polygon.isComplete() && !polygon.isEditing())
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
	
	public boolean removeLastPoint()
	{
		
		if (polygons.size() == 0)
			return false;
		
		if (openPolygon())
		{
			
			if (polygons.get(polygons.size()-1).removeLastPoint())
			{
				if (polygons.get(polygons.size() - 1).size() == 0)
				{
					polygons.remove(polygons.size() - 1);
				}
				return true;
			}
			
		}
		
		return false;
		
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
