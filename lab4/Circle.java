import java.util.*;
import java.awt.Point;
import java.awt.*;
import java.lang.Math;


public class Circle implements Shape
{
	private double radius;
	private Point center;
	private Color color;
	// private double area;
	// private double perimeter;
	// private void translate;

	public Circle(double radius, Point center, Color color)
	{
		this.radius = radius;
		this.center = center;
		this.color = color;
	}

	public double getRadius()
	{
		return radius;
	}

	public void setRadius(double radius)
	{
		this.radius = radius;
	}

	public Point getCenter()
	{
		return center;
	}

	public boolean equals(Object object) //CHECK
	{
		if(object == null) //if all instance variable of that object are null, or none
		{
			return false;
		}
		if(getClass() != object.getClass())//if one object is not equal to the other one, then return false
		{
			return false;
		} //CHECK 
		Circle c = (Circle)object;
		return radius == c.radius && center == c.center && color.equals(c.color); 
	}   //radius == (((Circle)object).radius) && center == (((Circle)object).center) && color.equals(((Circle)object).color);

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public double getArea()
	{
		return Math.PI*Math.pow(radius, 2);
	}

	public double getPerimeter()
	{
		return 2*Math.PI*radius;
	}

	public void translate(Point p) //CHECK translates jusst literally means move it to right or left
	{
		center.setLocation(center.getX() + p.getX(), center.getY() + p.getY()); //.setLocation
		//.move only takes in ints, while .setLocation takes ints, double, and floats
	}

}