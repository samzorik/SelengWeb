/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class RMIS_num_errors_in_bill {
 
  private List<String> cookies;
  private HttpsURLConnection conn;
  public static String res;
  private final String USER_AGENT = "Mozilla/5.0";
  public String getresult(){	  
	return res;  
  }
 
  public static void main(String[] args) throws Exception {
 
	String url = "https://bur.cdmarf.ru/cas/login?service=https://bur.cdmarf.ru/j_spring_cas_security_check";
	String gmail = "https://bur.cdmarf.ru/cases/service/418275/edit?backUrl=%2Fservices%23eyJzdGF0ZSI6eyJkZWZhdWx0RmlsdGVyIjp7InBhdGllbnRJZCI6e30sIm9yZ2FuaXphdGlvbklkIjp7fSwiY2FzZVR5cGVJZCI6e30sImNyZWF0ZURhdGUiOnt9LCJjbG9zZURhdGUiOnt9LCJvdXRjb21lSWQiOnt9LCJyZXN1bHRJZCI6e30sImRpc2Vhc2VJZCI6e30sImRpYWdub3Npc0lkIjp7fSwic2VydmljZUlkIjp7fSwic2VydmljZUNhdGVnb3J5SWQiOnt9LCJlbXBsb3llZUlkIjp7fSwic2VydmljZVR5cGVJZCI6e319LCJzZXJ2aWNlIjp7ImNvbHVtbnMiOlt7ImlkIjoiaDEifSx7ImlkIjoiaDIifSx7ImlkIjoiaDMifSx7ImlkIjoiaDQifSx7ImlkIjoiaDUifSx7ImlkIjoiaDYiLCJ3aWR0aCI6NDkxfSx7ImlkIjoiaDcifSx7ImlkIjoiaDgifSx7ImlkIjoiaDkifV0sImN1cnJlbnRQYWdlIjoxLCJzZWxlY3RlZEVudGl0eUlkIjo0MTgyNzV9fX0%3D#eyJzdGF0ZSI6eyJyZXN1bHRDYXRlZ29yeSI6e319fQ==";
	String gmail3 = "https://bur.cdmarf.ru/cases/cases";
	String first = "https://bur.cdmarf.ru/cases/service/";
	String last ="/edit?backUrl=%2Fservices%23eyJzdGF0ZSI6eyJkZWZhdWx0RmlsdGVyIjp7InBhdGllbnRJZCI6e30sIm9yZ2FuaXphdGlvbklkIjp7fSwiY2FzZVR5cGVJZCI6e30sImNyZWF0ZURhdGUiOnt9LCJjbG9zZURhdGUiOnt9LCJvdXRjb21lSWQiOnt9LCJyZXN1bHRJZCI6e30sImRpc2Vhc2VJZCI6e30sImRpYWdub3Npc0lkIjp7fSwic2VydmljZUlkIjp7fSwic2VydmljZUNhdGVnb3J5SWQiOnt9LCJlbXBsb3llZUlkIjp7fSwic2VydmljZVR5cGVJZCI6e319LCJzZXJ2aWNlIjp7ImNvbHVtbnMiOlt7ImlkIjoiaDEifSx7ImlkIjoiaDIifSx7ImlkIjoiaDMifSx7ImlkIjoiaDQifSx7ImlkIjoiaDUifSx7ImlkIjoiaDYiLCJ3aWR0aCI6NDkxfSx7ImlkIjoiaDcifSx7ImlkIjoiaDgifSx7ImlkIjoiaDkifV0sImN1cnJlbnRQYWdlIjoxLCJzZWxlY3RlZEVudGl0eUlkIjo0MTgyNzV9fX0%3D#eyJzdGF0ZSI6eyJyZXN1bHRDYXRlZ29yeSI6e319fQ=="; 
	String gmail2 = "https://bur.cdmarf.ru/";
	String shene = "https://bur.cdmarf.ru/cases/service/66512/edit?backUrl=";
	String bill="https://bur.cdmarf.ru/billing/bills/298/spec?page=";
	RMIS_num_errors_in_bill http = new RMIS_num_errors_in_bill();
	CookieHandler.setDefault(new CookieManager());
	String page = http.GetPageContent(url);
	String postParams = http.getFormParams(page, "samzorik", "sz665");
	http.sendPost(url, postParams);
	String result = http.GetPageContent(gmail);
	result = http.GetPageContent(gmail2);
    result = http.GetPageContent(bill);
	Document doc;
	Elements links;
		
	System.out.println("-------------------------------------");
//	Set<String> was=new Set<String>();
//	was.add("");
	Vector<String> allerr=new Vector<String>();
	Vector<Integer> koler=new Vector<Integer>();
	for (int i = 0; i < 100; i++) {
	
			 result = http.GetPageContent(bill+i);
				doc = Jsoup.parse(result);
				 Elements table=doc.getElementsByClass("lsd-table-rows");
				 Element tablebill=table.first();
				links = tablebill.getElementsByTag("td");
				int counter=1;
				for(Element link : links) {
					String nick=link.text();
//					if (!nick.isEmpty())
//					{
						counter++;
						if (counter==14)
		 				{
//							System.out.println(nick);
							String arr[]=nick.split("/");
							
							for (int j = 0; j < arr.length; j++) {
								arr[j]=arr[j].trim();
								boolean was=false;
							    for (int j2 = 0; j2 < allerr.size(); j2++) {
									if (allerr.get(j2).equalsIgnoreCase(arr[j]))
									{
										koler.set(j2, koler.get(j2)+1);
										was=true;
										break;
									}
								}
							    if (!was)
							    {
							    	allerr.add(arr[j]);
							    	koler.add(1);
							    }
//								System.out.println(arr[j]);							   
							}
							counter=0;
						}
//					}
					res+=link.text();
				}
				for (int j = 0; j < allerr.size(); j++) {
					System.out.print(allerr.get(j)+" | ");
					System.out.println(koler.get(j));
				}
//		}
	}
  }
 
  private void sendPost(String url, String postParams) throws Exception {
 
	URL obj = new URL(url);
	conn = (HttpsURLConnection) obj.openConnection();
 
	// Acts like a browser
	conn.setUseCaches(false);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Host", "bur.cdmarf.ru");
	conn.setRequestProperty("User-Agent", USER_AGENT);
	conn.setRequestProperty("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	for (String cookie : this.cookies) {
		conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
	}
	conn.setRequestProperty("Connection", "keep-alive");
	conn.setRequestProperty("Referer", "https://bur.cdmarf.ru/cas/login?service=https://bur.cdmarf.ru/j_spring_cas_security_check");
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
 
	conn.setDoOutput(true);
	conn.setDoInput(true);
 
	// Send post request
	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	wr.writeBytes(postParams);
	wr.flush();
	wr.close();
 
	int responseCode = conn.getResponseCode();
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + postParams);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader in = 
             new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();
 
	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	// System.out.println(response.toString());
 
  }
 
  private String GetPageContent(String url) throws Exception {
 
	URL obj = new URL(url);
	conn = (HttpsURLConnection) obj.openConnection();
 
	// default is GET
	conn.setRequestMethod("GET");
 
	conn.setUseCaches(false);
 
	// act like a browser
	conn.setRequestProperty("User-Agent", USER_AGENT);
	conn.setRequestProperty("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	conn.setRequestProperty("Accept-Language", "ru-RU,en;q=0.5");
	if (cookies != null) {
		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
	}
	int responseCode = conn.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader in = 
            new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();
 
	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
 
	// Get the response cookies
	setCookies(conn.getHeaderFields().get("Set-Cookie"));
 
	return response.toString();
 
  }
 
  public String getFormParams(String html, String username, String password)
		throws UnsupportedEncodingException {
 
	System.out.println("Extracting form's data...");
 
	Document doc = Jsoup.parse(html);
 
	// Google form id
	Element loginform = doc.getElementById("fm1");
	Elements inputElements = loginform.getElementsByTag("input");	
	List<String> paramList = new ArrayList<String>();
	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");
 
		if (key.equals("username"))
			value = username;
		else if (key.equals("password"))
			value = password;
		paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
	}
 
	// build parameters list
	StringBuilder result = new StringBuilder();
	for (String param : paramList) {
		if (result.length() == 0) {
			result.append(param);
		} else {
			result.append("&" + param);
		}
	}
	return result.toString();
  }
 
  public List<String> getCookies() {
	return cookies;
  }
 
  public void setCookies(List<String> cookies) {
	this.cookies = cookies;
  }
 
}