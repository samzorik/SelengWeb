package mainpak;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import weka.clusterers.CLOPE;
import weka.clusterers.CLOPEModification;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.converters.ArffLoader.ArffReader;
import weka.gui.explorer.ClustererPanel.TestData;

public class ArtTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

        boolean proceed = true;
        BufferedReader reader = new BufferedReader(new FileReader("D:/syntest.arff"));
        ArffReader arff = new ArffReader(reader);
        Instances m_Instances = arff.getData();
        boolean clope=false;
      	  Clusterer clusterer = new EM();
      		 long trainTimeStart = 0, trainTimeElapsed = 0;
      		 trainTimeStart = System.currentTimeMillis();
      		 
				trainTimeElapsed = System.currentTimeMillis() - trainTimeStart;
				StringBuffer outBuff=new StringBuffer();
	            Instances inst = new Instances(m_Instances);
	            
				ClusterEvaluation eval = new ClusterEvaluation();
	            
	            double clusterassign[]={1 ,1 ,1 ,1 ,2 ,2 ,2 ,2 ,3 ,3 ,3 ,3 ,4 ,4 ,4 ,4 ,5 ,5 ,5 ,5 ,6 ,6 ,6 ,6 ,7 ,7 ,7 ,7 ,8 ,8 ,8 ,8 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10 ,10};
//	            double clusterassign[]={1 ,1 ,2 ,2 ,2 ,2 ,3 ,3 ,3 ,3 ,4 ,4 ,4 ,4 ,5 ,5 ,5 ,5 ,6 ,6 ,6 ,6 ,7 ,7 ,7 ,7 ,8 ,8 ,8 ,8 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9 ,9};
	            ArrayList <Integer> aprioriclusterassign=new ArrayList();
	            
	            
	            int countclusters= 11 ;
	            
	            
	            int countattributes;
	            ArrayList clusters[]=new ArrayList[countclusters];
		        for(int i=0;i<countclusters;i++)
		        {
		            	clusters[i]=new ArrayList();
		        }
		        for(int i=0;i<clusterassign.length;i++)
		        {
		            	clusters[(int)clusterassign[i]].add(i);
		        }
		            countattributes=inst.numAttributes();
		            AttributeStats stat[]=new AttributeStats[countattributes];
		            AttributeStats statclus[]=new AttributeStats[countattributes];
		            ArrayList<Integer> nomindex=new ArrayList<Integer>();
		            ArrayList<Integer> numindex=new ArrayList<Integer>();
		            
		            
		             
		            for(int i=0;i<countattributes;i++)
		            {
		            	stat[i]=inst.attributeStats(i);
		            	if (inst.attribute(i).isNominal()||inst.attribute(i).isString())
		            	{
		            		nomindex.add(i);
		            	}
		            	else
		            	if(inst.attribute(i).isNumeric())
		            	{
		            		numindex.add(i);
		            	}
		            }
		            Instances clusinst[]=new Instances[countclusters];
		            int neclustercount=0,purity=0;
		            for(int i=0;i<countclusters;i++)
		            {
		            	if (clusters[i].size()==0) 
		            		continue;
		            	neclustercount++;
		            	outBuff.append("---------Кластер "+i+"----------\n");
		            	clusinst[i]=new Instances(inst,clusters[i].size());
		            	for(int j=0;j<clusters[i].size();j++)
		            	{
		            		clusinst[i].add(inst.instance((int)clusters[i].get(j)));//0;
		            	}            
		            	outBuff.append("Статистика по кластеру\n");
		            	outBuff.append("Наиболее часто встречаемые значения по атрибутам:\nАтрибут       Значение        Количествo       Доля в кластере     Доля во всей выборке\n");
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
			            	if ((int)nomindex.get(z)==0)
			            		purity+=maxcount;
			            		
			            	if (maxcount<clusters[i].size())
			            	outBuff.append(clusinst[i].attribute((int)nomindex.get(z)).name()+" \\  "+clusinst[i].attribute((int)nomindex.get(z)).value(maxj)+" \\ "+maxcount+"  \\ "+(double)maxcount/(double)clusters[i].size()*100.0+"% \\ "+(double)maxcount/(double)stat[(int)nomindex.get(z)].nominalCounts[inst.attribute((int)nomindex.get(z)).indexOfValue(clusinst[i].attribute((int)nomindex.get(z)).value(maxj))]*100.0+"% \\ "+inst.attribute((int)nomindex.get(z)).weight()+"\n");
			            	else
			            	outBuff.append(clusinst[i].attribute((int)nomindex.get(z)).name()+" \\  ALL VALUES EQUALS "+clusinst[i].attribute((int)nomindex.get(z)).value(maxj)+"\n");
			    		}
			            for(int z=0;z<numindex.size();z++)
			    		{
			            	outBuff.append(clusinst[i].attribute((int)numindex.get(z)).name()+" \\  "+inst.attribute((int)numindex.get(z)).weight()+"\n");
			    		}
		            }	    
		          String repulsion="";
		          
		          //-------------------CLUSTER EVALUATION-------------------------------
		          	          
		          
		          HashSet<Integer> maxes=new HashSet<Integer>();
		          int num_class = 0;
		          HashMap<Double,Integer> cluster_nums_I;
		          HashMap<String,Integer> cluster_nums_S;
		          ArrayList<HashSet<Integer>> apriori_clus = new ArrayList();
		          ArrayList<ArrayList<Integer>> result_clus = new ArrayList<>();
		          HashMap<Double,Integer> was = new HashMap();
//		          result_clus.
		          if (m_Instances.attribute(num_class).isNumeric())
		          {
		        	  cluster_nums_I = new HashMap();
		        	  for (int i = 0; i < m_Instances.size(); i++) {
						if (cluster_nums_I.containsKey(m_Instances.instance(i).value(num_class)))
						{
							(apriori_clus.get(cluster_nums_I.get(m_Instances.instance(i).value(num_class)))).add(i);
							aprioriclusterassign.add(cluster_nums_I.get(m_Instances.instance(i).value(num_class)));
						}
						else
						{
							HashSet temp=new HashSet<Integer>();
							temp.add(i);
							apriori_clus.add(temp);
							cluster_nums_I.put(m_Instances.instance(i).value(num_class), apriori_clus.size()-1);
							aprioriclusterassign.add(apriori_clus.size()-1);
						}
					}
		          }
		          else
		          {
		        	  cluster_nums_S = new HashMap();
		        	  for (int i = 0; i < m_Instances.size(); i++) {
							if (cluster_nums_S.containsKey(m_Instances.instance(i).stringValue(num_class)))
							{
								(apriori_clus.get(cluster_nums_S.get(m_Instances.instance(i).stringValue(num_class)))).add(i);
								aprioriclusterassign.add(cluster_nums_S.get(m_Instances.instance(i).stringValue(num_class)));
							}
							else
							{
								HashSet temp=new HashSet<Integer>();
								temp.add(i);
								apriori_clus.add(temp);
								cluster_nums_S.put(m_Instances.instance(i).stringValue(num_class), apriori_clus.size()-1);
								aprioriclusterassign.add(apriori_clus.size()-1);
							}
						}
		          }
		          
		          for (int i = 0; i < clusterassign.length; i++) {
					if (was.containsKey(clusterassign[i]))
					{
						result_clus.get(was.get(clusterassign[i])).add(i);					
					}
					else
					{
						ArrayList<Integer> temp=new ArrayList();
						temp.add(i);
						result_clus.add(temp);
						was.put(clusterassign[i], result_clus.size()-1);
					}
				  }
//		          aprioriclus
		          ArrayList<Fraction> fraction=new ArrayList();
		          int intersect[][]=new int[was.size()][apriori_clus.size()];
//		          Collections.sort(fraction.get(0));
		          for (int i = 0; i < result_clus.size(); i++) {
					for (int j = 0; j < result_clus.get(i).size(); j++) {
						for (int j2 = 0; j2 < apriori_clus.size(); j2++) {
							if (apriori_clus.get(j2).contains(result_clus.get(i).get(j)))
							{
								intersect[i][j2]++;
								break;
							}
						}
					}
					for (int j = 0; j < apriori_clus.size(); j++) {
						if(intersect[i][j]>0)
						{
							fraction.add(new Fraction(j,i,(double)intersect[i][j]/(double)apriori_clus.get(j).size(),(double)intersect[i][j]/(double)result_clus.get(i).size()));
						}					
					}
		          }
		          Collections.sort(fraction);
		          HashMap<Integer,Integer> checked=new HashMap();
		          ArrayList<Double> means=new ArrayList();
		          for (int i = 0; i < fraction.size(); i++) {
		        	  if (!checked.containsKey(fraction.get(i).res_clus))
		        	  {
						if (fraction.get(i).frac_in_apriori > 0.8 && fraction.get(i).frac_in_res > 0.7 && !(checked.containsValue(fraction.get(i).apriori_clus)))
						{
							checked.put(fraction.get(i).res_clus,fraction.get(i).apriori_clus);
							means.add(fraction.get(i).frac_in_apriori);
						}
		        	  }
		          }
		          
		          if (checked.size()<result_clus.size())
		          {
		        	  for (int i = 0; i < fraction.size(); i++) {
						if (!checked.containsKey(fraction.get(i).res_clus))
						{
								if (fraction.get(i).frac_in_apriori > 0.7 && !(checked.containsValue(fraction.get(i).apriori_clus)))
								{
									checked.put(fraction.get(i).res_clus,fraction.get(i).apriori_clus);
									means.add(fraction.get(i).frac_in_apriori);
								}
						}
			          }
		          }
		          
		          if (checked.size()<result_clus.size())
		          {
		        	  for (int i = 0; i < fraction.size(); i++) {
						if (!checked.containsKey(fraction.get(i).res_clus))
						{
								if (fraction.get(i).frac_in_res > 0.5 && !(checked.containsValue(fraction.get(i).apriori_clus)))
								{
									checked.put(fraction.get(i).res_clus,fraction.get(i).apriori_clus);
									means.add(fraction.get(i).frac_in_apriori);
								}
						}
			          }
		          }
		          
		          if (checked.size()<result_clus.size())
		          {
		        	  for (int i = 0; i < fraction.size(); i++) {
						if (!checked.containsKey(fraction.get(i).res_clus))
						{
								if (!(checked.containsValue(fraction.get(i).apriori_clus)))
								{
									checked.put(fraction.get(i).res_clus,fraction.get(i).apriori_clus);
									means.add(fraction.get(i).frac_in_apriori);
								}
						}
			          }
		          }
		          
		          double total_mark=0;
		          for (int i = 0; i < means.size(); i++) {
					total_mark+=means.get(i);
				  }
		          total_mark/=apriori_clus.size();
		          
		          //-------------------END CLUSTER EVALUATION---------------------------
		          
		          
		          //-------------------OTHER CLUSTER EVALUATION BEGIN-------------------
		          int DS=0,SS=0,SD=0,DD=0;
		          
		          for (int i = 0; i < m_Instances.numInstances(); i++) {
					for (int j = i+1; j < m_Instances.numInstances(); j++) {
//						if (i!=j)
//						{
							if (aprioriclusterassign.get(i)==aprioriclusterassign.get(j) && clusterassign[i]==clusterassign[j])
								SS++;
							if (aprioriclusterassign.get(i)==aprioriclusterassign.get(j) && clusterassign[i]!=clusterassign[j])
								DS++;
							if (aprioriclusterassign.get(i)!=aprioriclusterassign.get(j) && clusterassign[i]==clusterassign[j])
								SD++;
							if (aprioriclusterassign.get(i)!=aprioriclusterassign.get(j) && clusterassign[i]!=clusterassign[j])
								DD++;
//						}
					}
			      }
		          
		          //-------------------OTHER CLUSTER EVALUATION END---------------------
	              System.out.println("Чистота кластеров = "+purity/(double)m_Instances.numInstances()+"\n");
	              System.out.println("Индекс качества = "+total_mark+"\n");
	              System.out.println("Индекс RAND = "+(double)(SS+DD)/(double)(SS+DS+SD+DD)+"\n");
	              System.out.println("Индекс Jaccard = "+(double)(SS)/(double)(SS+DS+SD)+"\n");
	              System.out.println("Индекс FM = "+(double)Math.sqrt((double)(SS)/(double)(SS+SD)*(double)(SS)/(double)(SS+DS))+"\n");
	           
				
		}
//        }
      

}
