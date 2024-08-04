import com.sun.jdi.connect.Connector;
/**
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
    public void run() throws IOException{
        try(ServerSocket serverSocket = new ServerSocket(2020)) {
            while(true){
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new Server(socket));
                thread.start();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerConnection connection = new ServerConnection();
        connection.run();
    }
}
**/;