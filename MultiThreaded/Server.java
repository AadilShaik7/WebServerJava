package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientSocket.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws IOException {
        int port = 8010;
        Server consumer = new Server();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening to port");
            while(true) {
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->consumer.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
