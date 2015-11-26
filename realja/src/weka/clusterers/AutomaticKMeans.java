/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    SimpleKMeans.java
 *    Copyright (C) 2000-2012 University of Waikato, Hamilton, New Zealand
 *
 */
package weka.clusterers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.ArrayUtils;

import weka.classifiers.rules.DecisionTableHashKey;
import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.DenseInstance;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.InstanceComparator;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.Option;
import weka.core.RevisionUtils;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;
import weka.core.TechnicalInformationHandler;
import weka.core.Utils;
import weka.core.WeightedInstancesHandler;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

/**
 <!-- globalinfo-start -->
 * Cluster data using the k means algorithm. Can use either the Euclidean distance (default) or the Manhattan distance. If the Manhattan distance is used, then centroids are computed as the component-wise median rather than mean. For more information see:<br/>
 * <br/>
 * D. Arthur, S. Vassilvitskii: k-means++: the advantages of carefull seeding. In: Proceedings of the eighteenth annual ACM-SIAM symposium on Discrete algorithms, 1027-1035, 2007.
 * <p/>
 <!-- globalinfo-end -->
 * 
 <!-- technical-bibtex-start -->
 * BibTeX:
 * <pre>
 * &#64;inproceedings{Arthur2007,
 *    author = {D. Arthur and S. Vassilvitskii},
 *    booktitle = {Proceedings of the eighteenth annual ACM-SIAM symposium on Discrete algorithms},
 *    pages = {1027-1035},
 *    title = {k-means++: the advantages of carefull seeding},
 *    year = {2007}
 * }
 * </pre>
 * <p/>
 <!-- technical-bibtex-end -->
 * 
 <!-- options-start -->
 * Valid options are: <p/>
 * 
 * <pre> -N &lt;num&gt;
 *  number of clusters.
 *  (default 2).</pre>
 * 

 <!-- options-end -->
 * 
 * @author Mark Hall (mhall@cs.waikato.ac.nz)
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 9375 $
 * @see RandomizableClusterer
 */
public class AutomaticKMeans extends RandomizableClusterer implements
    NumberOfClustersRequestable, WeightedInstancesHandler,
    TechnicalInformationHandler {

  /** for serialization. */
  static final long serialVersionUID = -3235809600124455376L;

  /**
   * replace missing values in training instances.
   */
  private ReplaceMissingValues m_ReplaceMissingFilter;

  /**
   * number of clusters to generate.
   */
  private int m_NumClusters = 2;
  
  public Instances m_data;

  /**
   * holds the cluster centroids.
   */
  private Instances m_ClusterCentroids;

  /**
   * Holds the standard deviations of the numeric attributes in each cluster.
   */
  private Instances m_ClusterStdDevs;

  /**
   * For each cluster, holds the frequency counts for the values of each nominal
   * attribute.
   */
  private int[][][] m_ClusterNominalCounts;
  private int[][] m_ClusterMissingCounts;

  /**
   * Stats on the full data set for comparison purposes. In case the attribute
   * is numeric the value is the mean if is being used the Euclidian distance or
   * the median if Manhattan distance and if the attribute is nominal then it's
   * mode is saved.
   */
  private double[] m_FullMeansOrMediansOrModes;
  private double[] m_FullStdDevs;
  private int[][] m_FullNominalCounts;
  private int[] m_FullMissingCounts;

  /**
   * Display standard deviations for numeric atts.
   */
  private boolean m_displayStdDevs;

  private int[] m_ClusterSizes;


  protected DistanceFunction m_DistanceFunction = new EuclideanDistance();

  protected int[] m_Assignments = null;

  protected int[] m_RealAssignments = null;

  /** whether to use fast calculation of distances (using a cut-off). */
  protected boolean m_FastDistanceCalc = false;
  protected transient ExecutorService m_executorPool;

  /**
   * the default constructor.
   */
  public AutomaticKMeans() {
    super();

  }


  protected int m_completed;
  protected int m_failed;

  @Override
  public TechnicalInformation getTechnicalInformation() {
    TechnicalInformation result;

    result = new TechnicalInformation(Type.INPROCEEDINGS);
    result.setValue(Field.AUTHOR, "D. Arthur and S. Vassilvitskii");
    result.setValue(Field.TITLE,
        "k-means++: the advantages of carefull seeding");
    result.setValue(Field.BOOKTITLE, "Proceedings of the eighteenth annual "
        + "ACM-SIAM symposium on Discrete algorithms");
    result.setValue(Field.YEAR, "2007");
    result.setValue(Field.PAGES, "1027-1035");

    return result;
  }

  public String globalInfo() {
    return "Cluster data using the k means algorithm. Can use either "
        + "the Euclidean distance (default) or the Manhattan distance."
        + " If the Manhattan distance is used, then centroids are computed "
        + "as the component-wise median rather than mean."
        + " For more information see:\n\n"
        + getTechnicalInformation().toString();
  }

  @Override
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities();
    result.disableAll();
    result.enable(Capability.NO_CLASS);

    // attributes
    result.enable(Capability.NOMINAL_ATTRIBUTES);
    result.enable(Capability.NUMERIC_ATTRIBUTES);
    result.enable(Capability.MISSING_VALUES);

    return result;
  }

  
  @Override
  public void buildClusterer(Instances data) throws Exception {
   
	  
	  Add filter;
	  Remove dimfilter;
	  ReplaceMissingValues replace;
	  replace = new ReplaceMissingValues();
	  replace.setIgnoreClass(true);
	  replace.setInputFormat(data);
	  Instances tempData,removed,centroids;
	  tempData = Filter.useFilter(data, replace);
	  removed = new Instances(data);
	  m_data = new Instances(data);
	  Instances p_data=Filter.useFilter(data, replace);
	  m_DistanceFunction.setInstances(p_data);
	  removed.clear();
	  ArrayList<Integer> for_del = new ArrayList();
	  SimpleKMeans KM; 
	  InstanceComparator compare = new InstanceComparator();
	        KM=new SimpleKMeans();		        
	        while (true)
	        {
	        	int [] temp_clus_assign = new int [tempData.size()];
	        	HashSet<Integer> was=new HashSet(),pretend = new HashSet();
		        try {
		        	KM.setNumClusters(m_NumClusters);
		        	KM.setMaxIterations(500);
					KM.buildClusterer(tempData);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        for (int i = 0; i < temp_clus_assign.length; i++) {
					temp_clus_assign[i] = KM.clusterInstance(tempData.instance(i));
				}
		        if (removed.isEmpty())
		        {
		        	m_RealAssignments = temp_clus_assign;
		        }
		        for (int i = 0; i < temp_clus_assign.length; i++) {
					if (!was.contains(temp_clus_assign[i]))
					{
						if(!pretend.contains(temp_clus_assign[i]))
						{
							pretend.add(temp_clus_assign[i]);
						}
						else
						{
							pretend.remove(temp_clus_assign[i]);
							was.add(temp_clus_assign[i]);
						}					
					}
				}
		        if (pretend.isEmpty())
		        	break;
		        if (removed.isEmpty())
		        {
			        for (int i = 0; i < temp_clus_assign.length; i++) {
						if (pretend.contains(temp_clus_assign[i]))
						{
							for_del.add(i);
							removed.add(tempData.instance(i));
						}
					}
			        for (int j = for_del.size()-1; j >-1 ; j--) {
			        	tempData.delete(for_del.get(j));
					}
		        }
		        else
		        {
		        	centroids = KM.getClusterCentroids();
		        	for (int j = 0; j < p_data.size(); j++) {
		        		for (int j2 = 0; j2 < tempData.size(); j2++) {
		        			if (compare.compare(p_data.instance(j), tempData.instance(j2))==0)//(data.instance(j).equals(tempData.instance(j2)))
		        				m_RealAssignments[j] = KM.clusterInstance(tempData.instance(j2));
		        		}
		        	}
		        	for (int i = 0; i < removed.size(); i++) {
		        		double min = Double.MAX_VALUE; int jmin = -1;
		        		for (int j = 0; j < centroids.size(); j++) {
		        			if (min>m_DistanceFunction.distance(removed.get(i), centroids.get(j)))
		        			{
		        				min = m_DistanceFunction.distance(removed.get(i), centroids.get(j));
		        				jmin = j;
		        			}
						}
		        		if (pretend.isEmpty())
		        		{
		        			
		        			m_RealAssignments[for_del.get(i)] = KM.clusterInstance(centroids.get(jmin));
//		        			for
//		        			for (int j = 0; j < tempData.size(); j++) {
//								if (compare.compare(tempData.instance(j), centroids.get(jmin))==0)//(tempData.instance(j).equals(centroids.get(jmin)))
//								{
////									tempData.instance(j).
//									m_RealAssignments[for_del.get(i)] = KM.clusterInstance(tempData.instance(j));
//								}
//							}
		        		}
		        		else
		        		{
		        			if (pretend.contains(centroids.get(jmin)))
		        			{
		        				
		        			}
		        			m_RealAssignments[for_del.get(i)] = KM.clusterInstance(centroids.get(jmin));
//		        			if (centroids.get(jmin).equals(obj))
//		        			{
//		        				
//		        			}
		        		}
					}
		        	break;
		        }
		        
//		        this.se
	        };
//	        centroids=KM.getClusterCentroids();
//	        centroids.sort(0);
//	        for (int i = 0; i < data.size(); i++) {
//				
//			}
	        HashSet<Integer> was=new HashSet(),pretend = new HashSet();
	        for (int i = 0; i < p_data.size(); i++) {
				if (!was.contains(m_RealAssignments[i]))
				{
					if(!pretend.contains(m_RealAssignments[i]))
					{
						pretend.add(m_RealAssignments[i]);
					}
					else
					{
						pretend.remove(m_RealAssignments[i]);
						was.add(m_RealAssignments[i]);
					}					
				}
			}
	        if (pretend.isEmpty()) 
	        	return;
	        int []assign = ArrayUtils.toPrimitive(pretend.toArray(new Integer[0]));
	        for (int j = 0; j < assign.length; j++) {
		        for (int i = 0; i < m_RealAssignments.length; i++) {
					if (assign[j]==m_RealAssignments[i])
					{
						pretend.remove(assign[j]);
						pretend.add(i);
						assign[j] = i;
					}
				}
	        }
	        for (int i = 0; i < assign.length; i++) {
	        	double min = Double.MAX_VALUE; int jmin = -1;
	        	if (!pretend.contains(assign[i]))
	        		continue;
				for (int j = 0; j < p_data.size(); j++) {
					if (assign[i]!=j)
					{
						if (min>m_DistanceFunction.distance(p_data.get(assign[i]), p_data.get(j)))
	        			{
	        				min = m_DistanceFunction.distance(p_data.get(assign[i]), p_data.get(j));
	        				jmin = j;
	        			}
					}
				}
				if (pretend.contains(jmin))
				{
					m_RealAssignments[assign[i]] = m_RealAssignments[jmin];
					pretend.remove(jmin);
				}
				else
				{
					m_RealAssignments[assign[i]] = m_RealAssignments[jmin];
				}
			}
   return;
  }

  protected void kMeansPlusPlusInit(Instances data) throws Exception {
    Random randomO = new Random(getSeed());
    HashMap<DecisionTableHashKey, String> initC = new HashMap<DecisionTableHashKey, String>();

    // choose initial center uniformly at random
    int index = randomO.nextInt(data.numInstances());
    m_ClusterCentroids.add(data.instance(index));
    DecisionTableHashKey hk = new DecisionTableHashKey(data.instance(index),
        data.numAttributes(), true);
    initC.put(hk, null);

    int iteration = 0;
    int remainingInstances = data.numInstances() - 1;
    if (m_NumClusters > 1) {
      // proceed with selecting the rest

      // distances to the initial randomly chose center
      double[] distances = new double[data.numInstances()];
      double[] cumProbs = new double[data.numInstances()];
      for (int i = 0; i < data.numInstances(); i++) {
        distances[i] = m_DistanceFunction.distance(data.instance(i),
            m_ClusterCentroids.instance(iteration));
      }

      // now choose the remaining cluster centers
      for (int i = 1; i < m_NumClusters; i++) {

        // distances converted to probabilities
        double[] weights = new double[data.numInstances()];
        System.arraycopy(distances, 0, weights, 0, distances.length);
        Utils.normalize(weights);

        double sumOfProbs = 0;
        for (int k = 0; k < data.numInstances(); k++) {
          sumOfProbs += weights[k];
          cumProbs[k] = sumOfProbs;
        }

        cumProbs[data.numInstances() - 1] = 1.0; // make sure there are no
                                                 // rounding issues

        // choose a random instance
        double prob = randomO.nextDouble();
        for (int k = 0; k < cumProbs.length; k++) {
          if (prob < cumProbs[k]) {
            Instance candidateCenter = data.instance(k);
            hk = new DecisionTableHashKey(candidateCenter,
                data.numAttributes(), true);
            if (!initC.containsKey(hk)) {
              initC.put(hk, null);
              m_ClusterCentroids.add(candidateCenter);
            } else {
              System.err.println("We shouldn't get here....");
            }
            remainingInstances--;
            break;
          }
        }
        iteration++;

        if (remainingInstances == 0) {
          break;
        }

        for (int k = 0; k < data.numInstances(); k++) {
          if (distances[k] > 0) {
            double newDist = m_DistanceFunction.distance(data.instance(k),
                m_ClusterCentroids.instance(iteration));
            if (newDist < distances[k]) {
              distances[k] = newDist;
            }
          }
        }
      }
    }
  }

  @Override
  public int clusterInstance(Instance instance) throws Exception {
    Instance inst = null;
   InstanceComparator compare = new InstanceComparator();
    for (int i = 0; i < m_data.size() ; i++) {
		if (compare.compare(m_data.instance(i), instance)==0)
			return m_RealAssignments[i];
	}
	return m_NumClusters;
    
  }

  @Override
  public int numberOfClusters() throws Exception {
    return m_NumClusters;
  }

  @Override
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tnumber of clusters.\n" + "\t(default 2).",
        "N", 1, "-N <num>"));
   

    Enumeration en = super.listOptions();
    while (en.hasMoreElements())
      result.addElement(en.nextElement());

    return result.elements();
  }

  public String numClustersTipText() {
    return "set number of clusters";
  }

  @Override
  public void setNumClusters(int n) throws Exception {
    if (n <= 0) {
      throw new Exception("Number of clusters must be > 0");
    }
    m_NumClusters = n;
  }

  public int getNumClusters() {
    return m_NumClusters;
  }

  public String initializeUsingKMeansPlusPlusMethodTipText() {
    return "Initialize cluster centers using the probabilistic "
        + " farthest first method of the k-means++ algorithm";
  }

  public String distanceFunctionTipText() {
    return "The distance function to use for instances comparison "
        + "(default: weka.core.EuclideanDistance). ";
  }

  public DistanceFunction getDistanceFunction() {
    return m_DistanceFunction;
  }

  public void setDistanceFunction(DistanceFunction df) throws Exception {
    if (!(df instanceof EuclideanDistance)
        && !(df instanceof ManhattanDistance)) {
      throw new Exception(
          "SimpleKMeans currently only supports the Euclidean and Manhattan distances.");
    }
    m_DistanceFunction = df;
  }

  public String preserveInstancesOrderTipText() {
    return "Preserve order of instances.";
  }

  @Override
  public void setOptions(String[] options) throws Exception {

    String optionString = Utils.getOption('N', options);

    if (optionString.length() != 0) {
      setNumClusters(Integer.parseInt(optionString));
    }

 
    super.setOptions(options);
  }

  @Override
  public String[] getOptions() {
    int i;
    Vector result;
    String[] options;

    result = new Vector();

    result.add("-N");
    result.add("" + getNumClusters());


    options = super.getOptions();
    for (i = 0; i < options.length; i++)
      result.add(options[i]);

    return (String[]) result.toArray(new String[result.size()]);
  }

  @Override
  public String toString() {
    if (m_ClusterCentroids == null) {
      return "No clusterer built yet!";
    }

    int maxWidth = 0;
    int maxAttWidth = 0;
    boolean containsNumeric = false;
    for (int i = 0; i < m_NumClusters; i++) {
      for (int j = 0; j < m_ClusterCentroids.numAttributes(); j++) {
        if (m_ClusterCentroids.attribute(j).name().length() > maxAttWidth) {
          maxAttWidth = m_ClusterCentroids.attribute(j).name().length();
        }
        if (m_ClusterCentroids.attribute(j).isNumeric()) {
          containsNumeric = true;
          double width = Math.log(Math.abs(m_ClusterCentroids.instance(i)
              .value(j))) / Math.log(10.0);
          // System.err.println(m_ClusterCentroids.instance(i).value(j)+" "+width);
          if (width < 0) {
            width = 1;
          }
          // decimal + # decimal places + 1
          width += 6.0;
          if ((int) width > maxWidth) {
            maxWidth = (int) width;
          }
        }
      }
    }

    for (int i = 0; i < m_ClusterCentroids.numAttributes(); i++) {
      if (m_ClusterCentroids.attribute(i).isNominal()) {
        Attribute a = m_ClusterCentroids.attribute(i);
        for (int j = 0; j < m_ClusterCentroids.numInstances(); j++) {
          String val = a.value((int) m_ClusterCentroids.instance(j).value(i));
          if (val.length() > maxWidth) {
            maxWidth = val.length();
          }
        }
        for (int j = 0; j < a.numValues(); j++) {
          String val = a.value(j) + " ";
          if (val.length() > maxAttWidth) {
            maxAttWidth = val.length();
          }
        }
      }
    }

    if (m_displayStdDevs) {
      // check for maximum width of maximum frequency count
      for (int i = 0; i < m_ClusterCentroids.numAttributes(); i++) {
        if (m_ClusterCentroids.attribute(i).isNominal()) {
          int maxV = Utils.maxIndex(m_FullNominalCounts[i]);
          /*
           * int percent = (int)((double)m_FullNominalCounts[i][maxV] /
           * Utils.sum(m_ClusterSizes) * 100.0);
           */
          int percent = 6; // max percent width (100%)
          String nomV = "" + m_FullNominalCounts[i][maxV];
          // + " (" + percent + "%)";
          if (nomV.length() + percent > maxWidth) {
            maxWidth = nomV.length() + 1;
          }
        }
      }
    }

    // check for size of cluster sizes
    for (int i = 0; i < m_ClusterSizes.length; i++) {
      String size = "(" + m_ClusterSizes[i] + ")";
      if (size.length() > maxWidth) {
        maxWidth = size.length();
      }
    }

    if (m_displayStdDevs && maxAttWidth < "missing".length()) {
      maxAttWidth = "missing".length();
    }

    String plusMinus = "+/-";
    maxAttWidth += 2;
    if (m_displayStdDevs && containsNumeric) {
      maxWidth += plusMinus.length();
    }
    if (maxAttWidth < "Attribute".length() + 2) {
      maxAttWidth = "Attribute".length() + 2;
    }

    if (maxWidth < "Full Data".length()) {
      maxWidth = "Full Data".length() + 1;
    }

    if (maxWidth < "missing".length()) {
      maxWidth = "missing".length() + 1;
    }

    StringBuffer temp = new StringBuffer();
    temp.append("\nkMeans\n======\n");
   

   

    temp.append("\n\nCluster centroids:\n");
    temp.append(pad("Cluster#", " ", (maxAttWidth + (maxWidth * 2 + 2))
        - "Cluster#".length(), true));

    temp.append("\n");
    temp.append(pad("Attribute", " ", maxAttWidth - "Attribute".length(), false));

    temp.append(pad("Full Data", " ", maxWidth + 1 - "Full Data".length(), true));

    // cluster numbers
    for (int i = 0; i < m_NumClusters; i++) {
      String clustNum = "" + i;
      temp.append(pad(clustNum, " ", maxWidth + 1 - clustNum.length(), true));
    }
    temp.append("\n");

    // cluster sizes
    String cSize = "(" + Utils.sum(m_ClusterSizes) + ")";
    temp.append(pad(cSize, " ", maxAttWidth + maxWidth + 1 - cSize.length(),
        true));
    for (int i = 0; i < m_NumClusters; i++) {
      cSize = "(" + m_ClusterSizes[i] + ")";
      temp.append(pad(cSize, " ", maxWidth + 1 - cSize.length(), true));
    }
    temp.append("\n");

    temp.append(pad("", "=",
        maxAttWidth
            + (maxWidth * (m_ClusterCentroids.numInstances() + 1)
                + m_ClusterCentroids.numInstances() + 1), true));
    temp.append("\n");

    for (int i = 0; i < m_ClusterCentroids.numAttributes(); i++) {
      String attName = m_ClusterCentroids.attribute(i).name();
      temp.append(attName);
      for (int j = 0; j < maxAttWidth - attName.length(); j++) {
        temp.append(" ");
      }

      String strVal;
      String valMeanMode;
      // full data
      if (m_ClusterCentroids.attribute(i).isNominal()) {
        if (m_FullMeansOrMediansOrModes[i] == -1) { // missing
          valMeanMode = pad("missing", " ", maxWidth + 1 - "missing".length(),
              true);
        } else {
          valMeanMode = pad(
              (strVal = m_ClusterCentroids.attribute(i).value(
                  (int) m_FullMeansOrMediansOrModes[i])), " ", maxWidth + 1
                  - strVal.length(), true);
        }
      } else {
        if (Double.isNaN(m_FullMeansOrMediansOrModes[i])) {
          valMeanMode = pad("missing", " ", maxWidth + 1 - "missing".length(),
              true);
        } else {
          valMeanMode = pad(
              (strVal = Utils.doubleToString(m_FullMeansOrMediansOrModes[i],
                  maxWidth, 4).trim()), " ", maxWidth + 1 - strVal.length(),
              true);
        }
      }
      temp.append(valMeanMode);

      for (int j = 0; j < m_NumClusters; j++) {
        if (m_ClusterCentroids.attribute(i).isNominal()) {
          if (m_ClusterCentroids.instance(j).isMissing(i)) {
            valMeanMode = pad("missing", " ",
                maxWidth + 1 - "missing".length(), true);
          } else {
            valMeanMode = pad(
                (strVal = m_ClusterCentroids.attribute(i).value(
                    (int) m_ClusterCentroids.instance(j).value(i))), " ",
                maxWidth + 1 - strVal.length(), true);
          }
        } else {
          if (m_ClusterCentroids.instance(j).isMissing(i)) {
            valMeanMode = pad("missing", " ",
                maxWidth + 1 - "missing".length(), true);
          } else {
            valMeanMode = pad(
                (strVal = Utils.doubleToString(
                    m_ClusterCentroids.instance(j).value(i), maxWidth, 4)
                    .trim()), " ", maxWidth + 1 - strVal.length(), true);
          }
        }
        temp.append(valMeanMode);
      }
      temp.append("\n");

      if (m_displayStdDevs) {
        // Std devs/max nominal
        String stdDevVal = "";

        if (m_ClusterCentroids.attribute(i).isNominal()) {
          // Do the values of the nominal attribute
          Attribute a = m_ClusterCentroids.attribute(i);
          for (int j = 0; j < a.numValues(); j++) {
            // full data
            String val = "  " + a.value(j);
            temp.append(pad(val, " ", maxAttWidth + 1 - val.length(), false));
            int count = m_FullNominalCounts[i][j];
            int percent = (int) ((double) m_FullNominalCounts[i][j]
                / Utils.sum(m_ClusterSizes) * 100.0);
            String percentS = "" + percent + "%)";
            percentS = pad(percentS, " ", 5 - percentS.length(), true);
            stdDevVal = "" + count + " (" + percentS;
            stdDevVal = pad(stdDevVal, " ", maxWidth + 1 - stdDevVal.length(),
                true);
            temp.append(stdDevVal);

            // Clusters
            for (int k = 0; k < m_NumClusters; k++) {
              count = m_ClusterNominalCounts[k][i][j];
              percent = (int) ((double) m_ClusterNominalCounts[k][i][j]
                  / m_ClusterSizes[k] * 100.0);
              percentS = "" + percent + "%)";
              percentS = pad(percentS, " ", 5 - percentS.length(), true);
              stdDevVal = "" + count + " (" + percentS;
              stdDevVal = pad(stdDevVal, " ",
                  maxWidth + 1 - stdDevVal.length(), true);
              temp.append(stdDevVal);
            }
            temp.append("\n");
          }
          // missing (if any)
          if (m_FullMissingCounts[i] > 0) {
            // Full data
            temp.append(pad("  missing", " ",
                maxAttWidth + 1 - "  missing".length(), false));
            int count = m_FullMissingCounts[i];
            int percent = (int) ((double) m_FullMissingCounts[i]
                / Utils.sum(m_ClusterSizes) * 100.0);
            String percentS = "" + percent + "%)";
            percentS = pad(percentS, " ", 5 - percentS.length(), true);
            stdDevVal = "" + count + " (" + percentS;
            stdDevVal = pad(stdDevVal, " ", maxWidth + 1 - stdDevVal.length(),
                true);
            temp.append(stdDevVal);

            // Clusters
            for (int k = 0; k < m_NumClusters; k++) {
              count = m_ClusterMissingCounts[k][i];
              percent = (int) ((double) m_ClusterMissingCounts[k][i]
                  / m_ClusterSizes[k] * 100.0);
              percentS = "" + percent + "%)";
              percentS = pad(percentS, " ", 5 - percentS.length(), true);
              stdDevVal = "" + count + " (" + percentS;
              stdDevVal = pad(stdDevVal, " ",
                  maxWidth + 1 - stdDevVal.length(), true);
              temp.append(stdDevVal);
            }

            temp.append("\n");
          }

          temp.append("\n");
        } else {
          // Full data
          if (Double.isNaN(m_FullMeansOrMediansOrModes[i])) {
            stdDevVal = pad("--", " ", maxAttWidth + maxWidth + 1 - 2, true);
          } else {
            stdDevVal = pad(
                (strVal = plusMinus
                    + Utils.doubleToString(m_FullStdDevs[i], maxWidth, 4)
                        .trim()), " ",
                maxWidth + maxAttWidth + 1 - strVal.length(), true);
          }
          temp.append(stdDevVal);

          // Clusters
          for (int j = 0; j < m_NumClusters; j++) {
            if (m_ClusterCentroids.instance(j).isMissing(i)) {
              stdDevVal = pad("--", " ", maxWidth + 1 - 2, true);
            } else {
              stdDevVal = pad(
                  (strVal = plusMinus
                      + Utils.doubleToString(
                          m_ClusterStdDevs.instance(j).value(i), maxWidth, 4)
                          .trim()), " ", maxWidth + 1 - strVal.length(), true);
            }
            temp.append(stdDevVal);
          }
          temp.append("\n\n");
        }
      }
    }

    temp.append("\n\n");
    return temp.toString();
  }

  private String pad(String source, String padChar, int length, boolean leftPad) {
    StringBuffer temp = new StringBuffer();

    if (leftPad) {
      for (int i = 0; i < length; i++) {
        temp.append(padChar);
      }
      temp.append(source);
    } else {
      temp.append(source);
      for (int i = 0; i < length; i++) {
        temp.append(padChar);
      }
    }
    return temp.toString();
  }

  /**
   * Gets the the cluster centroids.
   * 
   * @return the cluster centroids
   */
  public Instances getClusterCentroids() {
    return m_ClusterCentroids;
  }

  /**
   * Gets the standard deviations of the numeric attributes in each cluster.
   * 
   * @return the standard deviations of the numeric attributes in each cluster
   */
  public Instances getClusterStandardDevs() {
    return m_ClusterStdDevs;
  }

  /**
   * Returns for each cluster the frequency counts for the values of each
   * nominal attribute.
   * 
   * @return the counts
   */
  public int[][][] getClusterNominalCounts() {
    return m_ClusterNominalCounts;
  }

  /**
   * Gets the squared error for all clusters.
   * 
   * @return the squared error, NaN if fast distance calculation is used
   * @see #m_FastDistanceCalc
   */


  /**
   * Gets the number of instances in each cluster.
   * 
   * @return The number of instances in each cluster
   */
  public int[] getClusterSizes() {
    return m_ClusterSizes;
  }

  /**
   * Gets the assignments for each instance.
   * 
   * @return Array of indexes of the centroid assigned to each instance
   * @throws Exception if order of instances wasn't preserved or no assignments
   *           were made
   */
  public int[] getAssignments() throws Exception {
   
    return m_Assignments;
  }

  /**
   * Returns the revision string.
   * 
   * @return the revision
   */
  @Override
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 9375 $");
  }

  /**
   * Main method for executing this class.
   * 
   * @param args use -h to list all parameters
   */
  public static void main(String[] args) {
    runClusterer(new AutomaticKMeans(), args);
  }

@Override
public String getout() {
	// TODO Auto-generated method stub
	return null;
}
}

