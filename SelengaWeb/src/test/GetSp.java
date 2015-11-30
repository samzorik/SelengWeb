package test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ButtonGroup;

import com.google.gson.Gson;

import Entities.Data_type;
import Entities.Group_pokaz;

@WebServlet("/getSp")
public class GetSp extends HttpServlet {

//    public static EntityManager em = Persistence.createEntityManagerFactory("SelengaWeb").createEntityManager();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryString;
		Query query;
		List<Data_type> all_data_data_type;
//		all_data_data_type
		ButtonGroup temp_bg = new ButtonGroup();
		try {
			queryString = "SELECT a FROM Data_type a where a.id>1";
			query = GetYears.em.createQuery(queryString);
			all_data_data_type = query.getResultList();
			Map<String, String> options = new LinkedHashMap<String, String>();
			 for (Iterator data = all_data_data_type.iterator(); data.hasNext();) {
		    	   Data_type datas = (Data_type) data.next();
//		    	   datas.
		    	   options.put(String.valueOf(datas.getId()), datas.getName());
			}			
			
			String json = new Gson().toJson(options);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;

	}
}
