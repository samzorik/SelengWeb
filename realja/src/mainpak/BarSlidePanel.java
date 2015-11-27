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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
//import org.jfree.data.ba;
import org.jfree.data.general.DatasetGroup;


public class BarSlidePanel extends JPanel implements ChangeListener{

	JScrollBar scroller;

    /** The dataset. */
    SlidingCategoryDataset dataset;

    /**
     * Creates a new demo panel.
     */
    public BarSlidePanel(SlidingCategoryDataset dataset,int orientation,int a,int b) {
        super(new BorderLayout());
        // get data for diagrams
        JFreeChart chart = createChart(dataset);
        ChartPanel cp1 = new ChartPanel(chart);
        cp1.setPreferredSize(new Dimension(400, 400));
        this.dataset=dataset;
        this.scroller = new JScrollBar((orientation == SwingConstants.HORIZONTAL) ? SwingConstants.VERTICAL:SwingConstants.HORIZONTAL
        		, 0, a, 0, b);
        add(cp1);
        this.scroller.getModel().addChangeListener(this);
        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.add(this.scroller);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        scrollPanel.setBackground(Color.white);
        add(scrollPanel, BorderLayout.SOUTH);
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
            "SlidingCategoryDatasetDemo2",         // chart title
            "Series",               // domain axis label
            "Value",                  // range axis label
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

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0.0, 100.0);

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64));
        renderer.setSeriesPaint(0, gp0);

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
    	JFrame opa=new JFrame("Chart");
    	DefaultCategoryDataset data=new DefaultCategoryDataset();
    	DatasetGroup group=new DatasetGroup();
//    	data.
    	data.addValue(10, "one", "two");
//    	data
    	SlidingCategoryDataset alldata=new SlidingCategoryDataset(data,0,1);
//    	alldata.
    	BarSlidePanel chart=new BarSlidePanel(alldata,0,0,0);
    	opa.add(chart);
    	opa.setSize(700, 500);
    	opa.setVisible(true);
    	
    }
	
}
