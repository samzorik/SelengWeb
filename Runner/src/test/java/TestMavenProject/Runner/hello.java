package TestMavenProject.Runner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test; 

public class hello {
 
    private EntityManagerFactory emf;
    private EntityManager em;
    private String PERSISTENCE_UNIT_NAME = "COLIBRI";
    
    @Test
    public static void main(String []args) throws Exception {
        hello hello = new hello();
        hello.initEntityManager();    
        hello.create();   
        hello.read();
        hello.closeEntityManager();
    } 
    private void initEntityManager() {
        em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    } 
    private void closeEntityManager() {
        em.close();
        emf.close();
    }
     private void create() {
        em.getTransaction().begin();
        Greeting g_en = new Greeting("hello world", "en");
        Greeting g_es = new Greeting("hola, mundo", "es");
        Greeting[] greetings = new Greeting[]{g_en, g_es};
        for(Greeting g : greetings) {
            em.persist(g);    
        }
        em.getTransaction().commit();
    }
     private void read() {
        Greeting g = (Greeting) em.createQuery("select g from Greeting g where .language = :language").setParameter("language", "en").getSingleResult();    
        System.out.println("Query returned: " + g);
    }
 
}