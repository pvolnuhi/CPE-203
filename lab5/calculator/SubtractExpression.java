class SubtractExpression extends BinaryExpression
{

   public SubtractExpression(Expression lft, Expression rht)
   {
      super(lft, rht, "-");
   }

   protected double _applyOperator(double lft, double rht)
   {
      return lft - rht;
   }
}

