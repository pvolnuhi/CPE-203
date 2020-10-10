import processing.core.PImage;

public interface ActivityEntity {
    void scheduleActivity(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    void executeActivity(EventScheduler scheduler, Activity activity);
}
