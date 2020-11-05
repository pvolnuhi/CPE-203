import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class OctoFull extends AnimationEntity {



    public OctoFull(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                    int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }


    private Point nextPositionOcto(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz, getPosition().y);

        if (horiz == 0 || newPos.isOccupied(world))
        {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x,
                    getPosition().y + vert);

            if (vert == 0 || newPos.isOccupied(world))
            {
                newPos = getPosition();
            }
        }

        return newPos;
    }

    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionOcto(world, target.getPosition()); //CHECK

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos); //do this for get methods
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        OctoNotFull octo = new OctoNotFull(getId(), getPosition(), getImages(), getActionPeriod(), getAnimationPeriod(), getResourceLimit());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleActivity(scheduler, world, imageStore);
        octo.scheduleAnimation(scheduler, world, imageStore);
    }

    public void executeAnimation(EventScheduler scheduler, Animation animation)
    {
        nextImage();

        if (animation.repeatCount != 1)
        {
            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }
    }

    public void executeActivity(EventScheduler scheduler, Activity activity)
    {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;
        Atlantis atlantis = new Atlantis(getId(), getPosition(), getImages());

        Optional<Entity> fullTarget = getPosition().findNearest(world, atlantis); //CHECK OVER

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), getActionPeriod());
        }
    }

}
