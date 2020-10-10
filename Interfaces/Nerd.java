public class Nerd implements Student {
    private double score;
    private String name;
    private int ParticipationPoint;

    public Nerd(String name, double score, int pp)
    {
        this.name=name;
        this.score = score;
        this.ParticipationPoint = pp;
    }
    //
    /*TO DO:
    Add an equals() method here such that it overrides the equals() method inherited from Object.
    Two Nerd objects are considered equal if they have the same name, same score and same number of ParticipationPoints
     Write the whole method here!
     */


    public int pp(){return ParticipationPoint;}
    //
    public double JavaCourseScore()
    {
        return this.score;
    }
    //
    public String name()
    {
        return this.name;
    }
}
