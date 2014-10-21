import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FileUtils;
import org.ini4j.Wini;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.SimpleBookmark;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.ImageIcon;

public class ConfigTools {

	private JFrame frame;
	private JTextField UATDeployDirectory;
	private JCheckBox cbFull, cbHealthCheck, cbDry, cbVersion, cbFrom, cbTo,
			cbSection, cbHelp, cbNoHealthCheck, cbOrderCheck,
			cbClearStoredPasswords, cbNoBadBlockCheck, cbNoConfigCheck,
			cbNoCheck, cbNoPrepStep, cbNoUpgradeStep, cbNoPostStep,
			cbNoExitOnFailure, cbNoConfirm, cbZone1, cbZone2, cbApplication;
	JComboBox<String> comboFrom, comboSuiteSelector;
	private JLabel lblUATDeployDirectory;
	public Wini settingsIni;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigTools window = new ConfigTools();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConfigTools() {
		setLookAndFeel();
		initialize();

	}

	private void setLookAndFeel() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info
						.getClassName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 861, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		frame.setLocationRelativeTo(null);

		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// frame.setLocation(dim.width/2-frame.getSize().width/2,
		// dim.height/2-frame.getSize().height/2);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);

		try {
			settingsIni = new Wini(new File("settings.ini"));
		} catch (IOException ioe) {

		}

		JPanel panelUAT = new JPanel();
		tabbedPane.addTab("UAT tool", null, panelUAT, null);
		panelUAT.setLayout(null);

		JButton btnStartUAT = new JButton("Run");
		btnStartUAT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				TerminalWindow t = new TerminalWindow(UATDeployDirectory
						.getText() + "/upgradeTool.sh -dry");
			}
		});
		btnStartUAT.setBounds(51, 434, 117, 25);
		panelUAT.add(btnStartUAT);
		
		lblUATDeployDirectory = new JLabel("");
		lblUATDeployDirectory.setVisible(false);
		lblUATDeployDirectory.setIcon(new ImageIcon(ConfigTools.class.getResource("/images/warning24x24.png")));
		lblUATDeployDirectory.setBounds(509, 8, 24, 24);
		panelUAT.add(lblUATDeployDirectory);
		
		UATDeployDirectory = new JTextField();
		UATDeployDirectory.setToolTipText("rthshdsghsghsg");

		UATDeployDirectory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				// chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setCurrentDirectory(new java.io.File(UATDeployDirectory
						.getText()));
				chooser.setDialogTitle("Select UAT deploy directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				e.getComponent().setBackground(Color.GREEN);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): "
							+ chooser.getCurrentDirectory());
					UATDeployDirectory.setText(chooser.getSelectedFile()
							.toString());
					System.out.println("getSelectedFile() : "
							+ chooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}
			}
		});
		UATDeployDirectory.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						warn(e);
					}

					public void removeUpdate(DocumentEvent e) {
						warn(e);
					}

					public void insertUpdate(DocumentEvent e) {
						warn(e);
					}

					public void warn(DocumentEvent e) {
						System.out.println(e);
						try {
							File dir = new File(UATDeployDirectory.getText()
									+ "/config");
							String[] extensions = new String[] { "xml" };
							System.out.println(dir);
							List<File> files = (List<File>) FileUtils
									.listFiles(dir, extensions, false);
							for (File file : files) {
								System.out.println("file: "
										+ file.getCanonicalPath());
								comboSuiteSelector.addItem(file.getName());
								comboSuiteSelector.setEnabled(true);
							}
							
							lblUATDeployDirectory.setVisible(false);
							
							//UATDeployDirectory.setBackground(new Color(0, 255, 0));
							//UATDeployDirectory.repaint();
						} catch (IOException ioe) {
							// TODO Auto-generated catch block
							ioe.printStackTrace();
							comboSuiteSelector.setEnabled(false);
							lblUATDeployDirectory.setVisible(true);
							//UATDeployDirectory.setBackground(Color.RED);
							//UATDeployDirectory.repaint();
						} catch (IllegalArgumentException iae) {
							// TODO Auto-generated catch block
							// iae.printStackTrace();
							System.out.println(iae.toString());
							comboSuiteSelector.setEnabled(false);
							//UATDeployDirectory.setBackground(Color.RED);
							//UATDeployDirectory.repaint();
							lblUATDeployDirectory.setVisible(true);
							
						}
					}
				});

		UATDeployDirectory.setBounds(128, 8, 376, 24);
		panelUAT.add(UATDeployDirectory);
		UATDeployDirectory.setColumns(10);

		JLabel lblUATDeployDir = new JLabel("UAT deploy dir");
		lblUATDeployDir.setBounds(12, 7, 152, 24);
		panelUAT.add(lblUATDeployDir);

		JPanel panelParameters = new JPanel();
		panelParameters.setBorder(new TitledBorder(new LineBorder(new Color(0,
				0, 0)), "Parameters", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		panelParameters.setBounds(551, 12, 291, 543);
		panelUAT.add(panelParameters);
		panelParameters.setLayout(null);

		comboSuiteSelector = new JComboBox<String>();
		comboSuiteSelector.setEnabled(false);
		comboSuiteSelector.setBounds(128, 44, 411, 24);
		comboSuiteSelector.addItem("Select suite!");
		panelUAT.add(comboSuiteSelector);

		JLabel lblTestSuite = new JLabel("Test suite");
		lblTestSuite.setBounds(12, 44, 152, 24);
		panelUAT.add(lblTestSuite);

		parametersSetup(panelParameters);
		
				JPanel panelJCAT = new JPanel();
				tabbedPane.addTab("JCAT tool", null, panelJCAT, null);

		

		

		loadParametersFromIni();
	}

	private void loadParametersFromIni() {

	}

	public void saveParameterValue(String section, String key, String value) {
		try {
			settingsIni.put(section, key, value);
			settingsIni.store();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parametersSetup(JPanel panelParameters) {
		try {
			UATDeployDirectory.setText(settingsIni.get("path",	"UATDeployDirectory"));
			
			cbFull = new JCheckBox(" -full");
			cbFull.setBounds(8, 41, 150, 20);
			cbFull.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "full")));
			cbFull.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbFull.isSelected()) {
						cbFrom.setSelected(false);
						cbTo.setSelected(false);
						cbSection.setSelected(false);
						saveParameterValue("UATparameters", "full", "true");
					} else {
						saveParameterValue("UATparameters", "full", "false");
					}
				}
			});
			panelParameters.add(cbFull);

			cbHealthCheck = new JCheckBox(" -healthcheck");
			cbHealthCheck.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "healthcheck")));
			cbHealthCheck.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbHealthCheck.isSelected()) {
						cbFull.setSelected(false);
						cbFrom.setSelected(false);
						cbTo.setSelected(false);
						cbSection.setSelected(false);
						saveParameterValue("UATparameters", "healthcheck", "true");
					} else {
						saveParameterValue("UATparameters", "healthcheck", "false");
					}
				}
			});
			cbHealthCheck.setBounds(8, 17, 150, 20);
			panelParameters.add(cbHealthCheck);

			cbDry = new JCheckBox(" -dry");
			cbDry.setSelected(Boolean.parseBoolean(settingsIni.get("UATparameters",
					"dry")));
			cbDry.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbDry.isSelected()) {
						saveParameterValue("UATparameters", "dry", "true");
					} else {
						saveParameterValue("UATparameters", "dry", "false");
					}
				}
			});
			cbDry.setBounds(8, 161, 150, 20);
			panelParameters.add(cbDry);

			cbVersion = new JCheckBox(" -version");
			cbVersion.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "version")));
			cbVersion.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbVersion.isSelected()) {
						saveParameterValue("UATparameters", "version", "true");
					} else {
						saveParameterValue("UATparameters", "version", "false");
					}
				}
			});
			cbVersion.setBounds(8, 183, 117, 20);
			panelParameters.add(cbVersion);

			cbFrom = new JCheckBox(" -from");
			cbFrom.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "from")));
			cbFrom.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbFrom.isSelected()) {
						cbHealthCheck.setSelected(false);
						cbFull.setSelected(false);
						saveParameterValue("UATparameters", "from", "true");
					} else {
						saveParameterValue("UATparameters", "from", "false");
					}
				}
			});
			cbFrom.setBounds(8, 65, 110, 20);
			panelParameters.add(cbFrom);

			cbTo = new JCheckBox(" -to");
			cbTo.setSelected(Boolean.parseBoolean(settingsIni.get("UATparameters",
					"to")));
			cbTo.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbTo.isSelected()) {
						cbHealthCheck.setSelected(false);
						cbFull.setSelected(false);
						saveParameterValue("UATparameters", "to", "true");
					} else {
						saveParameterValue("UATparameters", "to", "false");
					}
				}
			});
			cbTo.setBounds(8, 89, 110, 20);
			panelParameters.add(cbTo);

			cbSection = new JCheckBox(" -section");
			cbSection.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "section")));
			cbSection.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbSection.isSelected()) {
						cbHealthCheck.setSelected(false);
						cbFull.setSelected(false);
						new SectionSelector(UATDeployDirectory.getText());
						saveParameterValue("UATparameters", "section", "true");
					} else {
						saveParameterValue("UATparameters", "section", "false");
					}
				}
			});
			cbSection.setBounds(8, 113, 110, 24);
			panelParameters.add(cbSection);

			cbHelp = new JCheckBox(" -help");
			cbHelp.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "help")));
			cbHelp.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbHelp.isSelected()) {
						saveParameterValue("UATparameters", "help", "true");
					} else {
						saveParameterValue("UATparameters", "help", "false");
					}
				}
			});
			cbHelp.setBounds(8, 137, 110, 20);
			panelParameters.add(cbHelp);

			cbNoHealthCheck = new JCheckBox(" -nohealthcheck");
			cbNoHealthCheck.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "nohealthcheck")));
			cbHelp.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbHelp.isSelected()) {
						saveParameterValue("UATparameters", "help", "true");
					} else {
						saveParameterValue("UATparameters", "help", "false");
					}
				}
			});
			cbNoHealthCheck.setBounds(8, 207, 150, 20);
			panelParameters.add(cbNoHealthCheck);

			cbOrderCheck = new JCheckBox(" -ordercheck");
			cbOrderCheck.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "ordercheck")));
			cbOrderCheck.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbOrderCheck.isSelected()) {
						saveParameterValue("UATparameters", "ordercheck", "true");
					} else {
						saveParameterValue("UATparameters", "ordercheck", "false");
					}
				}
			});
			cbOrderCheck.setBounds(8, 231, 150, 20);
			panelParameters.add(cbOrderCheck);

			cbClearStoredPasswords = new JCheckBox(" -clearstoredpasswords");
			cbClearStoredPasswords.setSelected(Boolean.parseBoolean(settingsIni
					.get("UATparameters", "clearstoredpasswords")));
			cbClearStoredPasswords.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbClearStoredPasswords.isSelected()) {
						saveParameterValue("UATparameters", "clearstoredpasswords",
								"true");
					} else {
						saveParameterValue("UATparameters", "clearstoredpasswords",
								"false");
					}
				}
			});
			cbClearStoredPasswords.setBounds(8, 255, 200, 20);
			panelParameters.add(cbClearStoredPasswords);

			cbNoBadBlockCheck = new JCheckBox(" -nobadblockcheck");
			cbNoBadBlockCheck.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "nobadblockcheck")));
			cbNoBadBlockCheck.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoBadBlockCheck.isSelected()) {
						saveParameterValue("UATparameters", "nobadblockcheck",
								"true");
					} else {
						saveParameterValue("UATparameters", "nobadblockcheck",
								"false");
					}
				}
			});
			cbNoBadBlockCheck.setBounds(8, 279, 200, 20);
			panelParameters.add(cbNoBadBlockCheck);

			cbNoConfigCheck = new JCheckBox(" -noconfigcheck");
			cbNoConfigCheck.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "noconfigcheck")));
			cbNoConfigCheck.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoConfigCheck.isSelected()) {
						saveParameterValue("UATparameters", "noconfigcheck", "true");
					} else {
						saveParameterValue("UATparameters", "noconfigcheck",
								"false");
					}
				}
			});
			cbNoConfigCheck.setBounds(8, 303, 200, 20);
			panelParameters.add(cbNoConfigCheck);

			cbNoCheck = new JCheckBox(" -nocheck");
			cbNoCheck.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "nocheck")));
			cbNoCheck.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoCheck.isSelected()) {
						saveParameterValue("UATparameters", "nocheck", "true");
					} else {
						saveParameterValue("UATparameters", "nocheck", "false");
					}
				}
			});
			cbNoCheck.setBounds(8, 327, 200, 20);
			panelParameters.add(cbNoCheck);

			cbNoPrepStep = new JCheckBox(" -noprepstep");
			cbNoPrepStep.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "noprepstep")));
			cbNoPrepStep.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoPrepStep.isSelected()) {
						saveParameterValue("UATparameters", "noprepstep", "true");
					} else {
						saveParameterValue("UATparameters", "noprepstep", "false");
					}
				}
			});
			cbNoPrepStep.setBounds(8, 351, 200, 20);
			panelParameters.add(cbNoPrepStep);

			cbNoUpgradeStep = new JCheckBox(" -noupgradestep");
			cbNoUpgradeStep.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "noupgradestep")));
			cbNoUpgradeStep.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoUpgradeStep.isSelected()) {
						saveParameterValue("UATparameters", "noupgradestep", "true");
					} else {
						saveParameterValue("UATparameters", "noupgradestep",
								"false");
					}
				}
			});
			cbNoUpgradeStep.setBounds(8, 375, 200, 20);
			panelParameters.add(cbNoUpgradeStep);

			cbNoPostStep = new JCheckBox(" -nopoststep");
			cbNoPostStep.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "nopoststep")));
			cbNoPostStep.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoPostStep.isSelected()) {
						saveParameterValue("UATparameters", "nopoststep", "true");
					} else {
						saveParameterValue("UATparameters", "nopoststep", "false");
					}
				}
			});
			cbNoPostStep.setBounds(8, 399, 200, 20);
			panelParameters.add(cbNoPostStep);

			cbNoExitOnFailure = new JCheckBox(" -noexitonfailure");
			cbNoExitOnFailure.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "noexitonfailure")));
			cbNoExitOnFailure.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoExitOnFailure.isSelected()) {
						saveParameterValue("UATparameters", "noexitonfailure",
								"true");
					} else {
						saveParameterValue("UATparameters", "noexitonfailure",
								"false");
					}
				}
			});
			cbNoExitOnFailure.setBounds(8, 423, 200, 20);
			panelParameters.add(cbNoExitOnFailure);

			cbNoConfirm = new JCheckBox(" -noconfirm");
			cbNoConfirm.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "noconfirm")));
			cbNoConfirm.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbNoConfirm.isSelected()) {
						saveParameterValue("UATparameters", "noconfirm", "true");
					} else {
						saveParameterValue("UATparameters", "noconfirm", "false");
					}
				}
			});
			cbNoConfirm.setBounds(8, 446, 200, 20);
			panelParameters.add(cbNoConfirm);

			cbZone1 = new JCheckBox(" -zone1");
			cbZone1.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "zone1")));
			cbZone1.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbZone1.isSelected()) {
						saveParameterValue("UATparameters", "zone1", "true");
					} else {
						saveParameterValue("UATparameters", "zone1", "false");
					}
				}
			});
			cbZone1.setBounds(8, 470, 200, 20);
			panelParameters.add(cbZone1);

			cbZone2 = new JCheckBox(" -zone2");
			cbZone2.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "zone2")));
			cbZone2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbZone2.isSelected()) {
						saveParameterValue("UATparameters", "zone2", "true");
					} else {
						saveParameterValue("UATparameters", "zone2", "false");
					}
				}
			});
			cbZone2.setBounds(8, 494, 200, 20);
			panelParameters.add(cbZone2);

			cbApplication = new JCheckBox(" -application");
			cbApplication.setSelected(Boolean.parseBoolean(settingsIni.get(
					"UATparameters", "application")));
			cbApplication.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (cbApplication.isSelected()) {
						saveParameterValue("UATparameters", "application", "true");
					} else {
						saveParameterValue("UATparameters", "application", "false");
					}
				}
			});
			cbApplication.setBounds(8, 518, 200, 20);
			panelParameters.add(cbApplication);

			comboFrom = new JComboBox<String>();
			comboFrom.setModel(new DefaultComboBoxModel<String>(new String[] {
					"test", "adfda", "werwer", "werwe" }));
			comboFrom.setBounds(137, 69, 130, 20);
			panelParameters.add(comboFrom);
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}
	}
}
