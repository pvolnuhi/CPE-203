import java.util.List;
import processing.core.PImage;

public abstract class ActiveEntity extends Entity implements Actionable{
    protected int actionPeriod;

    public ActiveEntity(Point position, List<PImage> images,String id, int actionPeriod){
        super(position,images,id);
        this.actionPeriod = actionPeriod;
    }
    public Animation createAnimationAction( int repeatCount){
        return new Animation(this, null, null, repeatCount);
    }

    public Activity createActivityAction( WorldModel world, ImageStore imageStore){
        return new Activity( this, world, imageStore);
    }
    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore, int actionPeriod){
        scheduler.scheduleEvent((Entitys)this, (Action)(this.createActivityAction( world, imageStore)), this.actionPeriod);
    }

    public int getactionPeriod(){return actionPeriod;}

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
