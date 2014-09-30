import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;


public class Terminal extends JDialog {

	private static final long serialVersionUID = 1L;
	private RSyntaxTextArea terminal;
	
	public Terminal() {
		
		setTitle("Terminal");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			terminal = new RSyntaxTextArea();
			terminal.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
			terminal.setCodeFoldingEnabled(true);
			terminal.setAntiAliasingEnabled(true);
			JScrollPane terminalPane = new JScrollPane();
			getContentPane().add(terminalPane);	
			terminalPane.setViewportView(terminal);		
			
			(new Thread(new CommandExecutor(terminal))).start();
		}
	}

}
