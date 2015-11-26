package Entities;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityTest {
	public static EntityManager em = Persistence.createEntityManagerFactory("TestWebProj").createEntityManager();
	public static String output;
	
	 public static List<Fact_table> getAlldatatype()
	   {
		   String queryString = "SELECT a FROM Fact_table a ";
			Query query = em.createQuery(queryString);
			return query.getResultList();
	   }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		 em.getTransaction().begin();
//       em.getTransaction().commit();
       List<Fact_table> all_data = getAlldatatype();
       for (Iterator data = all_data.iterator(); data.hasNext();) {
    	   Fact_table datas = (Fact_table) data.next();
		System.out.println(datas);
		output+=datas;
	}
	}
}
