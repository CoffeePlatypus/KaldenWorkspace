import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Socket sock = null;

        DataOutputStream out = null;
        DataInputStream in = null;

        String host;
        int port;
        String client;

        System.out.print("Enter hostname/IP: ");
        host = scan.nextLine();
        System.out.print("Enter port: ");
        port = scan.nextInt();
        scan.nextLine();
        System.out.print("Enter client id: ");
        client = scan.nextLine();

        try {
            sock = new Socket(host, port);
            out = new DataOutputStream(sock.getOutputStream());
            in = new DataInputStream(sock.getInputStream());

            // create CONNECT packet
            byte[] fixedHeader = new byte[2];
            fixedHeader[0] = 0x10; // CONNECT message type

            // CONNECT "remaining length" is in the document 
            // as a variable byte integer, so now it is truncated 
            // to one byte
            int fixedHeaderLen = 10 + client.length(); // length of variable header plus client id
            if (fixedHeaderLen < 255) {
                fixedHeader[1] = (byte)fixedHeaderLen;
            } else {
                System.err.println("CONNECT message too long: " + fixedHeaderLen);
                System.exit(1);
            }

            byte[] variable = new byte[10];
            variable[0] = 0x00;
            variable[1] = 0x04; // length of magic number
            variable[2] = 0x4d; // "M"
            variable[3] = 0x51; // "Q"
            variable[4] = 0x54; // "T"
            variable[5] = 0x54; // "T"

            variable[6] = 0x05; // vers 5
            variable[7] = 0x02; // connect flags (clean start only)
            variable[8] = 0x00; // no keep-alive
            variable[9] = 0x00;

            // send CONNECT headers
            System.out.println(Arrays.toString(fixedHeader));
            System.out.println(Arrays.toString(variable));
            out.write(fixedHeader, 0 /* start index */, fixedHeader.length);
            out.write(variable, 0, variable.length);
            // send CONNECT payload, length first
            out.write(client.length()); 
            out.writeBytes(client);

            // normally, you would wait for a CONNACK here

            // and possibly SUBSCRIBE and/or PUBLISH as needed

            // and do a DISCONNECT before closing these streams...

            // but, that's your problem now

            in.close();
            out.close();
            sock.close();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + host);
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O exception to " + host);
            System.err.println(e.getMessage());
            System.exit(1);
        } 
    }
}
