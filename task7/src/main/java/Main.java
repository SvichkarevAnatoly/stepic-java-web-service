import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final int PORT = 5050;

    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        try (ServerSocket s = new ServerSocket(PORT)) {
            while (true) {
                final Socket socket = s.accept();
                final Thread repeatServerThread = new Thread(new RepeatServer(socket));
                repeatServerThread.start();
            }
        }
    }
}
