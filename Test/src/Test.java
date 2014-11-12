import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Test {

	public static void main(String[] args) {
		String log = "/opt/mirror/loadingGroup01_1 is kept open by: tftpd (PID: 28461, UID: 0).\n" +
				"/opt/mirror/loadingGroup01_1 is kept open by: tftpd (PID: 28463, UID: 0).\n" +
				"/opt/mirror/loadingGroup01_1 is kept open by: zxc (PID: 99, UID: 0).\n" +
				"/opt/mirror/loadingGroup01_1 iD: 0).\n" +
				"/opt/mirror/adm/etc is kept open by: named (PID: 29487, UID: 0).\n";
		//check(log);
		
		String log2 = "Remove /etc/save_ioconfig.conf from the /etc/save_ioconfig.conf config file, it is not allowed to be saved \n" +
				"Remove /etc/securetty from the /etc/save_ioconfig.conf config file, it is not allowed to be saved \n" +
				"Remove /etc/rsyslog.conf from the /etc/save_ioconfig.conf config file, it is not allowed to be saved \n" +
				"Remove /etc/sysctl.conf from the /etc/save_ioconfig.conf config file, it is not allowed to be saved \n";
		//check2(log2);
		
		//dateDiff();
		
		//isEmptyOrNullPrintout();
		
		listCustomSorting();
	}
	
	private static void listCustomSorting() {
			try {
				List<Date> dateList = new ArrayList<Date>();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
				dateList.add(format.parse("2014.09.11. 10:10"));
				dateList.add(format.parse("2013.09.33. 10:10"));
				dateList.add(format.parse("2012.09.11. 10:10"));
				dateList.add(format.parse("2017.09.11. 10:10"));
				dateList.add(format.parse("2009.09.11. 10:10"));
				
				System.out.println(dateList);
				
				Collections.sort(dateList, new Comparator<Date>(){
				    @Override
				    public int compare(Date arg0, Date arg1) {
				        return arg0.compareTo(arg1);
				    }
				});
				
				System.out.println(dateList);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private static void isEmptyOrNullPrintout(){
		Date date = new Date();
		Date date2 = new Date();
		
		System.out.println((int)( (date.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)));
		
		String string1 = null;
		String string2 = "string2";
		
		List<String> list1 = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();
		
		list2.add(3);
		
		System.out.println("Alarm found which meet the given condition(s): " + "\n" +
				(date == null ? "emptyOrNull" : date.toString()) + "\n" +
				(isEmptyOrNull(string1) ? "emptyOrNull" : string1) + "\n" +
				(isEmptyOrNull(string2) ? "emptyOrNull" : string2) + "\n" +
				(isEmptyOrNull(list1) ? "emptyOrNull" : list1) + "\n" +
				(isEmptyOrNull(list2) ? "emptyOrNull" : list2)
		);
	}
	
	private static boolean isEmptyOrNull(List<?> list){
		if (list == null || list.size() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isEmptyOrNull(String string){
		if (string == null || string.length() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	//2014.10.29. date difference between two dates
	private static void dateDiff(){
		
		String dateStart = "2014-10-29 09:29:58";
		String dateStop = "2014-10-31 10:31:48";
	 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
		Date d2 = null;
		Date d1 = null;
	 
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
	 
			//DateTime dt1 = new DateTime(d1);
			//DateTime dt2 = new DateTime(d2);
			
			
			System.out.print((d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000));
			//System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
			//System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
			//System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");
	 
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
	private static void check2(String response){
		String[] lines = response.split("\n");
		for (String line:lines){
			//System.out.println((line.split(" "))[1].replace("/", "\\/"));
			//System.out.println("#" + (line.split(" "))[1].replace("/", "\\/"));
			String oldString = (line.split(" "))[1].replace("/", "\\/");
        	String newString = "# " + oldString;
        	String configFile = (line.split(" "))[4];
        	String command = "sed -i 's/" + oldString + "/" + newString + "/g' " + configFile;
        	System.out.println(command);
    	}
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
