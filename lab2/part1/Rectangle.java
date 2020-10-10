public class Rectangle
{
	private final Point topLeft; //attributes
	private final Point bottomRight; //attributes

	public Rectangle(Point topLeft, Point bottomRight) //contructor
	{
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public Point getTopLeft() //methods
	{
		return topLeft;
	} 

	public Point getBottomRight()
	{
		return bottomRight;
	}
}
