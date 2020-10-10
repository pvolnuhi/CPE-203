public class Activity implements Action {

    public Actionable entity;
    public WorldModel world;
    public ImageStore imageStore;

    public Activity(Actionable entity, WorldModel world, ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler){
        entity.executeActivity( this.world,this.imageStore, scheduler);
    }
}
