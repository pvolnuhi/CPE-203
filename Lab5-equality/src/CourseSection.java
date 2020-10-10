import java.time.LocalTime;
import java.lang.Object;
import java.util.Objects;

class CourseSection {
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
                        final int enrollment, final LocalTime startTime, final LocalTime endTime) {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

//   public boolean Compare(Object sect, Object other) //helper function
//   {
//      return (sect == other || sect != null && other != null && sect.equals(other));
//   }


   @Override
   public boolean equals(Object other) //Need to do the long way, (apparently short way exists so double check what that looks like)
   {
      if (other == null) { //check for null first
         return false;
      }

      if (this.getClass() != other.getClass()) //Next, check for instanceof, or inheritance
      {
         return false;
      } else {
         CourseSection course = (CourseSection) other;
         boolean results = true;

         if (prefix == null) {
            if (course.prefix != null)
               return false;
         } else {
            results = prefix.equals(course.prefix);
         }
         if (number == null) {
            if (course.number != null)
               return false;
         } else {
            results = number.equals(course.number);
         }
         if (startTime == null) {
            if (course.startTime != null)
               return false;
         } else {
            results = startTime.equals(course.startTime);
         }
         if (endTime == null) {
            if (course.endTime != null)
               return false;
         } else {
            results = endTime.equals(course.endTime);
         }
         if (enrollment == course.enrollment) {
            return results;
         } else{
            return false;
         }
      }
   }

      //return Objects.equals(prefix, course.prefix)
//                 && Objects.equals(number, course.number)
//                 && enrollment == course.enrollment
//                 && Objects.equals(startTime, course.startTime)
//                 && Objects.equals(endTime, course.endTime);
//      }


   @Override
   public int hashCode() //how to do this the long way/calculations???    Objects.hash()????
   {
      int sum = 0;
      if(prefix != null) {
         sum += prefix.hashCode();
      }
      if(number != null){
         sum += number.hashCode();
      }
      if(startTime != null){
         sum += startTime.hashCode();
      }
      if(endTime != null){
         sum += endTime.hashCode();
      }
      else{
         return 0;
      }
      return sum + enrollment;
      //return prefix.hashCode() + number.hashCode() + enrollment + startTime.hashCode() + endTime.hashCode();

   } //sum = 0;
   //if prefix != null
   // sum += prefix.hasCode();
   //if number != null;
   //....
   // additional likely methods not defined since they are not needed for testing
}
