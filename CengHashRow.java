public class CengHashRow {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public String hashPrefix()
	{
		// TODO: Return row's hash prefix (such as 0, 01, 010, ...)
		return prefix;
	}

	public void sethashPrefix(String pre)
	{
		// TODO: Return row's hash prefix (such as 0, 01, 010, ...)
		prefix = pre;
	}
	
	public CengBucket getBucket()
	{
		// TODO: Return the bucket that the row points at.
		return bucket;
	}
	
	public boolean isVisited()
	{
		// TODO: Return whether the row is used while searching.
		return false;		
	}



	// Own Methods
	private String prefix;
	private CengBucket bucket;
	CengHashRow(String prefix,int localdepth){
		this.prefix = prefix;
		this.bucket =  new CengBucket(localdepth);
	}
	CengHashRow(){
		this.prefix = "";
		this.bucket =  new CengBucket(prefix.length());
	}
	CengHashRow(String prefix, CengBucket new_bucket){
		this.prefix = prefix;
		this.bucket =  new_bucket;
	}

}
