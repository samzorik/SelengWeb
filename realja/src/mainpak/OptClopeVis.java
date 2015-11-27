package mainpak;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.core.AttributeStats;
import weka.core.Instances;

public class OptClopeVis extends JFrame{

	/**
	 * @param args
	 */
	public JButton go;
	public JButton addgoal;
	public JButton deletegoal;
	public Vector<JComboBox> goalfaclist;
	public JComboBox templategoalfaclist;
	public JLabel goalfaclabel;
	public JComboBox splitfaclist;
	public JComboBox last;
	public JLabel splitfaclabel;
	public JPanel goal,split;
	protected Clusterer m_res;
	protected Instances m_data;
	public OptClopeVis(Clusterer res, Instances data){
		super();
		m_res=res;
		m_data=data;
		setTitle("Выбор атрибутов для визуализации");
		setLayout(new GridLayout(1, 2, 5, 5));
		
		goal=new JPanel();
		split=new JPanel();
		deletegoal=new JButton("Удалить целевой атрибут");
		addgoal=new JButton("Добавить целевой атрибут");
		goal.setLayout(new GridLayout(7, 1, 5, 5));
		split.setLayout(new GridLayout(3, 1, 5, 5));
		goalfaclist=new Vector<JComboBox>();
		templategoalfaclist=new JComboBox();		
		deletegoal.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (goalfaclist.size()>1)
	        	{
	        		goal.remove(goalfaclist.get(goalfaclist.size()-1));
	        		goalfaclist.remove(goalfaclist.size()-1);	        	
	        		repaint();
	        		revalidate();
	        	}
	        }
	      });
		splitfaclist=new JComboBox();
		for (int i = 0; i < data.numAttributes(); i++) {
			templategoalfaclist.addItem(data.attribute(i).name());
			splitfaclist.addItem(data.attribute(i).name());
		}
		go=new JButton("Отобразить");
		goalfaclabel=new JLabel("Целевые атрибуты");
		splitfaclabel=new JLabel("Разделяющий атрибут");
		goalfaclist.add(templategoalfaclist);
		goal.add(goalfaclabel);
		goal.add(goalfaclist.get(0));
		goal.add(addgoal);
		goal.add(deletegoal);
		split.add(splitfaclabel);
		split.add(splitfaclist);
		split.add(go);
		add(goal);
		add(split);
		setSize(600,300);
		setVisible(false);	
		revalidate();
		go.addActionListener(new ActionListener(){ 

			public void actionPerformed(ActionEvent e)
			{
			    
				ClusterEvaluation eval=new ClusterEvaluation();
				eval.setClusterer(m_res);
				try {
					eval.evaluateClusterer(m_data);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 double n[]=eval.getClusterAssignments();
				  int countclusters=eval.getNumClusters();
				  int countattributes;
				  ArrayList clusters[]=new ArrayList[countclusters];
				  ArrayList nzclusters[];
				  StringBuffer outBuff=new StringBuffer("");
				  for(int i=0;i<countclusters;i++)
				  {
				  	clusters[i]=new ArrayList();
				  }
				  for(int i=0;i<n.length;i++)
				  {
				  	clusters[(int)n[i]].add(i);
				  }
				  countattributes=m_data.numAttributes();
				  AttributeStats stat[]=new AttributeStats[countattributes];
				  AttributeStats statclus[]=new AttributeStats[countattributes];
				  ArrayList<Integer> nomindex=new ArrayList<Integer>();
				  ArrayList<Integer> numindex=new ArrayList<Integer>();
				  ArrayList<Integer> indexofNotNullClusters=new ArrayList<Integer>();
			     			
				  for(int i=0;i<countattributes;i++)
				  {		
					  stat[i]=m_data.attributeStats(i);
				  	if (m_data.attribute(i).isNominal()||m_data.attribute(i).isString())
				  	{
				  		nomindex.add(i);
				  	}
				  	else
				  	if(m_data.attribute(i).isNumeric())
				  	{
				  		numindex.add(i);
				  	}
				  }
				  Instances clusinst[]=new Instances[countclusters];
				  ArrayList<ArrayList <Integer>> maximat=new ArrayList<ArrayList <Integer>>();
				  ArrayList<ArrayList <String>> smaximat=new ArrayList<ArrayList <String>>();
				  for(int i=0;i<countclusters;i++)
				  {		
				  	clusinst[i]=new Instances(m_data,clusters[i].size());
				  	for(int j=0;j<clusters[i].size();j++)
				  	{
				  		clusinst[i].add(m_data.instance((int)clusters[i].get(j)));
				  	} 
				  	if (clusinst[i].size()==0) continue;
				  	indexofNotNullClusters.add(i);
			    	maximat.add(new ArrayList <Integer>());
			    	smaximat.add(new ArrayList <String>());
				    for(int z=0;z<nomindex.size();z++)
					{
				      	int maxj=-1,maxcount=-1;
				      	statclus[(int)nomindex.get(z)]=clusinst[i].attributeStats((int)nomindex.get(z));
				      	for(int j=0;j<statclus[(int)nomindex.get(z)].nominalCounts.length;j++)
				      	{
				          		if (maxcount<statclus[(int)nomindex.get(z)].nominalCounts[j])
				          		{
				          			maxcount=statclus[(int)nomindex.get(z)].nominalCounts[j];
				          			maxj=j;
				          		}	
				      	}
//			      		if (nomindex.get(z)!=goalfaclist.getSelectedIndex())
//			      		{
			          			maximat.get(maximat.size()-1).add(nomindex.get(z));
			          			smaximat.get(maximat.size()-1).add(clusinst[i].attribute((int)nomindex.get(z)).value(maxj));
//			      		}
					}
				  }	 				  
				  nzclusters=new ArrayList[indexofNotNullClusters.size()];
				  for (int i = 0; i < indexofNotNullClusters.size(); i++) {
					nzclusters[i]=new ArrayList(clusters[indexofNotNullClusters.get(i)]);
				  }
				  StringBuffer realtree=new StringBuffer();
				  realtree.append("digraph CobwebTree {");
				  double min=Double.MAX_VALUE;
				  int selectedfac=splitfaclist.getSelectedIndex();
				  int nodecounter=1,parent=0;
				  realtree.append("\nN0 [label=\"Все объекты \" ]");
				  for (int i = 0; i < stat[selectedfac].nominalCounts.length; i++) {
					  realtree.append("\nN"+parent+"->N"+nodecounter);
					  realtree.append("\nN"+nodecounter+" [label=\""+m_data.attribute(selectedfac).value(i)+" \\n"+m_data.attribute(selectedfac).name()+"\"]");
					  int subparent=nodecounter;
					  for (int j = 0; j < smaximat.size(); j++) {
						if (m_data.attribute(selectedfac).value(i).compareTo(smaximat.get(j).get(selectedfac))==0)
						{
							nodecounter++;
							 if (nzclusters[j].size()>1)
							  {
								  realtree.append("\nN"+subparent+"->N"+nodecounter);
								  realtree.append("\nN"+nodecounter+" [label=\"Кластерная группа \"]");
								  int subsubparent=nodecounter;
								  for (int z = 0; z < nzclusters[j].size(); z++) {
									  nodecounter++;
									  realtree.append("\nN"+subsubparent+"->N"+nodecounter);
									  realtree.append("\nN"+nodecounter+" [label=\"");
									for (int tecfac=0;tecfac<goalfaclist.size(); tecfac++) {
										realtree.append(goalfaclist.get(tecfac).getSelectedItem().toString()+" : "+ m_data.instance((int)nzclusters[j].get(z)).stringValue(goalfaclist.get(tecfac).getSelectedIndex())+" \\n ");
									 }
									realtree.append("\\n \"]");
//									  m_data.attribute(0).
//									  m_data.instance((int)nzclusters[j].get(z)).stringValue(goalfaclist.getSelectedIndex());
									  
								  }
							  }
							  else
							  {
								  realtree.append("\nN"+subparent+"->N"+nodecounter);
								  realtree.append("\nN"+nodecounter+" [label=\"");
									for (int tecfac=0;tecfac<goalfaclist.size(); tecfac++) {
										realtree.append(goalfaclist.get(tecfac).getSelectedItem().toString()+" : "+ m_data.instance((int)nzclusters[j].get(0)).stringValue(goalfaclist.get(tecfac).getSelectedIndex())+"  \\n  ");
									 }
									realtree.append("\\n \"]");
//								  m_data.attribute(goalfaclist.getSelectedIndex()).value((int)nzclusters[j].get(0))
							  }
						}
					  }
					  nodecounter++;
				  }
				  realtree.append("\n}"); 
			}
		});
		addgoal.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e)	{
				final JComboBox goalfac=new JComboBox();
				for (int i = 0; i < m_data.numAttributes(); i++) {
					goalfac.addItem(m_data.attribute(i).name());
				}
				goalfaclist.add(goalfac);
				goal.remove(addgoal);
				goal.remove(deletegoal);
				goal.add(goalfac);
				goal.add(deletegoal);
				goal.add(addgoal);
				repaint();
				revalidate();
				return;
				}
			});
	}
	public static void main(String[] args) {
	}

}
