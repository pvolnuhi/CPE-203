import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap //look over and edit tomorrow!!!!
{
   public static List<String> highEnrollmentStudents(
      Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List <String> overEnrolledStudents = new LinkedList<>(); // list of students names with overenrolled units

      for(String nameOfStudent: courseListsByStudentName.keySet()){
         int units = 0;
         List<Course> courses = courseListsByStudentName.get(nameOfStudent); //get's course name associated with student's name
         for (Course course : courses) { //goes through course names
         units += course.getNumUnits(); //gets number of units from each course name
         }
         if (units > unitThreshold) { //units > unitThreshold
         overEnrolledStudents.add(nameOfStudent); //adds to the list of overenrolled students
         } 
      }

      return overEnrolledStudents;      
   }
}

      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */

