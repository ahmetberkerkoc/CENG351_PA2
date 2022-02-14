import java.util.ArrayList;


public class CengBucket {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int pokeCount()
	{
		// TODO: Return the pokemon count in the bucket.
		return counter;
	}
	
	public CengPoke pokeAtIndex(int index)
	{
		// TODO: Return the corresponding pokemon at the index.
		return pokemons[index];
	}
	
	public int getHashPrefix()
	{
		// TODO: Return hash prefix length.
		return localDepth;
	}
	
	public Boolean isVisited()
	{
		// TODO: Return whether the bucket is found while searching.
		return false;		
	}



	// Own Methods
	public int localDepth;
	public int counter;
	public CengPoke[] pokemons;
	CengBucket(int localDepth)
	{
		this.counter=0;
		this.localDepth = localDepth;
		this.pokemons = new CengPoke[CengPokeKeeper.getBucketSize()];
	}

}
