import java.lang.Object;
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
       if(other == null){
           return false; //first check if null
       }
       if(this.getClass() != other.getClass())
       {
           return false; //next check if instanceof or if inheritance applies
       }
       else{
           Student stud = (Student)other;
           return Objects.equals(surname, stud.surname)
                 && Objects.equals(givenName, stud.givenName)
                 && age == stud.age
                 && Objects.equals(currentCourses, stud.currentCourses);

       }
   }

   @Override
    public int hashCode(){
       //return surname.hashCode() + givenName.hashCode() + age + currentCourses.hashCode();
       return Objects.hash(surname, givenName, age, currentCourses); //handles null pointer exceptions
   }
}
