import java.util.List;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class Spongebob extends Moveable implements PathingStrategy{

    public static final Random rand1 = new Random();


    private PathingStrategy strategy = new AStarPathingStrategy();
    public static final String PATTY_KEY = "patty";


    public Spongebob(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, List<Point> pathing) {
        super(position, images, id, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        Optional<Entitys> OctoNF = world.findNearest(this.position, OctoFull.class);

        if (!OctoNF.isPresent() || !moveTo(world, OctoNF.get(), scheduler)) {
            if (OctoNF.isPresent() && this.position.adjacent(OctoNF.get().getposition())) {
                Patty temp = new Patty(PATTY_KEY + rand1.nextInt(), this.position, imageStore.getImageList(PATTY_KEY), 500);


                world.removeEntity(this);
                world.removeEntity(OctoNF.get());

                scheduler.unscheduleAllEvents(this);

                world.addEntity(temp);
                scheduler.scheduleEvent(temp, temp.createActivityAction(world, imageStore), temp.actionPeriod);


            }
            scheduler.scheduleEvent((Entitys) this, (Action) (this.createActivityAction(world, imageStore)), (long) this.actionPeriod);
        }

    }

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point,
            Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        return potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(end)
                                && Math.abs(end.x - pt.x) <= Math.abs(end.x - start.x)
                                && Math.abs(end.y - pt.y) <= Math.abs(end.y - start.y))
                .limit(1)
                .collect(Collectors.toList());
    }
}
