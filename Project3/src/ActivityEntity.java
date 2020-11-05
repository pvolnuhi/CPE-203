import processing.core.PImage;

import java.util.List;

abstract class ActivityEntity extends Entity {

    //private final int imageIndex;
    private final int actionPeriod;
    private int imageIndex;
    //contain executeActivity;

    public ActivityEntity(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.imageIndex = imageIndex; //0
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int val) { //check
        imageIndex = val;
    }

    //check
    public abstract void executeActivity(EventScheduler scheduler, Activity activity); //check

    protected Activity createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);

    }

    public void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
    }
}
