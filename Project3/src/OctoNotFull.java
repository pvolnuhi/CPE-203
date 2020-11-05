import java.util.List;
import java.util.*;

import processing.core.PImage;

final class OctoNotFull extends AnimationEntity {

    private int resourceCount;

    public OctoNotFull(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                       int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }


    public void executeActivity(EventScheduler scheduler, Activity activity) {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;
        Fish fish = new Fish(getId(),getPosition(),getImages(),getActionPeriod());

        Optional<Entity> notFullTarget = getPosition().findNearest(world, fish); //CHECK OVER

        if (!notFullTarget.isPresent() ||
                !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), getActionPeriod());
        }
    }
    public void executeAnimation(EventScheduler scheduler, Animation animation)
    {
        nextImage();

        if (animation.repeatCount != 1)
        {
            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }
    }

    private Point nextPositionOcto(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz,
                getPosition().y);

        if (horiz == 0 || newPos.isOccupied(world)) {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x,
                    getPosition().y + vert);

            if (vert == 0 || newPos.isOccupied(world)) {
                newPos = getPosition();
            }
        }

        return newPos;
    }

    private boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = this.nextPositionOcto(world, target.getPosition());

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

    private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (resourceCount >= getResourceLimit()) {
            OctoFull octo = new OctoFull(getId(), getPosition(), getImages(), getActionPeriod(), getAnimationPeriod(), getResourceLimit());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            octo.scheduleActivity(scheduler, world, imageStore);
            octo.scheduleAnimation(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
}


