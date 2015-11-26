import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
//import java.util.p


public class calc_error_AKT_MEKT {

	/**
	 * @param args
	 */
	static public class Pair<A, B> implements Comparable<Pair<A, B>>{
	    private A first;
	    private B second;

	    public Pair() {
	    	super();
//	    	this.first = first;
//	    	this.second = second;
	    }

	    public int hashCode() {
	    	int hashFirst = first != null ? first.hashCode() : 0;
	    	int hashSecond = second != null ? second.hashCode() : 0;

	    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
	    }

	    public boolean equals(Object other) {
	    	if (other instanceof Pair) {
	    		Pair otherPair = (Pair) other;
	    		return 
	    		((  this.first == otherPair.first ||
	    			( this.first != null && otherPair.first != null &&
	    			  this.first.equals(otherPair.first))) &&
	    		 (	this.second == otherPair.second ||
	    			( this.second != null && otherPair.second != null &&
	    			  this.second.equals(otherPair.second))) );
	    	}

	    	return false;
	    }

	    public String toString()
	    { 
	           return "(" + first + ", " + second + ")"; 
	    }

	    public A getFirst() {
	    	return first;
	    }

	    public void setFirst(A first) {
	    	this.first = first;
	    }

	    public B getSecond() {
	    	return second;
	    }

	    public void setSecond(B second) {
	    	this.second = second;
	    }
        
	    private static int compare(Object o1, Object o2) {
	        return o1 == null ? o2 == null ? 0 : -1 : o2 == null ? +1
	                : ((Comparable) o1).compareTo(o2);
	    }
	    @Override
	    public int compareTo(Pair<A, B> o) {
	        int cmp = compare(first, o.first);
	        return cmp == 0 ? compare(second, o.second) : cmp;
	    }
	}
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		HashMap<String,Pair<Double,Integer>> map = new HashMap<String,Pair<Double,Integer>>();	
		calc_error_AKT_MEKT p = new calc_error_AKT_MEKT();
	
	     double sumer=0;
	     double price=0;
	     
		while(scanner.hasNext())
		{
			String str=scanner.nextLine();
			String []substr=str.split(";");
			price=Double.valueOf(substr[substr.length-1].trim());
			for (int i = 0; i < substr.length-1; i++) {
				substr[i]=substr[i].trim();
				if (map.containsKey(substr[i]))
				{
					Pair<Double,Integer> temp=map.get(substr[i]);
					temp.setFirst(temp.getFirst()+price);
					temp.setSecond(temp.getSecond()+1);
					map.put(substr[i], temp);
				}
				else
				{
					int tempi=1;
					Pair<Double,Integer> temp=new Pair<Double,Integer>();
					temp.setFirst(price);
					temp.setSecond(1);
					map.put(substr[i], temp);
				}
				sumer+=1;			
			}
		}
		Map<String, Pair<Double,Integer>> sortedMap = sortByComparator(map);
		Set<String> keys = sortedMap.keySet();
		String []lastkeys =keys.toArray(new String[0]);
        for (String string : sortedMap.keySet()) {
        	System.out.println(string+";"+String.format("%1$,.2f", sortedMap.get(string).getFirst())+";"+String.format("%1$,.0f", (double)sortedMap.get(string).getSecond())+";"+String.format("%1$,.2f", (double)sortedMap.get(string).getSecond()/(double)sumer*100.0)+"%");
		}	
//        Sy
//        for (int i = 0; i < 3; i++) {
//        	System.out.print(lastkeys[i]+" "+sortedMap.get(lastkeys[i])+" "+String.valueOf(100.0*(double)sortedMap.get(lastkeys[i])/(double)sumer).subSequence(0, 2)+"% |");
//		}
        
	}

	private static Map sortByComparator(Map unsortMap) {
		 
		List list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
                                       .compareTo(((Map.Entry) (o2)).getValue())*(-1);
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
 
	public static void printMap(Map<String, String> map){
		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() 
                                   + " Value : " + entry.getValue());
		}
	}

}
