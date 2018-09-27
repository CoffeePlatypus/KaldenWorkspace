import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException {
		Socket sock = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		String hostName = "127.0.0.1";//args[0]; 
		int portNumber = 54321;
		try {
			sock = new Socket(hostName, portNumber);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
		
			String userInput = "Want all the data";
			out.println(userInput);
			System.out.println("K thx bye: " + in.readLine());
		} catch (UnknownHostException e) {System.err.println("Unknown host " + hostName);
			System.exit(1);
		} catch (IOException e) {System.err.println("I/O exception to " + hostName);
			System.exit(1);
		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
			if (sock != null) sock.close();
		}
		
	}
}
