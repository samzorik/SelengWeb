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

@WebServlet("/getYears")
public class GetYears extends HttpServlet {

    public static EntityManager em = Persistence.createEntityManagerFactory("SelengaWeb").createEntityManager();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryString;
		Query query;
		List<Integer> all_years;
		ButtonGroup temp_bg = new ButtonGroup();
		try {
			queryString = "SELECT a.year FROM Fact_table a group by a.year order by a.year";
			query = em.createQuery(queryString);
			all_years = query.getResultList();
			String json = new Gson().toJson(all_years);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;

	}
}
