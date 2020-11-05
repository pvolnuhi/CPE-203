import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Patrick extends Moveable {


    public static final Random rand1 = new Random();
    private List<Point> path;
    public static final String ROCK_KEY = "rock";


    public Patrick(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, List<Point> path) {
        super(position, images, id, actionPeriod, animationPeriod);
        this.path = path;

    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Optional<Entitys> sgrass = world.findNearest(this.position, Sgrass.class);
        if (!sgrass.isPresent() || !moveTo(world, sgrass.get(), scheduler)) {
            if (sgrass.isPresent() && this.position.adjacent(sgrass.get().getposition())) {
                Rock temp = new Rock(ROCK_KEY + rand1.nextInt(), sgrass.get().getposition(), imageStore.getImageList(ROCK_KEY));
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
                world.removeEntity(sgrass.get());
                world.addEntity(temp);

            }

        }
        scheduler.scheduleEvent((Entitys) this, (Action) (this.createActivityAction(world, imageStore)), (long) this.actionPeriod);
    }
}







