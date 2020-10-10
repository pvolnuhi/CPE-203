interface Actionable{
    public int getactionPeriod();
    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore, int actionPeriod);
    public void executeActivity(WorldModel world,ImageStore imageStore, EventScheduler scheduler);
}