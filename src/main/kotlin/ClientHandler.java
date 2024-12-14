import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;


    }

    @Override
    public void run() {
        try (InputStream input = clientSocket.getInputStream()) {
            DataInputStream dataInputStream = new DataInputStream(input);
            String fileName = dataInputStream.readUTF(); //Get file Name
            long fileSize = dataInputStream.readLong();// get file size
            System.out.println("Received File :" + fileName + "(" + fileSize + ")" + "bytes");
            File receivedFile = new File(fileName);
            try (
                    FileOutputStream fileOutput = new FileOutputStream(receivedFile);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutput);

            ){
              byte[] buffer = new byte[4096];
              long totalBytesRead = 0;
              int byteRead = 0;
              
              while((byteRead = input.read(buffer, 0, byteRead)) != -1){
                  bufferedOutputStream.write(buffer, 0, byteRead);
                  totalBytesRead += byteRead;
                  int progress = (int) ((totalBytesRead * 100)/fileSize);
                  System.out.println("\rProgress :" + progress +"%");
              }
                System.out.println("\n File received:" + receivedFile.getAbsolutePath());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
