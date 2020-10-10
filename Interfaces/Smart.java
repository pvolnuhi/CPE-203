public class Smart implements Student{
    private double score;
    private String name;

    public Smart(String name, double score)
    {
        this.name=name;
        this.score = score;
    }
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
