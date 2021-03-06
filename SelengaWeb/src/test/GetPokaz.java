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

import Entities.*;

@WebServlet("/getPokaz")
public class GetPokaz extends HttpServlet {

//    public static EntityManager em = Persistence.createEntityManagerFactory("SelengaWeb").createEntityManager();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryString;
		Query query;
		List<Pokazateli> all_data_group_pokaz;
		String id=request.getParameter("id");
//		all_data_data_type
		ButtonGroup temp_bg = new ButtonGroup();
		try {
			queryString = "SELECT a FROM Pokazateli a where a.gp.id="+id;
			query = GetYears.em.createQuery(queryString);
			Map<String, String> options = new LinkedHashMap<String, String>();
			all_data_group_pokaz = query.getResultList();
		       for (Iterator data = all_data_group_pokaz.iterator(); data.hasNext();) {
		    	   Pokazateli datas = (Pokazateli) data.next();
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
