import processing.core.PImage;

import java.util.List;

public class Atlantis extends AnimatedEntity{

    public Atlantis( String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod){
        super(position,images,id,actionPeriod,animationPeriod);
    }

    public void executeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,(Action)(new Animation( this, null, null, ATLANTIS_ANIMATION_REPEAT_COUNT)), getAnimationPeriod());
    }
}
