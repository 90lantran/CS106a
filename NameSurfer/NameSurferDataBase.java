
import java.io.*;
import java.util.*;

import acm.util.*;

public class NameSurferDataBase {
	public NameSurferDataBase(){
		try{
			BufferedReader rd = new BufferedReader(new FileReader("Namesdata.txt"));
			while(true){
				String line = rd.readLine();
				if(line == null) break;
				NameSurferEntry entry = new NameSurferEntry(line);
				database.put(entry.getName(), entry);
				
			}
			rd.close();
		}catch(IOException e){
			throw new ErrorException(e);
		}
					
	}
	public NameSurferEntry findEntry(String name){
		if (database.containsKey(name)){
			return database.get(name);
		}else{
			return null;
		}
	}


	private Map<String,NameSurferEntry> database = new HashMap<String,NameSurferEntry>();
}
