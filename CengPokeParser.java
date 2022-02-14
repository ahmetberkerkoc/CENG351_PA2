import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class CengPokeParser {

	public static ArrayList<CengPoke> parsePokeFile(String filename)
	{
		String x;
		ArrayList<CengPoke> pokeList = new ArrayList<CengPoke>();
		CengPoke poke_new;

		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String st;
			while ((st = br.readLine()) != null){
				String[] array1 = st.split("\t");
				Integer key  = Integer.parseInt(array1[1]);
				String name = array1[2];
				String power = array1[3];
				String type = array1[4];

				poke_new = new CengPoke(key,name,power,type);
				pokeList.add(poke_new);

			}


		}
		catch (Exception e) {
			e.printStackTrace();
		}


		// You need to parse the input file in order to use GUI tables.
		// TODO: Parse the input file, and convert them into CengPokes

		return pokeList;
	}
	
	public static void startParsingCommandLine() throws IOException
	{


		int a=0;
		boolean checker = true;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (checker) {
				
				String command = br.readLine();
				String[] command_list  = command.split("\t");
				String x=command_list[0];
				if (x.equalsIgnoreCase("quit")) {
					checker = false;
					return;
				}
				else if (x.equalsIgnoreCase("add")) {

					CengPoke poke_new = new CengPoke(Integer.parseInt(command_list[1]),command_list[2],command_list[3],command_list[4]);
					CengPokeKeeper.addPoke(poke_new);
				}
				else if (x.equalsIgnoreCase("search")) {
					Integer key = Integer.parseInt(command_list[1]);
					CengPokeKeeper.searchPoke(key);
				}
				else if (x.equalsIgnoreCase("delete")) {
					Integer key = Integer.parseInt(command_list[1]);
					CengPokeKeeper.deletePoke(key);
				}
				else if (x.equalsIgnoreCase("print")) {
					CengPokeKeeper.printEverything();
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// TODO: Start listening and parsing command line -System.in-.
		// There are 5 commands:
		// 1) quit : End the app. Print nothing, call nothing.
		// 2) add : Parse and create the poke, and call CengPokeKeeper.addPoke(newlyCreatedPoke).
		// 3) search : Parse the pokeKey, and call CengPokeKeeper.searchPoke(parsedKey).
		// 4) delete: Parse the pokeKey, and call CengPokeKeeper.removePoke(parsedKey).
		// 5) print : Print the whole hash table with the corresponding buckets, call CengPokeKeeper.printEverything().

		// Commands (quit, add, search, print) are case-insensitive.



	}
}
