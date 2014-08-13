import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import acm.util.ErrorException;
import acm.program.*;

public class TestNameSurferEntry extends ConsoleProgram{
	public void init(){
		try{
			BufferedReader rd = new BufferedReader(new FileReader("Namesdata.txt"));
			
				String line = rd.readLine();
				//if(line == null) break;
				NameSurferEntry entry = new NameSurferEntry(line);
				println(entry.toString());
			
			
			rd.close();
		}catch(IOException e){
			throw new ErrorException(e);
		}
	}
	

}
