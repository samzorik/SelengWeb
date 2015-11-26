/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    Copyright (C) 2008
 *    & Alexander Smirnov (austellus@gmail.com)
 */
package weka.clusterers;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import weka.core.AttributeStats;
import weka.core.Capabilities;
import weka.core.Drawable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.RevisionUtils;
import weka.core.SparseInstance;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformationHandler;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;

public class CLOPEModification
  extends AbstractClusterer
  implements   Drawable,OptionHandler, TechnicalInformationHandler {

  /** for serialization */
  static final long serialVersionUID = -567567567567588L;
  public class Pair<A, B> {
	    private A first;
	    private B second;

	    public Pair(A first, B second) {
	    	super();
	    	this.first = first;
	    	this.second = second;
	    }

	    public int hashCode() {
	    	int hashFirst = first != null ? first.hashCode() : 0;
	    	int hashSecond = second != null ? second.hashCode() : 0;

	    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
	    }

	    public boolean equals(Object other) {
	    	if (other instanceof Pair) {
	    		Pair otherPair = (Pair) other;
	    		return 
	    		((  this.first == otherPair.first ||
	    			( this.first != null && otherPair.first != null &&
	    			  this.first.equals(otherPair.first))) &&
	    		 (	this.second == otherPair.second ||
	    			( this.second != null && otherPair.second != null &&
	    			  this.second.equals(otherPair.second))) );
	    	}

	    	return false;
	    }

	    public String toString()
	    { 
	           return "(" + first + ", " + second + ")"; 
	    }

	    public A getFirst() {
	    	return first;
	    }

	    public void setFirst(A first) {
	    	this.first = first;
	    }

	    public B getSecond() {
	    	return second;
	    }

	    public void setSecond(B second) {
	    	this.second = second;
	    }
	}
  /**
   * Inner class for cluster of CLOPE.
   *
   * @see Serializable
   */
  public double weights[];
  public void setweight(int i,double d)
  {
	weights[i]=d;  
  };
  
  public int countiterations = 0;
  public class CLOPECluster implements Serializable {

    /**
     * Number of transactions
     */
    public int N = 0; //number of transactions
    
    /**
     * Number of distinct items (or width)
     */
    public double W = 0;
    
    /**
     * Size of cluster
     */
    public double S = 0;
    
    
    /**
     * Hash of <item, occurrence> pairs
     */
    public HashMap occ = new HashMap();

    /**
     *  Add item to cluster
     */
    public void AddItem(String Item,double w) {
      int count;
      if (!this.occ.containsKey(Item)) {
	this.occ.put(Item, 1);
	this.W+=w;
      } else {
	count = (Integer) this.occ.get(Item);
	count++;
	this.occ.remove(Item);
	this.occ.put(Item, count);
      }
      this.S+=w;
    }

    public void AddItem(Integer Item,double w) {
      int count;
      if (!this.occ.containsKey(Item)) {
	this.occ.put(Item, 1);
	this.W+=w;
      } else {
	count = (Integer) this.occ.get(Item);
	count++;
	this.occ.remove(Item);
	this.occ.put(Item, count);
      }
      this.S+=w;
    }

    /**
     *  Delete item from cluster
     */
     public void DeleteItem(String Item,double w) {
      int count;

      count = (Integer) this.occ.get(Item);

      if (count == 1) {
	this.occ.remove(Item);
	this.W-=w;
      } else {
	count--;
	this.occ.remove(Item);
	this.occ.put(Item, count);
      }
      this.S-=w;
     }

     public void DeleteItem(Integer Item, double w) {
       int count;

       count = (Integer) this.occ.get(Item);

       if (count == 1) {
	 this.occ.remove(Item);
	 this.W-=w;
       } else {
	 count--;
	 this.occ.remove(Item);
	 this.occ.put(Item, count);
       }
       this.S-=w;
     }

     /**
      * Calculate Delta
      */
      public double DeltaAdd(Instance inst, double r) {
	//System.out.println("DeltaAdd");
	double S_new;
	double W_new;
	double profit;
	double profit_new;
	double deltaprofit;
	S_new = 0;
	W_new = this.W;

	if (inst instanceof SparseInstance) {
	  //System.out.println("DeltaAddSparceInstance");
	  for (int i = 0; i < inst.numValues(); i++) {
	    S_new+=weights[i];

	    if ((Integer) this.occ.get(inst.index(i)) == null) {
	      W_new+=weights[i];
	    }
	  }
	} else {
	  for (int i = 0; i < inst.numAttributes(); i++) {
	    if (!inst.isMissing(i)) {
	      S_new+=weights[i];
	      if ((Integer) this.occ.get(i + " "+inst.toString(i)) == null) {
		W_new+=weights[i];
	      }
	    }
	  }
	}
	S_new += S;


	if (N == 0) {
	  deltaprofit = S_new / Math.pow(W_new, r);
	} else {
	  profit = S * N / Math.pow(W, r);
	  profit_new = S_new * (N + 1) / Math.pow(W_new, r);
	  deltaprofit = profit_new - profit;
	}
	return deltaprofit;
      }

      /**
       * Add instance to cluster
       */
      public void AddInstance(Instance inst) {
	if (inst instanceof SparseInstance) {
	  //  System.out.println("AddSparceInstance");
	  for (int i = 0; i < inst.numValues(); i++) {
	    AddItem(inst.index(i),weights[i]);
	    //  for(int i=0;i<inst.numAttributes();int++){
	    // AddItem(inst.index(i)+inst.value(i));
	  }
	} else {
	  for (int i = 0; i < inst.numAttributes(); i++) {

	    if (!inst.isMissing(i)) {

	      AddItem(i + " " + inst.toString(i),weights[i]);
	    }
	  }
	}
//	this.W = this.occ.size();
	this.N++;
      }

      /**
       * Delete instance from cluster
       */
      public void DeleteInstance(Instance inst) {
	if (inst instanceof SparseInstance) {
	  //   System.out.println("DeleteSparceInstance");
	  for (int i = 0; i < inst.numValues(); i++) {
	    DeleteItem(inst.index(i),weights[i]);
	  }
	} else {
	  for (int i = 0; i <= inst.numAttributes() - 1; i++) {

	    if (!inst.isMissing(i)) {
	      DeleteItem(i + " " + inst.toString(i),weights[i]);
	    }
	  }
	}
//	this.W = this.occ.size();
	this.N--;
      }
  }
  /**
   * Array of clusters
   */
  public ArrayList<CLOPECluster> clusters = new ArrayList<CLOPECluster>();
  
  /**
   * Specifies the repulsion default
   */
  protected double m_RepulsionDefault = 2.6;
  
  /**
   * Specifies the repulsion
   */
  protected double m_Repulsion = m_RepulsionDefault;
  
  protected String stats="";
  /**
   * Number of clusters 
   */
  protected int m_numberOfClusters = -1;
  
  private boolean m_WithVisualization;
  
  public Instances idata;
  
  /**
   * Counter for the processed instances
   */
  protected int m_processed_InstanceID;
  
  /**
   * Number of instances
   */
  protected int m_numberOfInstances;
  
  
  protected String resultgr;
  /**
   * 
   */
  protected ArrayList<Integer> m_clusterAssignments = new ArrayList();
  
  /** 
   * whether the number of clusters was already determined
   */
  protected boolean m_numberOfClustersDetermined = false;

  public int numberOfClusters() {
    determineNumberOfClusters();
    return m_numberOfClusters;
  }

  protected void determineNumberOfClusters() {

    m_numberOfClusters = clusters.size();

    m_numberOfClustersDetermined = true;
  }

  public Enumeration listOptions() {
    Vector result = new Vector();
    result.addElement(new Option(
	"\tRepulsion\n" + "\t(default " + m_RepulsionDefault + ")",
	"R", 1, "-R <num>"));
    
    result.addElement(new Option(
            "\tWithVisualization(good when there are "
                + "many clusters)\n", "O", 0, "-O"));
    return result.elements();
  }

  
  public void setWithVisualization(boolean d) {
	    m_WithVisualization = d;
	  }

  /**
   * Parses a given list of options. <p/>
   * 
    <!-- options-start -->
    * Valid options are: <p/>
    * 
    * <pre> -R &lt;num&gt;
    *  Repulsion
    *  (default 2.6)</pre>
    * <pre> -O
    *  Withvisualization (good when there are many clusters)
    * </pre>
    <!-- options-end -->
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  public void setOptions(String[] options) throws Exception {
    String tmpStr;

    tmpStr = Utils.getOption('R', options);
    if (tmpStr.length() != 0) {
      setRepulsion(Double.parseDouble(tmpStr));
    } else {
      setRepulsion(m_RepulsionDefault);
    }
    setWithVisualization(Utils.getFlag('O', options));
  }

  /**
   * Gets the current settings of CLOPE
   *
   * @return an array of strings suitable for passing to setOptions()
   */
  public String[] getOptions() {
    Vector result;

    result = new Vector();

    result.add("-R");
    result.add("" + getRepulsion());

    return (String[]) result.toArray(new String[result.size()]);
  }

  /**
   * Returns the tip text for this property
   * @return tip text for this property suitable for
   * displaying in the explorer/experimenter gui
   */
  public String repulsionTipText() {
    return "Repulsion to be used.";
  }

  /**
   * set the repulsion
   *
   * @param value the repulsion
   * @throws Exception if number of clusters is negative
   */
  public void setRepulsion(double value) {
    m_Repulsion = value;
  }

  /**
   * gets the repulsion
   *
   * @return the repulsion
   */
  public double getRepulsion() {
    return m_Repulsion;
  }
  public String WithVisualizationTipText() {
	    return "Use old format for model output. The old format is "
	        + "better when there are many clusters. The new format "
	        + "is better when there are fewer clusters and many attributes.";
	  }

  
	  /**
	   * Set whether to display model output in the old, original format.
	   * 
	   * @param d true if model ouput is to be shown in the old format
	   */
	  /**
	   * Get whether to display model output in the old, original format.
	   * 
	   * @return true if model ouput is to be shown in the old format
	   */
	  public boolean getWithVisualization() {
	    return m_WithVisualization;
	  }

  /**
   * Returns default capabilities of the clusterer.
   *
   * @return      the capabilities of this clusterer
   */
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities();
    result.disableAll();
    result.enable(Capability.NO_CLASS);

    // attributes
    result.enable(Capability.NOMINAL_ATTRIBUTES);
    // result.enable(Capability.NUMERIC_ATTRIBUTES);
    result.enable(Capability.MISSING_VALUES);

    return result;
  }

  /**
   * Generate Clustering via CLOPE
   * @param instances The instances that need to be clustered
   * @throws java.lang.Exception If clustering was not successful
   */
  public void buildClusterer(Instances data) throws Exception {
    clusters.clear();
    m_processed_InstanceID = 0;
    m_clusterAssignments.clear();
    m_numberOfInstances = data.numInstances();
    boolean moved;
    //Phase 1 
    for (int i = 0; i < data.numInstances(); i++) {
      int clusterid = AddInstanceToBestCluster(data.instance(i));
      m_clusterAssignments.add(clusterid);

    }
    countiterations=1;
    //Phase 2
    do {
      moved = false;
      for (int i = 0; i < data.numInstances(); i++) {
	m_processed_InstanceID = i;
	int clusterid = MoveInstanceToBestCluster(data.instance(i));
	if (clusterid != m_clusterAssignments.get(i)) {
	  moved = true;
	  m_clusterAssignments.set(i, clusterid);
	}
      }
      countiterations++;
      if (countiterations>10) break;
    } while (!moved);
    m_processed_InstanceID = 0;
    
    if (m_WithVisualization)
    {    
    	idata=new Instances(data);
		ClusterEvaluation eval=new ClusterEvaluation();
		eval.setClusterer(this);
		eval.evaluateClusterer(idata);
		 double n[]=eval.getClusterAssignments();
		  int countclusters=eval.getNumClusters();
		  int countattributes;
		  ArrayList clusters[]=new ArrayList[countclusters];
		  ArrayList nzclusters[];
		  StringBuffer   outBuff=new StringBuffer("");
		  for(int i=0;i<countclusters;i++)
		  {
		  	clusters[i]=new ArrayList();
		  }
		  for(int i=0;i<n.length;i++)
		  {
		  	clusters[(int)n[i]].add(i);
		  }
		  countattributes=idata.numAttributes();
		  AttributeStats stat[]=new AttributeStats[countattributes];
		  AttributeStats statclus[]=new AttributeStats[countattributes];
		  ArrayList<Integer> nomindex=new ArrayList<Integer>();
		  ArrayList<Integer> numindex=new ArrayList<Integer>();
		  ArrayList<Integer> nzindex=new ArrayList<Integer>();
	      double sum=0,avg=0;
	      double disps[]=new double[countattributes];
			
		  for(int i=0;i<countattributes;i++)
		  {
			sum=0;
		  	stat[i]=idata.attributeStats(i);
		  	for (int j = 0; j < stat[i].nominalCounts.length; j++) {
		  		sum+=stat[i].nominalCounts[j];
			}
		  	avg=sum/stat[i].nominalCounts.length;
		  	disps[i]=0;
		  	for (int j = 0; j < stat[i].nominalCounts.length; j++) {
		  		disps[i]+=(stat[i].nominalCounts[j]-avg)*(stat[i].nominalCounts[j]-avg);
			}
		  	disps[i]/=stat[i].nominalCounts.length;
		  	
		  	if (idata.attribute(i).isNominal()||idata.attribute(i).isString())
		  	{
		  		nomindex.add(i);
		  	}
		  	else
		  	if(idata.attribute(i).isNumeric())
		  	{
		  		numindex.add(i);
		  	}
		  }
		  Instances clusinst[]=new Instances[countclusters];
		  ArrayList<ArrayList <Integer>> maximat=new ArrayList<ArrayList <Integer>>();
		  ArrayList<ArrayList <String>> smaximat=new ArrayList<ArrayList <String>>();
		  for(int i=0;i<countclusters;i++)
		  {		
		  	clusinst[i]=new Instances(idata,clusters[i].size());	  	
		  	for(int j=0;j<clusters[i].size();j++)
		  	{
		  		clusinst[i].add(idata.instance((int)clusters[i].get(j)));//0;
		  	} 
		  	if (clusinst[i].size()==0) continue;
		  	nzindex.add(i);
		  	stats+="---------Кластер "+i+"----------\n";
		  	stats+="Количество элементов в кластере - "+clusinst[i].size();	  	
	    	stats+="\nНаиболее часто встречаемые значения по атрибутам:\nАтрибут   \\    Значение    \\   Количествo   \\    Доля в кластере   \\  Доля во всей выборке\n";
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
	      		if (nomindex.get(z)!=0)
	      		{
	          			maximat.get(maximat.size()).add(nomindex.get(z));
	          			smaximat.get(maximat.size()).add(clusinst[i].attribute((int)nomindex.get(z)).value(maxj));
	      		}
		      	stats+=clusinst[i].attribute((int)nomindex.get(z)).name()+" \\  "+clusinst[i].attribute((int)nomindex.get(z)).value(maxj)+" \\ "+maxcount+"  \\ "+(double)maxcount/(double)clusters[i].size()*100.0+" \\ "+(double)maxcount/(double)stat[(int)nomindex.get(z)].nominalCounts[idata.attribute((int)nomindex.get(z)).indexOfValue(clusinst[i].attribute((int)nomindex.get(z)).value(maxj))]*100.0+" \\ "+idata.attribute((int)nomindex.get(z)).weight()+"\n";
			}
		    for(int z=0;z<numindex.size();z++)
			{
		      	stats+=clusinst[i].attribute((int)numindex.get(z)).name()+" \\  "+idata.attribute((int)numindex.get(z)).weight()+"\n";
			}
		  }	 
		  
		  nzclusters=new ArrayList[nzindex.size()];
		  for (int i = 0; i < nzindex.size(); i++) {
			nzclusters[i]=new ArrayList(clusters[nzindex.get(i)]);
			 stats+="\n";
			for (int j = 0; j < maximat.get(i).size(); j++) {
				stats+=maximat.get(i).get(j)+"("+smaximat.get(i).get(j)+") ";
			}		
		  }
		  stats+="\n";
		  for (int i = 0; i < countattributes; i++) {
			stats+=disps[i]+" ";
		  }
		  StringBuffer realtree=new StringBuffer();
		  realtree.append("digraph CobwebTree {");
		  double min=Double.MAX_VALUE;
		  int imin=-1;
		  for (int i = 1; i < countattributes; i++) {
			if (min>disps[i])
			{
				min=disps[i];
				imin=i;
			}
		  }
		  int nodecounter=1,parent=0;
		  realtree.append("\nN0 [label=\"Все объекты \" ]");
		  ArrayList <Integer> biect[]=new ArrayList [stat[imin].nominalCounts.length];
		  ArrayList<Integer> group;
		  LinkedList<Integer> nodes=new LinkedList();
		  LinkedList <ArrayList <Integer>> forSplit =new LinkedList();
		  for (int i = 0; i < stat[imin].nominalCounts.length; i++) {
			  realtree.append("\nN"+parent+"->N"+nodecounter);
			  realtree.append("\nN"+nodecounter+" [label=\""+idata.attribute(imin).value(i)+" \\n"+idata.attribute(imin).name()+"\"]");
			  nodes.addLast(nodecounter);
			  biect[i]=new ArrayList<Integer>();
			  for (int j = 0; j < smaximat.size(); j++) {
				if (idata.attribute(imin).value(i).compareTo(smaximat.get(j).get(imin-1))==0)
				{
					biect[i].add(j);
				}
			  }
			  forSplit.addLast(biect[i]);
			  nodecounter++;
		  }
		  while(!forSplit.isEmpty())
		  {
			  parent=nodes.getFirst();
			  nodes.removeFirst();
			  group=forSplit.getFirst();
			  forSplit.removeFirst();
			  if (group.size()==1)
			  {
				  if (nzclusters[group.get(0)].size()>1)
				  {
					  realtree.append("\nN"+parent+"->N"+nodecounter);
					  realtree.append("\nN"+nodecounter+" [label=\"Кластерная группа \"]");
					  parent=nodecounter;
					  nodecounter++;
					  for (int i = 0; i < nzclusters[group.get(0)].size(); i++) {
						  realtree.append("\nN"+parent+"->N"+nodecounter);
						  realtree.append("\nN"+nodecounter+" [label=\""+idata.attribute(0).value((int)nzclusters[group.get(0)].get(i))+" \"]");
						  nodecounter++;
					  }
				  }
				  else
				  {
					  realtree.append("\nN"+parent+"->N"+nodecounter);
					  realtree.append("\nN"+nodecounter+" [label=\""+idata.attribute(0).value((int)nzclusters[group.get(0)].get(0))+" \"]");
					  nodecounter++;
				  }
			  }
			  else
			  {
				  HashSet<String> allmeans=new HashSet();
				  int splitAttr=-1;
				  for (int i = 0; i < countattributes; i++) {
					allmeans.clear();
					for (int j = 0; j < group.size(); j++) {
						allmeans.add(smaximat.get(group.get(j)).get(i));
					}
					 if (allmeans.size()>1)
					  {
						 splitAttr=i;
						 break;
					  }
				  }				  
				  for (String string : allmeans) {
					  realtree.append("\nN"+parent+"->N"+nodecounter);
					  realtree.append("\nN"+nodecounter+" [label=\""+string+"\\n"+idata.attribute(splitAttr+1).name()+"\"]");
					  nodes.addLast(nodecounter);
					  ArrayList part=new ArrayList();
					  for (int j = 0; j < group.size(); j++) {
						  if (string==smaximat.get(group.get(j)).get(splitAttr))
						  {
							  part.add(group.get(j));
						  }
					  }
					  forSplit.addLast(part);
					  nodecounter++;
				  }
			  }
		  }
		 
		  realtree.append("\n}");	  
		  stats+=realtree.toString();
		  resultgr=realtree.toString();
    }
    else
    {
    	 StringBuffer realtree=new StringBuffer();
		 realtree.append("digraph CobwebTree {");
		 realtree.append("\nN0 [label=\"Все объекты \" ]");
		 realtree.append("\n}");	  
		 stats+=realtree.toString();
		 resultgr=realtree.toString();
    }
  }

  /**
   * the default constructor
   */
  public CLOPEModification() {
    super();
  }

  /**
   * Add instance to best cluster
   */
  public int AddInstanceToBestCluster(Instance inst) {

    double delta;
    double deltamax;
    int clustermax = -1;
    if (clusters.size() > 0) {
      double tempS = 0;
      double tempW = 0;
      if (inst instanceof SparseInstance) {
		for (int i = 0; i < inst.numValues(); i++) {
		  tempS+=weights[i];
		  tempW+=weights[i];
		}
      } else {
		for (int i = 0; i < inst.numAttributes(); i++) {
		  if (!inst.isMissing(i)) {
		    tempS+=weights[i];
		    tempW+=weights[i];
		  }
		}
      }

      deltamax = tempS / Math.pow(tempW, m_Repulsion);

      for (int i = 0; i < clusters.size(); i++) {
	CLOPECluster tempcluster = clusters.get(i);
	delta = tempcluster.DeltaAdd(inst, m_Repulsion);
	//  System.out.println("delta " + delta);
	if (delta > deltamax) {
	  deltamax = delta;
	  clustermax = i;
	}
      }
    } else {
      CLOPECluster newcluster = new CLOPECluster();
      clusters.add(newcluster);
      newcluster.AddInstance(inst);
      return clusters.size() - 1;
    }

    if (clustermax == -1) {
      CLOPECluster newcluster = new CLOPECluster();
      clusters.add(newcluster);
      newcluster.AddInstance(inst);
      return clusters.size() - 1;
    }
    clusters.get(clustermax).AddInstance(inst);
    return clustermax;
  }

  /**
   * Move instance to best cluster
   */
  public int MoveInstanceToBestCluster(Instance inst) {

    clusters.get(m_clusterAssignments.get(m_processed_InstanceID)).DeleteInstance(inst);
    m_clusterAssignments.set(m_processed_InstanceID, -1);
    double delta;
    double deltamax;
    int clustermax = -1;
    double tempS = 0;
    double tempW = 0;

    if (inst instanceof SparseInstance) {
      for (int i = 0; i < inst.numValues(); i++) {
	tempS+=weights[i];
	tempW+=weights[i];
      }
    } else {
      for (int i = 0; i < inst.numAttributes(); i++) {
	if (!inst.isMissing(i)) {
	  tempS+=weights[i];
	  tempW+=weights[i];
	}
      }
    }

    deltamax = tempS / Math.pow(tempW, m_Repulsion);
    for (int i = 0; i < clusters.size(); i++) {
      CLOPECluster tempcluster = clusters.get(i);
      delta = tempcluster.DeltaAdd(inst, m_Repulsion);
      // System.out.println("delta " + delta);
      if (delta > deltamax) {
	deltamax = delta;
	clustermax = i;
      }
    }
    if (clustermax == -1) {
      CLOPECluster newcluster = new CLOPECluster();
      clusters.add(newcluster);
      newcluster.AddInstance(inst);
      return clusters.size() - 1;
    }
    clusters.get(clustermax).AddInstance(inst);
    return clustermax;
  }

  /**
   * Classifies a given instance.
   *
   * @param instance The instance to be assigned to a cluster
   * @return int The number of the assigned cluster as an integer
   * @throws java.lang.Exception If instance could not be clustered
   * successfully
   */
  public int clusterInstance(Instance instance) throws Exception {
    if (m_processed_InstanceID >= m_numberOfInstances) {
      m_processed_InstanceID = 0;
    }
    int i = m_clusterAssignments.get(m_processed_InstanceID);
    m_processed_InstanceID++;
    return i;
  }

  /**
   * return a string describing this clusterer
   *
   * @return a description of the clusterer as a string
   */
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("CLOPEModification clustering results\n" +
    "========================================================================================\n\n");
    stringBuffer.append("Clustered instances: " + m_clusterAssignments.size() + "\n");
    return stringBuffer.toString() + "\n";
  }

  /**
   * Returns a string describing this DataMining-Algorithm
   * @return String Information for the gui-explorer
   */
  public String globalInfo() {
    return getTechnicalInformation().toString();
  }

  /**
   * Returns an instance of a TechnicalInformation object, containing
   * detailed information about the technical background of this class,
   * e.g., paper reference or book this class is based on.
   *
   * @return the technical information about this class
   */
  public TechnicalInformation getTechnicalInformation() {
    TechnicalInformation result;

    result = new TechnicalInformation(Type.INPROCEEDINGS);
    result.setValue(Field.AUTHOR, "Yiling Yang and Xudong Guan and Jinyuan You");
    result.setValue(Field.TITLE, "CLOPE: a fast and effective clustering algorithm for transactional data");
    result.setValue(Field.BOOKTITLE, "Proceedings of the eighth ACM SIGKDD international conference on Knowledge discovery and data mining");
    result.setValue(Field.YEAR, "2002");
    result.setValue(Field.PAGES, "682-687");
    result.setValue(Field.PUBLISHER, "ACM  New York, NY, USA");

    return result;
  }
  
  /**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 5538 $");
  }

  /**
   * Main method for testing this class.
   *
   * @param argv should contain the following arguments: <p>
   * -t training file [-R repulsion]
   */
  public static void main(String[] argv) {
    runClusterer(new CLOPEModification(), argv);
  }

@Override
public int graphType() {
	// TODO Auto-generated method stub
	return Drawable.TREE;
}

@Override
public String graph() throws Exception {
	// TODO Auto-generated method stub
	
	return resultgr;
}

@Override
public String getout() {
	// TODO Auto-generated method stub
	return stats;
}
}

