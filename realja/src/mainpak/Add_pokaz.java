package mainpak;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mainpak.Explorer_for_selenga.JRB;
import net.miginfocom.swing.MigLayout;

public class Add_pokaz extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	JComboBox pokaz_level = new JComboBox();
	JComboBox group_pokaz = new JComboBox();
	JComboBox pokaz_mu = new JComboBox();
	JButton add_pokaz =new JButton("�������� ����������");
	JTextField pokaz_name = new JTextField();
	Connection conn;
	public Add_pokaz(Connection c) throws SQLException{
		// TODO Auto-generated constructor stub
		super();
		conn = c;
		setTitle("�������� ����������");
		setLayout(new MigLayout("fillx,wrap 2"));
		
		
		add(new JLabel("������� ����������"),"");
		pokaz_level.insertItemAt("�� ������", 0);
		pokaz_level.insertItemAt("�� ��������� ���������", 1);
		pokaz_level.setSelectedIndex(0);
		add(pokaz_level,"wrap,span");
		pokaz_level.addActionListener(this);
		

		
		add(new JLabel("������ �����������"),"");
				Statement st = conn.createStatement();
		String get_group = "select * from group_pokaz where is_sp = "+pokaz_level.getSelectedIndex();
		ResultSet rs= st.executeQuery(get_group);
		rs = st.executeQuery(get_group);
		int i=0;
		while (rs.next()) {
			group_pokaz.insertItemAt(rs.getString("name"), i);
			i++;
		}
		rs.close();
		group_pokaz.setSelectedIndex(0);
		add(group_pokaz,"wrap,span");
		
		
		add(new JLabel("������� ���������"),"");
		String get_mu = "select * from measurement_units";
		rs= st.executeQuery(get_mu);
		i=0;
		while (rs.next()) {
			pokaz_mu.insertItemAt(rs.getString("name"), i);
			i++;
		}
		rs.close();
		pokaz_mu.setSelectedIndex(0);
		add(pokaz_mu,"wrap");


		add(new JLabel("�������� ����������"),"");
		add(pokaz_name,"wrap,grow");
		add (add_pokaz,"wrap,span");
		add_pokaz.addActionListener(this);
		
		
		setSize(700, 300);
		setVisible(true);
		
		
	}
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/selenga", "postgres",
					"178");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (conn != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		
		Add_pokaz frame = new Add_pokaz(conn);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource().equals(pokaz_level))
		{
			Statement st;
			try {
					st = conn.createStatement();
					String get_group = "select * from group_pokaz where is_sp = "+pokaz_level.getSelectedIndex();
					ResultSet rs= st.executeQuery(get_group);
					rs = st.executeQuery(get_group);
					group_pokaz.removeAllItems();
//					group_pokaz.
					int i=0;
					while (rs.next()) {
						group_pokaz.insertItemAt(rs.getString("name"), i);
						i++;
					}
					rs.close();
//					repaint();
//					revalidate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			group_pokaz.setSelectedIndex(0);
		}

		
		if (arg0.getSource().equals(add_pokaz))
		{
			if (pokaz_name.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "������� �������� ����������!");
				return;
			}
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs=st.executeQuery("select * from pokazateli order by id DESC");
				rs.next();
				int temp_id = rs.getInt("id");
				temp_id++;
				
				rs=st.executeQuery("select * from group_pokaz where name = \'"+group_pokaz.getSelectedItem()+"\'");
				rs.next();
				int temp_gp = rs.getInt("id");
				
				rs=st.executeQuery("select * from measurement_units where name = \'"+pokaz_mu.getSelectedItem()+"\'");
				rs.next();
				int temp_mu = rs.getInt("id");
				
				String add_query = "insert into pokazateli(id,group_pokaz_id,measurement_units_id,name) values ("+temp_id+","+temp_gp+","+temp_mu+",\'"+pokaz_name.getText()+"\')";
				st.executeUpdate(add_query);
				rs.close();
				JOptionPane.showMessageDialog(this, "���������� ������� ��������!");
				setVisible(false);
//				setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		if (arg0.getSource().equals(pokaz_level))
		{
			
		}
		
		
		
	}
}