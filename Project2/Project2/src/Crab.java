import java.util.List;
import java.util.*;

import processing.core.PImage;

final class Crab implements Entity, ActivityEntity, AnimationEntity {

    private static final String QUAKE_KEY = "quake";

    private final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final int actionPeriod;
    private final int animationPeriod;

    public Crab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        position = newPosition;
    }

    public void scheduleActivity(EventScheduler scheduler,
                                 WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0), getAnimationPeriod());

    }

    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public void executeAnimation(EventScheduler scheduler, Animation animation) {
        nextImage();

        if (animation.repeatCount != 1) {
            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }
    }

    public void executeActivity(EventScheduler scheduler, Activity activity) {
        executeCrabActivity(activity.world, activity.imageStore, scheduler);
    }


    private Activity createActivityAction(WorldModel world,
                                          ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    private Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    private Point nextPositionCrab(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        Optional<Entity> occupant = world.getOccupant(newPos); //CHECK

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.getClass().getSimpleName() == "FISH")))
        {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.getClass().getSimpleName() == "FISH")))
            {
                newPos = position;
            }
        }

        return newPos;
    }

    private boolean moveToCrab(WorldModel world, Entity target, EventScheduler scheduler) {
        if (position.adjacent(target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionCrab(world, target.getPosition());

            if (!position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    private void executeCrabActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> crabTarget = position.findNearest(world, "SGRASS");
        long nextPeriod = actionPeriod;

        if (crabTarget.isPresent())
        {
            Point tgtPos = crabTarget.get().getPosition();

            if (this.moveToCrab(world, crabTarget.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos, imageStore.getImageList(QUAKE_KEY));


                world.addEntity(quake);
                nextPeriod += actionPeriod;
                quake.scheduleActivity(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), nextPeriod);
    }


}
