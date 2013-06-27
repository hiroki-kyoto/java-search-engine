package purpleman;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class TokenLog {
	public static void log(String s)throws Exception{
		Calendar cal = Calendar.getInstance();
	    int yea = cal.get(Calendar.YEAR);
	    int mon = cal.get(Calendar.MONTH)+1;
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    int hou = cal.get(Calendar.HOUR);
	    int min = cal.get(Calendar.MINUTE);
	    int sec = cal.get(Calendar.SECOND);
	    String logTime = yea + "_" + mon + "-" + day + "-" + hou + "-" + min + "" + sec; 
		File logfile = new File("log/" + logTime + ".txt");
		FileWriter fw = new FileWriter(logfile);
		fw.write(logTime + "发生以下事件：\n" + s + "\n");
		fw.close();
	}
}
