import java.lang.reflect.Method;
import java.util.ArrayList;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import java.util.*;
//import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PartOneTestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getCenter", "getRadius");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getTopLeft", "getBottomRight");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getPoints");

      final List<Class> expectedMethodReturns = Arrays.asList(
         List.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[][] {new Class[0]});

      verifyImplSpecifics(Polygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testUtilImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "perimeter", "perimeter", "perimeter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class, double.class, double.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[] {Circle.class},
         new Class[] {Polygon.class},
         new Class[] {Rectangle.class});

      verifyImplSpecifics(Util.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testPerimPoly() {
        List < Point >points = new ArrayList < Point >(); 
        points.add(new Point(0, 0));
        points.add(new Point(3,0));
        points.add(new Point(0,4));
        double d = Util.perimeter(new Polygon(points));
        assertEquals(12.0, d, DELTA);
   }

   @Test 
   public void testPerimRect(){ 
      Rectangle rect = new Rectangle(new Point(-1,4), new Point(8,9));
      double d = Util.perimeter(rect);
      assertEquals(28.0, d, DELTA);
   }

   @Test
   public void testPerimCirc1(){ 
      Circle circ = new Circle(new Point(-2, 0), 8.0);
      double d = Util.perimeter(circ);
      assertEquals(201.06192982974676, d, DELTA);
   }

   @Test //not mine 
   public void testPerimCirc2(){ 
      Circle circ = new Circle(new Point(8,-4), 4.0);
      double d = Util.perimeter(circ);
      assertEquals(50.26548245743669, d, DELTA);
   }

    @Test
   public void testBiggerPoly(){ //FIX 
      Circle circ = new Circle(new Point(-2.0,0.0), 8.0); //12.56
      Rectangle rect = new Rectangle(new Point(-1.0,4.0),new Point(8.0,9.0)); //20.8
      List<Point> points = new ArrayList<Point>(); 
      points.add(new Point(0, 0));
      points.add(new Point(3,1));
      points.add(new Point(1,4));
      points.add(new Point(-1,4));
      Polygon poly = new Polygon(points);
      double d = Util.perimeter(poly);
      assertEquals(12.890934561250031, d, DELTA);
   }
//two more bigger tests rect and circ
     @Test
   public void testBiggerRect(){ //FIX 
      Circle circ = new Circle(new Point(1.0,0.0), 2.0); 
      Rectangle rect = new Rectangle(new Point(4.0,10.0), new Point(2.0,2.0)); 
      List<Point> points = new ArrayList<Point>(); 
      points.add(new Point(0, 0));
      points.add(new Point(3,1));
      points.add(new Point(1,4));
      points.add(new Point(-1,4));
      Polygon poly = new Polygon(points);
      double d = Util.perimeter(rect);
      assertEquals(20.0, d, DELTA); //!!!
   }

     @Test
   public void testBiggerCirc(){ //FIX 
      Circle circ = new Circle(new Point(-2.0,0.0), 8.0); 
      Rectangle rect = new Rectangle(new Point(-1.0,4.0),new Point(8.0,9.0)); 
      List<Point> points = new ArrayList<Point>(); 
      points.add(new Point(0, 0));
      points.add(new Point(3,1));
      points.add(new Point(1,4));
      points.add(new Point(-1,4));
      Polygon poly = new Polygon(points);
      double d = Util.perimeter(circ);
      assertEquals(201.06192982974676, d, DELTA);
   }


   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}



