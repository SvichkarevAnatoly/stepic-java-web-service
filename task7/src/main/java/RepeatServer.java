import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class RepeatServer implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public RepeatServer(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    @Override
    public void run() {
        final Logger logger = LogManager.getLogger(
                RepeatServer.class.getName()
                + ":thread-"
                + Thread.currentThread().getId());

        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("Bye.")) {
                    socket.close();
                    logger.info("Connection close");
                    break;
                } else {
                    logger.info(str);
                    out.println(str);
                }
            }
        } catch (IOException e) {
            System.err.println("IO Exception");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}
