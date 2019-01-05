import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Broker {
	private ServerSocket brokerSocket;
	private Socket clientSocket;
	private PrintWriter resWriter;
	private BufferedReader mqttReader;
	private int portNumber;
	private HashMap<Integer, ArrayList<Integer>> clientSubs = new HashMap<Integer, ArrayList<Integer>>(); // for keeping track of users and later their subscriptions
	
	public Broker() {
		brokerSocket = null;
		clientSocket = null;
		resWriter = null;
		mqttReader = null;
		portNumber = 1883;
	}
	
	public void connect() throws IOException {
		brokerSocket = new ServerSocket(portNumber);
		clientSocket = brokerSocket.accept();
		resWriter = new PrintWriter(clientSocket.getOutputStream(), true);
		mqttReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public void close() throws IOException {
		if (mqttReader != null)
			mqttReader.close();
		if (resWriter != null)
			resWriter.close();
		if (clientSocket != null)
			clientSocket.close();
		if (brokerSocket != null)
			brokerSocket.close();
	}
	
	public void readMQTT() throws IOException {
		int msgType = mqttReader.read();
		System.out.println("msg type: "+msgType);
		int fixedLen = mqttReader.read();
		System.out.println(fixedLen);
		int msbLen = mqttReader.read();
		System.out.println("msb Len: "+msbLen);
		int lsbLen = mqttReader.read();
		System.out.println("lsb Len: "+lsbLen);
		System.out.println((char)mqttReader.read());
		System.out.println((char)mqttReader.read());
		System.out.println((char)mqttReader.read());
		System.out.println((char)mqttReader.read());
		int ver = mqttReader.read();
		System.out.println("mqtt ver: "+ver);
		int cflags = mqttReader.read();
		System.out.println(cflags);
		int kliveMsb = mqttReader.read();
		int kliveLsb = mqttReader.read();
		System.out.println("Live? "+ kliveMsb+" "+kliveLsb);
		switch(msgType)  {
		case 16 : // Clean start connect
			mqttConnect(); break;
		case 17 : // connack
			mqttConnack();
			break;
		case 128 :
			System.out.println("Subscribe");
			break;
		case 18 :
			System.out.println("publish");
			break;
		case 19 :
			System.out.println("pub back");
		case 129 :
			System.out.println("sub bakk");
		default :
			System.out.println("Message type not supported yet :]");
		}
			
	}
	
	public void mqttConnack() {
		
	}
	
	public void mqttConnect() throws IOException {
		int clen = mqttReader.read();
		int cid = mqttReader.read() - 48;
		for(int i = 1; i < clen; i++) {
			cid = (cid * 10) + (mqttReader.read() - 48);
		}
		System.out.println(cid);
		clientSubs.put(cid, new ArrayList<Integer>());
	}
	
	
	public static void main(String [] args) throws IOException{
		try {
			Broker b = new Broker();
			b.connect();
			System.out.println("Connected!");
			b.readMQTT();
		}catch (IOException e) {
			System.out.println("IO Exception");
		}
	}
}
