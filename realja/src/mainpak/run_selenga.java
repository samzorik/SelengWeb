package mainpak;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.xml.sax.InputSource;

import weka.gui.explorer.Explorer;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

public class run_selenga implements ActionListener {

	/**
	 * @param args
	 */
	protected JTabbedPane m_TabbedPane = new JTabbedPane();
	static Statement stmt = null;
	static Connection c = null;
	static ResultSet rs;
	JPanel show_group_pokaz;
	JPanel button_panel = new JPanel();
	static Vector<Vector<String>> data = new Vector<Vector<String>>();
	JPanel show_pokaz = new JPanel();
	JPanel select_data = new JPanel();
	JPanel edit_data = new JPanel();
	JPanel pokaz_and_buttons = new JPanel();
	HashSet<Integer> selected = new HashSet();
	JFrame frame = new JFrame(
			"Система ввода, хранения и анализа социо-экономических показателей Селенгинского района");
	JButton check_all = new JButton("Выбрать все");
	JButton show = new JButton("Загрузить выбранные атрибуты");
	JButton add_year = new JButton("Добавить год");
	JPanel edit_buttons = new JPanel();
	JTable edit_table = new JTable();
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

	public class DatabaseTableModel extends DefaultTableModel {
		public Map<Integer,Integer> row_pokaz_id = new HashMap();
		public Map<Integer,Integer> column_year = new HashMap();
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
				if (!cell.equals(""))
				{
					Statement st = conn.createStatement();
					int pokaz_id = row_pokaz_id.get(row);
					int year = column_year.get(column);
					ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year);
					if (rs.next())
					{
						st.executeUpdate("update fact_table set value = "+cell+" where pokaz = "+pokaz_id+" and year ="+year);
					}
					else
					{
						st.executeUpdate("insert into fact_table(year,pokaz,value) values ("+year+","+pokaz_id+","+cell+")");
					}
				}
				else
				{
					Statement st = conn.createStatement();
					int pokaz_id = row_pokaz_id.get(row);
					int year = column_year.get(column);
					ResultSet rs = st.executeQuery("select * from fact_table where pokaz = "+pokaz_id+" and year ="+year);
					if (rs.next())
					{
						st.executeUpdate("delete from fact_table where pokaz = "+pokaz_id+" and year ="+year);
					}
				}
			} catch (SQLException e) {
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

	public run_selenga() throws SQLException {
		frame.setSize(1000, 600);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
//		m_PreprocessPanel.
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Год");
		columnNames.add("Название показателя");
		columnNames.add("Значение");
		select_data = new JPanel();
		button_panel.setLayout(new GridLayout(1, 3, 5, 5));
		button_panel.add(check_all);
		button_panel.add(show);
		check_all.addActionListener(this);
		show.addActionListener(this);
		add_year.addActionListener(this);
		select_data.setLayout(new BorderLayout());
		show_group_pokaz = new JPanel();
		show_group_pokaz.setBorder(BorderFactory
				.createTitledBorder("Группы показателей"));
		show_pokaz.setBorder(BorderFactory.createTitledBorder("Показатели"));
		show_group_pokaz.setLayout(new GridLayout(8, 2, 5, 5));
		ButtonGroup groups = new ButtonGroup();
		connect();
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM group_pokaz;");
		while (rs.next()) {
			JRB temp = this.new JRB();
			temp.setText(rs.getString("name"));
			temp.tag = rs.getInt("id");
			groups.add(temp);
			temp.addActionListener(this);
			show_group_pokaz.add(temp, BorderLayout.NORTH);
		}
		rs.close();
		select_data.add(show_group_pokaz, BorderLayout.NORTH);
		select_data.add(show_pokaz, BorderLayout.CENTER);
		select_data.add(button_panel, BorderLayout.SOUTH);

		m_TabbedPane.addTab("Выбор данных", null, select_data, "");
		m_TabbedPane.addTab("Редактирование данных", null, edit_data, "");
		frame.add(m_TabbedPane);
		frame.setVisible(true);
		groups.getSelection();
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		run_selenga rrr = new run_selenga();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
			if (arg0.getSource().getClass().getName().equals("mainpak.run_selenga$JRB"))
			{
				System.out.println("wow!"+((JRB)arg0.getSource()).tag);
				try {
					stmt=c.createStatement();
					rs = stmt.executeQuery("SELECT * FROM pokazateli where group_pokaz_id ="+((JRB)arg0.getSource()).tag);
					show_pokaz.removeAll();
					show_pokaz.setLayout(new GridLayout(8,2,5,5));
					while (rs.next())
					{
						JCB temp = this.new JCB();
						temp.setText(rs.getString("name"));
						temp.tag = rs.getInt("id");
						if (selected.contains(temp.tag))
						{
							temp.setSelected(true);
						}
						temp.addActionListener(this);
						show_pokaz.add(temp);
					}
					frame.repaint();
					frame.revalidate();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			if (arg0.getSource().getClass().getName().equals("mainpak.run_selenga$JCB"))
			{
//				System.out.println(arg0.getSource().getClass().getName());
				int c = ((JCB)arg0.getSource()).tag;
				if (((JCB)arg0.getSource()).isSelected() )
				{
					selected.add(c);
				}
				else
				{
					selected.remove(c);
				}
			}
			else
			if (arg0.getSource()==check_all)
			{
//				System.out.println(arg0.getSource().getClass().getName());
				for (int i = 0; i < show_pokaz.getComponentCount(); i++) {
					((JCB)show_pokaz.getComponent(i)).setSelected(true);
					int c = ((JCB)show_pokaz.getComponent(i)).tag;
					selected.add(c);
				}
			}	
			if (arg0.getSource()==show)
			{
				model=new DatabaseTableModel();
				model.conn=c;
				edit_table = new JTable(model);		
	            model.addColumn("Показатель");
				try {
					stmt=c.createStatement();
					Integer []sel =selected.toArray(new Integer [0]);
					String base_query="SELECT * FROM fact_table where";
					for (int i = 0; i < sel.length; i++) {
						base_query+=" pokaz="+sel[i]+" or";
					}
					base_query=base_query.substring(0, base_query.length()-2);
					String group_query="select year from ("+base_query+") bb group by bb.year order by year";
					rs = stmt.executeQuery(group_query);
					while (rs.next())
					{
						model.addColumn(Integer.toString(rs.getInt("year")));
						model.column_year.put(model.getColumnCount()-1, rs.getInt("year"));
					}
					group_query="select gp.name from ("+base_query+") bb " +
							"LEFT JOIN pokazateli p " +
							"ON bb.pokaz = p.id " +
							"LEFT JOIN group_pokaz gp " +
							"ON p.group_pokaz_id = gp.id " +
							"group by gp.name order by gp.name";
					ResultSet subrs; 
					rs=stmt.executeQuery(group_query);
					while(rs.next())
					{
						String cur_group = rs.getString("name");
						Vector<Object> temp_dat = new Vector();
						Statement stmt2 = c.createStatement();
						temp_dat.add(cur_group);
						for (int i = 1; i < model.getColumnCount(); i++) {
							temp_dat.add("*");
						}
						model.addRow(temp_dat);
//						model.
						model.not_editable_row.add(model.getRowCount()-1);
						subrs=stmt2.executeQuery("select p.name, p.id from ("+base_query+") bb " +
								"LEFT JOIN pokazateli p " +
								"ON bb.pokaz = p.id " +
								"LEFT JOIN group_pokaz gp " +
								"ON p.group_pokaz_id = gp.id " +
								"where gp.name =\'"+cur_group+"\' "+
								"group by p.name, p.id " +
								"order by p.name");
						while(subrs.next())
						{
							int pokaz_id = subrs.getInt("id");
							Statement stmt3 = c.createStatement();
							String pokaz_name = subrs.getString("name");
							String sub_group_query = "SELECT * from ("+base_query+") bb where bb.pokaz="+pokaz_id;
							ResultSet subsubrs = stmt3.executeQuery(sub_group_query);
							Vector<Object> temp_data = new Vector();
							temp_data.setSize(model.getColumnCount());
							temp_data.set(0, "    "+pokaz_name);
							for (int i = 1; i < temp_data.size(); i++) {
								temp_data.set(i, "");
							}
							while (subsubrs.next())
							{
								int year=subsubrs.getInt("year");
								for (int j = 1; j < model.getColumnCount(); j++) {
									if (model.column_year.get(j) == year)
									{
										temp_data.set(j,subsubrs.getDouble("value"));
									}
								}
							}
							model.addRow(temp_data);
							model.row_pokaz_id.put(model.getRowCount()-1, pokaz_id);
							subsubrs.close();
						}
				
						subrs.close();
					}
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				edit_data.removeAll();
				edit_data.setLayout(new BorderLayout());
				edit_data.add(new JScrollPane(edit_table),BorderLayout.CENTER);
				edit_buttons.add(add_year);
				edit_data.add(edit_buttons,BorderLayout.SOUTH);
				edit_data.repaint();
				frame.repaint();
				frame.revalidate();
			}	
			else 
			if (arg0.getSource()==add_year)
			{
				String new_year = JOptionPane.showInputDialog("Введите год:");
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
			}
			else
			{
					System.out.println(arg0.getSource().getClass().getName());
			}		
	}
}
