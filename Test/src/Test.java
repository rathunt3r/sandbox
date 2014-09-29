import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Test {

	public static void main(String[] args) {
		String log = "/opt/mirror/loadingGroup01_1 is kept open by: tftpd (PID: 28461, UID: 0).\n" +
				"/opt/mirror/loadingGroup01_1 is kept open by: tftpd (PID: 28463, UID: 0).\n" +
				"/opt/mirror/loadingGroup01_1 is kept open by: zxc (PID: 99, UID: 0).\n" +
				"/opt/mirror/loadingGroup01_1 iD: 0).\n" +
				"/opt/mirror/adm/etc is kept open by: named (PID: 29487, UID: 0).\n";
		 
		check(log);
	}
	
	private static void check(String response){
		ArrayList<String> allowedProcesses = new ArrayList<String>();
    	allowedProcesses.add("axefsd");
    	allowedProcesses.add("dicosapplog");
    	allowedProcesses.add("dicossyslog");
    	allowedProcesses.add("named");
    	allowedProcesses.add("rpc.mountd");
    	allowedProcesses.add("sitedb");
    	allowedProcesses.add("slapd");
    	allowedProcesses.add("tftpd");
    	
    	Map<String, String> processes = new HashMap<String, String>();
    	
    	String[] lines = response.split("\n");
    	for (String line:lines){
    		if (line.contains("kept open by: ")){
    			String name = line.substring(line.indexOf("kept open by: ") + 14, line.indexOf(" (PID: "));
    			String PID = line.substring(line.indexOf("(PID: ") + 6, line.indexOf(", UID: "));
    			if (!allowedProcesses.contains(name)){
    				processes.put(PID, name);
    			}
    		}
    	}
    	
    	System.out.println(processes.toString());
    	
    	/*for (Object key : processes.keySet()){
			theLogger.info("Disallowed process ["+ key.toString() + " " + processes.get(key) +"] found, trying to terminate");
			String[] responses = sendSshCommands(new String[]{"kill -9 " + key.toString()}, sshConnection, false);
			if (responses == null){
	    		theLogger.warn("Something went wrong during process termination");
	    		return;
	    	}
    	}*/
	}

}
