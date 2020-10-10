import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{

   @Test
   public void test_professorAvailable()
   {

      Map<String, List<Section>> sectionsListByCourseMap = new HashMap<>();

      sectionsListByCourseMap.put("cpe101",
              Arrays.asList(
                      new Section("Humer", 34, 27, "01"),
                      new Section("Einakian", 34, 34, "03"),
                      new Section("Einakian", 34, 32, "05"),
                      new Section("Kauffman", 34, 34, "07"),
                      new Section("Smith", 34, 34, "09"),
                      new Section("Workman", 34, 34, "11"),
                      new Section("Kauffman", 34, 34, "13"),
                      new Section("Einakian", 34, 28, "15"),
                      new Section("Workman", 34, 24, "17"),
                      new Section("Kauffman", 34, 18, "19"),
                      new Section("Humer", 34, 16, "21"),
                      new Section("Humer", 34, 0, "23"),
                      new Section("Mork", 34, 10, "25"),
                      new Section("Hatalsky", 34, 6, "27"),
                      new Section("Hatalsky", 34, 5, "29")));

      sectionsListByCourseMap.put("cpe203",
              Arrays.asList(
                      new Section("Wood", 36, 36, "01"),
                      new Section("Einakian", 32, 31, "03"),
                      new Section("Mork", 30, 30, "05"),
                      new Section("Mork", 36, 34, "07"),
                      new Section("Humer", 32, 32, "09"),
                      new Section("Workman", 30, 28, "11"),
                      new Section("Einakian", 36, 36, "13")));


      Map<String, List<String>> coursesListByStudentMap = new HashMap<>();
      coursesListByStudentMap.put("Marie",
              Arrays.asList("cpe101","cpe202","cpe203","csc225","csc357"));
      coursesListByStudentMap.put("Steven",
              Arrays.asList("cpe101","cpe202","cpe203","csc225","csc357"));
      coursesListByStudentMap.put("Mila",
              Arrays.asList("phys141","cpe101"));

      assertTrue(MyPass.professorAvailable(coursesListByStudentMap,sectionsListByCourseMap,"Marie","Workman"));
      assertFalse(MyPass.professorAvailable(coursesListByStudentMap,sectionsListByCourseMap,"Marie","Wood"));
      assertFalse(MyPass.professorAvailable(coursesListByStudentMap,sectionsListByCourseMap,"Mila","Kurfess"));

   }
}
