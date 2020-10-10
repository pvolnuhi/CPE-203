import java.util.ArrayList;

public class JavaCourse {
    private String ProfName;
    private ArrayList<Student> students;

    /* TO DO
    Initialize the ProfName and the students list to an empty ArrayList
     */
    public JavaCourse(String ProfName)
    {

    }
    public void add(Student s)
    {
        students.add(s);
    }

    /* TO DO
   Return the average JavaCourseScore of all students in the course.
    */

    public double getCourseAverage()
    {
    return 0.0;
    }

    /* TO DO
    Return the String containing the ProfName, average grade of all students in the course,
    and a listing of all students. Follow the example format WITHOUT the quotation marks but all spaces as shown:
    "Humer (Course Average: 100.0 %): Stefan Nicole Sepp"
     */
    public String toString()
    {
    return null;
    }

    /* TO DO
    Add a method to "cut" all the Nerds from the students list who have a value of ParticipationPoint less
    than the given threshold. (Remove them from the list of students)
     */
    public void cutNerds(int threshold)
    {



    }
}
