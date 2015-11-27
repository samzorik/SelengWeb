package mainpak;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class discrete_convert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		String output="";
		double []scale={170,156,142,128,114,100,86,71};
		String []scale_name={"высокий","повышенный","средний","ниже среднего","низкий","неизменный","отрицательный","низкоотрицательный"};
		while(scanner.hasNext())
		{
			String str=scanner.nextLine();
			String []substr=str.split(",");		
			output+=substr[0];
			for (int i = 1; i < substr.length; i++) {
				if (substr[i].isEmpty())
				{
					output+=",";
					continue;
				}
				boolean flag=false;
				for (int j = 0; j < 6; j++) {
					if (Double.valueOf(substr[i])>scale[j])
					{
						output+=",\""+scale_name[j-1]+"\"";
						flag=true;
						break;
					}
				}
				if (flag) continue;
				if (Double.valueOf(substr[i])==100.0)
				{
					output+=",\"неизменный\"";
					continue;
				}
				for (int j = 6; j < scale.length; j++) {
					if (Double.valueOf(substr[i])>scale[j])
					{
						output+=",\""+scale_name[j]+"\"";
						break;
					}
				}
			}
			output+="\n";
		}
		System.out.println(output);
	}

}
