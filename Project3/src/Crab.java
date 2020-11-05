import java.util.List;
import java.util.*;
import java.util.Optional;

import processing.core.PImage;

final class Crab extends AnimationEntity {

    private static final String QUAKE_KEY = "quake";


    public Crab(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }

    //@Override
    public void executeAnimation(EventScheduler scheduler, Animation animation) {
        nextImage();

        if (animation.repeatCount != 1) {
            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }

    }

    public void executeActivity(EventScheduler scheduler, Activity activity) { //FIND THIS IN THE PROJECT 2
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;
        Sgrass sgrass = new Sgrass(getId(),getPosition(),getImages(), getActionPeriod());
        Optional<Entity> crabTarget = getPosition().findNearest(world, sgrass);

        //Optional<Entity> crabTarget = getPosition().findNearest(world, "SGrass");
        long nextPeriod = getActionPeriod();

        if (crabTarget.isPresent()) {
            Point pos = crabTarget.get().getPosition();

            if (this.moveToCrab(world, crabTarget.get(), scheduler))
            {
                Quake quake = new Quake(Quake.QUAKE_ID, getPosition(), imageStore.getImageList(QUAKE_KEY),
                        Quake.QUAKE_ACTION_PERIOD, Quake.QUAKE_ANIMATION_PERIOD, 0);
                //Quake quake = new Quake(getId(), getPosition(), imageStore.getImageList(QUAKE_KEY), getActionPeriod(), getAnimationPeriod(), getResourceLimit());

                world.addEntity(quake); //check
                nextPeriod += getActionPeriod();
                quake.scheduleActivity(scheduler, world, imageStore); //scheduleActions
                quake.scheduleAnimation(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), nextPeriod);
    }


    private boolean moveToCrab(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = this.nextPositionCrab(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private Point nextPositionCrab(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz, getPosition().y);

        Optional<Entity> occupant = world.getOccupant(newPos); //CHECK
        Fish fish = new Fish(getId(), getPosition(), getImages(), getActionPeriod());

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.getClass().isInstance(fish)))) {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x, getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.getClass().isInstance(fish)))) {
                newPos = getPosition();
            }
        }

        return newPos;
    }
}
