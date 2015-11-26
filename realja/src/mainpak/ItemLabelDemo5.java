package mainpak;

import java.awt.Dimension;
import java.awt.Paint;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.*;

public class ItemLabelDemo5 extends JFrame
{
	public static String [][] data;
	private static class MyStackedBarRenderer extends StackedBarRenderer
	{

		int oldColumn;
		int count;
		Paint list[];

		public Paint getItemPaint(int i, int j)
		{
			if (oldColumn != j)
			{
				count = 0;
				oldColumn = j;
			} else
			{
				count++;
			}
			return list[count];
		}

		private MyStackedBarRenderer()
		{
			oldColumn = -99;
			count = 0;
			list = DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE;
		}

	}


	public ItemLabelDemo5(String s)
	{
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}
	
	public ItemLabelDemo5(String [][]s, String title)
	{
		super(title);
		data = s;
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
		pack();
//		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}
	

	public static CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
//		defaultcategorydataset.addValue(52.829999999999998D, "Germanydghdfghdfghdfghdfgh", "Western EU");
//		defaultcategorydataset.addValue(20.829999999999998D, "France", "Western EU");
//		defaultcategorydataset.addValue(10.83D, "Great Britain", "Western EU");
//		defaultcategorydataset.addValue(7.3300000000000001D, "Netherlands", "Western EU");
//		defaultcategorydataset.addValue(4.6600000000000001D, "Belgium", "Western EU");
//		defaultcategorydataset.addValue(57.140000000000001D, "Spain", "Southern EU");
//		defaultcategorydataset.addValue(14.279999999999999D, "Greece", "Southern EU");
//		defaultcategorydataset.addValue(14.279999999999999D, "Italy", "Southern EU");
//		defaultcategorydataset.addValue(14.279999999999999D, "Portugal", "Southern EU");
//		defaultcategorydataset.addValue(100D, "Czech Republic", "Eastern EU");
//		defaultcategorydataset.addValue(66.659999999999997D, "Denmark", "Scandinavia");
//		defaultcategorydataset.addValue(33.329999999999998D, "Finland", "Scandinavia");
//		defaultcategorydataset.addValue(0.0D, "", "Africa");
//		defaultcategorydataset.addValue(100D, "Israel", "Asia");
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				defaultcategorydataset.addValue(1, data[i][j], "������� "+(i+1));
			}
		}
		return defaultcategorydataset;
	}

	private static JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createStackedBarChart("����������� ���������", null, null, categorydataset, PlotOrientation.VERTICAL, false, true, false);
		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
		MyStackedBarRenderer mystackedbarrenderer = new MyStackedBarRenderer();
		categoryplot.setRenderer(mystackedbarrenderer);
//		categoryplot.
		ItemLabelPosition itemlabelposition = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 0.0D);
		mystackedbarrenderer.setPositiveItemLabelPositionFallback(itemlabelposition);
		mystackedbarrenderer.setNegativeItemLabelPositionFallback(itemlabelposition);
		StandardCategoryItemLabelGenerator standardcategoryitemlabelgenerator = new StandardCategoryItemLabelGenerator("{0}", NumberFormat.getInstance());
		mystackedbarrenderer.setBaseItemLabelGenerator(standardcategoryitemlabelgenerator);
		mystackedbarrenderer.setBaseItemLabelsVisible(true);
//		mystackedbarrenderer.
		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		numberaxis.setUpperBound(7D);
		ChartUtilities.applyCurrentTheme(jfreechart);
		return jfreechart;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[])
	{
		ItemLabelDemo5 itemlabeldemo5 = new ItemLabelDemo5("����������� ���������");
		itemlabeldemo5.pack();
		RefineryUtilities.centerFrameOnScreen(itemlabeldemo5);
		itemlabeldemo5.setVisible(true);
	}
}
