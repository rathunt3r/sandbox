import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Terminal extends JDialog {

	private static final long serialVersionUID = 1L;
	private RSyntaxTextArea terminal;
	private ProcessRunner processRunner;
	private String command;
	
	public Terminal(String command) {
		
		this.command = command;
		
		setTitle("Terminal");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		setBounds(100, 100, 700, 300);
		
		getContentPane().setLayout(new BorderLayout());
		{
			terminal = new RSyntaxTextArea();
			terminal.setEditable(false);
			terminal.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
			terminal.setCodeFoldingEnabled(true);
			terminal.setAntiAliasingEnabled(true);
			JScrollPane terminalPane = new JScrollPane();
			getContentPane().add(terminalPane);	
			terminalPane.setViewportView(terminal);		
			
			processRunner = new ProcessRunner(terminal, command);
			//(new Thread(new ProcessRunner(terminal, "ping -c 10 index.hu"))).start();
		}		
	}
}
