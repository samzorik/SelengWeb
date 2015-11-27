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
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class cases_without_services {
 
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
	String shene = "https://bur.cdmarf.ru/cases/cases#eyJzdGF0ZSI6eyJkZWZhdWx0RmlsdGVyIjp7InBhdGllbnRJZCI6e30sIm9yZ2FuaXphdGlvbklkIjp7InZhbHVlIjp7ImxhYmVsIjoi0JPQkTEiLCJkYXRhIjp7InZhbHVlIjoiNjA0ODg2MiJ9fX0sImNhc2VUeXBlSWQiOnt9LCJjcmVhdGVEYXRlIjp7fSwiY2xvc2VEYXRlIjp7fSwib3V0Y29tZUlkIjp7fSwicmVzdWx0SWQiOnt9LCJkaXNlYXNlSWQiOnt9LCJkaWFnbm9zaXNJZCI6e30sInNlcnZpY2VJZCI6e30sInNlcnZpY2VDYXRlZ29yeUlkIjp7fSwiZW1wbG95ZWVJZCI6e319LCJjYXNlIjp7ImNvbHVtbnMiOlt7ImlkIjoiaDEifSx7ImlkIjoiaDIiLCJ3aWR0aCI6MTkzfSx7ImlkIjoiaDMiLCJ3aWR0aCI6MjQ0fSx7ImlkIjoiaDQifSx7ImlkIjoiaDUifSx7ImlkIjoiaDYifSx7ImlkIjoiaDcifSx7ImlkIjoiaDgifSx7ImlkIjoiaDkifSx7ImlkIjoiaDEwIn0seyJpZCI6ImgxMSJ9LHsiaWQiOiJoMTIifSx7ImlkIjoiaDEzIn1dLCJjdXJyZW50UGFnZSI6MTMsInNlbGVjdGVkRW50aXR5SWQiOjE0MTc3Mn19fQ==";
//	System.setOut(new PrintStream(System.out, true, "CP1251"));
//	System.setErr(new PrintStream(System.err, true, "CP1251"));
//	PrintStream
	cases_without_services http = new cases_without_services();
 
	// make sure cookies is turn on
	CookieHandler.setDefault(new CookieManager());
 
	// 1. Send a "GET" request, so that you can extract the form's data.
	String page = http.GetPageContent(url);
	String postParams = http.getFormParams(page, "samzorik", "sz665");
 
	// 2. Construct above post's content and then send a POST request for
	// authentication
	http.sendPost(url, postParams);
	
	// 3. success then go to gmail.
	String result = http.GetPageContent(gmail);
//	System.out.println(result);
	result = http.GetPageContent(gmail2);
//	System.out.println(result);
    result = http.GetPageContent(gmail);
//	System.out.println(result);
	Document doc = Jsoup.parse(result);
	 
	// Google form id
//	Element loginform = doc.getElementById("fm1");
	Elements links = doc.getElementsByTag("input");
//	doc.ge
//	links.
	for(Element link : links) {
//		links.
		String nick = new String(link.val().getBytes("CP1251"), "UTF-8");
		if (!nick.isEmpty())
		System.out.println(nick);
		res+=link.text();
	}
	
	
	 result = http.GetPageContent(gmail3);
//		System.out.println(result);
		doc = Jsoup.parse(result);
		 
		// Google form id
//		Element loginform = doc.getElementById("fm1");
		links = doc.getElementsByTag("input");
//		doc.ge
//		links.
		for(Element link : links) {
//			links.
			String nick = new String(link.val().getBytes("CP1251"), "UTF-8");
			if (!nick.isEmpty())
//			link.
			System.out.println(nick);
			res+=link.text();
		}
//		for (int i = 318248; i < 318348; i++) {
//			 result = http.GetPageContent(first+Integer.toString(i)+last);
////				System.out.println(result);
//				doc = Jsoup.parse(result);
//				 
//				// Google form id
////				Element loginform = doc.getElementById("fm1");
//				links = doc.getElementsByTag("input");
////				doc.ge
////				links.
//				for(Element link : links) {
////					links.
//					String nick = new String(link.val().getBytes("CP1251"), "UTF-8");
//					if (!nick.isEmpty())
////					link.
//					System.out.println(nick);
//					res+=link.text();
//				}
//		}
//		for (int i = 1; i < 5; i++) {
//			 result = http.GetPageContent(shene);//+Integer.toString(i));
//				System.out.println(result);
//				doc = Jsoup.parse(result);
//				 
				// Google form id
//				Element loginform = doc.getElementById("fm1");
				
//				Elements table = doc.getElementsByClass("lsd-table-rows");
//				lsd-table-rows
				
//				links = table.first().getElementsByTag("td");
//				links = doc.g
//				doc.ge
////				links.
//				for(Element link : links) {
////					links.
//					String nick = new String(link.text().getBytes("CP1251"), "UTF-8");
//					if (!nick.isEmpty())
////					link.
//					System.out.println(nick);
//					res+=link.text();
//				}
				
				
				
				 result = http.GetPageContent(shene);//+Integer.toString(i));
					System.out.println(result);
					doc = Jsoup.parse(result);
					 
					// Google form id
//					Element loginform = doc.getElementById("fm1");
					
					Elements table = doc.getElementsByClass("lsd-form-body");
//					lsd-table-rows
					
					links = table.first().getElementsByTag("div");
//					links = doc.g
//					doc.ge
//					links.
					for(Element link : links) {
//						links.
						String nick = new String(link.text().getBytes("CP1251"), "UTF-8");
						if (!nick.isEmpty())
//						link.
						System.out.println(nick);
						res+=link.text();
					}
//		}
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