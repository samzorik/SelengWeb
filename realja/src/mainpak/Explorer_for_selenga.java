  /** for serialization */
package mainpak;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import org.xml.sax.InputSource;
import javax.persistence.*;
import Entities.*;


import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.gui.beans.PluginManager;
import weka.gui.explorer.Explorer;
import weka.gui.explorer.ExplorerDefaults;
import weka.gui.explorer.Explorer.ExplorerPanel;
import weka.gui.explorer.Explorer.LogHandler;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Vector;

public class Explorer_for_selenga extends Explorer implements ActionListener {

		/**
		 * @param args
		 */
	    public static EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();
	    
	    List<Group_pokaz> all_data_group_pokaz;
	    List<Fact_table> all_data_fact_table;
	    List<Data_type> all_data_data_type;
	    List<Pokazateli> all_data_pokazateli;
	    List<Measurement_units> all_data_mu;
	    List<Integer> all_years;
	    String queryString;
	    Query query;
		protected JTabbedPane TabbedPane = new JTabbedPane();
		 protected Vector<ExplorerPanel> Panels = new Vector<ExplorerPanel>();
		static Statement stmt = null;
		static Connection c = null;
		static ResultSet rs;
		JPanel show_group_pokaz;
		JPanel button_panel = new JPanel();
		static Vector<Vector<String>> data = new Vector<Vector<String>>();
		JPanel show_data_type = new JPanel();
		JPanel show_year = new JPanel();
		JPanel show_sp = new JPanel();
		JPanel show_pokaz = new JPanel();
		JPanel select_data = new JPanel();
		JPanel edit_data = new JPanel();
		JPanel pokaz_and_buttons = new JPanel();
		HashSet<Integer> selected_pokaz = new HashSet(), selected_years = new HashSet(), selected_sps = new HashSet();
		HashMap<Integer, String> sp_id_name = new HashMap();
		Add_pokaz add_pokaz_form;
		int selected_year = -1 , selected_sp = -1;
		
		JRB_data_type by_year = new JRB_data_type(),by_sp = new JRB_data_type(),by_district = new JRB_data_type();
		
		JFrame frame = new JFrame(
				"������� �����, �������� � ������� �����-������������� ����������� ������������� ������");
		JButton check_all = new JButton("������� ���");
		JButton show = new JButton("��������� ��������� ����������");
		JButton load_for_analyze = new JButton("��������� ������ ��� �������");
		JButton add_year = new JButton("�������� ���");
		JButton add_pokaz_button = new JButton("�������� ����� ����������");
		JButton delete_pokaz_button = new JButton("������� ��������� ����������");
		JPanel edit_buttons = new JPanel();
		JTable edit_table = new JTable();
		javax.swing.Timer timer;
		JRB last_group;
		public DatabaseTableModel model=new DatabaseTableModel();

		public void connect() {
			try {

				Class.forName("org.postgresql.Driver");

			} catch (ClassNotFoundException e) {

				System.out.println("Where is your PostgreSQL JDBC Driver? "
						+ "Include in your library path!");
				e.printStackTrace();
				return;

			}

			System.out.println("PostgreSQL JDBC Driver Registered!");

			c = null;

			try {

				c = DriverManager.getConnection(
						"jdbc:postgresql://127.0.0.1:5432/selenga", "postgres",
						"178");

			} catch (SQLException e) {

				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;

			}

			if (c != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		}

		public class JRB extends JRadioButton {
			public int tag;
		}

		public class JCB extends JCheckBox {
			public int tag;
		}
		public class JRB_data_type extends JRadioButton {
			public int tag;
		}		
		public class JCB_sp extends JCheckBox {
			public int tag;
		}
		public class JCB_year extends JCheckBox {
			public int tag;
		}
		public class JRB_sp extends JRadioButton {
			public int tag;
		}
		public class JRB_year extends JRadioButton {
			public int tag;
		}

		public class DatabaseTableModel extends DefaultTableModel {
			public Map<Integer,Integer> row_pokaz_id = new HashMap();
			public Map<Integer,Integer> column_year = new HashMap();
			public boolean by_year = false;
			public int selected_year = -1;
			public int selected_sp = -1;
			public Connection conn;
			private static final long serialVersionUID = 1L;
			String[] columnNames = {}; 
			public HashSet<Integer> not_editable_row = new HashSet();

			public DatabaseTableModel(byte[] data, int cells) {
				super();
			}

			public DatabaseTableModel() {
				super();
			}
			@Override
			public void setValueAt(Object cell, int row, int column) {
				((Vector) dataVector.get(row)).set(column, cell);
				try {
					em.getTransaction().begin();
					if (!cell.equals(""))
					{
						
						int pokaz_id = row_pokaz_id.get(row);
						int year = column_year.get(column);
						if (by_year)
						{
							queryString = "SELECT a FROM Fact_table a where a.pokaz.id = "+pokaz_id+" and a.year = "+selected_year+" and a.data_type.id = "+year;
							
							query = em.createQuery(queryString);	
							all_data_fact_table = query.getResultList();
							
							int data_type = year;
//							ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+selected_year+" and data_type = "+data_type);
							
							if (!(all_data_fact_table.isEmpty()))
							{
//								st.executeUpdate("update fact_table set value = "+cell+" where pokaz = "+pokaz_id+" and year ="+selected_year+" and data_type = "+data_type);
								
								all_data_fact_table.get(0).setValue(Double.valueOf((String) cell));
								em.merge(all_data_fact_table.get(0));
							}
							else
							{
								Fact_table tt=new Fact_table(selected_year,em.find(Pokazateli.class, pokaz_id),em.find(Data_type.class, data_type),Double.valueOf((String) cell));
								em.persist(tt);
//								st.executeUpdate("insert into fact_table(year,pokaz,value,data_type) values ("+selected_year+","+pokaz_id+","+cell+","+data_type+")");
							}
						}
						else
						{
							if (selected_sp != -1)
							{
//								int data_type = year;
//								ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year+" and data_type = "+selected_sp);
								
								queryString = "SELECT a FROM Fact_table a where a.pokaz.id = "+pokaz_id+" and a.year = "+year+" and a.data_type.id = "+selected_sp;
								
								query = em.createQuery(queryString);	
								all_data_fact_table = query.getResultList();
								
								if (!(all_data_fact_table.isEmpty()))
								{
//									st.executeUpdate("update fact_table set value = "+cell+" where pokaz = "+pokaz_id+" and year ="+year+" and data_type = "+selected_sp);
									all_data_fact_table.get(0).setValue(Double.valueOf((String) cell));
									em.merge(all_data_fact_table.get(0));
								}
								else
								{
//									st.executeUpdate("insert into fact_table(year,pokaz,value,data_type) values ("+year+","+pokaz_id+","+cell+","+selected_sp+")");
									Fact_table tt=new Fact_table(year,em.find(Pokazateli.class, pokaz_id),em.find(Data_type.class, selected_sp),Double.valueOf((String) cell));
									em.persist(tt);								
								}
							}
							else
							{
//								ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year+" and data_type = 0");
                                queryString = "SELECT a FROM Fact_table a where a.pokaz.id = "+pokaz_id+" and a.year = "+year+" and a.data_type.id = 0";
								query = em.createQuery(queryString);	
								all_data_fact_table = query.getResultList();
								if (all_data_fact_table.size()>0)
								{
//									st.executeUpdate("update fact_table set value = "+cell+" where pokaz = "+pokaz_id+" and year ="+year+" and data_type = 0");
									all_data_fact_table.get(0).setValue(Double.valueOf((String) cell));
									em.merge(all_data_fact_table.get(0));
								}
								else
								{
//									st.executeUpdate("insert into fact_table(year,pokaz,value,data_type) values ("+year+","+pokaz_id+","+cell+", 0 )");
									Fact_table tt=new Fact_table(year,em.find(Pokazateli.class, pokaz_id),em.find(Data_type.class, 0),Double.valueOf((String) cell));
									em.persist(tt);	
								}
							}
						}
					}
					else
					{
						int pokaz_id = row_pokaz_id.get(row);
						int year = column_year.get(column);
						if (by_year)
						{
							int data_type = year;
//							ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year);
//							ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+selected_year+" and data_type = "+data_type);
							 queryString = "SELECT a FROM Fact_table a where a.pokaz.id = "+pokaz_id+" and a.year = "+selected_year+" and a.data_type.id = "+data_type;
								query = em.createQuery(queryString);	
								all_data_fact_table = query.getResultList();
							if (all_data_fact_table.size()>0)
							{
//								st.executeUpdate("delete from fact_table where pokaz = "+pokaz_id+" and year ="+selected_year+" and data_type = "+data_type);
								em.remove(all_data_fact_table.get(0));
							}
						}
						else
						{
							if (selected_sp != -1)
							{
//								int data_type = year;
//								ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year+" and data_type = "+selected_sp);
								queryString = "SELECT a FROM Fact_table a where a.pokaz.id = "+pokaz_id+" and a.year = "+year+" and a.data_type.id = "+selected_sp;
								query = em.createQuery(queryString);	
								all_data_fact_table = query.getResultList();
								if (all_data_fact_table.size()>0)
								{
//										st.executeUpdate("delete from fact_table where pokaz = "+pokaz_id+" and year ="+selected_year+" and data_type = "+data_type);
									em.remove(all_data_fact_table.get(0));
								}
							}
							else
							{
//								ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year+" and data_type = 0");
								queryString = "SELECT a FROM Fact_table a where a.pokaz.id = "+pokaz_id+" and a.year = "+year+" and a.data_type.id = 0";
								query = em.createQuery(queryString);	
								all_data_fact_table = query.getResultList();
								if (all_data_fact_table.size()>0)
								{
//										st.executeUpdate("delete from fact_table where pokaz = "+pokaz_id+" and year ="+selected_year+" and data_type = "+data_type);
									em.remove(all_data_fact_table.get(0));
								}
//								if (rs.next())
//								{
//									st.executeUpdate("delete from fact_table where pokaz = "+pokaz_id+" and year ="+year+" and data_type = 0");
//								}
							}
						}
					}
					em.getTransaction().commit();	
					em.clear();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				fireTableDataChanged();
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if ((columnIndex == 0)||(not_editable_row.contains(rowIndex)))
					return false;
				return true;
			}
		}

		public Explorer_for_selenga() throws SQLException {
//			super(); 
//			m_PreprocessPanel.setInstances(inst)
			frame.setSize(1300, 700);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			Vector<String> columnNames = new Vector<String>();
			by_district.setSelected(true);
			by_district.addActionListener(this);
			by_year.addActionListener(this);
			by_sp.addActionListener(this);
			columnNames.add("���");
			columnNames.add("�������� ����������");
			columnNames.add("��������");
			select_data = new JPanel();
			button_panel.setLayout(new GridLayout(1, 3, 5, 5));
			button_panel.add(check_all);
			button_panel.add(add_pokaz_button);			
			button_panel.add(delete_pokaz_button);			
			button_panel.add(show);
			check_all.addActionListener(this);
			show.addActionListener(this);
			add_pokaz_button.addActionListener(this);
			delete_pokaz_button.addActionListener(this);
			add_year.addActionListener(this);
			load_for_analyze.addActionListener(this);
			select_data.setLayout(new BorderLayout());
			JPanel option_selection = new JPanel();
			option_selection.setLayout(new BorderLayout());
			JPanel main_option_selection = new JPanel();
			show.setEnabled(false);
			add_pokaz_button.setEnabled(true);
			delete_pokaz_button.setEnabled(true);
			main_option_selection.setLayout(new BorderLayout());
			show_data_type.setBorder(BorderFactory
					.createTitledBorder("������� ������ ��:"));
			show_data_type.setLayout(new GridLayout(1,3,5,5));
			ButtonGroup select_data_type = new ButtonGroup();
			by_year.setText("�� ����");
			by_sp.setText("�� ��������� ���������");
			by_district.setText("�� ������ � �����");
			select_data_type.add(by_year);
			select_data_type.add(by_sp);
			select_data_type.add(by_district);
			show_data_type.add(by_year);
			show_data_type.add(by_sp);
			show_data_type.add(by_district);
			show_year.setBorder(BorderFactory.createTitledBorder("���"));
			ButtonGroup select_year = new ButtonGroup(), select_sp=new ButtonGroup();
			show_sp.setBorder(BorderFactory.createTitledBorder("�������� ���������"));
			main_option_selection.add(show_data_type,BorderLayout.NORTH);
			main_option_selection.add(show_year,BorderLayout.CENTER);
			main_option_selection.add(show_sp,BorderLayout.SOUTH);
			option_selection.add(main_option_selection,BorderLayout.NORTH);
			show_group_pokaz = new JPanel();
			option_selection.add(show_group_pokaz,BorderLayout.CENTER);
			show_group_pokaz.setBorder(BorderFactory
					.createTitledBorder("������ �����������"));
			show_pokaz.setBorder(BorderFactory.createTitledBorder("����������"));
			show_group_pokaz.setLayout(new GridLayout(4, 3, 5, 5));
			ButtonGroup groups = new ButtonGroup();			
			connect();
			
			String queryString = "SELECT a FROM Group_pokaz a where a.is_sp = 0";
			Query query = em.createQuery(queryString);	
			
			all_data_group_pokaz = query.getResultList();
		       for (Iterator data = all_data_group_pokaz.iterator(); data.hasNext();) {
		    	   Group_pokaz datas = (Group_pokaz) data.next();
//		    	   datas.
		    	   JRB temp = this.new JRB();
					temp.setText(datas.getName());
					temp.tag = datas.getId(); 
					groups.add(temp);
					temp.addActionListener(this);
					show_group_pokaz.add(temp, BorderLayout.NORTH);
			}	
			
			select_data.add(option_selection, BorderLayout.NORTH);
			select_data.add(show_pokaz, BorderLayout.CENTER);
			select_data.add(button_panel, BorderLayout.SOUTH);

			TabbedPane.addTab("����� ������", null, select_data, "");
			TabbedPane.addTab("���� � �������������� ������", null, edit_data, "");
			TabbedPane.addTab(m_PreprocessPanel.getTabTitle(), null,
			        m_PreprocessPanel, m_PreprocessPanel.getTabTitleToolTip());
			TabbedPane.setEnabledAt(TabbedPane.getTabCount()-1, false);
			 String[] tabs = ExplorerDefaults.getTabs();
//			for (int i = 0; i < m_Panels.size(); i++) {
//				m_TabbedPane.add((Component)m_Panels.get(i));
//			}			
			 Hashtable<String, HashSet> tabOptions = new Hashtable<String, HashSet>();
			    for (int i = 0; i < tabs.length; i++) {
			      try {
			        // determine classname and additional options
			        String[] optionsStr = tabs[i].split(":");
			        String classname = optionsStr[0];
			        if (PluginManager.isInDisabledList(classname)) {
			          continue;
			        }
			        HashSet options = new HashSet();
			        tabOptions.put(classname, options);
			        for (int n = 1; n < optionsStr.length; n++)
			          options.add(optionsStr[n]);

			        // setup panel
			        ExplorerPanel panel = (ExplorerPanel) Class.forName(classname)
			            .newInstance();
			        panel.setExplorer(this);
			        Panels.add(panel);
			        if (panel instanceof LogHandler)
			          ((LogHandler) panel).setLog(m_LogPanel);
			        TabbedPane.addTab(panel.getTabTitle(), null, (JPanel) panel,
			            panel.getTabTitleToolTip());
			        TabbedPane.setEnabledAt(TabbedPane.getTabCount()-1, false);
			      } catch (Exception e) {
			        e.printStackTrace();
			      }
			    }
			
			frame.add(TabbedPane);
			frame.setVisible(true);
			groups.getSelection();
//			m_PreprocessPanel.
			m_PreprocessPanel.addPropertyChangeListener(new PropertyChangeListener() {
			      @Override
			      public void propertyChange(PropertyChangeEvent e) {
			        for (int i = 0; i < Panels.size(); i++) {
			          Panels.get(i).setInstances(m_PreprocessPanel.getInstances());
			          TabbedPane.setEnabledAt(i + 2, true);
			        }
			      }
			    });
			timer = new javax.swing.Timer( 100, new ActionListener()
			  {
			      public void actionPerformed(ActionEvent e)
			      {
			    	  if (by_year.isSelected() || by_sp.isSelected())
			    	  {
				    	  if(selected_year == -1 && selected_sp == -1)
				    	  {
				    		  show.setEnabled(false);
				    	  }
				    	  if(selected_sps.isEmpty() && selected_years.isEmpty())
				    	  {
				    		  show.setEnabled(false);
				    	  }
			    	  }
			    	  if (selected_pokaz.isEmpty())
			    	  {
			    		  delete_pokaz_button.setEnabled(false);
			    		  show.setEnabled(false);
			    	  }
			    	  else
			    	  {
			    		  delete_pokaz_button.setEnabled(true);
			    		  if (by_year.isSelected() || by_sp.isSelected())
			    		  {
			    			  if(selected_year != -1 || selected_sp != -1)
			    			  {
			    				  if (!selected_sps.isEmpty() || !selected_years.isEmpty())
			    				  show.setEnabled(true);
			    			  }
			    		  }
			    		  else
			    		  {
			    			  show.setEnabled(true);
			    		  }
			    	  }
			      }
			  } );
			timer.start();
		}

		public static void main(String[] args)  {
			// TODO Auto-generated method stub
			try {
				Explorer_for_selenga rrr = new Explorer_for_selenga();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void change_data_type(ActionEvent arg0)
		{
			selected_pokaz.clear();
			show_sp.removeAll();
			show_year.removeAll();
			show_group_pokaz.removeAll();
			frame.repaint();
			frame.revalidate();
			show.setEnabled(false);
			selected_sp = -1;
			selected_sps.clear();
			selected_year= -1;
			selected_years.clear();
//			arg0.
			ButtonGroup groups = new ButtonGroup();
			try {
				if (by_district.isSelected())
				{
//					stmt = c.createStatement();
//					rs = stmt.executeQuery("SELECT * FROM group_pokaz where is_sp=0;");
//					while (rs.next()) {
//						JRB temp = this.new JRB();
//						temp.setText(rs.getString("name"));
//						temp.tag = rs.getInt("id");
//						groups.add(temp);
//						temp.addActionListener(this);
//						show_group_pokaz.add(temp, BorderLayout.NORTH);
//					}
//					rs.close();
					
					String queryString = "SELECT a FROM Group_pokaz a where a.is_sp = 0";
					Query query = em.createQuery(queryString);	
					
					all_data_group_pokaz = query.getResultList();
				       for (Iterator data = all_data_group_pokaz.iterator(); data.hasNext();) {
				    	   Group_pokaz datas = (Group_pokaz) data.next();
//				    	   datas.
				    	   JRB temp = this.new JRB();
							temp.setText(datas.getName());
							temp.tag = datas.getId(); 
							groups.add(temp);
							temp.addActionListener(this);
							show_group_pokaz.add(temp, BorderLayout.NORTH);
					}	
					
					frame.repaint();
					frame.revalidate();
					
					((JRB)(show_group_pokaz.getComponent(0))).setSelected(true);
				    ActionEvent event=new ActionEvent(show_group_pokaz.getComponent(0), 1001, "com"); // ��������� ���������� ����������� �������
				    actionPerformed(event);
				} 
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (by_sp.isSelected())
			{
				ButtonGroup temp_bg = new ButtonGroup();
				try {					
					String queryString = "SELECT a FROM Data_type a where a.id>1";
					Query query = em.createQuery(queryString);	
					show_sp.setLayout(new GridLayout(2,8,5,5));
					all_data_data_type= query.getResultList();
				       for (Iterator data = all_data_data_type.iterator(); data.hasNext();) {
				    	   Data_type datas = (Data_type) data.next();
//				    	   datas.
				    	   JRB_sp temp = this.new JRB_sp();
							temp.setText(datas.getName());
							temp_bg.add(temp);
							temp.tag = datas.getId();
							sp_id_name.put(datas.getId(), datas.getName());
							temp.addActionListener(this);
							show_sp.add(temp);
							show_sp.setVisible(true);
					}	
					
//					
//					
//					stmt=c.createStatement();
//					rs = stmt.executeQuery("SELECT year FROM fact_table group by year order by year");
//					
//					while (rs.next())
//					{
//						JCB_year temp = this.new JCB_year();
//						temp.setText(rs.getString("year"));
//						temp.tag = rs.getInt("year");
//						temp.addActionListener(this);
//						show_year.add(temp);
//						show_year.setVisible(true);
//					}
					
					queryString = "SELECT a.year FROM Fact_table a group by a.year order by a.year";
					query = em.createQuery(queryString);	
					show_sp.setLayout(new GridLayout(2,8,5,5));
					all_years = query.getResultList();
				       for (Iterator data = all_years.iterator(); data.hasNext();) {
				    	   Integer datas = (Integer) data.next();
//				    	   datas.
				    	   JCB_year temp = this.new JCB_year();
							temp.setText(Integer.toString(datas));
							temp.tag = datas;
							temp.addActionListener(this);
							show_year.add(temp);
							show_year.setVisible(true);
					}	
					
					
//					stmt = c.createStatement();
//					
//					rs = stmt.executeQuery("SELECT * FROM group_pokaz where is_sp=1;");
//					while (rs.next()) {
//						JRB temp = this.new JRB();
//						temp.setText(rs.getString("name"));
//						temp.tag = rs.getInt("id");
//						groups.add(temp);
//						temp.addActionListener(this);
//						show_group_pokaz.add(temp, BorderLayout.NORTH);
//					}
//					rs.close();
					
					queryString = "SELECT a FROM Group_pokaz a where a.is_sp=1";
					query = em.createQuery(queryString);	
					all_data_group_pokaz = query.getResultList();
				       for (Iterator data = all_data_group_pokaz.iterator(); data.hasNext();) {
				    	   Group_pokaz datas = (Group_pokaz) data.next();
//				    	   datas.
				    	   JRB temp = this.new JRB();
							temp.setText(datas.getName());
							temp.tag = datas.getId();
							groups.add(temp);
							temp.addActionListener(this);
							show_group_pokaz.add(temp, BorderLayout.NORTH);
					}	
					
					
					frame.repaint();
					frame.revalidate();
//					rs.close();
					((JRB)(show_group_pokaz.getComponent(0))).setSelected(true);
				    ActionEvent event=new ActionEvent(show_group_pokaz.getComponent(0), 1001, "com"); // ��������� ���������� ����������� �������
				    actionPerformed(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
			if (by_year.isSelected())
			{
				ButtonGroup temp_bg = new ButtonGroup();
				try {
//					stmt=c.createStatement();
//					rs = stmt.executeQuery("SELECT year FROM fact_table group by year order by year");
//					show_sp.setLayout(new GridLayout(2,8,5,5));
//					while (rs.next())
//					{
//						JRB_year temp = this.new JRB_year();
//						temp.setText(rs.getString("year"));
//						temp.tag = rs.getInt("year");
//						temp_bg.add(temp);
//						
//						temp.addActionListener(this);
//						show_year.add(temp);
//						show_year.setVisible(true);
//					}
					
					queryString = "SELECT a.year FROM Fact_table a group by a.year order by a.year";
					query = em.createQuery(queryString);	
					show_sp.setLayout(new GridLayout(2,8,5,5));
					all_years = query.getResultList();
				       for (Iterator data = all_years.iterator(); data.hasNext();) {
				    	   Integer datas = (Integer) data.next();
//				    	   datas.
				    	   JRB_year temp = this.new JRB_year();
							temp.setText(Integer.toString(datas));
							temp.tag = datas;
							temp_bg.add(temp);
							
							temp.addActionListener(this);
							show_year.add(temp);
							show_year.setVisible(true);
					}	
					
					
//					stmt=c.createStatement();
//					rs = stmt.executeQuery("SELECT * FROM data_type where id > 1");
//					show_sp.setLayout(new GridLayout(2,8,5,5));
//					while (rs.next())
//					{
//						JCB_sp temp = this.new JCB_sp();
//						temp.setText(rs.getString("name"));
//						temp.tag = rs.getInt("id");
//						temp.addActionListener(this);
//						sp_id_name.put(rs.getInt("id"), rs.getString("name"));
//						show_sp.add(temp);
//						show_sp.setVisible(true);
//					}
					
					String queryString = "SELECT a FROM Data_type a where a.id>1";
					Query query = em.createQuery(queryString);	
					show_sp.setLayout(new GridLayout(2,8,5,5));
					all_data_data_type= query.getResultList();
				       for (Iterator data = all_data_data_type.iterator(); data.hasNext();) {
				    	   Data_type datas = (Data_type) data.next();
//				    	   datas.
				    	   JCB_sp temp = this.new JCB_sp();
							temp.setText(datas.getName());
							temp.tag = datas.getId();
							temp.addActionListener(this);
							sp_id_name.put(datas.getId(), datas.getName());
							show_sp.add(temp);
							show_sp.setVisible(true);
					}	
					
//					stmt = c.createStatement();
//					rs = stmt.executeQuery("SELECT * FROM group_pokaz where is_sp=1;");
//					while (rs.next()) {
//						JRB temp = this.new JRB();
//						temp.setText(rs.getString("name"));
//						temp.tag = rs.getInt("id");
//						groups.add(temp);
//						temp.addActionListener(this);
//						show_group_pokaz.add(temp, BorderLayout.NORTH);
//					}
//					rs.close();
					
					queryString = "SELECT a FROM Group_pokaz a where a.is_sp=1";
					query = em.createQuery(queryString);	
					all_data_group_pokaz = query.getResultList();
				       for (Iterator data = all_data_group_pokaz.iterator(); data.hasNext();) {
				    	   Group_pokaz datas = (Group_pokaz) data.next();
//				    	   datas.
				    	   JRB temp = this.new JRB();
							temp.setText(datas.getName());
							temp.tag = datas.getId();
							groups.add(temp);
							temp.addActionListener(this);
							show_group_pokaz.add(temp, BorderLayout.NORTH);
					}	
					
					
					frame.repaint();
					frame.revalidate();
					
					((JRB)(show_group_pokaz.getComponent(0))).setSelected(true);
				    ActionEvent event=new ActionEvent(show_group_pokaz.getComponent(0), 1001, "com"); // ��������� ���������� ����������� �������
				    actionPerformed(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		
		}
		public static int trunc(double d) {
			return ((int)d)%10;
			}
		public void show_button_click(ActionEvent arg0)
		{

			model=new DatabaseTableModel();
			model.conn=c;
			edit_table = new JTable(model);	
			 final JPopupMenu popupMenu = new JPopupMenu();
	          edit_table.setComponentPopupMenu(popupMenu);
            model.addColumn("����������");
            if (by_district.isSelected())
            {
				try {
					stmt=c.createStatement();
					model.selected_sp = -1;
					model.selected_year = -1;
					Integer []sel =selected_pokaz.toArray(new Integer [0]);
					queryString = "SELECT a FROM Fact_table a where ";
					for (int i = 0; i < sel.length; i++) {
						queryString+=" a.pokaz.id="+sel[i]+" or";
					}
					queryString=queryString.substring(0, queryString.length()-2);
					
					query = em.createQuery(queryString);	
					all_data_fact_table = query.getResultList();
					HashSet<Integer> years = new HashSet();
					HashSet<Pokazateli> pokazat = new HashSet();
					HashSet<Group_pokaz> gp = new HashSet();
					HashSet<Pokazateli> pokaz_by_gp = new HashSet();
					
				       for (Iterator data = all_data_fact_table.iterator(); data.hasNext();) {
				    	   Fact_table datas = (Fact_table) data.next();
//				    	   datas.
				    	   years.add(datas.getYear());
				    	   pokazat.add(datas.getPokaz());
				    	   gp.add(datas.getPokaz().getGp());
					}	
					Integer []years_array =years.toArray(new Integer [0]);
				    
					for (int i = 0; i < years_array.length; i++) {
						model.addColumn(Integer.toString(years_array[i]));
						model.column_year.put(model.getColumnCount()-1, years_array[i]);
					}
					
					Group_pokaz []gp_array =gp.toArray(new Group_pokaz[0]);
					
					for (int i = 0; i < gp_array.length; i++) {

						String cur_group = gp_array[i].getName();
						Vector<Object> temp_dat = new Vector();
						Statement stmt2 = c.createStatement();
						temp_dat.add(cur_group);
						for (int j = 1; j < model.getColumnCount(); j++) {
							temp_dat.add("*");
						}
						model.addRow(temp_dat);
						model.not_editable_row.add(model.getRowCount()-1);
						
						Pokazateli []pokaz_array =pokazat.toArray(new Pokazateli[0]);
						pokaz_by_gp.clear();
						for (int j = 0; j < pokaz_array.length; j++) {
							if (pokaz_array[j].getGp()==gp_array[i])
							{
								pokaz_by_gp.add(pokaz_array[j]);
							}
						}
						Pokazateli []temp_pokaz =pokaz_by_gp.toArray(new Pokazateli[0]);
						for (int j = 0; j < temp_pokaz.length; j++) {

							int pokaz_id = temp_pokaz[j].getId();
							String pokaz_name = temp_pokaz[j].getName();
							String mu = temp_pokaz[j].getMu().getName();
							
							ArrayList<Fact_table> temp_fact =new ArrayList();
							for (Iterator data = all_data_fact_table.iterator(); data.hasNext();) {
						    	   Fact_table datas = (Fact_table) data.next();
						    	   if (datas.getPokaz()==temp_pokaz[j])
						    	   {
						    		   temp_fact.add(datas);						    		   
						    	   }
							}	
							
							Vector<Object> temp_data = new Vector();
							temp_data.setSize(model.getColumnCount());
							temp_data.set(0, "    "+pokaz_name+", "+mu);
							for (int z = 1; z < temp_data.size(); z++) {
								temp_data.set(z, "");
							}
							for (int k = 0; k < temp_fact.size(); k++) {
								int year= temp_fact.get(k).getYear();
								for (int m = 1; m < model.getColumnCount(); m++) {
									if (model.column_year.get(m) == year)
									{
										double val=temp_fact.get(k).getValue();
										double tempval;
										tempval=val*10;
										if(trunc(tempval)==9 || trunc(tempval)==0)
										{
											val=Math.round(val);
										}
										temp_data.set(m,val);
									}
								}
							}
							model.addRow(temp_data);
							model.row_pokaz_id.put(model.getRowCount()-1, pokaz_id);
						
						}					
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				edit_data.removeAll();
				edit_data.setLayout(new BorderLayout());
				edit_data.add(new JScrollPane(edit_table),BorderLayout.CENTER);
				edit_buttons.add(add_year);
				edit_buttons.add(load_for_analyze);
				edit_data.add(edit_buttons,BorderLayout.SOUTH);
				edit_data.repaint();
				frame.repaint();
				frame.revalidate();
				return;
            }	
            
            
            if (by_year.isSelected())
            {
            	try {
					model.by_year = true;
					model.selected_year = selected_year;
					Integer []sel =selected_pokaz.toArray(new Integer [0]);
					Integer []sel_sp = selected_sps.toArray(new Integer[0]);
					
					queryString = "SELECT a FROM Fact_table a where ";
					for (int i = 0; i < sel.length; i++) {
						queryString+=" a.pokaz.id="+sel[i]+" or";
					}
					queryString=queryString.substring(0, queryString.length()-2);
					queryString+=") and (";
					
					for (int i = 0; i < sel_sp.length; i++) {
						queryString+=" a.data_type.id="+sel_sp[i]+" or";
						model.addColumn(sp_id_name.get(sel_sp[i]));
						model.column_year.put(model.getColumnCount()-1, sel_sp[i]);
					}
					queryString=queryString.substring(0, queryString.length()-2);
					queryString+=")";
					
					
					query = em.createQuery(queryString);	
					
					all_data_fact_table = query.getResultList();
					
					HashSet<Integer> years = new HashSet();
					HashSet<Pokazateli> pokazat = new HashSet();
					HashSet<Group_pokaz> gp = new HashSet();
					HashSet<Pokazateli> pokaz_by_gp = new HashSet();
					
				       for (Iterator data = all_data_fact_table.iterator(); data.hasNext();) {
				    	   Fact_table datas = (Fact_table) data.next();
				    	   years.add(datas.getYear());
				    	   pokazat.add(datas.getPokaz());
				    	   gp.add(datas.getPokaz().getGp());
					}	
					Integer []years_array =years.toArray(new Integer [0]);
					Group_pokaz []gp_array =gp.toArray(new Group_pokaz[0]);
					
					for (int i = 0; i < gp_array.length; i++) {

						String cur_group = gp_array[i].getName();
						Vector<Object> temp_dat = new Vector();
						temp_dat.add(cur_group);
						for (int j = 1; j < model.getColumnCount(); j++) {
							temp_dat.add("*");
						}
						model.addRow(temp_dat);
						model.not_editable_row.add(model.getRowCount()-1);
						
						Pokazateli []pokaz_array =pokazat.toArray(new Pokazateli[0]);
						pokaz_by_gp.clear();
						for (int j = 0; j < pokaz_array.length; j++) {
							if (pokaz_array[j].getGp()==gp_array[i])
							{
								pokaz_by_gp.add(pokaz_array[j]);
							}
						}
						Pokazateli []temp_pokaz =pokaz_by_gp.toArray(new Pokazateli[0]);
						for (int j = 0; j < temp_pokaz.length; j++) {

							int pokaz_id = temp_pokaz[j].getId();
							String pokaz_name = temp_pokaz[j].getName();
							String mu = temp_pokaz[j].getMu().getName();
							ArrayList<Fact_table> temp_fact =new ArrayList();
							for (Iterator data = all_data_fact_table.iterator(); data.hasNext();) {
						    	   Fact_table datas = (Fact_table) data.next();
						    	   if (datas.getPokaz()==temp_pokaz[j])
						    	   {
						    		   temp_fact.add(datas);						    		   
						    	   }
							}	
							Vector<Object> temp_data = new Vector();
							temp_data.setSize(model.getColumnCount());
							temp_data.set(0, "    "+pokaz_name+", "+mu);
							for (int k = 1; k < temp_data.size(); k++) {
								temp_data.set(k, "");
							}
							for (int k = 0; k < temp_fact.size(); k++) {
								int year= temp_fact.get(k).getData_type().getId();
								for (int m = 1; m < model.getColumnCount(); m++) {
									if (model.column_year.get(m) == year)
									{
										temp_data.set(m,temp_fact.get(k).getValue());
									}
								}
							}
							model.addRow(temp_data);
							model.row_pokaz_id.put(model.getRowCount()-1, pokaz_id);
						}
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				edit_data.removeAll();
				edit_data.setLayout(new BorderLayout());
				edit_data.add(new JScrollPane(edit_table),BorderLayout.CENTER);
				edit_buttons.removeAll();
				edit_buttons.add(load_for_analyze);
				edit_data.add(edit_buttons,BorderLayout.SOUTH);
				edit_data.repaint();
				frame.repaint();
				frame.revalidate();
            	return;
            }
            
            
            if (by_sp.isSelected())
            {
				try {
					model.by_year = false;
					model.selected_sp = selected_sp;
					Integer []sel =selected_pokaz.toArray(new Integer [0]);
					Integer []sel_year =selected_years.toArray(new Integer [0]);
					Arrays.sort(sel_year);
					queryString = "SELECT a FROM Fact_table a where a.data_type.id = "+selected_sp+" and (";
					for (int i = 0; i < sel.length; i++) {
						queryString+=" a.pokaz.id="+sel[i]+" or";
					}
					queryString=queryString.substring(0, queryString.length()-2);
					queryString+=")";
					
					for (int i = 0; i < sel_year.length; i++) {
						model.addColumn(Integer.toString(sel_year[i]));
						model.column_year.put(model.getColumnCount()-1, sel_year[i]);
					}
					
					
					query = em.createQuery(queryString);	
					
					all_data_fact_table = query.getResultList();
					
					HashSet<Integer> years = new HashSet();
					HashSet<Pokazateli> pokazat = new HashSet();
					HashSet<Group_pokaz> gp = new HashSet();
					HashSet<Pokazateli> pokaz_by_gp = new HashSet();
					
					 for (Iterator data = all_data_fact_table.iterator(); data.hasNext();) {
				    	   Fact_table datas = (Fact_table) data.next();
				    	   years.add(datas.getYear());
				    	   pokazat.add(datas.getPokaz());
				    	   gp.add(datas.getPokaz().getGp());
					}	
					 
					Integer []years_array =years.toArray(new Integer [0]);
					Group_pokaz []gp_array =gp.toArray(new Group_pokaz[0]);
					
					for (int i = 0; i < gp_array.length; i++) {

						String cur_group = gp_array[i].getName();
						Vector<Object> temp_dat = new Vector();
						temp_dat.add(cur_group);
						for (int j = 1; j < model.getColumnCount(); j++) {
							temp_dat.add("*");
						}
						model.addRow(temp_dat);
						model.not_editable_row.add(model.getRowCount()-1);
						
						Pokazateli []pokaz_array =pokazat.toArray(new Pokazateli[0]);
						pokaz_by_gp.clear();
						for (int j = 0; j < pokaz_array.length; j++) {
							if (pokaz_array[j].getGp()==gp_array[i])
							{
								pokaz_by_gp.add(pokaz_array[j]);
							}
						}
						Pokazateli []temp_pokaz =pokaz_by_gp.toArray(new Pokazateli[0]);
						for (int j = 0; j < temp_pokaz.length; j++) {

							int pokaz_id = temp_pokaz[j].getId();
							String pokaz_name = temp_pokaz[j].getName();
							String mu = temp_pokaz[j].getMu().getName();
							ArrayList<Fact_table> temp_fact =new ArrayList();
							for (Iterator data = all_data_fact_table.iterator(); data.hasNext();) {
						    	   Fact_table datas = (Fact_table) data.next();
						    	   if (datas.getPokaz()==temp_pokaz[j])
						    	   {
						    		   temp_fact.add(datas);						    		   
						    	   }
							}	
							Vector<Object> temp_data = new Vector();
							temp_data.setSize(model.getColumnCount());
							temp_data.set(0, "    "+pokaz_name+", "+mu);
							for (int k = 1; k < temp_data.size(); k++) {
								temp_data.set(k, "");
							}
							for (int k = 0; k < temp_fact.size(); k++) {
								int year= temp_fact.get(k).getYear();
								for (int m = 1; m < model.getColumnCount(); m++) {
									if (model.column_year.get(m) == year)
									{
										temp_data.set(m,temp_fact.get(k).getValue());
									}
								}
							}
							model.addRow(temp_data);
							model.row_pokaz_id.put(model.getRowCount()-1, pokaz_id);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				edit_data.removeAll();
				edit_data.setLayout(new BorderLayout());
				edit_data.add(new JScrollPane(edit_table),BorderLayout.CENTER);
				edit_buttons.add(add_year);
				edit_buttons.add(load_for_analyze);
				edit_data.add(edit_buttons,BorderLayout.SOUTH);
				edit_data.repaint();
				frame.repaint();
				frame.revalidate();
				return;
            }
		
		}
		
		public void load_for_analyze_click(ActionEvent arg0)
		{

			ArrayList<Attribute> attribs = new ArrayList();
			if (model.by_year)
			{
				List<String> values = new ArrayList();
//				java.util.List<E>
				for (int i = 1; i < model.getColumnCount(); i++) {
					values.add(model.getColumnName(i));
				}
//				new att
				attribs.add(new Attribute("�������� ���������",values));
				String header= "";
				int kolattr = 1;
				for (int i = 0; i < model.getRowCount(); i++) {
					if (model.not_editable_row.contains(i))
					{
						header =(String) model.getValueAt(i, 0);
					}
					else
					{
						String name = header+" : "+(String) model.getValueAt(i, 0);
						attribs.add(new Attribute(name));
						kolattr++;
					}
				}
				Instances data = new Instances("������ �� �������� ���������� �� "+model.selected_year+" ���", attribs, 100);
				for (int i = 1; i < model.getColumnCount(); i++) {
					int tempi = 1;
					Instance temp = new DenseInstance(kolattr);
					temp.setValue((Attribute)attribs.get(0), model.getColumnName(i));
					for (int j = 1; j < model.getRowCount(); j++) {
						if (!model.not_editable_row.contains(j))
						{
							try{
							if (!(model.getValueAt(j, i)==null) && (model.getValueAt(j, i) instanceof String))
							{
								temp.setValue(tempi, Float.valueOf((String)model.getValueAt(j, i)));
								tempi++;
							}
							else
							{
								if (!(model.getValueAt(j, i)==null) && (model.getValueAt(j, i) instanceof Double))
								{
									temp.setValue(tempi, (Double)model.getValueAt(j, i));
									tempi++;
								}
								else
								{
									temp.setMissing(tempi);
									tempi++;
								}
							}
							} catch (NumberFormatException e)
							{
								temp.setMissing(tempi);
								tempi++;
							}
						}
					}
					data.add(temp);
				}
				m_PreprocessPanel.setInstances(data);						
			}
			else
			{
				if (model.selected_sp == -1)
				{
					List<String> values = new ArrayList();
//					java.util.List<E>
					for (int i = 1; i < model.getColumnCount(); i++) {
						values.add(model.getColumnName(i));
					}
//					new att
					attribs.add(new Attribute("���",values));
					String header= "";
					int kolattr = 1;
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.not_editable_row.contains(i))
						{
							header =(String) model.getValueAt(i, 0);
						}
						else
						{
							String name = header+" : "+(String) model.getValueAt(i, 0);
							attribs.add(new Attribute(name));
							kolattr++;
						}
					}
					Instances data = new Instances("������ �� ������������� ������", attribs, 100);
					for (int i = 1; i < model.getColumnCount(); i++) {
						int tempi = 1;
						Instance temp = new DenseInstance(kolattr);
						temp.setValue((Attribute)attribs.get(0), model.getColumnName(i));
						for (int j = 1; j < model.getRowCount(); j++) {
							if (!model.not_editable_row.contains(j))
							{
								try{
								if (!(model.getValueAt(j, i)==null) && (model.getValueAt(j, i) instanceof String))
								{
									temp.setValue(tempi, Float.valueOf((String)model.getValueAt(j, i)));
									tempi++;
								}
								else
								{
									if (!(model.getValueAt(j, i)==null) && (model.getValueAt(j, i) instanceof Double))
									{
										temp.setValue(tempi, (Double)model.getValueAt(j, i));
										tempi++;
									}
									else
									{
										temp.setMissing(tempi);
										tempi++;
									}
								}
								} catch (NumberFormatException e)
								{
									temp.setMissing(tempi);
									tempi++;
								}
							}
						}
						data.add(temp);
					}
					m_PreprocessPanel.setInstances(data);
				}
				else
				{
					List<String> values = new ArrayList();
//					java.util.List<E>
					for (int i = 1; i < model.getColumnCount(); i++) {
						values.add(model.getColumnName(i));
					}
//					new att
					attribs.add(new Attribute("���",values));
					String header= "";
					int kolattr = 1;
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.not_editable_row.contains(i))
						{
							header =(String) model.getValueAt(i, 0);
						}
						else
						{
							String name = header+" : "+(String) model.getValueAt(i, 0);
							attribs.add(new Attribute(name));
							kolattr++;
						}
					}
					Instances data = new Instances("������ �� ��������� ��������� "+sp_id_name.get(model.selected_sp), attribs, 100);
					for (int i = 1; i < model.getColumnCount(); i++) {
						int tempi = 1;
						Instance temp = new DenseInstance(kolattr);
						temp.setValue((Attribute)attribs.get(0), model.getColumnName(i));
						for (int j = 1; j < model.getRowCount(); j++) {
							if (!model.not_editable_row.contains(j))
							{
								try{
								if (!(model.getValueAt(j, i)==null) && (model.getValueAt(j, i) instanceof String))
								{
									temp.setValue(tempi, Double.valueOf((String)model.getValueAt(j, i)));
									tempi++;
								}
								else
								{
									if (!(model.getValueAt(j, i)==null) && (model.getValueAt(j, i) instanceof Double))
									{
										temp.setValue(tempi, (Double)model.getValueAt(j, i));
										tempi++;
									}
									else
									{
										temp.setMissing(tempi);
										tempi++;
									}
								}
								} catch (NumberFormatException e)
								{
									temp.setMissing(tempi);
									tempi++;
								}
							}
						}
						data.add(temp);
					}
					m_PreprocessPanel.setInstances(data);
				}
			}
			return;		
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JRB_data_type"))
				{
					change_data_type(arg0);
					return;
				}			
				
				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JRB_year"))
				{
					selected_year = ((JRB_year)arg0.getSource()).tag;
					return;
				}
				
				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JCB_year"))
				{
					int c = ((JCB_year)arg0.getSource()).tag;
					if (((JCB_year)arg0.getSource()).isSelected() )
					{
						selected_years.add(c);
					}
					else
					{
//						selected_years.
						selected_years.remove(c);
					}
					return;
				}

				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JRB_sp"))
				{
					selected_sp = ((JRB_sp)arg0.getSource()).tag;
					return;
				}
				
				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JCB_sp"))
				{
					int c = ((JCB_sp)arg0.getSource()).tag;
					if (((JCB_sp)arg0.getSource()).isSelected() )
					{
						selected_sps.add(c);
					}
					else
					{
						selected_sps.remove(c);
					}
					return;
				}
				
				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JRB"))
				{
					System.out.println("wow!"+((JRB)arg0.getSource()).tag);
					try {
						stmt=c.createStatement();
						rs = stmt.executeQuery("SELECT * FROM pokazateli where group_pokaz_id ="+((JRB)arg0.getSource()).tag);
						show_pokaz.removeAll();
						show_pokaz.setLayout(new GridLayout(4,3,5,5));
						while (rs.next())
						{
							JCB temp = this.new JCB();
							temp.setText(rs.getString("name"));
							temp.tag = rs.getInt("id");
							if (selected_pokaz.contains(temp.tag))
							{
								temp.setSelected(true);
							}
							temp.addActionListener(this);
							show_pokaz.add(temp);
						}
						frame.repaint();
						frame.revalidate();
						rs.close();
						last_group = ((JRB)arg0.getSource());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				
				
				
				
				if (arg0.getSource().getClass().getName().equals("mainpak.Explorer_for_selenga$JCB"))
				{
					int c = ((JCB)arg0.getSource()).tag;
					if (((JCB)arg0.getSource()).isSelected() )
					{
						selected_pokaz.add(c);
					}
					else
					{
						selected_pokaz.remove(c);
					}
					if (selected_pokaz.size()>0)
					{
						show.setEnabled(true);
					}
					else
						show.setEnabled(false);
					return;
				}
				
				
				if (arg0.getSource()==check_all)
				{
//					System.out.println(arg0.getSource().getClass().getName());
					for (int i = 0; i < show_pokaz.getComponentCount(); i++) {
						((JCB)show_pokaz.getComponent(i)).setSelected(true);
						int c = ((JCB)show_pokaz.getComponent(i)).tag;
						selected_pokaz.add(c);
					}
					return;
				}	
				
				
				
				if (arg0.getSource()==show)
				{
					show_button_click(arg0);
					TabbedPane.setSelectedIndex(1);
					return;
				}
				
				
				if (arg0.getSource()==add_year)
				{
					String new_year = JOptionPane.showInputDialog("������� ���:");
					if ((new_year!=null)&&(!new_year.isEmpty()))
					{
						int year=Integer.parseInt(new_year);
						boolean flag=true;
						for (int i = 0; i < model.getColumnCount(); i++) {
							if (model.getColumnName(i).equals(new_year))
							{
								flag=false;
								break;
							}
						}
						if (flag)
						{
						 model.addColumn(new_year);
						 model.column_year.put(model.getColumnCount()-1,year);
						}
					}		
					return;
				}
				
				
				if (arg0.getSource()==load_for_analyze)
				{
					load_for_analyze_click(arg0);
					TabbedPane.setSelectedIndex(2);
					return;
				}
				
				if (arg0.getSource()==add_pokaz_button)
				{
					try {
						if (add_pokaz_form == null)
						add_pokaz_form = new Add_pokaz(c);
						else
						add_pokaz_form.setVisible(true);	
						add_pokaz_form.addWindowListener(new WindowAdapter() {
							public void windowDeactivated(WindowEvent e) {
								if (last_group != null)
								{
									last_group.setSelected(true);
								    ActionEvent event=new ActionEvent(last_group, 1001, "com");
								    actionPerformed(event);
								}
								frame.repaint();
								frame.revalidate();
							}
						});
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				
				if (arg0.getSource()==delete_pokaz_button)
				{
					if (JOptionPane.showConfirmDialog(this, "�� ������������� ������ ������� ��������� ����������?", "�������� �����������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					{
						try {
							Integer []sel = selected_pokaz.toArray(new Integer [0]);
							String delete_query = "delete from pokazateli where";
							for (int i = 0; i < sel.length; i++) {
								delete_query+="  id ="+sel[i]+" or";
							}
							delete_query=delete_query.substring(0, delete_query.length()-2);
							Statement st=c.createStatement();
							st.executeUpdate(delete_query);
							for (int i = 0; i < sel.length; i++) {
								selected_pokaz.remove(sel[i]);
							}
							st.close();
							if (last_group != null)
							{
//								last_group.setSelected(true);
							    ActionEvent event=new ActionEvent(last_group, 1001, "com");
							    actionPerformed(event);
							}
							frame.repaint();
							frame.revalidate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							JOptionPane.showMessageDialog(this, "�� ������� ������� ��������� �������� ��������� ������� ��������� ������!");
						} 
					}
					return;
				}
				
				System.out.println(arg0.getSource().getClass().getName());
				return;
		}
	}


