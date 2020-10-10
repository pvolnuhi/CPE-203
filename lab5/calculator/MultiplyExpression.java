class MultiplyExpression extends BinaryExpression
{
   
   public MultiplyExpression(Expression lft, Expression rht)
   {
      super(lft, rht, "*");
   }

   protected double _applyOperator(double lft, double rht)
   {
      return lft * rht;
   }
}

