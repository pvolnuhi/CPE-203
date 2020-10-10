import java.util.List;
import java.util.*;

import processing.core.PImage;

final class OctoNotFull extends Moveable{

    private int resourceLimit;
    private int resourceCount;
    List<Point> pathing;
    public OctoNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod,List<Point> pathing){
        super(position,images,id,actionPeriod,animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
        this.pathing = pathing;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
    public int getResourceCount(){
        return resourceCount;
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<Entitys> notFullTarget = world.findNearest( this.position, Fish.class);

        if (!notFullTarget.isPresent() || !moveTo( world, notFullTarget.get(), scheduler) || !transformNotFull( world, scheduler, imageStore)){

            scheduler.scheduleEvent((Entitys)this, (Action)(this.createActivityAction( world, imageStore)), (long)this.actionPeriod);
        }
    }



    public boolean transformNotFull( WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        if (this.resourceCount >= this.resourceLimit){
            OctoFull octo = (OctoFull)world.createOctoFull(this.id, this.resourceLimit, this.position, this.actionPeriod, this.animationPeriod, this.images);
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(octo);
            octo.scheduleActions( scheduler, world, imageStore);
            return true;
        }
        return false;
    }
}
