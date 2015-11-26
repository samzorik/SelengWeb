package mainpak;

import javax.swing.JFrame;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.PiePlot;

public class PieChart extends JFrame implements ChartMouseListener {
private JFreeChart chart; 
private PiePlot plot;
private int angle=270;
private static double explodePercent = 0.99;
private static long count=0;

public PieChart(String title,ChartPanel chp){
		super(title);        
        chp.addChartMouseListener(this); 
//        chp.
//        chp.add
        setContentPane(chp);
}
public void chartMouseClicked(ChartMouseEvent chartmouseevent){
ChartEntity chartentity = chartmouseevent.getEntity();
if (chartentity != null){
   System.out.println("Mouse clicked: " + chartentity.toString());
}
else
System.out.println("Mouse clicked: null entity.");
}

public void chartMouseMoved(ChartMouseEvent chartmouseevent){
}
}