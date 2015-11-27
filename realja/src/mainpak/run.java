package mainpak;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.JFrame;

import org.xml.sax.InputSource;

import weka.gui.explorer.Explorer;


public class run {

	/**
	 * @param args 10412792
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		InputStream is = run.class.getResourceAsStream("treeml.dtd");
//        return is != null ? new InputSource(is) :
//            super.resolveEntity(publicId, systemId);
		
		JFrame frame = new JFrame("Система предварительного анализа данных");
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		Explorer clus = new Explorer();
		frame.add(clus);
		frame.setSize(1280, 600);
		frame.setVisible(true);
	}
}
