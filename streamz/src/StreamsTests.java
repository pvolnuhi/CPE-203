import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StreamsTests
{
    public static CsCohort CalPoly2019 = new CsCohort(2019,200,0.20);
    public static CsCohort CalPoly2018 = new CsCohort(2018,2000,0.55);
    public static CsCohort CalPoly2017 = new CsCohort(2017,700,0.90);
    public static CsCohort CalPoly2016 = new CsCohort(2016,20,0.70);
    public static CsCohort CalPoly2015 = new CsCohort(2015,500,0.30);
    public static CsCohort CalPoly2014 = new CsCohort(2014,800,1);
    public static CsCohort CalPoly2013 = new CsCohort(2013,1200,0.30);

    public static CsCohort CSUMB2019 = new CsCohort(2019,200,0.60);
    public static CsCohort CSUMB2018 = new CsCohort(2018,2000,0.75);
    public static CsCohort CSUMB2017 = new CsCohort(2017,701,0.10);
    public static CsCohort CSUMB2016 = new CsCohort(2016,2000,0.80);
    public static CsCohort CSUMB2015 = new CsCohort(2015,5000,0.50);
    public static CsCohort CSUMB2014 = new CsCohort(2014,80,0.30);
    public static CsCohort CSUMB2013 = new CsCohort(2013,120,1);

    public static List<CsCohort> single= new ArrayList<>();
    public static List<CsCohort> csumb= new ArrayList<>();
    public static List<CsCohort> empty    =new ArrayList<>();
    public static  List<CsCohort> all = new ArrayList<>();
    public static  List<CsCohort> calpoly= new ArrayList<>();

    public static final double DELTA = 0.00001;


    public static void setup(){
        calpoly.clear();
        csumb.clear();
        single.clear();

        calpoly.add(CalPoly2019);
        calpoly.add(CalPoly2018);
        calpoly.add(CalPoly2017);
        calpoly.add(CalPoly2016);
        calpoly.add(CalPoly2015);
        calpoly.add(CalPoly2014);
        calpoly.add(CalPoly2013);
        csumb.add(CSUMB2013);
        csumb.add(CSUMB2014);
        csumb.add(CSUMB2017);
        csumb.add(CSUMB2016);
        csumb.add(CSUMB2018);
        csumb.add(CSUMB2019);
        csumb.add(CSUMB2015);
        single.add(CalPoly2019);
    }


    @Test(timeout=1000)
    public void test_yearsAboveTarget()
    {
        setup();
        List<Integer> test= new ArrayList<>();
        test = ProblemStreams.yearsAboveTarget(csumb,1000);
        List<Integer> expectedList = Arrays.asList(2015,2016,2018);
        assertEquals(test,expectedList);
    }





}
