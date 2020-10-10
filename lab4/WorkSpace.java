import java.util.*;
import java.awt.Point;
import java.awt.*;
import java.lang.Math;

public class WorkSpace
{
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public WorkSpace(){}

	public void add(Shape shape)
	{
		shapes.add(shape); //Adds an object which implements the Shape interfac
	}

	public Shape get(int index)
	{
		return shapes.get(index);
	}

	public int size()
	{
		return shapes.size();
	}

	public ArrayList<Circle> getCircles()
	{
		ArrayList<Circle> circles = new ArrayList<Circle>();
		for(Shape shape: shapes) //first Shape means its implementing from shape class, goes through shapes
		{
			if(shape instanceof Circle)
			{
				circles.add((Circle)shape);
			}
		}
		return circles;
	}

	public ArrayList<Rectangle> getRectangles()
	{
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		for(Shape shape: shapes)
		{
			if(shape instanceof Rectangle)
			{
				rectangles.add((Rectangle)shape);
			}
		}
		return rectangles;
	}

	public ArrayList<Triangle> getTriangles()
	{
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for(Shape shape: shapes)
		{
			if(shape instanceof Triangle)
			{
				triangles.add((Triangle)shape);
			}
		}
		return triangles;
	}

	public ArrayList<Shape> getShapesByColor(Color color)
	{
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		for(Shape shape: shapes) //for each shape in shapes...
		{
			if(shape.getColor().equals(color))
			{
				shapes.add(shape);
			}
		}
		return shapes;
	}

	public double getAreaOfAllShapes()
	{
		double areaSum = 0.0;
		for(Shape shape: shapes) //for each shape in shapes...
		{
			areaSum += shape.getArea();
		}
		return areaSum;
	}

	public double getPerimeterOfAllShapes()
	{
		double perSum = 0.0;
		for(Shape shape: shapes)
		{
			perSum += shape.getPerimeter();
		}
		return perSum;
	}




}