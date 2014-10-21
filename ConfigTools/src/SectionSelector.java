import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JLabel;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import javax.swing.JComboBox;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;

import org.apache.commons.io.FileUtils;

import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Dialog.ModalityType;

public class SectionSelector extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> bookmarks = new TreeMap<String, String>();
	private String upgradeBaseSection = "";
	
	public SectionSelector(final String UATDeployDirectory) {
		
		
		
		getSections();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle("Section selector");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
		}
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 10));
		
		JButton btnNewButton = new JButton("New button");
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_3 = new JButton("New button");
		panel_1.add(btnNewButton_3);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 10));
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_2.add(btnNewButton_1);
		
		JButton btnNewButton_4 = new JButton("New button");
		panel_2.add(btnNewButton_4);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 10));
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_3.add(btnNewButton_2);
		
		JButton btnNewButton_5 = new JButton("New button");
		panel_3.add(btnNewButton_5);
		
		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println(comboBox.getSelectedItem());
					
					/*
					
					File fXmlFile = new File(UATDeployDirectory + "/config/" + comboBox.getSelectedItem().toString());
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder;
					try {
						dBuilder = dbFactory.newDocumentBuilder();
						Document doc = dBuilder.parse(fXmlFile);
						
						doc.getDocumentElement().normalize();
						 
						System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					 
						NodeList nList = doc.getElementsByTagName("Test");
					 
						System.out.println("----------------------------");
					 
						for (int temp = 0; temp < nList.getLength(); temp++) {
					 
							Node nNode = nList.item(temp);
					 
							System.out.println("\nCurrent Element :" + nNode.getNodeName());
					 
							if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					 
								Element eElement = (Element) nNode;
					 
								System.out.println("Type : " + eElement.getAttribute("type"));
								System.out.println("Name : " + eElement.getElementsByTagName("Name").item(0).getTextContent());
								System.out.println("SuiteClass : " + eElement.getElementsByTagName("SuiteClass").item(0).getTextContent());
								System.out.println("Method : " + eElement.getElementsByTagName("Method").item(0).getTextContent());
								System.out.println("Description : " + eElement.getElementsByTagName("Description").item(0).getTextContent());
								System.out.println("Type : " + eElement.getElementsByTagName("Type").item(0).getTextContent());
								System.out.println("StopOnEvent : " + eElement.getElementsByTagName("StopOnEvent").item(0).getTextContent());
								System.out.println("FetchLog : " + eElement.getElementsByTagName("FetchLog").item(0).getTextContent());
					 
							}
						}
						
					} catch (SAXException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					*/
					
				}
			}
		});
		//comboBox.setMinimumSize(new Dimension(150, 24));
		panel_4.add(comboBox);
		
		try {
			File dir = new File(UATDeployDirectory + "/config");
			String[] extensions = new String[] { "xml" };
			System.out.println(dir);
			List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, false);
			for (File file : files) {
				System.out.println("file: " + file.getCanonicalPath());
				comboBox.addItem(file.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getSections(){
		try {
			PdfReader reader;
		
			reader = new PdfReader("/home/egabczo/upgrade_doc.pdf");
		
			List<HashMap<String, Object>> list = SimpleBookmark.getBookmark(reader);
			for (Iterator<HashMap<String, Object>> i = list.iterator(); i.hasNext();) {
				showBookmark((Map<?, ?>) i.next());
			}
			
			System.out.println(bookmarks);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void showBookmark(Map<?, ?> bookmark) {
		String title = bookmark.get("Title").toString();
		System.out.println(title);
		String key = title.split(" ")[0];
		String value = title.replace(key + " ", "");
		
		
		if (title.toLowerCase().contains("upgrading tsp")){
			upgradeBaseSection = title.split(" ")[0];
			System.out.println("upgradeBaseSection found");
		}
		
		if (upgradeBaseSection.length() > 0 && title.startsWith(upgradeBaseSection)){
			bookmarks.put(key, value);
		}
		
		ArrayList<?> kids = (ArrayList<?>) bookmark.get("Kids");
		if (kids == null)
			return;
		for (Iterator<?> i = kids.iterator(); i.hasNext();) {
			showBookmark((Map<?, ?>) i.next());
		}
	}
}
