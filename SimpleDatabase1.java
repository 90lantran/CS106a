import acm.program.*;
import java.util.*;


public class SimpleDatabase1 extends ConsoleProgram{

	Map<String,String> currentTransaction = new HashMap<String,String>();
	Stack<Map> blocks = new Stack<Map>();

	public void run(){
		String cmd;
		do{
			cmd = readLine();
			StringTokenizer tokenizer = new StringTokenizer(cmd);
			ArrayList<String> components = new ArrayList<String>();

			for (int i = 0 ; tokenizer.hasMoreTokens() ; i++){
				components.add(tokenizer.nextToken());
			}

			if(components.get(0).equals("SET")){
				setMethod(components );
			}else if(components.get(0).equals("GET")){
				getMethod(components);
			}else if (components.get(0).equals("UNSET")){
				unsetMethod(components );
			}else if (components.get(0).equals("NUMEQUALTO")){		
				numEqualToMethod(components );


			}
		}while (!cmd.equals("END")); 
	}


	private void setMethod(ArrayList<String> components ){
		name = components.get(1);
		value = components.get(2);
		currentTransaction.put(name, value);
	}

	private void getMethod(ArrayList<String> components){
		name = components.get(1);
		if(currentTransaction.containsKey(name)){
			println(currentTransaction.get(name));
		}else{
			println("NULL");
		}
	}
	private void unsetMethod(ArrayList<String> components ){
		name = components.get(1);
		currentTransaction.remove(name);
	}
	private void numEqualToMethod(ArrayList<String> components ){
		int counter = 0;
		value = components.get(1);
		for (String name:currentTransaction.keySet()){
			if (currentTransaction.get(name).equals(value)){
				counter++;
			}
		}
		println(counter);
	}

	private String name;
	private String value;


}
