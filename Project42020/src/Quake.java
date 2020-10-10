import java.util.List;

import processing.core.PImage;

final class Quake extends AnimatedEntity implements Animated {

    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake( String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(position,images,id,actionPeriod,animationPeriod);
    }

    public void executeActivity( WorldModel world,ImageStore imageStore, EventScheduler scheduler){
        scheduler.unscheduleAllEvents((Entitys)this);
        world.removeEntity(this);
    }

    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(((Entitys)this),(Action)createActivityAction( world, imageStore),(long)this.actionPeriod);
        scheduler.scheduleEvent(((Entitys)this),(Action)createAnimationAction( QUAKE_ANIMATION_REPEAT_COUNT),getAnimationPeriod());
    }





}
