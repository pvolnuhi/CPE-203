class Activity implements Action{

    public ActivityEntity entity;
    public WorldModel world;
    public ImageStore imageStore;

    public Activity(ActivityEntity entity, WorldModel world, ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void execute(EventScheduler scheduler)
    {
        entity.executeActivity(scheduler, this);
    }
}
