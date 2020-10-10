import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public boolean Compare(Object sect, Object other) //helper function
   {
      return (sect == other || sect != null && other != null && sect.equals(other));
   }

   @Override
   public boolean equals(Object other)
   {
      if (other == null)
      {
         return false;
      }
      if (other.getClass() != getClass())
      {
         return false; //false
      }
      CourseSection sect = (CourseSection)other;
      return Compare(prefix, sect.prefix) && Compare(number, sect.number) && enrollment == sect.enrollment 
      && Compare(startTime, sect.startTime) && Compare(endTime, sect.endTime); 
   }

   @Override
   public int hashCode()
   {
      return prefix.hashCode() + number.hashCode() + enrollment + startTime.hashCode() + endTime.hashCode();
   }
   //additional likely methods not defined since they are not needed for testing
}
