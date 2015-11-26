
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;

public class test
{
     public static void main(String args[])
     {
          HtmlPage page = null;
          boolean savePagesLocally = false;
          String url = "https://bur.cdmarf.ru/cas/login?service=https%3A%2F%2Fbur.cdmarf.ru%2Fj_spring_cas_security_check";

          WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
          webClient.setThrowExceptionOnScriptError(false);

          String savePagesLocallyString = System.getProperty("savePagesLocally");
          if(savePagesLocallyString != null )
          { savePagesLocally = Boolean.valueOf(savePagesLocallyString); }

          int pageNum = 1;
          String localFilePath = null;

          if(savePagesLocally)
          {
               localFilePath = System.getProperty("localFilePath");

               if(localFilePath == null)
               {
                    System.out.println("localFilePath property needs to be specified on command line, like so:" +
                         "-DlocalFilePath=somefilepath");
                    throw new RuntimeException("localFilePath property was not specified");
               }
               else
               {
                    String osName = System.getProperty("os.name");
                    String separator = null;

                    if(osName.indexOf(WINDOWS_OS) > -1)
                    { separator = "\\"; }
                    else // UNIX-style path
                    { separator = "/"; }

                    if( !localFilePath.endsWith(separator) )
                    { localFilePath += separator; }

                    // Create a new folder for local files- folder name is current date and time
                    SimpleDateFormat sd = new SimpleDateFormat("MM-dd-yyyy_HH_mm");
                    String formattedDate = sd.format(new Date());
                    localFilePath += formattedDate + separator;
                    File newLocalFolder = new File(localFilePath);
                    boolean success = newLocalFolder.mkdirs();

                    if(!success)
                    { throw new RuntimeException("Could not create new folder at location " + localFilePath); }

                }
          }

          try
          {
               page = webClient.getPage( url );

               if( savePagesLocally )
               {
                    String fullPath = savePageLocally(page, localFilePath, pageNum);
                    pageNum++;
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

               HtmlAnchor anchor1 = (HtmlAnchor) page.getElementById("submit");
               page = anchor1.click();

               System.out.println("Current page: –Ã»—");

               // Current page:
               // Title=–Ã»—
               // URL=https://bur.cdmarf.ru/cas/login?username=%5BLjava.lang.String%3B%401e4bfe1&_eventId=%5BLjava.lang.String%3B%40f177ce&service=%5BLjava.lang.String%3B%40f09ddf&mac=%5BLjava.lang.String%3B%4010cc3f6&lt=%5BLjava.lang.String%3B%40115574c&password=%5BLjava.lang.String%3B%40166309&execution=%5BLjava.lang.String%3B%401c009df

               if( savePagesLocally )
               {
                    String fullPath = savePageLocally(page, localFilePath, pageNum);
                    pageNum++;
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }


               System.out.println("Test has completed successfully");
          }
          catch ( FailingHttpStatusCodeException e1 )
          {
               System.out.println( "FailingHttpStatusCodeException thrown:" + e1.getMessage() );
               e1.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = savePageLocally(page, localFilePath, true, pageNum);
                    System.out.println(ERROR_PAGE + " saved to " + fullPath);
               }

          }
          catch ( MalformedURLException e1 )
          {
               System.out.println( "MalformedURLException thrown:" + e1.getMessage() );
               e1.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = savePageLocally(page, localFilePath, true, pageNum);
                    System.out.println(ERROR_PAGE + " saved to " + fullPath);
               }

          }
          catch ( IOException e1 )
          {
               System.out.println( "IOException thrown:" + e1.getMessage() );
               e1.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = savePageLocally(page, localFilePath, true, pageNum);
                    System.out.println(ERROR_PAGE + " saved to " + fullPath);
               }

          }
          catch( Exception e )
          {
               System.out.println( "General exception thrown:" + e.getMessage() );
               e.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = savePageLocally(page, localFilePath, true, pageNum);
                    System.out.println(ERROR_PAGE + " saved to " + fullPath);
               }

          }
     }

     public static final String WINDOWS_OS = "Windows";
     public static final String ERROR_PAGE = "error_page";
     public static final String STANDARD_PAGE = "output";

     protected static String savePageLocally(HtmlPage page, String filePath, int pageNum)
     {
          return savePageLocally(page, filePath, false, pageNum);
     }

     protected static String savePageLocally(HtmlPage page, String filePath, boolean isErrorPage, int pageNum)
     {
          String fullFilePath = null;
          if( isErrorPage )
          { fullFilePath = filePath + ERROR_PAGE; }
          else
          { fullFilePath = filePath + STANDARD_PAGE + "_" + pageNum; }

          File saveFolder = new File(fullFilePath);

          // Overwrite the standard HtmlUnit .html page to add diagnostic info at the top
          File webPage = new File(fullFilePath + ".html");
          BufferedWriter writer = null;
          BufferedReader reader = null;
          try
          {
               // Save all the images and css files using the HtmlUnit API
               page.save(saveFolder);

               reader = new BufferedReader( new FileReader( webPage) );
               StringBuffer buffer = new StringBuffer();

               String line;
               while( (line = reader.readLine() ) != null )
               {
                    buffer.append( line );
                    buffer.append("\n");
               }

               writer = new BufferedWriter( new FileWriter( webPage ) );

               // Diagnostic info
               Throwable t = new Throwable();
               StackTraceElement[] trace= t.getStackTrace();

               // Get the line of code that called this method
               StackTraceElement callingElement = trace[trace.length-1];
               writer.write( "Java code: " + callingElement.toString() + "&nbsp;");

               if( isErrorPage )
               { writer.write( "<a href=" + STANDARD_PAGE + "_" + (pageNum-1) + ".html>Previous</a>" ); }
               else
               {
                    if( pageNum > 1)
                    { writer.write( "<a href=" + STANDARD_PAGE + "_" + (pageNum-1) + ".html>Previous</a>" ); }

                    writer.write( "&nbsp;<a href=" + STANDARD_PAGE + "_" + (pageNum+1) + ".html>Next</a>" );
                    writer.write( "&nbsp;<a href=" + ERROR_PAGE + ".html>Error page</a><br>");
               }

               // Main body of page as seen by HTMLUnit
               writer.write( buffer.toString() );
          }
          catch ( IOException e )
          {
               System.out.println( "IOException was thrown: " + e.getMessage() );
               e.printStackTrace();
          }
          finally
          {
               if( writer != null )
               {
                    try
                    {
                         writer.flush();
                         writer.close();
                    }
                    catch ( IOException e )
                    { }
               }
               if( reader != null )
               {
                    try
                    {
                         reader.close();
                    }
                    catch ( IOException e )
                    { }
               }
          }

          return fullFilePath + ".html";
     }
}
