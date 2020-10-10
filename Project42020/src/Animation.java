public class Animation implements Action{
    //public int repeatCount;
    private Entitys entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;
   // private int repeatCount;

    public Animation( Entitys entity, WorldModel world,ImageStore imageStore, int repeatCount){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler){
        ((Animated)(this.entity)).nextImage();

        if (this.repeatCount != 1){
            scheduler.scheduleEvent( this.entity,(Action)(new Animation( (this.entity), world, imageStore, Math.max(this.repeatCount - 1, 0))),((Animated)(this.entity)).getAnimationPeriod());
        }
    }

}