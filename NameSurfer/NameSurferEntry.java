
/*
 * File: NameSurferEntry.java
 * --------------------------* 
 * This class represents a single entry in the database. Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */
import acm.util.*;
import java.util.*;


	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file. Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
public class NameSurferEntry implements NameSurferConstants {
	public NameSurferEntry(String line) {
		// You fill this in //
		int nameEnd = line.indexOf(" ");
		name = line.substring(0, nameEnd);

		rank = new int[11];

		int rankStart = nameEnd;
		for (int i = 0 ; i < 11; i++){
			try{
				int rankEnd = line.indexOf(" ",rankStart + 1);
				String rankString = line.substring(rankStart + 1, rankEnd);
				rank[i] = Integer.parseInt(rankString);
				rankStart = rankEnd;
			}catch(IndexOutOfBoundsException e){
				String lastRank = line.substring(rankStart+1);
				rank[i] = Integer.parseInt(lastRank);				
			}
		}
	}
	
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade. The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE. If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		if (decade > 11){
			return 0;
		} else{
			return rank[decade];
		}
	}
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
//	public int[] getNumData(){
//		return rank;
//	}
	public String toString() {
		// You need to turn this stub into a real implementation //
		return (this.getName()+" "+"["+ rank[0] +" "+rank[1] +" "+rank[2] +" "+rank[3] +" "
				+rank[4] +" "+rank[5] +" "+rank[6] +" "+rank[7] +" "+rank[8] +" "+
				rank[9] + " " +rank[10]+"]");
	}
	private String name;
	private int[] rank;

}

