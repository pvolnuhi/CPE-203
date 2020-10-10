import java.util.Comparator;

class ComposedComparator implements Comparator<Song> {
	private Comparator<Song> c1;
	private Comparator<Song> c2;

	public ComposedComparator(Comparator<Song> c1, Comparator<Song> c2 )
	{
		this.c1 = c1; //primary key
		this.c2 = c2; //secondary key 
	}

	public int compare(Song one, Song two)
	{
		int primaryKey = c1.compare(one, two);
		int secondaryKey = c2.compare(one, two);

		if(primaryKey == 0)//if equiv by ordering of c1, use c2
		{
			return secondaryKey;
		}
		else
		{
			return primaryKey;
		}
	}

	
}