import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int portNumber = 54321;
		try {
			serverSocket = new ServerSocket(portNumber);
			clientSocket = serverSocket.accept();     
			out = new PrintWriter(clientSocket.getOutputStream(), true);                   
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine = in.readLine();
			System.out.println("Request was: " + inputLine);out.println("have some data");
		} catch (IOException e) {System.out.println("I/O Exception on port " + portNumber);
			System.out.println(e.getMessage());
		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
			if (clientSocket != null) clientSocket.close();
			if (serverSocket != null) serverSocket.close();
		}

	}

}
