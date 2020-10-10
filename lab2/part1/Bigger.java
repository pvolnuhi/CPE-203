public class Bigger
{
	private static double whichIsBigger(Circle circle, Rectangle rectangle, Polygon polygon)
	{
		double circlePer = Util.perimeter(circle);
		double rectanglePer = Util.perimeter(rectangle);
		double polygonPer = Util.perimeter(polygon);

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