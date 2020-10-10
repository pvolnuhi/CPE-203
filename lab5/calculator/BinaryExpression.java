abstract class BinaryExpression implements Expression
{

    private final Expression lft;
    private final Expression rht;
    private final String operation;

    public BinaryExpression(Expression lft, Expression rht, String operation) 
    {
        this.lft = lft;
        this.rht = rht;
        this.operation = operation;
    }

    //@Override
    public String toString() 
    {
        return "(" + lft + " " + operation + " " + rht + ")";
    }

    //@Override
    public double evaluate(Bindings bindings) 
    {
        return _applyOperator(lft.evaluate(bindings), rht.evaluate(bindings));
    }

    abstract protected double _applyOperator(double lft, double rht);
        
     //subclass of class number becomes Double, Ints, etc...

}