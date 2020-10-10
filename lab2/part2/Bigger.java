public class Bigger
{
	private static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon)
	{
		double circlePer = circle.perimeter();
		double rectanglePer = rectangle.perimeter();
		double polygonPer = polygon.perimeter();

		if(circlePer > rectanglePer && circlePer > polygonPer)
		{
			return circlePer;
		}

		else if(rectanglePer > circlePer && rectanglePer > polygonPer)
		{
			return rectanglePer;
		}

		else if(polygonPer > circlePer && polygonPer > rectanglePer)
		{
			return polygonPer;
		}

		else if(polygonPer == circlePer)
		{
			return polygonPer;
		}

		else if(polygonPer == rectanglePer)
		{
			return polygonPer;
		}

		else
		{
			return rectanglePer;
		}

	}
}