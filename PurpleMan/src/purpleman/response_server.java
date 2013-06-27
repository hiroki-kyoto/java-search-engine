package purpleman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class response_server {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		SocketCommunicationServer scs = new SocketCommunicationServer();
		scs.StartUp();
		
	}
}


class SocketCommunicationServer{

	public void StartUp() throws InterruptedException{
		try{
			Socket client=null;
			
			ServerSocket serverSocket =new ServerSocket(10002);
			
			while(true){
				client = serverSocket.accept();
				(new serverThread(client)).start();
			}
			
		}catch(IOException e){
			try{
				TokenLog.log("creating response server error:" + e.getMessage());
			}catch(Exception es){}
		}
	}
}


/**************************************
 * set the Thread LIVE-TIME:T=unlimited
 * 
 * the Maximum Thread set is L=30000.
 * 
 * use the lock method to ensure we 
 * get the unique instance of the class
 * 
 * set the session end signal:[EXIT]
 * 
 * This is a server follow the HTTP/1.1
 * which means we just let only one talk.
 * 
 * @author SuperShame
 *
 */
class serverThread extends Thread{
	Socket client = null;
	long id = 0;
	serverThread(Socket sock) throws InterruptedException{
		client = sock;
		id = UserID.getInstance().getID();
	}
	
	public void run(){
		if(id<0){return;}
		
		String request = "";
		
		try {
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			
			PrintWriter pw=new PrintWriter(os);
			BufferedReader br =new BufferedReader(new InputStreamReader(is));
			
			request = br.readLine();
			//System.out.println("receive request:" + request);
			
			WebSearch ws = new WebSearch();
			try {
				String response = ws.Search(request);
				pw.println(response);
				pw.flush();
			} catch (Exception e) {
				try{TokenLog.log(e.getMessage());}
				catch(Exception ee){}
			}
			
			UserID.getInstance().popID();
			br.close();
			pw.close();
			client.close();
				
		} catch (IOException e) {
			try {
				TokenLog.log(e.getMessage());
			} catch (Exception ee) {}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

}

class UserID{
	
	private long Limit = 30000;
	private static long Total = 0;
	
	private static volatile UserID uid = null;
	public static UserID getInstance(){
		if(uid==null){
			synchronized (UserID.class){
				if(uid==null){
					uid = new UserID();
					return uid;
				}
			}
		}
		return uid;
	}
	
	UserID(){Total=0;}
	
	public long getID(){
		if(Total>Limit){return -1;}
		Total++;
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.SECOND)*1000 
				+ cal.get(Calendar.MILLISECOND);
	}
	
	public void popID(){
		Total--;
	}

}
