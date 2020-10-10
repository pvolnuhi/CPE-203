import java.util.List;
public class Polygon
{
	private final List<Point> points;

	public Polygon(List<Point> points)
	{
		this.points = points;
	} 

	public List<Point> getPoints()
	{
		return points;
	}

	public double perimeter()
	{
		double totalPerimeter = 0.0; //look this over 
		for(int i = 0; i < points.size()-1; i++)
		{
			Point poly1 = points.get(i);
			Point poly2 = points.get(i+1);
			double dist = Math.sqrt(Math.pow(poly1.getX() - poly2.getX(),2) + Math.pow(poly1.getY() - poly2.getY(),2)); //takes square root
			totalPerimeter = totalPerimeter + dist;
		}
		//doublem polygonPoints = polygon.getPoints().size()-1
		Point lastpoint = points.get(points.size()-1); //FIX 
		Point firstpoint = points.get(0);
		double sum = Math.sqrt(Math.pow(lastpoint.getX() - firstpoint.getX(),2) + Math.pow(lastpoint.getY() - firstpoint.getY(),2));

		totalPerimeter = totalPerimeter + sum;
		return totalPerimeter;
	}
}