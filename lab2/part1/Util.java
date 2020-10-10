import java.lang.*;
public class Util
{
	public static double perimeter(Circle circle) // C = 2piR
	{
		return Math.PI*(Math.pow(circle.getRadius(),2));
	}

	public static double perimeter(Rectangle rectangle) //P = 2w + 2l
	{ //need to cater towar5ds potential negative values/coordinates 
		double w = Math.abs(rectangle.getTopLeft().getX() - rectangle.getBottomRight().getX()) * 2; 
		double l = Math.abs(rectangle.getTopLeft().getY() - rectangle.getBottomRight().getY()) * 2; // P = 2w + 2l

		double perimeter = w + l; 
		return perimeter;  //may need to switch
	}

	public static double perimeter(Polygon polygon) // P = N * S //FIX
	{
		double totalPerimeter = 0.0; //look this over 
		for(int i = 0; i < polygon.getPoints().size()-1; i++)
		{
			Point poly1 = polygon.getPoints().get(i);
			Point poly2 = polygon.getPoints().get(i+1);
			double dist = Math.sqrt(Math.pow(poly1.getX() - poly2.getX(),2) + Math.pow(poly1.getY() - poly2.getY(),2)); //takes square root
			totalPerimeter = totalPerimeter + dist;
		}
		//doublem polygonPoints = polygon.getPoints().size()-1
		Point lastpoint = polygon.getPoints().get(polygon.getPoints().size()-1); //FIX 
		Point firstpoint = polygon.getPoints().get(0);
		double sum = Math.sqrt(Math.pow(lastpoint.getX() - firstpoint.getX(),2) + Math.pow(lastpoint.getY() - firstpoint.getY(),2));

		totalPerimeter = totalPerimeter + sum;
		return totalPerimeter;

	}

}