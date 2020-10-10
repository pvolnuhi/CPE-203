import java.lang.*;

public class Point
{
	private final double x; //why?
	private final double y; //why?

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double getRadius()
	{
		double distX = Math.pow(getX(), 2);
		double distY = Math.pow(getY(), 2);

		double radius = Math.sqrt(distX + distY);
		return radius;
	}
	public double getAngle()
	{
		double distY = getY();
		double distX = getX();

		double angle = Math.atan2(distY, distX);
		return angle;
	}
	public Point rotate90()
	{
		return new Point(-y, x);
	}
}