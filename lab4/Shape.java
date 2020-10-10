import java.awt.Color;
import java.awt.Point;
import java.util.List;

public interface Shape{
	Color getColor();
	void setColor(Color c);
	double getArea();
	double getPerimeter();
	void translate(Point p);
}