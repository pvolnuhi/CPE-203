class SimpleIf
{
   public static double max(double x, double y)
   {
   	if (x > y) {
   		return x;
   	} else if (x < y) {
   		return y;
      } else {
         return 0; // clearly not correct -- but testable
      }
   	
      /* TO DO: Write an if statement to determine which
         argument is larger and return that value.
      */
   }
}
