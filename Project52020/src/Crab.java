import java.util.List;
import java.util.*;
import java.util.Optional;
import processing.core.PImage;

import processing.core.PImage;

final class Crab extends Moveable{

    public List<Point> getPath() {
        return path;
    }

    private List<Point> path;
    public Crab(String id, Point position, List<PImage> images, int resourceLimit, int animationPeriod, List<Point> path){
        super(position,images,id,resourceLimit,animationPeriod);
        this.path = path;
    }

    public void executeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler){

        Optional<Entitys> crabTarget = world.findNearest(getposition(), Sgrass.class);
        long nextPeriod = getactionPeriod();

        if (crabTarget.isPresent()){
            Point tgtPos = crabTarget.get().getposition();
            if (moveTo(world, crabTarget.get(), scheduler)){
                Entitys quake = world.createQuake(tgtPos, imageStore.getImageList( QUAKE_KEY));
                world.addEntity(quake);
                nextPeriod += getactionPeriod();
                ((AnimatedEntity)quake).scheduleActions( scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,createActivityAction( world, imageStore),nextPeriod);
    }
}