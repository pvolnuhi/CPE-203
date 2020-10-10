public class CsCohort {
    private int year;
    private int enrolled;
    private double retainedPercent;

    public CsCohort(int inYear, int inEnroll, double inPercent) {
        this.year = inYear;
        this.enrolled = inEnroll;
        this.retainedPercent = inPercent;
    }

    public int getYear() {
        return year;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public double getPercent() {
        return retainedPercent;
    }

    public int retained() {
        return (int) (retainedPercent * enrolled);
    }


}

