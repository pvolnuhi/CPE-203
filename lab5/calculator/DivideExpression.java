class DivideExpression extends BinaryExpression //use extends for reaching interfaces
{

   public DivideExpression(Expression lft, Expression rht)
   {
      super(lft, rht, "/");
   }

   protected double _applyOperator(double lft, double rht)
   {
      return lft/rht; 
   }
}

