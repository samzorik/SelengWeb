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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
 
public class JiraJsoup_analyze_kir_reaction {
 
  private List<String> cookies;
  private HttpsURLConnection conn;
  public static String res;
  private final String USER_AGENT = "Mozilla/3.6";
  public String getresult(){	  
	return res;  
  }
 
  public static void main(String[] args) throws Exception {
 
	String url = "https://jira.kirkazan.ru/login.jsp";
	String gmail = "https://jira.kirkazan.ru/browse/BUR-";
	String gmail3 = "https://bur.cdmarf.ru/cases/cases";
	String first = "https://bur.cdmarf.ru/cases/service/";
	String last ="/edit?backUrl=%2Fservices%23eyJzdGF0ZSI6eyJkZWZhdWx0RmlsdGVyIjp7InBhdGllbnRJZCI6e30sIm9yZ2FuaXphdGlvbklkIjp7fSwiY2FzZVR5cGVJZCI6e30sImNyZWF0ZURhdGUiOnt9LCJjbG9zZURhdGUiOnt9LCJvdXRjb21lSWQiOnt9LCJyZXN1bHRJZCI6e30sImRpc2Vhc2VJZCI6e30sImRpYWdub3Npc0lkIjp7fSwic2VydmljZUlkIjp7fSwic2VydmljZUNhdGVnb3J5SWQiOnt9LCJlbXBsb3llZUlkIjp7fSwic2VydmljZVR5cGVJZCI6e319LCJzZXJ2aWNlIjp7ImNvbHVtbnMiOlt7ImlkIjoiaDEifSx7ImlkIjoiaDIifSx7ImlkIjoiaDMifSx7ImlkIjoiaDQifSx7ImlkIjoiaDUifSx7ImlkIjoiaDYiLCJ3aWR0aCI6NDkxfSx7ImlkIjoiaDcifSx7ImlkIjoiaDgifSx7ImlkIjoiaDkifV0sImN1cnJlbnRQYWdlIjoxLCJzZWxlY3RlZEVudGl0eUlkIjo0MTgyNzV9fX0%3D#eyJzdGF0ZSI6eyJyZXN1bHRDYXRlZ29yeSI6e319fQ=="; 
	String gmail2 = "https://bur.cdmarf.ru/";
	String shene = "https://bur.cdmarf.ru/cases/service/66512/edit?backUrl=";
	int issues[]={ 98, 110,  90, 140, 92, 112, 64, 189, 87, 209, 66, 208, 233, 215, 238, 245, 264, 256, 188, 240, 271, 278, 284, 283, 296, 257, 295, 308, 206, 312, 227, 331, 329, 343, 317, 352, 353, 363, 340, 367, 344, 229, 372, 330, 337, 339, 368, 316, 375, 237, 373, 380, 379, 186, 377, 383, 388, 387, 313, 394, 378, 303, 395, 392, 390, 398, 397, 259, 403, 401, 269, 405};
	Date p=new Date();
//	p.
//	Date.
//	System.setOut(new PrintStream(System.out, true, "CP1251"));
//	System.setErr(new PrintStream(System.err, true, "CP1251"));
//	PrintStream
	JiraJsoup_analyze_kir_reaction http = new JiraJsoup_analyze_kir_reaction();
    HashSet<String> c=new HashSet(); 
    c.add("Бардымов Петр (Бурятия)");
    c.add("Зорикто Самбялов");
    c.add("Леонов Владимир ( Бурятия )");
    c.add("Жаргал Махачкеев");
	// make sure cookies is turn on
	CookieHandler.setDefault(new CookieManager());
	
	// 1. Send a "GET" request, so that you can extract the form's data.
	String page = http.GetPageContent(url);
	String postParams = http.getFormParams(page, "eleonova", "sz665");
 
	// 2. Construct above post's content and then send a POST request for
	// authentication
	http.sendPost(url, postParams);
	// 3. success then go to gmail.
	for(int i=0;i<issues.length;i++)
	{
		String result = http.GetPageContent(gmail+Integer.toString(issues[i]));
		Document doc = Jsoup.parse(result);
		
		Element answer=doc.getElementById("issue_actions_container");
//		if (answer==null) continue;
		Element status=doc.getElementById("status-val");
//		if (status.text().equalsIgnoreCase("Closed")||status.text().equalsIgnoreCase("Resolved")) continue;
		Element authors = doc.getElementById("peopledetails");
		Elements listauthor=authors.getElementsByTag("a");
		
		Elements links=answer.getElementsByClass("user-hover");
				
		Element header=doc.getElementById("issue_header_summary");
		Elements head=header.getElementsByTag("a");
		
		
		Elements answers=doc.getElementsByClass("action-body");			
				
		Elements dates=answer.getElementsByClass("date");
		Element create_date=doc.getElementById("create-date");
		Element updated_date=doc.getElementById("updated-date");
		String create=create_date.text().split(" ")[0];
		String update=updated_date.text().split(" ")[0];
		Calendar tec=Calendar.getInstance();
//		for (Element ans : answers) {
//			System.out.print(ans.text()+",");
//		}
		answers.size();
		if (create.equalsIgnoreCase("Сегодня"))
		{
			create=Integer.toString(p.getDate())+"/"+Integer.toString(p.getMonth()+1)+"/"+Integer.toString(p.getYear()+1900);
		}
		if (update.equalsIgnoreCase("Сегодня"))
		{ 
			update=Integer.toString(p.getDate())+"/"+Integer.toString(p.getMonth()+1)+"/"+Integer.toString(p.getYear()+1900);
		}
//		if(c.contains(listauthor.get(1).text()))
//		{
			System.out.print("BUR-"+issues[i]);
			System.out.print("|"+head.get(0).text());
			System.out.print("|"+listauthor.get(0).text()+"|"+listauthor.get(1).text());
			if (links.size()>0)
			System.out.print("|"+links.get(links.size()-1).text());
			else
				System.out.print("|");
			System.out.print("|"+create+"|"+update);
			System.out.print("|"+status.text());
			if (answers.size()>0)
			System.out.println("|"+answers.get((answers.size()-1)).text());
			else
			System.out.println("|Пока нет ни одного комментария");	
//		}
	}
}
  
  private void sendPost(String url, String postParams) throws Exception {
 
	URL obj = new URL(url);
	conn = (HttpsURLConnection) obj.openConnection();
 
	// Acts like a browser
	conn.setUseCaches(false);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Host", "https://jira.kirkazan.ru/secure/Dashboard.jspa");
	conn.setRequestProperty("User-Agent", USER_AGENT);
	conn.setRequestProperty("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	for (String cookie : this.cookies) {
		conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
	}
	conn.setRequestProperty("Connection", "keep-alive");
	conn.setRequestProperty("Referer", "https://jira.kirkazan.ru/secure/Dashboard.jspa");
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
//	System.out.println("\nSending 'POST' request to URL : " + url);
//	System.out.println("Post parameters : " + postParams);
//	System.out.println("Response Code : " + responseCode);
 
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
//	System.out.println("\nSending 'GET' request to URL : " + url);
//	System.out.println("Response Code : " + responseCode);
 
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
	Element loginform = doc.getElementById("login-form");
	Elements inputElements = loginform.getElementsByTag("input");	
	List<String> paramList = new ArrayList<String>();
	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");
 
		if (key.equals("os_username"))
			value = username;
		else if (key.equals("os_password"))
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