final class Point
{
   private final int FISH_REACH = 1;
   public final int x;
   public final int y;

   private Point previous;
   private double h;
   private double g = 0;

   public double getHeuristic() {return g + h;}

   public void Hval(double h) {this.h = h;}

   public double getGval() {return g;}

   public void Gval(double g) {this.g = g;}

   public Point getPrev() { return previous;}

   public void setPrev(Point previous) {this.previous = previous;}

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }


   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other)
   {
      return other instanceof Point &&
              ((Point)other).x == this.x &&
              ((Point)other).y == this.y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public boolean adjacent( Point p2)
   {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
              (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
   }

   public int distanceSquared( Point p1)
   {
      int deltaX = p1.x - this.x;
      int deltaY = p1.y - this.y;

      return deltaX * deltaX + deltaY * deltaY;
   }


}
