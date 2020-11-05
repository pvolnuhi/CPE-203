import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;


class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        start.Gval(0);
        start.Hval(start.distanceSquared(end));
        start.Hval(0);

        List<Point> closedpath = new LinkedList<>();
        Queue<Point> openpath = new PriorityQueue<>(Comparator.comparingDouble(Point::getHeuristic));
        openpath.add(start);
        while (!openpath.isEmpty()) {
            Point curr = openpath.remove();
            closedpath.add(curr);

            if (withinReach.test(curr, end)) {
                List<Point> newPoints = new ArrayList<>();
                Point traceOver = curr;   //end points

                while (traceOver != start) { // we reach to end points before getting here
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