package mainpak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
//import org.jfree.data.ba;
import org.jfree.data.general.DatasetGroup;
import org.jfree.ui.RectangleEdge;


public class BarChart extends JPanel implements ChangeListener{

	JScrollBar scroller;

    /** The dataset. */
    SlidingCategoryDataset dataset;

    /**
     * Creates a new demo panel.
     */
    public BarChart(SlidingCategoryDataset dataset) {
        super(new BorderLayout());
            
        JFrame opa=new JFrame("Гистограмма кластера");
    	JFreeChart chart=createChart(dataset);
//    	chart.getLegend().setPosition(RectangleEdge.BOTTOM);
    	ChartPanel chpanel=new ChartPanel(chart);
    	opa.add(chpanel);
    	opa.setSize(900, 700);
    	opa.setVisible(true);
    }

        /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Гистограмма кластера",         // chart title
            "Состав кластера",               // domain axis label
            "Количество вхождений",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            false,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);
//        chart.getLegend().
        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0.0, 10.0);

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        renderer.setItemMargin(.0);
//        renderer.set
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64));
        renderer.setSeriesPaint(0, gp0);
//        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        return chart;

    }

    /**
     * Handle a change in the slider by updating the dataset value.  This
     * automatically triggers a chart repaint.
     *
     * @param e  the event.
     */
    public void stateChanged(ChangeEvent e) {
        dataset.setFirstCategoryIndex(this.scroller.getValue());
    }
    
    public static void main(String[] args)
    {
    	DefaultCategoryDataset data=new DefaultCategoryDataset();
    	for (int i = 0; i < 10; i++) {
			
    		data.addValue(10, "attr1", "cluster"+i);
    		data.addValue(50, "attr2", "cluster"+i);
    		data.addValue(25, "attr1", "cluster"+i+i);
    		data.addValue(100, "attr2", "cluster"+i+i);
		}
    	SlidingCategoryDataset alldata=new SlidingCategoryDataset(data,0,100);
    	BarChart shene=new BarChart(alldata);
    }
	
}
