import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   public boolean Compare(Object stud, Object other)
   {
      return (stud == other || stud != null && other != null && stud.equals(other));
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
         return false; //flase
      }

      Student stud = (Student)other;
      return Compare(surname, stud.surname) && Compare(givenName, stud.givenName) && age == stud.age && Compare(currentCourses, stud.currentCourses); 
   }

   @Override
   public int hashCode()
   {
      return surname.hashCode() + givenName.hashCode() + age + currentCourses.hashCode(); //adding up to create on unique hash value
   }
}
