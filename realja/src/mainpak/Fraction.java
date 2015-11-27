package mainpak;

import java.util.*;

public class Fraction implements Comparable
{
  public int apriori_clus;
  public int res_clus;
  public double frac_in_apriori;
  public double frac_in_res;
  public int count;
                           
  public Fraction(int apriori_clus, int res_clus, double frac_in_apriori, double frac_in_res)
  {
    this.apriori_clus = apriori_clus;
    this.res_clus = res_clus;
    this.frac_in_apriori = frac_in_apriori;
    this.frac_in_res = frac_in_res;  
  }
                           
  /* Перегрузка метода compareTo */
                       
  public int compareTo(Object obj)
  {
    Fraction tmp = (Fraction)obj;
    if(this.frac_in_apriori < tmp.frac_in_apriori)
    {
      /* текущее меньше полученного */
      return 1;
    }   
    else if(this.frac_in_apriori > tmp.frac_in_apriori)
    {
      /* текущее больше полученного */
      return -1;
    }
    /* текущее равно полученному */
    return 0;  
  }

}