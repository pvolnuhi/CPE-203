import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      Comparator<Song> mysongs = new ArtistComparator();
      assertEquals(mysongs.compare(songs[0], songs[1]), -14); //comes before DIFFERENCE MATTERS

      Comparator<Song> mysongs1 = new ArtistComparator();
      assertEquals(mysongs1.compare(songs[0], songs[2]), 3); //comes after

      // Comparator<Song> mysongs2 = new ArtistComparator();
      // assertEquals(mysongs2.compare(songs[3], songs[7]), 0);



   }

   @Test
   public void testLambdaTitleComparator() //compares by first letter?
   {
      Comparator<Song> titleCompLambda = (Song one, Song two)->{return one.getTitle().compareTo(two.getTitle());}; //CHECK

      assertEquals(titleCompLambda.compare(songs[0], songs[1]), 8); //comes 

      assertEquals(titleCompLambda.compare(songs[3], songs[2]), -18); //comes before

      assertEquals(titleCompLambda.compare(songs[3], songs[5]), 0); //same


   }

   @Test
   public void testYearExtractorComparator() //CHECK
   {
    
      Song[] origList = new Song[] {songs[4], songs[2], songs[0], songs[1], songs[3], songs[5], songs[7], songs[6]};
      Song[] sortedList = songs.clone();
      Arrays.sort(sortedList, Comparator.comparingInt(Song::getYear).reversed());

      for (int i=0; i < sortedList.length; i++) {
         assertEquals(sortedList[i], origList[i]);
      }
   }



   @Test
   public void testComposedComparator()
   {
      Comparator<Song> comp1 = Comparator.comparing(Song :: getArtist);
      Comparator<Song> comp2 = Comparator.comparing(Song :: getYear);
      Comparator<Song> compComparator = new ComposedComparator(comp1, comp2);


      assertEquals(compComparator.compare(songs[3], songs[7]), 1); //first thing > second thing 
      assertEquals(compComparator.compare(songs[7], songs[1]), -11); 


   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> comp1 = Comparator.comparing(Song :: getTitle);
      Comparator<Song> thenCompare = comp1.thenComparing(Comparator.comparing(Song::getArtist));

      assertEquals(thenCompare.compare(songs[3],songs[5]), 1);
      //assertEquals(thenCompare.compare(songs[1],songs[5]), 1);
      assertEquals(thenCompare.compare(songs[2],songs[3]), 18);
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

         //pass comparator here;
      Comparator<Song> artistComp = new ArtistComparator();
      Comparator<Song> titleComp = (Song one, Song two) ->{return one.getTitle().compareTo(two.getTitle());};
      songList.sort(artistComp.thenComparing(titleComp.thenComparing(Comparator.comparingInt(Song::getYear))));
      //CHECK


      assertEquals(songList, expectedList);

      assertEquals(songList, expectedList);
   }
}