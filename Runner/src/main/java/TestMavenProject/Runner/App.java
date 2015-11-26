package TestMavenProject.Runner;

import org.apache.log4j.*;
/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger log = Logger.getLogger(App.class);
    public static void main(String args[])
    {
    	log.debug("Start logging");
    	log.warn("rakamakafour");
        System.out.println( "Hello World!" );
    }
}
