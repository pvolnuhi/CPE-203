import java.util.List;

import processing.core.PImage;

final class Fish extends ActivityEntity {

    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;


    public Fish(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(EventScheduler scheduler, Activity activity)
    {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;

        Point pos = getPosition();
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this); //CHECK OVER


        Crab crab = new Crab(getId() + CRAB_ID_SUFFIX, pos, imageStore.getImageList(CRAB_KEY), getActionPeriod() / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN + Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN), 0);

        world.addEntity(crab);
        crab.scheduleActivity(scheduler, world, imageStore);
        crab.scheduleAnimation(scheduler, world, imageStore);
    }

}


