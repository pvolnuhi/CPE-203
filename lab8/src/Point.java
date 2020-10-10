public class Point {
    private double x;
    private double y;
    private double z;

    public Point(String x, String y, String z) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Point scale(double half) {

        return new Point(this.getX() * half, this.getY() * half, this.getZ() * half);

    }

    public Point translate(Point translate){ //QUESTIONS

        this.x += translate.getX();
        this.y += translate.getY();

        return this;


    }

    @Override
    public String toString() {
        return "(" + x + "," + y +"," + z + ")";
    }

}

