

//import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;


public class Jira {	
 @Test 
 public static void main(String[] args) throws Exception, FailingHttpStatusCodeException, MalformedURLException, IOException {

    final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
    final HtmlPage page = webClient.getPage("http://www.gks.ru/dbscripts/munst/munst81/#1");
    final HtmlPage page2;
    HtmlPage page3;
    System.out.println(page.asText());
    
    
    
    

    HtmlElement theElement1 = (HtmlElement) page.getElementByName("p8001001");               
   theElement1.click();

    
    
    for (DomElement domel : page.getElementsByName("t8001001")) {
		HtmlElement c=(HtmlElement) domel;
		c.click();
	} ;
	HtmlElement temp=(HtmlElement) page.getElementById("Knopka");
	page2=temp.click();
	System.out.print("Second\n\n");
	System.out.print(page2.asText());
//    usrname.click();
//    usrname.type("eleonova");
//    HtmlElement pass = page.getHtmlElementById("login-form-password");
//    pass.click();
//    pass.type("sz665");
//    HtmlElement login = page.getHtmlElementById("login-form-submit");
//    page2=pass.click();
//    System.out.println(page2.asText());
//    page2.
//    for (DomElement domNode : page2.) {
//    	 System.out.print(domNode.asText()+ "     as xml:");
//    	 System.out.println(domNode.asXml());
//    	 if (domNode.asText().compareTo("ВКС — Википедия")==0)
//    	 {
//    		 HtmlElement temp=(HtmlElement) domNode;
//    		 page3=temp.click();
//		   DomNodeList<DomElement> c2=page3.getElementsByTagName("a");
//			    for (DomElement domNode2 : c2) {
//			    	 System.out.println(domNode2.asText());//+ "     as xml:");
//			        
//		    }
//		System.exit(0);
//		}
//	}
    
//    page2.getBody().asText();
    
    webClient.closeAllWindows();
  }
}