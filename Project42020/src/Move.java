public interface Move extends Animated{
    public boolean moveTo( WorldModel world, Entitys target, EventScheduler scheduler);
    public Point nextPosition( WorldModel world, Point destPos);
    public void setposition(Point position);

}
