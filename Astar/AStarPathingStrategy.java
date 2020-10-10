import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        start.Gval(0);
        start.Hval(start.distanceSquared(end));
        start.Hval(0);


        //start.Gval(0);

        List<Point> closedpath = new LinkedList<>();
        //return path;
        Queue<Point> openpath = new PriorityQueue<>(Comparator.comparingDouble(Point::getHeuristic));
        //return closedpath;
        openpath.add(start);

        //check while open list isn't empty
        while (!openpath.isEmpty()) {
            Point curr = openpath.remove();
            closedpath.add(curr);

            //checking to see if got to ending of points
            if (withinReach.test(curr, end)) {
                List<Point> newPoints = new ArrayList<>();
                Point traceOver = curr;

                while (traceOver != start) {
                    newPoints.add(traceOver);
                    traceOver = traceOver.getPrev();
                }
                Collections.reverse(newPoints);
                return newPoints;
            }


            List<Point> validNodes = potentialNeighbors.apply(curr)
                    .filter(validNode -> canPassThrough.test(validNode))
                    .filter(validNode -> !closedpath.contains(validNode))
                    .collect(Collectors.toList());


            for (Point neighbor : validNodes) {

                double GNeighbor = curr.getGval() + 1;
                //+ neighbor.distanceSquared(curr);
                double HNeighbor = neighbor.distanceSquared(end);
                double newH = GNeighbor + HNeighbor;

                if (newH < neighbor.getHeuristic()) {
                    neighbor.Gval(GNeighbor);
                    neighbor.Hval(HNeighbor);
                    neighbor.setPrev(curr);
                }
                neighbor.Gval(GNeighbor);
                neighbor.Hval(HNeighbor);
                neighbor.setPrev(curr);


                if (!openpath.contains(neighbor)) {
                    openpath.add(neighbor);
                }

            }
        }
        return new ArrayList<>();
    }

}