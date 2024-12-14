import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer {
        private static final int PORT = 5000;
        private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Server is running on port " + PORT);

            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            while(true){
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(
                        new ClientHandler(clientSocket)
                );
            }

        }catch(IOException e){
            System.err.println("Server Error :" + e.getMessage());
        }
    }
}
