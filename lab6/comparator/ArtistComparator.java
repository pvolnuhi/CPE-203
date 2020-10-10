import java.util.Comparator;

class ArtistComparator implements Comparator<Song> {
	public int compare(Song one, Song two) //compare() --> two objects
	{
		return one.getArtist().compareTo(two.getArtist()); //comparing two string, and returning values (-1, 0, 1);
	}
}