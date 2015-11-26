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


public class calc_error {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		HashMap<String,Integer> map = new HashMap<String,Integer>();	
		calc_error p = new calc_error();
	
	     int sumer=0;
		while(scanner.hasNext())
		{
			String str=scanner.nextLine();
			String []substr=str.split(";");
			
			for (int i = 0; i < substr.length; i++) {
				substr[i]=substr[i].trim();
				if (substr[i].isEmpty()) continue;
				sumer++;
				if (map.containsKey(substr[i]))
				{
					map.put(substr[i], map.get(substr[i])+1);
				}
				else
				{
					map.put(substr[i], 1);
				}
			}
//			sumer+=substr.length;			
		}
		Map<String, Integer> sortedMap = sortByComparator(map);
		Set<String> keys = sortedMap.keySet();
		String []lastkeys =keys.toArray(new String[0]);
//        for (String string : sortedMap.keySet()) {
//        	System.out.println(string+" "+sortedMap.get(string)+" "+(double)sortedMap.get(string)/(double)sumer);
//		}	
//        Sy
        for (int i = 0; i < sortedMap.size(); i++) {
        	System.out.println(lastkeys[i]+" | "+sortedMap.get(lastkeys[i])+" | "+String.valueOf(100.0*(double)sortedMap.get(lastkeys[i])/(double)sumer).subSequence(0, 4)+"% |");
		}
        
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
