import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class OctoFull implements Entity, AnimationEntity, ActivityEntity {

    private final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final int actionPeriod;
    private final int animationPeriod;

    public OctoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point newPosition)
    {
        position = newPosition;
    }

    public void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
        scheduler.scheduleEvent(this, this.createAnimationAction(0), getAnimationPeriod());
    }

    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }

    public int getAnimationPeriod()
    {
        return animationPeriod;
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
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
        this.executeOctoFullActivity(activity.world, activity.imageStore, scheduler);
    }

    private Activity createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore);
    }

    private Animation createAnimationAction(int repeatCount)
    {
        return new Animation(this, repeatCount);
    }

    private Point nextPositionOcto(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz,
                position.y);

        if (horiz == 0 || newPos.isOccupied(world))
        {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x,
                    position.y + vert);

            if (vert == 0 || newPos.isOccupied(world))
            {
                newPos = position;
            }
        }

        return newPos;
    }

    private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (position.adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionOcto(world, target.getPosition()); //CHECK

            if (!position.equals(nextPos))
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
        OctoNotFull octo = new OctoNotFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleActivity(scheduler, world, imageStore);
    }

    private void executeOctoFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = position.findNearest(world, "ATLANTIS"); //CHECK OVER

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
        }
    }

}
