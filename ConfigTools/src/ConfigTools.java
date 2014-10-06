import java.awt.EventQueue;

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

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ConfigTools {

	private JFrame frame;
	private JTextField deployDirectory;
	private JCheckBox 	cbFull, 
						cbHealthCheck,
						cbDry,
						cbVersion,
						cbFrom,
						cbTo,
						cbSection,
						cbHelp,
						cbNoHealthCheck,
						cbOrderCheck,
						cbClearStoredPasswords,
						cbNoBadBlockCheck,
						cbNoConfigCheck,
						cbNoCheck,
						cbNoPrepStep,
						cbNoUpgradeStep,
						cbNoPostStep,
						cbNoExitOnFailure,
						cbNoConfirm,
						cbZone1,
						cbZone2,
						cbApplication;
	JComboBox<String> comboFrom;				

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
		initialize();
	}

	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 861, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panelUAT = new JPanel();
		tabbedPane.addTab("UAT tool", null, panelUAT, null);
		panelUAT.setLayout(null);
		
		JButton btnStartUAT = new JButton("Run");
		btnStartUAT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Terminal t = new Terminal(deployDirectory.getText() + "/upgradeTool.sh -dry");
			}
		});
		btnStartUAT.setBounds(51, 434, 117, 25);
		panelUAT.add(btnStartUAT);
		
		deployDirectory = new JTextField();
		deployDirectory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Select UAT deploy directory");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			    	deployDirectory.setText(chooser.getSelectedFile().toString());
			    	System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			    } else {
			    	System.out.println("No Selection ");
			    }
			}
		});
		deployDirectory.setBounds(126, 23, 365, 19);
		panelUAT.add(deployDirectory);
		deployDirectory.setColumns(10);
		
		JLabel lblUATDeployDir = new JLabel("UAT deploy dir");
		lblUATDeployDir.setBounds(12, 25, 152, 15);
		panelUAT.add(lblUATDeployDir);
		
		JPanel panelParameters = new JPanel();
		panelParameters.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelParameters.setBounds(551, 12, 291, 543);
		panelUAT.add(panelParameters);
		panelParameters.setLayout(null);
		
		parametersSetup(panelParameters);
		
		JPanel panelJCAT = new JPanel();
		tabbedPane.addTab("JCAT tool", null, panelJCAT, null);
	}
	
	private void parametersSetup(JPanel panelParameters){
		cbFull = new JCheckBox(" -full");
		cbFull.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbFull.isSelected()){
					cbHealthCheck.setSelected(false);
				}
			}
		});
		cbFull.setBounds(8, 41, 150, 20);
		panelParameters.add(cbFull);
		
		cbHealthCheck = new JCheckBox(" -healthcheck");
		cbHealthCheck.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbHealthCheck.isSelected()){
					cbFull.setSelected(false);
					cbFrom.setSelected(false);
					cbTo.setSelected(false);
					cbSection.setSelected(false);
				}
			}
		});
		cbHealthCheck.setBounds(8, 17, 150, 20);
		panelParameters.add(cbHealthCheck);
		
		cbDry = new JCheckBox(" -dry");
		cbDry.setBounds(8, 161, 150, 20);
		panelParameters.add(cbDry);
		
		cbVersion = new JCheckBox(" -version");
		cbVersion.setBounds(8, 183, 117, 20);
		panelParameters.add(cbVersion);
		
		cbFrom = new JCheckBox(" -from");
		cbFrom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbFrom.isSelected()){
					cbHealthCheck.setSelected(false);
				}
			}
		});
		cbFrom.setBounds(8, 65, 110, 20);
		panelParameters.add(cbFrom);
		
		cbTo = new JCheckBox(" -to");
		cbTo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbTo.isSelected()){
					cbHealthCheck.setSelected(false);
				}
			}
		});
		cbTo.setBounds(8, 89, 110, 20);
		panelParameters.add(cbTo);
				
		cbSection = new JCheckBox(" -section");
		cbSection.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbSection.isSelected()){
					cbHealthCheck.setSelected(false);
				}
			}
		});
		cbSection.setBounds(8, 113, 110, 20);
		panelParameters.add(cbSection);
		
		cbHelp = new JCheckBox(" -help");
		cbHelp.setBounds(8, 137, 110, 20);
		panelParameters.add(cbHelp);
		
		cbNoHealthCheck = new JCheckBox(" -nohealthcheck");
		cbNoHealthCheck.setBounds(8, 207, 150, 20);
		panelParameters.add(cbNoHealthCheck);
		
		cbOrderCheck = new JCheckBox(" -ordercheck");
		cbOrderCheck.setBounds(8, 231, 150, 20);
		panelParameters.add(cbOrderCheck);
		
		cbClearStoredPasswords = new JCheckBox(" -clearstoredpasswords");
		cbClearStoredPasswords.setBounds(8, 255, 200, 20);
		panelParameters.add(cbClearStoredPasswords);
		
		cbNoBadBlockCheck = new JCheckBox(" -nobadblockcheck");
		cbNoBadBlockCheck.setSelected(true);
		cbNoBadBlockCheck.setBounds(8, 279, 200, 20);
		panelParameters.add(cbNoBadBlockCheck);
		
		cbNoConfigCheck = new JCheckBox(" -noconfigcheck");
		cbNoConfigCheck.setBounds(8, 303, 200, 20);
		panelParameters.add(cbNoConfigCheck);
		
		cbNoCheck = new JCheckBox(" -nocheck");
		cbNoCheck.setBounds(8, 327, 200, 20);
		panelParameters.add(cbNoCheck);
		
		cbNoPrepStep = new JCheckBox(" -noprepstep");
		cbNoPrepStep.setBounds(8, 351, 200, 20);
		panelParameters.add(cbNoPrepStep);
		
		cbNoUpgradeStep = new JCheckBox(" -noupgradestep");
		cbNoUpgradeStep.setBounds(8, 375, 200, 20);
		panelParameters.add(cbNoUpgradeStep);
		
		cbNoPostStep = new JCheckBox(" -nopoststep");
		cbNoPostStep.setBounds(8, 399, 200, 20);
		panelParameters.add(cbNoPostStep);
		
		cbNoExitOnFailure = new JCheckBox(" -noexitonfailure");
		cbNoExitOnFailure.setBounds(8, 423, 200, 20);
		panelParameters.add(cbNoExitOnFailure);
		
		cbNoConfirm = new JCheckBox(" -noconfirm");
		cbNoConfirm.setBounds(8, 446, 200, 20);
		panelParameters.add(cbNoConfirm);
		
		cbZone1 = new JCheckBox(" -zone1");
		cbZone1.setBounds(8, 470, 200, 20);
		panelParameters.add(cbZone1);
		
		cbZone2 = new JCheckBox(" -zone2");
		cbZone2.setBounds(8, 494, 200, 20);
		panelParameters.add(cbZone2);
		
		cbApplication = new JCheckBox(" -application");
		cbApplication.setBounds(8, 518, 200, 20);
		panelParameters.add(cbApplication);
		
		comboFrom = new JComboBox<String>();
		comboFrom.setModel(new DefaultComboBoxModel<String>(new String[] {"test", "adfda", "werwer", "werwe"}));
		comboFrom.setBounds(137, 69, 130, 20);
		panelParameters.add(comboFrom);
	}
}
