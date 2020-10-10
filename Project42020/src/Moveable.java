import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Moveable extends AnimatedEntity implements Move {

    private PathingStrategy strategy = new AStarPathingStrategy();

    public Moveable(Point position, List<PImage> images,String id, int actionPeriod, int animationPeriod) {
        super(position,images,id,actionPeriod,animationPeriod);
    }

    public Point nextPosition( WorldModel world, Point destPos) {
        List<Point> points;

        points = strategy.computePath(getposition(), destPos,
                p -> world.withinBounds(p) && !world.isOccupied(p),
                //grid[p.y][p.x] != PathingMain.GridValues.OBSTACLE,
                (p1, p2) -> neighbors(p1, p2),
                PathingStrategy.CARDINAL_NEIGHBORS);

        if (points.size() == 0)
        {
            //System.out.println("No path found");
            return getposition();
        }

        return points.get(0);

    }


    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }

    public boolean moveTo( WorldModel world, Entitys target, EventScheduler scheduler){
        List<Point> points;

        if (getposition().adjacent( target.getposition())){
            world.removeEntity( target);
            if(this instanceof OctoNotFull){
                ((OctoNotFull)this).setResourceCount(((OctoNotFull)this).getResourceCount() + 1);
            }
            scheduler.unscheduleAllEvents( target);
            return true;
        }else{

            Point nextPos = nextPosition( world, target.getposition());

            if (!getposition().equals(nextPos)){
                Optional<Entitys> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()){
                    scheduler.unscheduleAllEvents( occupant.get());
                }


                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    public void setposition(Point position){
        this.position = position;
    }

}