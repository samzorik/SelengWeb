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


public class Number_Filter_Weka {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext())
		{
			String str=scanner.nextLine();
			System.out.println(str);
			if (str.equals("@data"))
				break;
		}
		while(scanner.hasNext())
		{
			String str=scanner.nextLine();
			String []substr=str.split(",");
			String newstr="";
			int len=substr.length;
			for (int i = 0; i < substr.length-2; i++) {
				newstr+=substr[i]+",";
			}
			if (substr[len-2].charAt(substr[len-2].length()-1)>='0' && substr[len-2].charAt(substr[len-2].length()-1)<='9'&& substr[len-1].charAt(0)>='0' && substr[len-1].charAt(0)<='9')
			{
				newstr+=substr[len-2]+"."+substr[len-1];
			}
			else
			{
				newstr+=substr[len-2]+","+substr[len-1];
			}
			System.out.println(newstr);					
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
