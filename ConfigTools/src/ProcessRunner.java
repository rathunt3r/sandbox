import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


public class ProcessRunner implements Runnable{
	
	private RSyntaxTextArea terminal;
	private Process process;
	private Runtime runtime;
	private String command;
	private Thread thread;
	private boolean suspended;
	
	public ProcessRunner(RSyntaxTextArea terminal, String command){		
		
		this.suspended = false;
		this.terminal = terminal;
		this.command = command;
		
		runtime = Runtime.getRuntime();
		
		thread = new Thread(this);
		thread.start();
		
	}

	@Override
	public void  run() {				
		try {
			process = runtime.exec(command);		

			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
					
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				terminal.append(line + "\n");
				synchronized(this){
					while(suspended){
						wait();
					}
				}
				if (i == 5){
					new Dialog(this);
				}
				i++;
			}
		
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void suspend(){		
		suspended = true;		
	}
	
	synchronized void resume(){
		suspended = false;
		notify();
	}
	
	public void destroy(){
		process.destroy();
	}
	
	@SuppressWarnings("unused")
	private void sendToProcess(String input) throws IOException{
		OutputStream os = process.getOutputStream();
		os.write(input.getBytes());
        os.close();
	}
}
