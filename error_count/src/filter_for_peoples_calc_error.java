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

public class filter_for_peoples_calc_error {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);

		int sumer = 0;
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			String[] substr = str.split(" ");
			if (str.isEmpty())
				continue;
			for (int i = 0; i < 3; i++) {
				substr[i] = substr[i].trim();
				if (substr[i].isEmpty())
					continue;
			}
			System.out.println("<lx:Criteria type=\"And\">\r\n" + 
						"		<lx:Criterion type=\"Like\">\r\n" + 
						"			<lx:Path OwnerQueryLevel=\"0\">\r\n" + 
						"				<lx:StepInt index=\"5080009\" />\r\n" + 
						"			</lx:Path>\r\n" + 
						"			<lx:ConstOperand type=\"String\"><![CDATA["+substr[0].substring(0,4)+"%]]></lx:ConstOperand>\r\n" + 
								"		</lx:Criterion>\r\n" + 
								"		<lx:Criterion type=\"Like\">\r\n" + 
								"			<lx:Path OwnerQueryLevel=\"0\">\r\n" + 
								"				<lx:StepInt index=\"5080010\" />\r\n" + 
								"			</lx:Path>\r\n" + 
								"			<lx:ConstOperand type=\"String\"><![CDATA["+substr[1].substring(0,4)+"%]]></lx:ConstOperand>\r\n" + 
										"		</lx:Criterion>\r\n" + 
										"		<lx:Criterion type=\"Like\">\r\n" + 
										"			<lx:Path OwnerQueryLevel=\"0\">\r\n" + 
										"				<lx:StepInt index=\"5080011\" />\r\n" + 
										"			</lx:Path>\r\n" + 
										"			<lx:ConstOperand type=\"String\"><![CDATA["+substr[2].substring(0, 4)+"%]]></lx:ConstOperand>\r\n" + 
												"		</lx:Criterion>\r\n" + 
												"	</lx:Criteria>\n");
			
			// sumer+=substr.length;
		}
		

	}

}
