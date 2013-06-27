package purpleman.tester;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ClientTest {
	
	public static void main(String[] args) throws Exception{
		Socket sock = new Socket();
		String input = "�人����ѧ";
		String output = "";
		
		try {
			sock.connect(new InetSocketAddress("127.0.0.1", 10002));

			if(sock.isConnected()){
				DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
				DataInputStream dis = new DataInputStream(sock.getInputStream());
				
				dos.writeUTF(input);
				output = dis.readUTF();
				System.out.print(output);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
