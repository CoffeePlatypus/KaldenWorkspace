import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Marvin {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter res;
	private BufferedReader httpRin;
	private int portNumber;
	
	public Marvin(int pn) {
		serverSocket = null;
		clientSocket = null;
		res = null;
		httpRin = null;
		portNumber = pn;
	}
	
	public void close() throws IOException {
		if (httpRin != null) httpRin.close();
		if (res != null) res.close();
		if (clientSocket != null) clientSocket.close();
		if (serverSocket != null) serverSocket.close();
	}
	
	public void connect() throws IOException {
		serverSocket = new ServerSocket(portNumber);
		clientSocket = serverSocket.accept();     
		res = new PrintWriter(clientSocket.getOutputStream(), true);                   
		httpRin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public void readHttpReq() throws IOException {
		// Read http request
		String inputLine = httpRin.readLine(); // Read http  Request
		String [] firstLine = inputLine.split(" "); // 1 - method; 2 - resource; 3 - http version; 
					
		inputLine = httpRin.readLine();
		while(inputLine != "" && inputLine != null) {
			//headers - validate host 
			System.out.println(inputLine);
			inputLine = httpRin.readLine();
		}
		if(inputLine != null) {
			inputLine = httpRin.readLine();
			while(inputLine != null) {
			// read body
			inputLine = httpRin.readLine();
			}
		}		
	}
	
	public void sendRes() {
		res.println("have some data"); // Sent html or whatever was requested from client
	}
	
	public static void main(String[] args) throws IOException {
		Marvin server = new Marvin(80);
		try {
			server.connect();
			server.readHttpReq();
			server.sendRes();
			
		} catch (IOException e) {System.out.println("I/O Exception on port " + server.portNumber);
			System.out.println(e.getMessage());
			// log error
			
		} finally {
			server.close();
		}

	}
}
