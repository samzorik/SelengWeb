

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


public class homePage {	
 @Test 
 public static void main(String[] args) throws Exception, FailingHttpStatusCodeException, MalformedURLException, IOException {

    final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
    final HtmlPage page = webClient.getPage("http://ya.ru");
    final HtmlPage page2;
    HtmlPage page3;
    HtmlElement usrname = page.getHtmlElementById("text");
//    page.getb
    usrname.click();
    usrname.type("ÂÊÑ");
//    HjitmlElement psswrd = page.getHtmlElementById("password");
//    psswrd.click();
//    psswrd.type("sz665");
//    HtmlSelect select = (HtmlSelect) page.getElementById("cmbProducts");
////    HtmlOption option = select.getOptionByValue("ITDirect");
////    select.setSelectedAttribute(option, true);
//    HtmlElement signin = page.getHtmlElementById("submit");
//    signin.click();
//    if( savePagesLocally )
//    {
//         String fullPath = savePageLocally(page, localFilePath, pageNum);
//         pageNum++;
//         System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
//    }

    List<HtmlElement> elements1 = (List<HtmlElement>) page.getByXPath(
            "(//input[translate(@type, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'submit'])[1]");
       HtmlElement element2 = elements1.get(0);
     page2=element2.click();
//     page2.ge
//     element2.
//    System.out.println("Current page: ÐÌÈÑ");

    // Current page:
    // Title=ÐÌÈÑ
    // URL=https://bur.cdmarf.ru/cas/login?username=%5BLjava.lang.String%3B%401e4bfe1&_eventId=%5BLjava.lang.String%3B%40f177ce&service=%5BLjava.lang.String%3B%40f09ddf&mac=%5BLjava.lang.String%3B%4010cc3f6&lt=%5BLjava.lang.String%3B%40115574c&password=%5BLjava.lang.String%3B%40166309&execution=%5BLjava.lang.String%3B%401c009df
    System.out.println(page.getTitleText());
    DomNodeList<DomElement> c=page2.getElementsByTagName("a");
   
    for (DomElement domNode : c) {
    	 System.out.print(domNode.asText()+ "     as xml:");
    	 System.out.println(domNode.asXml());
    	 if (domNode.asText().compareTo("ÂÊÑ — Âèêèïåäèÿ")==0)
    	 {
    		 HtmlElement temp=(HtmlElement) domNode;
    		 page3=temp.click();
		   DomNodeList<DomElement> c2=page3.getElementsByTagName("a");
			    for (DomElement domNode2 : c2) {
			    	 System.out.println(domNode2.asText());//+ "     as xml:");
			        
		    }
		System.exit(0);
		}
	}
    
//    page2.getBody().asText();
    
    webClient.closeAllWindows();
  }
}