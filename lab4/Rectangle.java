import java.util.*;
import java.awt.Point;
import java.awt.*;
import java.lang.Math;
//import java.math.BigDecimal;

public class Rectangle implements Shape
{
	private double width;
	private double height;
	private Point topLeft;
	private Color color;

	public Rectangle(double width, double height, Point topLeft, Color color)
	{
		this.width = width;
		this.height = height;
		this.topLeft = topLeft;
		this.color = color;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public Point getTopLeft()
	{
		return topLeft;
	}

	@Override
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
		Rectangle r = (Rectangle)object;
		return width == (r.width) && height == (r.height) && color.equals(r.color) && topLeft.equals(r.topLeft);
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
		return height*width;
	}

	public double getPerimeter()
	{
		return 2*height + 2*width;
	}

	//void isn't going to return anything 
	public void translate(Point p) //CHECK translates jusst literally means move it to right or left
	{
		topLeft.setLocation(topLeft.getX() + p.getX(), topLeft.getY() + p.getY()); //.setLocation
		//.move only takes in ints, while .setLocation takes ints, double, and floats
	}

}

