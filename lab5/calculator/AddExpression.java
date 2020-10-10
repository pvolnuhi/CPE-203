class AddExpression extends BinaryExpression
{

   public AddExpression(Expression lft, Expression rht, String operation)
   {
      super(lft, rht, "+");
   }

   protected double _applyOperator(double lft, double rht)
   {
      return lft + rht;
   }
}
