import java.awt.*;
import java.util.*;
import java.awt.Point;
import java.lang.Math;

public class Triangle implements Shape
{
	private Point a;
	private Point b;
	private Point c;
	private Color color;

	public Triangle(Point a, Point b, Point c, Color color)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.color = color;
	}

	public Point getVertexA()
	{
		return a;
	}

	public Point getVertexB()
	{
		return b;
	}

	public Point getVertexC()
	{
		return c;
	}

	public boolean equals(Object object)
	{
		if(object == null)
		{
			return false;
		}
		if(getClass() != object.getClass())
		{
			return false;
		}
		Triangle t = (Triangle)object;
		return color.equals(t.color) && a.equals(t.a) && b.equals(t.b) && c.equals(t.c);
	}

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
		return Math.abs((a.getX()*(b.getY()-c.getY()) + b.getX()*(a.getY()-c.getY()) + c.getX()*(a.getY()-b.getY())/(2)));
	}

	public double getPerimeter()//distance formula for each coordinate
	{
		double aDist = Math.sqrt((Math.pow(b.getX()-a.getX(),2) + Math.pow(b.getY()-a.getY(),2)));
		double bDist = Math.sqrt((Math.pow(c.getX()-b.getX(),2) + Math.pow(c.getY()-b.getY(),2)));
		double cDist = Math.sqrt((Math.pow(a.getX()-c.getX(),2) + Math.pow(a.getY()-c.getY(),2)));
		double sum = (aDist + bDist + cDist);
		return sum;
	}

	//Point is a double class
	public void translate(Point p) //CHECK translates jusst literally means move it to right or left
	{   
		a.setLocation(a.getX() + p.getX(), a.getY() + p.getY());
		b.setLocation(b.getX() + p.getX(), b.getY() + p.getY());
		c.setLocation(c.getX() + p.getX(), c.getY() + p.getY());
				//return topLeft.move(topLeft.getX() + point.getX(), topLeft.getY() + point.getY()); //.setLocation
		//.move only takes in ints, while .setLocation takes ints, double, and floats
	}


}
