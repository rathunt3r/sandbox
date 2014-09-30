import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import java.awt.GridLayout;


public class TerminalWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RSyntaxTextArea terminal;

	public TerminalWindow(){
		
		try {
			
		
			setAlwaysOnTop(true);			
			setVisible(true);			
			terminal = new RSyntaxTextArea();
			terminal.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
			terminal.setCodeFoldingEnabled(true);
			terminal.setAntiAliasingEnabled(true);		
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(new GridLayout(1, 0, 0, 0));			
			JScrollPane terminalPane = new JScrollPane();
			contentPane.add(terminalPane);
			terminalPane.setViewportView(terminal);
			
			(new Thread(new CommandExecutor(terminal))).start();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
