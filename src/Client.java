import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client extends Thread {

    private String clientName;
    private String[] messageArray;
    
    private int port;
    private Socket socket;
    BufferedWriter writer;

    public Client(String name, String message, int port) {
        this.clientName = name;
        this.port = port;

        this.messageArray = message.split("\s+");
    }

    @Override
    public void run() {
        System.out.printf("[%s] - Online%n", clientName);

        try {
            socket = new Socket("127.0.0.1", port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 

            for(var number : messageArray) {
                writer.write(number);  
                writer.newLine();
                writer.flush();     
                         
                Thread.sleep(2000);
            }

            writer.write("<end>");
            writer.newLine();
            writer.flush();

            socket.close();
            writer.close();

        } catch (IOException | InterruptedException e) {
            System.out.printf("[%s] - Something went wrong!%n", clientName);
        }

    }  
}
