import java.io.PrintStream;
import java.util.ArrayList;

public class CengHashTable {

	private int globaldept;
	private int bucketsize;
	private ArrayList<CengHashRow> row;

	public CengHashTable()
	{
		this.globaldept  = 0;
		this.bucketsize = CengPokeKeeper.getBucketSize();
		this.row = new ArrayList<CengHashRow>();
		row.add(new CengHashRow());
		// TODO: Create a hash table with only 1 row.
	}

	public void deletePoke(Integer pokeKey)
	{
		int hashmod = CengPokeKeeper.getHashMod();
		int hasValue;
		int index;
		if (globaldept==0)
			index = 0;
		else{
			hasValue= pokeKey % hashmod;
			index = hashing(hasValue,hashmod);
		}
		int poke_count = row.get(index).getBucket().pokeCount();
		row.get(index).getBucket().counter--;
		int be=0;
		for (int i=0;i<poke_count-1;i++){
			if (row.get(index).getBucket().pokemons[i].pokeKey().equals(pokeKey)){
				be=1;
			}
			if (be==1){
				CengPoke temp = row.get(index).getBucket().pokemons[i];
				row.get(index).getBucket().pokemons[i] = row.get(index).getBucket().pokemons[i+1];
				row.get(index).getBucket().pokemons[i+1] = row.get(index).getBucket().pokemons[i];
			}
		}
		//row.get(index).getBucket().pokemons[poke_count] = poke; //del işlemi var mı??
		double zero=0;
		for(int i=0;i<row.size();i++){
			if(row.get(i).getBucket().counter==0){
				zero = zero + (double)1/(Math.pow(2,globaldept-row.get(i).getBucket().localDepth));
			}

		}
		try{
			PrintStream myoutstream;
			myoutstream = new PrintStream(System.out,true,"UTF-8");
			myoutstream.println("\"delete\": {");
			myoutstream.println("\t\"emptyBucketNum\": "+(int)zero);
			myoutstream.println("}");
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		// TODO: Empty Implementation
	}

	public void addPoke(CengPoke poke)
	{
		Integer key  = poke.pokeKey();
		int hashmod = CengPokeKeeper.getHashMod();
		int index;
		int hasValue;

		if (globaldept==0)
			index = 0;
		else{
			hasValue= key % hashmod;
			index = hashing(hasValue,hashmod);
		}

		int poke_count = row.get(index).getBucket().pokeCount();
		if (poke_count<bucketsize){
			row.get(index).getBucket().pokemons[poke_count] = poke;
			row.get(index).getBucket().counter++;
		}
		else {
			Split(index,poke);
			//addPoke(poke);
		}
		// TODO: Empty Implementation
	}

	public void searchPoke(Integer pokeKey)
	{
		try {
			PrintStream myoutstream;
			myoutstream = new PrintStream(System.out, true, "UTF-8");
			int hashmod = CengPokeKeeper.getHashMod();
			int hasValue;
			ArrayList<Integer> index_counter = new ArrayList<Integer>();
			int localdept = -1;
			String pre;
			String search_pre;
			int index;
			boolean there = false;
			int i;
			int poke_count;
			if (globaldept == 0) {
				poke_count = row.get(0).getBucket().pokeCount();
				for (i = 0; i < poke_count; i++) {
					if (pokeKey.equals(row.get(0).getBucket().pokemons[i].pokeKey())) {
						there = true;
						break;
					}
				}
				if (there == true) {
					myoutstream.println("\"search\": {");
					print_row(row.get(0), row.get(0).hashPrefix());
					myoutstream.println("}");
				} else {
					myoutstream.println("\"search\": {");
					myoutstream.println("}");
				}

			} else {
				hasValue = pokeKey % hashmod;
				index = hashing(hasValue, hashmod);
				poke_count = row.get(index).getBucket().pokeCount();
				for (i = 0; i < poke_count; i++) {
					if (pokeKey.equals(row.get(index).getBucket().pokemons[i].pokeKey())) {
						there = true;
						break;

					}
				}
				if (there == true) {
					localdept = row.get(index).getBucket().localDepth;
					pre = String.valueOf(row.get(index).hashPrefix());
					search_pre = pre.substring(0, localdept);

					int num_row = row.size();
					String row_pre;
					for (i = 0; i < num_row; i++) {
						row_pre = row.get(i).hashPrefix();
						row_pre = row_pre.substring(0, localdept);
						if (localdept == row.get(i).getBucket().localDepth && row_pre.equals(search_pre)) {
							index_counter.add(i);
						} else
							continue;
					}
					myoutstream.println("\"search\": {");
					for (i = 0; i < index_counter.size(); i++) {

						print_row(row.get(index_counter.get(i)), row.get(index_counter.get(i)).hashPrefix());
						if (i < index_counter.size() - 1)
							myoutstream.print(",");
						myoutstream.print("\n");
					}
					myoutstream.println("}");
				} else {
					myoutstream.println("\"search\": {");
					myoutstream.println("}");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		// TODO: Empty Implementation
	}

	public void print()
	{
		try{
		PrintStream myoutstream;
		myoutstream = new PrintStream(System.out,true,"UTF-8");

			myoutstream.println("\"table\": {");
			for(int i=0;i<row.size();i++){
				print_row(row.get(i),row.get(i).hashPrefix());
				if(i<row.size()-1)
					myoutstream.print(",");

				myoutstream.print("\n");
			}
			myoutstream.println("}");
		}

		catch (Exception e) {
		e.printStackTrace();
	}
		// TODO: Empty Implementation
	}


	// GUI-Based Methods
	// These methods are required by GUI to work properly.

	public int prefixBitCount()
	{
		// TODO: Return table's hash prefix length.
		return 0;
	}

	public int rowCount()
	{
		// TODO: Return the count of HashRows in table.
		return 0;
	}

	public CengHashRow rowAtIndex(int index)
	{
		// TODO: Return corresponding hashRow at index.
		return null;
	}

	// Own Methods
	private int hashing(int value,int hashmod){



		int digit=(int)(Math.log(hashmod)/Math.log(2));
		String binary_val = Integer.toBinaryString((1 << digit) | value).substring(1);
		String binary_hash = binary_val.substring(0, globaldept);
		int index =  Integer.parseInt(binary_hash, 2);
		return index;
		//System.out.println(binHash);
		//System.out.println(binary_val);
	}

	private String bin_hashing(Integer pokeKey){
		int hashmod = CengPokeKeeper.getHashMod();
		int value;
		value = pokeKey%hashmod;
		int digit=(int)(Math.log(hashmod)/Math.log(2));
		String binary_val = Integer.toBinaryString((1 << digit) | value).substring(1);
		//String binary_hash = binary_val.substring(0, globaldept);
		return binary_val;
		//System.out.println(binHash);
		//System.out.println(binary_val);
	}

	private int twicerow(int index,int localdept)
	{
		int new_ind=index;
		int num_row = row.size();
		String pre;
		String orijin = String.valueOf(row.get(index).hashPrefix());
		for(int i=0;i<num_row;i++){
			pre= String.valueOf(row.get(i).hashPrefix());
			if(pre.equals("")){
				row.get(index).sethashPrefix("0");
				row.add(new CengHashRow("1",1));
				new_ind=index;
			}
			else{
				if (pre.equals(orijin)){
					row.get(i).sethashPrefix(pre+"0");
					row.add(i+1,new CengHashRow(pre+"1",localdept));
					new_ind=i;
					i=i+1;
					num_row++;
				}
				else{
					row.get(i).sethashPrefix(pre+"0");
					row.add(i+1,new CengHashRow(pre+"1",row.get(i).getBucket()));
					i=i+1;
					num_row++;
				}


				//else{
				//	row.add(new CengHashRow(pre+"1",row.get(i).getBucket()));
				//}
			}
		}

		globaldept++;
		return new_ind;
	}

	private void Split(int index,CengPoke poke)
	{
		int localdepth = ++row.get(index).getBucket().localDepth;

		if (localdepth>globaldept){
			index=twicerow(index,localdepth);
			row.get(index).getBucket().counter=0;
			for (int i=0;i<bucketsize;i++){
				addPoke(row.get(index).getBucket().pokemons[i]);
			}
			addPoke(poke);
		}
		else{
			CengBucket buc = row.get(index).getBucket();
			buc.counter=0;
			String pre = String.valueOf(row.get(index).hashPrefix());
			String p_hash = pre.substring(0,localdepth-1);
			int ber=0;
			for (int i=0;i<row.size();i++){
				String row_hash_origin = String.valueOf(row.get(i).hashPrefix());
				String row_hash = String.valueOf(row.get(i).hashPrefix());
				row_hash = row_hash.substring(0,localdepth-1);
				if (row_hash.equals(p_hash)){
					row_hash = String.valueOf(row.get(i).hashPrefix());
					row_hash = row_hash.substring(0,localdepth);
					if (row_hash.equals(p_hash+"1")){
						if (ber==0) {
							row.set(i, new CengHashRow(row_hash_origin, localdepth));
							ber = i + 1;
						}
						else
							row.set(i,new CengHashRow(row_hash_origin,row.get(ber-1).getBucket()));
					}
					

				}
			}
			
			for (int i=0;i<bucketsize;i++){
				addPoke(buc.pokemons[i]);
			}
			addPoke(poke);
		}


	}


	private void print_pokemon(CengPoke poke,String hash_Val){
		try {
			PrintStream myoutstream;
			myoutstream = new PrintStream(System.out, true, "UTF-8");
			myoutstream.println("\t\t\t\t\"poke\": {");
			myoutstream.println("\t\t\t\t\t\"hash\": " + hash_Val + ",");
			myoutstream.println("\t\t\t\t\t\"pokeKey\": " + poke.pokeKey() + ",");
			myoutstream.println("\t\t\t\t\t\"pokeName\": " + poke.pokeName() + ",");
			myoutstream.println("\t\t\t\t\t\"pokePower\": " + poke.pokePower() + ",");
			myoutstream.println("\t\t\t\t\t\"pokeType\": " + poke.pokeType());
			myoutstream.print("\t\t\t\t}");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void print_bucket(CengBucket bucket,int hash_length){
		try {
			PrintStream myoutstream;
			myoutstream = new PrintStream(System.out, true, "UTF-8");
			myoutstream.println("\t\t\"bucket\": {");
			myoutstream.println("\t\t\t\"hashLength\": " + hash_length + ",");
			myoutstream.println("\t\t\t\"pokes\": [");
			for (int i = 0; i < bucket.counter; i++) {
				print_pokemon(bucket.pokemons[i], bin_hashing(bucket.pokemons[i].pokeKey()));
				if (i < bucket.counter - 1)
					myoutstream.println(",");
				else
					myoutstream.print("\n");
			}
			myoutstream.println("\t\t\t]");
			myoutstream.println("\t\t}");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void print_row(CengHashRow row,String prefix){
		try {
			PrintStream myoutstream;
			myoutstream = new PrintStream(System.out, true, "UTF-8");
			myoutstream.println("\t\"row\": {");
			myoutstream.println("\t\t\"hashPref\": " + prefix + ",");
			print_bucket(row.getBucket(), row.getBucket().localDepth);    //make control

			myoutstream.print("\t}");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}
