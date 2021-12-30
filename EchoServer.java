import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Thread;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("\n Server started on port: " + port);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("\n Client connected");
            EchoHandler handler = new EchoHandler(clientSocket);
            handler.start();
        }
    }
}

class EchoHandler extends Thread {
    Socket clientSocket;

    EchoHandler (Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            while (true) {
                String received = in.readLine();
                if (received.startsWith("echo")) {
                    String msg[] = received.split(" ", 2);
                    out.println("EchoServer: " + msg[1] + "\n");
                }
                else if (received.trim().equals("quit")) {
                    out.close();
                    in.close();
                    System.out.println("\n Client disconnected");
                    break;
                }
            }
        }
        catch(Exception erro) { System.out.println("\n Error: " + erro + "client disconnected"); }
        finally {
            try { clientSocket.close(); }
            catch (Exception e ) { ; }
        }
    }
}