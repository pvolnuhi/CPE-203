import org.junit.Test;
import java.io.*;
import java.lang.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

    public class TestCases
    {
        public static final double DELTA = 0.00001;
        public static int score =0;
        private Object FileUtils;

        @Test
        public void getCourseAverage()
        {
            JavaCourse j = new JavaCourse("Humer");
            j.add(new Nerd("xx",100.0,20));
            j.add(new Smart("xx",10.0));
            j.add(new Clueless("xx",0.0));
            j.add(new Nerd("xx",33.0,100));
            j.add(new Nerd("xx",72.0,50));
            j.add(new Nerd("xx",98.0,66));
            assertEquals("populated arraylist",52.166666666666664,j.getCourseAverage(),DELTA);
        }


        @Test
        public void testToString()
        {
            JavaCourse j = new JavaCourse("Humer");
            j.add(new Nerd("xx",100.0,20));
            j.add(new Smart("Stefan",10.0));
            j.add(new Clueless("Someone",0.0));
            j.add(new Nerd("Sepp",33.0,100));
            j.add(new Nerd("Steffi",72.0,50));
            j.add(new Nerd("Elke",98.0,66));
           assertEquals("to string for 6 student in array","Humer (Course Average: 52.166666666666664 %): xx Stefan Someone Sepp Steffi Elke",j.toString());

        }

        @Test
        public void cutNerds() throws IllegalAccessException, IllegalArgumentException
        {
            JavaCourse j = new JavaCourse("Humer");
            j.add(new Nerd("xx",100.0,20));
            j.add(new Smart("Nicole",10.0));
            j.add(new Clueless("Nobody",0.0));
            j.add(new Nerd("Sepp",33.0,100));
            j.add(new Nerd("Steffi",72.0,50));
            j.add(new Nerd("Elke",98.0,66));
            j.cutNerds(50);//all nerds left
            Class<?> jClass = j.getClass();
            //Print all fields and values
            Field fields[]=jClass.getDeclaredFields();

            for(Field f : fields)
            {
                if(f.getName().equals("students"))
                {
                    f.setAccessible(true);
                    ArrayList<Student> s = (ArrayList<Student>)f.get(j);

                    assertEquals("Cut nerds with pp less than 50",s.size(),5);
                }

            }
        }

        @Test
        public void equals()
        {
            Nerd n= new Nerd("xx",100.0,20);
            Nerd m= new Nerd("xx",100.0,20);
            assertTrue("Equals, same everything",n.equals(m));

        }

        @Test
        public void test_newClass_Star() throws NoSuchMethodException
        {
            final List<String> expectedMethodNames = Arrays.asList(
                    "JavaCourseScore",
                    "name"
            );

            final List<Class> expectedMethodReturns = Arrays.asList(
                    double.class,
                    String.class
            );

            final List<Class[]> expectedMethodParameters = Arrays.asList(
                    new Class[0] ,
                    new Class[0]
            );

            //Comment this part in after  you have created the Star class!!!

            assertTrue("Uncomment part below after you wrote the Star Class!!",false);
//            verifyImplSpecifics(Star.class, expectedMethodNames,
//                    expectedMethodReturns, expectedMethodParameters);
//
//            Student s = new Star("java4ever");
//            assertEquals("a start should have a default JavaCourseScore of 100.0",s.JavaCourseScore(),100.0,DELTA);

        }

        ///////////////////////////
        private static void verifyImplSpecifics(
                final Class<?> clazz,
                final List<String> expectedMethodNames,
                final List<Class> expectedMethodReturns,
                final List<Class[]> expectedMethodParameters)
                throws NoSuchMethodException
        {
//      //keine public fields
//      assertEquals("Unexpected number of public fields",
//         0, clazz.getFields().length);

            final List<Method> publicMethods = Arrays.stream(
                    clazz.getDeclaredMethods())
                    .filter(m -> Modifier.isPublic(m.getModifiers()))
                    .collect(Collectors.toList());

            final List<Method> privateMethods = Arrays.stream(
                    clazz.getDeclaredMethods())
                    .filter(m -> Modifier.isPrivate(m.getModifiers()))
                    .collect(Collectors.toList());


            List<String> methodNamesPublic= new LinkedList<String>();
            List<String> methodNamesPrivate= new LinkedList<String>();

            for(Method m: publicMethods)
            {
                methodNamesPublic.add(m.getName());
            }

            for(Method m: privateMethods)
            {
                methodNamesPrivate.add(m.getName());
            }

            for(String s: expectedMethodNames)
            {
                assertTrue(methodNamesPublic.contains(s) || methodNamesPrivate.contains(s));
            }

//      assertTrue("Unexpected number of public methods",
//         expectedMethodNames.size()+1 >= publicMethods.size());

            assertTrue("Invalid test configuration",
                    expectedMethodNames.size() == expectedMethodReturns.size());
            assertTrue("Invalid test configuration",
                    expectedMethodNames.size() == expectedMethodParameters.size());


            for (int i = 0; i < expectedMethodNames.size(); i++)
            {
                Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                        expectedMethodParameters.get(i));

                assertEquals(expectedMethodReturns.get(i), method.getReturnType());
            }
        }
    }

