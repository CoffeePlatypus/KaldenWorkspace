import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
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
		if (httpRin != null)
			httpRin.close();
		if (res != null)
			res.close();
		if (clientSocket != null)
			clientSocket.close();
		if (serverSocket != null)
			serverSocket.close();
	}

	public void connect() throws IOException {
		serverSocket = new ServerSocket(portNumber);
		clientSocket = serverSocket.accept();
		res = new PrintWriter(clientSocket.getOutputStream(), true);
		httpRin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String readHttpReq() throws IOException, NoSuchMethodException {
		System.out.println();
		// Read http request
		String inputLine = httpRin.readLine(); // Read http Request
		System.out.println(inputLine);
		String[] firstLine = inputLine.split(" "); // 1 - method; 2 - resource; 3 - http version;

		if ((!firstLine[0].equals("GET") && !firstLine[0].equals("POST")) || !firstLine[2].equals("HTTP/1.1")) {
			System.out.println("invalid method or http version");
			throw new NoSuchMethodException();
		}

		inputLine = httpRin.readLine();
		int lenght = 0;
		while (inputLine != null && !inputLine.equals("")) {
			// headers - validate host - content type
			System.out.println(inputLine);
			String [] lineSplit = inputLine.split(": ");
			if(lineSplit[0].equals("Host") && ( !lineSplit[1].equals("localhost") && !lineSplit[1].equals("127.0.0.1"))) {
				System.out.println("INVALID host " + lineSplit[1]);
				throw new IOException();
			}else if(lineSplit[0].equals("Accept") && !lineSplit[1].contains("text") ) {
				System.out.println("doesn't like " + lineSplit[1].contains("text"));
				throw new IOException();
			}else if (lineSplit[0].equals("Content-Length")) {
				lenght = Integer.parseInt(lineSplit[1]);
			}
			
			inputLine = httpRin.readLine();
		}
		if (inputLine != null && firstLine[0].equals("POST")) {
			String word = "";
			for(int i = 0; i<lenght; i++) {
				char t = (char)httpRin.read();
				if(t == '&') {
					System.out.println(word);
					log(word, "SUBMITTED");
					word = "";
				}else {
					word += t;
				}
				
			}
			System.out.println(word);
			log(word, "SUBMITTED");
		}
		System.out.println("finished reading request");
		
		if(firstLine[1].contains("?")) {
			String[] res = firstLine[1].split("\\?");
			log(res[1], "SUBMITTED");
			return res[0];
		}
		return firstLine[1];
	}

	public void sendRes(String resource) throws FileNotFoundException, IOException {
		resource = resource.endsWith("/") ? resource.substring(1, resource.length())+"index.html" : resource.substring(1, resource.length());
		if(resource.equals("src/Marvin.java")) throw new FileNotFoundException(); // still added for safety
		System.out.println(resource);
		// resource = String.join("\\", resource.split("/")); // swaps slashes // looks like not needed
//		System.out.println(resource);
		BufferedReader fin = new BufferedReader(new FileReader(resource));
		String line = fin.readLine();
		
		res.println("HTTP/1.1 200 OK");
		res.println("Connection: close");
		res.println("Content-Type: text/html; charset=utf-8");
		res.println("Cache-Control: no-cache");
		res.println("Server: Marvin");
		res.println();

		while (line != null) {
			res.println(line);
			line = fin.readLine();
		}
		fin.close();
		System.out.println("response sent");
	}
	
	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime  now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public void log(String message, String type) throws IOException {
		PrintWriter logger = new PrintWriter(new FileWriter("http-log.txt",true));
		logger.println(type.toUpperCase()+" "+getDate()+" "+message);
		logger.close();
	}
	
	public void sendError(String type) throws IOException {
		res.println("HTTP/1.1 404 Not Found");
		res.println("Error-Type: "+type);
		res.println("Connection: close");
		res.println("Content-Type: text/html; charset=utf-8");
		res.println("Cache-Control: no-cache");
		res.println("Server: Marvin");
		res.println();
		
		BufferedReader fin = new BufferedReader(new FileReader("error404.html"));
		String line = fin.readLine();
		while (line != null) {
			res.println(line);
			line = fin.readLine();
		}
		fin.close();
		System.out.println("response sent");

	}

	public static void main(String[] args) throws IOException {
		while (true) {
			Marvin server = new Marvin(80);
			try {
				server.connect();
				String resource = server.readHttpReq();
				server.sendRes(resource);

			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				server.log("File Not Found", "ERROR");
				// TODO send file not found message to client
				server.sendError("File-Not-Found");
			} catch (IOException e) {
				System.out.println("I/O Exception on port " + server.portNumber);
				System.out.println(e.getMessage());
				server.log(e.getMessage(), "ERROR");
				server.sendError("I/O-Exception");
			} catch (NoSuchMethodException e) {
				// TODO send error response to client
				System.out.println("Bad message");
				server.log("Bad method or http version", "ERROR");
				server.sendError("Bad-Method/HTTP-Version");
			} finally {
				server.close();
			}
		}

	}
}
