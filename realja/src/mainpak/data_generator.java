package mainpak;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;;

public class data_generator {

	/**
	 * @param args
	 */
	protected static JFileChooser filechooser;
	protected static JButton but1;
	protected static JFrame dialog;
	protected static JTextField ed;
	protected static JLabel lab;
	protected static JLabel lab3;
	protected static JLabel lab2;
	protected static JLabel labx;
	protected static JLabel laby;
	protected static JLabel labx2;
	protected static JLabel laby2;
	protected static JLabel Radius;
	protected static JTextField edX;
	protected static JTextField edY;
	protected static JTextField edX2;
	protected static JTextField edY2;
	protected static JTextField edR;
	protected static JCheckBox addend;
	protected static JComboBox sel;
	protected static int count;
	
	public static void main(String[] args){// throws IOException {
//	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		BufferedStreamReader f=new BufferedStreamReader();
		filechooser = new JFileChooser();
		but1=new JButton();
		dialog = new JFrame("Генератор объектов");
		ed = new JTextField();
		lab=new JLabel("Количество объектов:");
		lab3=new JLabel("Тип объектов:");
		lab2=new JLabel(" ");
		labx=new JLabel("Координата Х");
		laby=new JLabel("Координата Y");
		labx2=new JLabel("Координата Х2");
		laby2=new JLabel("Координата Y2");
		Radius=new JLabel("Радиус");
		edX = new JTextField();
		edY = new JTextField();
		edX2 = new JTextField();
		edY2 = new JTextField();
		edR = new JTextField();
		addend = new JCheckBox("Дописать в конец файла");
		sel=new JComboBox();
		count = 0 ;
		
		dialog.setSize(500, 500);
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialog.dispose();
			}
		});
		JButton but1=new JButton();
//		but1.setSize(50, 100);
		but1.setText("Сгенерировать");
		JPanel buttons = new JPanel();
//		JButton content=new JButton("ВАх!");
//		JEditorPane edit=new JEditorPane();
//		edit.se
		
		sel.setToolTipText("fffff");
		sel.add("wow",new JLabel("wow!"));
		sel.addItem("Шар");
		sel.addItem("Кривая");
		sel.addItem("Окружность");
		//sel.
		buttons.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		buttons.setLayout(new GridLayout(8, 2, 5, 5));
//		buttons.setLayout(arg0)
//		GridLayout();
		buttons.add(but1);
		buttons.add(addend);
//		buttons.add(edit);
		buttons.add(lab);
		buttons.add(ed);
		buttons.add(lab3);
		buttons.add(sel);
//		sel.addItemListener(arg0)
		buttons.add(labx);
		buttons.add(edX);
		buttons.add(laby);
		buttons.add(edY);
		buttons.add(Radius);
		buttons.add(edR);
		buttons.add(labx2);
		buttons.add(edX2);
		buttons.add(laby2);
		buttons.add(edY2);
//		tt.
//		buttons.add(content);
		but1.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
//		    	  FileFilter fil=new FileFilter();
//		    	  filechooser.getFileFilter().;
		    	  
		    	  int result = filechooser.showSaveDialog(dialog);
		          if (result == JFileChooser.APPROVE_OPTION) {
		            try {
//		             File temp=
		            	File truewrite=new File(filechooser.getSelectedFile().getPath()+".arff");
//		            	truewrite.
//		            	File yy=new File()
//		            	filechooser.setSelectedFile(tt);
//		            	tt.
		                //=new File(filechooser.getSelectedFile().getPath());
		            	BufferedWriter writer = new BufferedWriter(
		                                        new FileWriter(
		                                          truewrite,true));
		              //filechooser.gets
//		              Str
		              if (!addend.isSelected())
		              {
//		            	  writer.flush();
			              writer.write("@relation '"+filechooser.getSelectedFile().getName()+"'");
			              writer.newLine();
			              writer.write("@attribute 'type' real\r\n @attribute 'X' real\r\n @attribute 'Y' real \r\n @data\r\n");
		              }
		              else
		              {
		            	  BufferedReader reader = new BufferedReader(new FileReader(truewrite));
		            	  ArffReader arff = new ArffReader(reader);
		            	  Instances data = arff.getData();
//		            	  data.setClassIndex(data.numAttributes() - 1);
//		            	  Instances temp=
		            	  count=data.attributeStats(0).distinctCount;
		              }
		              writer.newLine();
//		              File tt=new File(filechooser.getSelectedFile().getPath());		              
//		              writer.
		              int kol=Integer.parseInt(ed.getText());
		              int type=sel.getSelectedIndex();
		              count++;
		              switch(type){
			              case(0):
			              {
				              double x,y,rad=Integer.parseInt(edR.getText()),ox= Double.parseDouble(edX.getText()),oy= Double.parseDouble(edY.getText());
				              for(int i=0;i<kol;i++)
				              {
				            	 do{
				            	 x=Math.random()*rad*2-(rad-ox);
				            	 y=Math.random()*rad*2-(rad-oy);
				            	 }
				            	 while (Math.sqrt((ox-x)*(ox-x)+(oy-y)*(oy-y))>rad);
					            writer.write(count+","+x+","+y);
					            writer.newLine();
				              }
				              writer.flush();
				              writer.close();
				              JOptionPane.showMessageDialog(
				                dialog, 
				                "Output successfully saved to file '" 
				                + filechooser.getSelectedFile() + "'!",
				                "Information",
				                JOptionPane.INFORMATION_MESSAGE);
				              break;
			              }
			              case(1): 
			              {
			            	  double x1= Double.parseDouble(edX.getText()),y1=Double.parseDouble(edY.getText());
			            	  double x2= Double.parseDouble(edX2.getText()),y2=Double.parseDouble(edY2.getText()),outx,outy;
			            	  for(int i=0;i<kol;i++)
			            	  {
//				            	 do{
//					            	 x=Math.random()*rad*2-(rad-ox);
//					            	 y=Math.random()*rad*2-(rad-oy);
//				            	 }
//				            	 while (Math.sqrt((ox-x)*(ox-x)+(oy-y)*(oy-y))>rad);
			            		 if (x1==x2)
			            		 {
			            			 outx=x1;
			            			 if (y1>y2)
			            			 {
			            				 outy=y2+(y1-y2)*Math.random();
			            			 }
			            			 else
			            			 {
			            				 outy=y1+(y2-y1)*Math.random();
			            			 }
			            		 }
			            		 else
			            		 if (y1==y2)
			            		 {
			            			 outy=y1;
			            			 if (x1>x2)
			            			 {
			            				 outx=x2+(x1-x2)*Math.random();
			            			 }
			            			 else
			            			 {
			            				 outx=x1+(x2-x1)*Math.random();
			            			 }
			            		 }			 
			            		 else
			            		 {			            			 
			            			 if (y1>y2)
			            			 {
			            				 outy=y2+(y1-y2)*Math.random();
			            			 }
			            			 else
			            			 {
			            				 outy=y1+(y2-y1)*Math.random();
			            			 }
			            			 outx=(outy-y1)*(x2-x1)/(y2-y1)+x1;
			            		 }
					             writer.write(count+","+outx+","+outy);
					             writer.newLine();
				              }
				              writer.flush();
				              writer.close();
				              JOptionPane.showMessageDialog(
				                dialog, 
				                "Output successfully saved to file '" 
				                + filechooser.getSelectedFile() + "'!",
				                "Information",
				                JOptionPane.INFORMATION_MESSAGE);
				              break;
			              }  
		              };
		            }
		            catch (Exception er) {
		              er.printStackTrace();
		            }
		            dialog.dispose();
		          }
//		  		 m_writer = new BufferedWriter(new FileWriter());
		  	}
		    	    });
//		but1.set
		dialog.add(buttons, BorderLayout.CENTER);
}
}
