import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.*;


public class streamPoint{

    private static final String outputFileName = "drawMe.txt";

    public static void writeToFile(String fileName) {

        try (Stream<String> inputFileStream = Files.lines(Paths.get(fileName))) {
            BufferedWriter fileOutputBuffer = new BufferedWriter(new FileWriter(outputFileName));

            String[] result = inputFileStream
                    .filter((x) -> Double.parseDouble(x.split(",")[x.split(",").length - 1]) <= 2.0)
                    .map((x) -> Arrays.stream(x.split(","))
                            .map((y) -> Double.toString(Double.parseDouble(y) * 0.5))
                            .collect(Collectors.joining(", ")))
                    .map((x) -> translatePt(x))
                    .toArray(String[]::new);

            for (String line: result) {
                fileOutputBuffer.write(line + "\n");
                System.out.println(line);
            }

            fileOutputBuffer.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open " + fileName);
        } catch (IOException ex) {
            System.out.println("Unable to read " + fileName);
        }
    }

    private static String translatePt(String _input) {
        String[] result = _input.split(", ");
        result[0] = Double.toString(Double.parseDouble(result[0]) - 150.0);
        result[1] = Double.toString(Double.parseDouble(result[1]) - 37.0);
        return result[0] + ", " + result[1] + ", " + result[2];
    }

}

//
//
//import java.io.File;
//import java.io.FileNotFoundException;
////import java.util.HashMap;
////import java.util.LinkedList;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
////import java.util.Map;
//import java.util.Scanner;
//import java.lang.String;
//import java.util.stream.*;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.stream.*;
//
//
//
//public class streamPoint {
//    private static FileWriter fileWriter;
//
//    //private static final String outputFileName = "drawMe.txt";
//
//    //FileWriter fileWriter;
//
//    private static List<Point> points;
//    public String fileName; //private
//
//
//    public List<Point> getPoints() {
//        return points;
//    }
//
//    public static void processLine(String line, List<Point> points) {
//
//        final String[] words = line.split(",\\s");
//
//        if (words.length == 0) {
//            return;
//        }
//
//        points.add(new Point(words[0], words[1], words[2]));
//    }
//
//    public static void processFile(Scanner input, List<Point> points) {
//        while (input.hasNextLine()) {
//            processLine(input.nextLine(), points);
//        }
//    }
//
//    private static void populateDataStructures(String filename, List<Point> points)
//            throws FileNotFoundException {
//        try (Scanner input = new Scanner(new File(filename))) {
//            processFile(input, points);
//            /* add arguments as needed */
//        }
//    }
//
//    private static String getFilename(String[] args) {
//        if (args.length < 1) {
//            System.err.println("Log file not specified.");
//            System.exit(1);
//        }
//
//        return args[0];
//    }
//
//    public void openfileWriter() {
//        try {
//            fileWriter = new FileWriter((new File("drawMe.txt")));
//        } catch (Exception e) {
//
//            System.out.println("can't open file");
//
//        }
//    }
//
//
////    public static void writeToFile(String fileName) {
////
////        try {
////
////
////            fileWriter = new FileWriter((new File("drawMe.txt")));
////
////            points = points.stream()
////                    .filter(p -> p.getZ() > 2.0)   //Remove all points that have a z value > 2.0.
////                    .map(p -> p.scale(0.5)) //Scale down all the points by 0.5
////                    .map(p -> p.translate(new Point(-150, -37, 0)))  //Translate all the points by {-150, -37}
////                    .collect(Collectors.toList()); //collect
////
////
////            for (int i = 0; i < points.size(); i++) {
////                //just going to write to a file
////                fileWriter.write(String.format("%s, ", Double.toString(points.get(i).getX())));
////                fileWriter.write(String.format("%s, ", Double.toString(points.get(i).getY())));
////                fileWriter.write(String.format("%s%n", Double.toString(points.get(i).getZ())));
////            }
////
////            fileWriter.close();
////        } catch (IOException ex) {
////
////            System.out.println("can't write to open file");
////
////        }
////
////
////    }
////}
////
//////    public static void main(String[] args)
//////    {
//////        List<Point> points = new ArrayList<Point>(); //maybe?
//////
//////        //String fileName;
//////        String filename = getFilename(args);
//////
//////        try
//////        {
//////            //String fileName = "positions.txt";
//////            populateDataStructures(filename, points);
//////            writeToFile(filename);
//////            /* add parameters as needed */
//////        }
//////        catch (FileNotFoundException e)
//////        {
//////            System.err.println(e.getMessage());
//////        }
//////
//////    }
////}