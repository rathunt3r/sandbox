import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


public class CommandExecutor implements Runnable{
	
	private RSyntaxTextArea terminal;
	
	public CommandExecutor(RSyntaxTextArea terminal){
		this.terminal = terminal;
	}

	@Override
	public void run() {
		
		try {
		
			Runtime runtime = Runtime.getRuntime();
			Process process;

			process = runtime.exec("ping -c 10 index.hu");		
			
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			
			while ((line = br.readLine()) != null) {
				terminal.append(line + "\n");
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
