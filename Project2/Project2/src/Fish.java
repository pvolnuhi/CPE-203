import java.util.List;

import processing.core.PImage;

final class Fish implements Entity, ActivityEntity{

    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

    private final String id;
    private Point position;
    private List<PImage> images;
    private final int actionPeriod;

    public Fish(String id, Point position, int actionPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.images = images;
    }


    public PImage getCurrentImage()
    {
        return images.get(0);
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

    }

    public void executeActivity(EventScheduler scheduler, Activity activity)
    {
        this.executeFishActivity(activity.world, activity.imageStore, scheduler);
    }

    private Activity createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore);
    }

    private void executeFishActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = position;
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this); //CHECK OVER

        Crab crab = new Crab(id + CRAB_ID_SUFFIX, pos, actionPeriod / CRAB_PERIOD_SCALE, CRAB_ANIMATION_MIN + Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),  imageStore.getImageList(CRAB_KEY));

        world.addEntity(crab);
        crab.scheduleActivity(scheduler, world, imageStore);
    }

}

