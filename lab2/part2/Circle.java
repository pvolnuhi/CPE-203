public class Circle
{
	private final Point center; //do we need final in the block?
	private final double radius; // if we know it'll remain unchanged anyways?

	public Circle(Point center, double radius) //constructor
	{
		this.center = center;
		this.radius = radius;
	}

	public Point getCenter()
	{
		return center;
	}

	public double getRadius()
	{
		return radius;
	}

	public double perimeter()
	{
		return 2*Math.PI*this.radius;
	}

}