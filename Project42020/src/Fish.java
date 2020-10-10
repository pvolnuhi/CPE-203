import java.util.List;

import processing.core.PImage;

class Fish extends ActiveEntity {

    private List<Point> path;

    public Fish( String id, Point position,List<PImage> images,int actionPeriod){
        super(position,images,id,actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Point pos = this.position;  // store current position before removing
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        Crab crab = new Crab(this.id + CRAB_ID_SUFFIX, pos, imageStore.getImageList(CRAB_KEY),
                this.actionPeriod / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN + rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN), path);
        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

}


