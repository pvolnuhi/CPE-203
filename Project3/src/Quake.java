import java.util.List;

import processing.core.PImage;

final class Quake extends AnimationEntity{

    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;



    //private final String id = QUAKE_ID;
//    private Point position;
//    private List<PImage> images;
//    private int imageIndex;
//    private final int actionPeriod;
//    private final int animationPeriod;

    public Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                 int resourceLimit) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }


    public void executeAnimation(EventScheduler scheduler, Animation animation)
    {
        nextImage();

        if (animation.repeatCount != 1) {
            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
        }
    }

    public void executeActivity(EventScheduler scheduler, Activity activity)
    {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;

        //executeQuakeActivity(activity.world, activity.imageStore, scheduler);

        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);

    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){

        scheduleActivity(scheduler, world, imageStore);
        scheduleAnimation(scheduler, world, imageStore);

    }

//    private Activity createActivityAction(WorldModel world, ImageStore imageStore)
//    {
//        return new Activity(this, world, imageStore);
//    }
//
//    private Animation createAnimationAction(int repeatCount)
//    {
//        return new Animation(this, repeatCount);
//    }



}
