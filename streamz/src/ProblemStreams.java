import java.util.*;
//import java.Streams.*;
//import java.Collections.*;
import java.util.stream.Collectors;

public class ProblemStreams {

    /*
    IMPORTANT:
    Your solutions for this problem MUST use Streams. You may not write a loop in your code.
    */

    /*
    TO DO (10 points):
    Complete the following method. Given a list of CsCohort, passed as a parameter to the function,
    use Streams to return a list of the years where at least the specified target number of students were retained,
    that is ordered in ascending order.
    */

    public static List<Integer> yearsAboveTarget(List<CsCohort> theCohorts, int targetNumStudents) {
        List<Integer> result = theCohorts.stream().filter(m ->m.retained() >= targetNumStudents).
        mapToInt(m->m.getYear()).distinct().sorted().boxed().collect(Collectors.toList());
        return result;

    }
}



























