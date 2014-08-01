import acm.program.*;
import java.util.*;


public class SimpleDatabase1 extends ConsoleProgram{

	Map<String,String> currentTransaction = new HashMap<String,String>(); 
	Stack<Map<String,String>> blocks = new Stack<Map<String,String>>();
	public void run(){
		String cmd;
		//int index = -1;
		do{


			cmd = readLine();
			StringTokenizer tokenizer = new StringTokenizer(cmd);
			ArrayList<String> components = new ArrayList<String>();

			for (int i = 0 ; tokenizer.hasMoreTokens() ; i++){
				components.add(tokenizer.nextToken());
			}



			if (components.get(0).equals("BEGIN")){
				//index++;
				Map<String,String> newTransaction = new HashMap<String,String>();
				if (currentTransaction.size()!= 0){
					for (String name : currentTransaction.keySet()){
						newTransaction.put(name, currentTransaction.get(name));
					}
				}
				
				
				blocks.push(newTransaction);
				currentTransaction = blocks.peek();
			}else if(components.get(0).equals("ROLLBACK")){
				if (!blocks.isEmpty()){ // escape from current block 
					//println(currentTransaction);
					blocks.pop();
					if (!blocks.isEmpty()){ 
						currentTransaction = blocks.peek();
						//println("DONE");
						//println(currentTransaction);
					}else{
						currentTransaction = null;
						println("NO TRANSACTION");
					}
				}else{
					println("NO TRANSACTION");
				}
				// currentTransaction is the one right after that

			}else if (components.get(0).equals("COMMIT")){
				if(!blocks.isEmpty()){
					currentTransaction = blocks.peek();
					
				}
				// blocks = null; cannot erase a Stack by assign a NULL value
				
				while (!blocks.isEmpty()){
					blocks.pop();
				}
				blocks.push(currentTransaction);
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
		try{
			if(currentTransaction.containsKey(name)){
				println(currentTransaction.get(name));
			}else{
				println("NULL");
			}
		}catch (NullPointerException e){
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
