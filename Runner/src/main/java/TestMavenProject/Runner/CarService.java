package TestMavenProject.Runner;

import TestMavenProject.Runner.Car;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

public class CarService {

   public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();

   public List<data_type> getAlldatatype()
   {
	   String queryString = "SELECT a FROM data_type a ";
		Query query = em.createQuery(queryString);
		return query.getResultList();
   }
   public Car add(Car car){
       em.getTransaction().begin();
       Car carFromDB = em.merge(car);
//       Seller sell=new Seller("sds",1,1,carFromDB);
//       em.merge(sell);
       em.getTransaction().commit();
       List<data_type> all_data =getAlldatatype();
//       for (Iterator data = all_data.iterator(); data.hasNext();) {
//		data_type datas = (data_type) data.next();
//		System.out.println(datas);
//	}
       return carFromDB;
   }

   public void delete(long id){
       em.getTransaction().begin();
       em.remove(get(id));
       em.getTransaction().commit();
   }

   public Car get(long id){
       return em.find(Car.class, id);
   }

   public void update(Car car){
       em.getTransaction().begin();
       em.merge(car);
       em.getTransaction().commit();
   }

   public List<Car> getAll(){
       TypedQuery<Car> namedQuery = em.createNamedQuery("Car.getAll", Car.class);
       return namedQuery.getResultList();
   }

}