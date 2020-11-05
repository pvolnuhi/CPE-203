import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Patty extends ActiveEntity{

    public Patty(String id, Point position, List<PImage> images, int actionPeriod){
        super(position,images,id,actionPeriod);
    }

    public void executeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent()){
            Fish fish = (Fish)(world.createFish(FISH_ID_PREFIX + this.id, openPt.get(), FISH_CORRUPT_MIN + rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN), imageStore.getImageList(FISH_KEY)));
            world.addEntity((Entitys)fish);
            fish.scheduleActions( scheduler, world, imageStore, fish.actionPeriod);
        }
        scheduler.scheduleEvent((Entitys)this, (Action)(this.createActivityAction( world, imageStore)), this.actionPeriod);
    }
}



