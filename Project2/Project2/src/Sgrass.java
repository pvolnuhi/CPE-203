import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class Sgrass implements Entity, ActivityEntity {

    private static final String FISH_ID_PREFIX = "fish -- ";

    private final String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final int actionPeriod;

    public Sgrass(String id, Point position,  int actionPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
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

    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }


    public void executeActivity(EventScheduler scheduler, Activity activity)
    {
        this.executeSgrassActivity(activity.world, activity.imageStore, scheduler);
    }


    private Activity createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore);
    }


    private void executeSgrassActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = position.findOpenAround(world);

        if (openPt.isPresent()) {
            Fish fish = new Fish(FISH_ID_PREFIX + id, openPt.get(), actionPeriod, imageStore.getImageList(WorldModel.FISH_KEY));
            world.addEntity(fish);
            fish.scheduleActivity(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
    }

}
