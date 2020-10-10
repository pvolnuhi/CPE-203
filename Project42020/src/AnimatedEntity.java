import java.util.List;
import processing.core.PImage;

public abstract class AnimatedEntity extends ActiveEntity implements Animated{


    protected int animationPeriod;

    public AnimatedEntity(Point position, List<PImage> images,String id, int actionPeriod, int animationPeriod){
        super(position,images,id,actionPeriod);
        this.animationPeriod = animationPeriod;
    }


    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduleAnimation(scheduler, world, imageStore);
        scheduleActions(scheduler, world, imageStore, this.actionPeriod);
    }
    public void scheduleAnimation( EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent((Entitys)this, (Action)(this.createAnimationAction(0)), getAnimationPeriod());
    }

    public int getAnimationPeriod(){return this.animationPeriod;}
    public void nextImage(){this.imageIndex = (this.imageIndex + 1) % this.images.size();}

}