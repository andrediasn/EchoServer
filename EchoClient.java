import java.net.Socket;
import java.lang.Thread;
import java.io.*;

public class EchoClient extends Thread {
    Socket connection;
    public EchoClient(Socket socket) {
        this.connection = socket;
    }
    public static void main(String[] args) {
        try {   
            String host ="localhost";
            int port = 4444;
            Socket socket = new Socket(host, port);
            System.out.println("\nConnected to " + host + " in port " + port + "\n");

            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            Thread thread = new EchoClient(socket);
            thread.start();

            String received;
            while (true) {
                received = in.readLine();
                out.println(received);
            }
        } 
        catch (IOException erro) { System.out.println("Error: " + erro); }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

            String received;
            while (true) {
                received = in.readLine();
                if (received == null) {
                    System.out.println("\nConnection terminated");
                    System.exit(0);
                }
                System.out.println(received);
            }
        } 
        catch (IOException erro) { System.out.println("Error: " + erro); }
    }
}