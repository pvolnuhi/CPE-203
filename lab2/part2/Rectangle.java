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

	public double perimeter()
	{
		double w = Math.abs(2*(this.getTopLeft().getX() - this.getBottomRight().getX())); 
		double l = Math.abs(2*(this.getTopLeft().getY() - this.getBottomRight().getY())); // P = 2w + 2l

		double perimeter = w + l; 
		return perimeter;  //may need to switch
	}
}
