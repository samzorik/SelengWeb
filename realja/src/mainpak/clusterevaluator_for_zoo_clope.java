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
import java.io.FileWriter;
import java.io.FileReader;
import java.util.*;

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

public class clusterevaluator_for_zoo_clope {
	static JFrame dialog;
	static JFileChooser filechooser;
	public static void main(String[] args){// throws IOException {
//		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
//			BufferedStreamReader f=new BufferedStreamReader();
		 dialog = new JFrame("Генератор объектов");
		 filechooser = new JFileChooser();
			
			dialog.setVisible(true);
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			    	  filechooser.setMultiSelectionEnabled(true);
			    	  int result = filechooser.showSaveDialog(dialog);
			          if (result == JFileChooser.APPROVE_OPTION) {
			            try {
			            	for (int per = 0; per < filechooser.getSelectedFiles().length; per++) {
							File truewrite=new File(filechooser.getSelectedFiles()[per].getPath());
			            	BufferedReader reader = new BufferedReader(
			                                        new FileReader(truewrite));
			            	String text;
			            	text=reader.readLine();
					        while  (!text.contains("data"))
					        	{
					        	text=reader.readLine();
					        	}
					        int n = 0, tec = 0, num = 0, pos, max, maxi;
					        int ranges[]={1000,1500,2000,2500,3000,5000,10000,50000};
					        ArrayList<ArrayList<Integer>> mas=new ArrayList<ArrayList<Integer>>();
					        ArrayList<Double> res=new ArrayList<Double>();
					        HashSet<Integer> maxes=new HashSet<Integer>();
					        int temp[] = new int [50001];
//					        ranges.
					        String substr;
					        text=reader.readLine();
					        mas.add(new ArrayList<Integer>());
					        for (int i = 0; i < temp.length; i++) {
								temp[i]=0;
							}
					        max = 0;
					        maxi = 0;
					        while (text!=null)
					        {					        	
					        	pos=text.indexOf("cluster");
					        	substr=text.substring(pos+7);
					        	num=Integer.valueOf(substr);
					        	if (tec<ranges[n])
					        	{
					        		mas.get(n).add(num);
					        		temp[num]++;
					        	}
					        	else
					        	{
					        		for (int i = 0; i < temp.length; i++) {
										if ((temp[i]>max) && (!maxes.contains(i)))
										{
											max=temp[i];
											maxi = i;
										}
									}
					        		res.add((double)max/(double)mas.get(n).size());
					        		maxes.add(maxi);
					        		n++;
					        		mas.add(new ArrayList<Integer>());
					        		mas.get(n).add(num);
					        		max = 0;
					        		maxi = 0;
					        		for (int i = 0; i < temp.length; i++) {
										temp[i]=0;
									}
					        	}
					        	tec++;
					        	text=reader.readLine();
					        }
					        for (int i = 0; i < temp.length; i++) {
								if (temp[i]>max&& (!maxes.contains(i)))
								{
									max=temp[i];
									maxi = i;
								}
							}
			        		res.add((double)max/(double)mas.get(n).size());
			        		n++;
			        		mas.add(new ArrayList<Integer>());
			        		mas.get(n).add(num);
			        		max = 0;
			        		maxi = 0;
			        		for (int i = 0; i < temp.length; i++) {
								temp[i]=0;
							}
			        		double total = 0;
			        		for (int i = 0; i < res.size(); i++) {
								total+=res.get(i);								
							}
			        		
					        System.out.println("\n\nFile "+filechooser.getSelectedFiles()[per].getPath()+"\nCount="+n+"   Quality="+total/res.size());
					        for (int i = 0; i < res.size(); i++) {
					        	System.out.println(res.get(i));
							}
			            }
			            }
					    catch (Exception er) {
						   er.printStackTrace();
						}
			            System.exit(0);
					             
	}
	}

}
