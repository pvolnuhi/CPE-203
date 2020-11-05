import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class Sgrass extends ActivityEntity {

    private static final String FISH_ID_PREFIX = "fish -- ";


    public Sgrass(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }


    public void executeActivity(EventScheduler scheduler, Activity activity) {
        WorldModel world = activity.world;

        Optional<Point> point = getPosition().findOpenAround(world);

        ImageStore imageStore = activity.imageStore;

        if (point.isPresent()) {
            Fish fish = new Fish(FISH_ID_PREFIX + getId(), point.get(), imageStore.getImageList(WorldModel.FISH_KEY),
                    getActionPeriod());

            world.addEntity(fish);
            fish.scheduleActivity(scheduler, world, imageStore);

        }

        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), getActionPeriod());
    }
}


