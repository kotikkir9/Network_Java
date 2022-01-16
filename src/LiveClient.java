import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class LiveClient {
    
    private int port;
    private Socket socket;
    
    private String DISCONNECT = "disconnect";

    public LiveClient(int port) {
        this.port = port;

        run();
    }

    public void run() {
        System.out.println("<--- Connecting to the server... --->");

        try {
            socket = new Socket("127.0.0.1", port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("<--- Online --->");

            Scanner scanner = new Scanner(System.in);
            String message = "";

            while(!message.equalsIgnoreCase(DISCONNECT)) {
                System.out.print("> ");
                message = scanner.nextLine();

                if(message.equalsIgnoreCase(DISCONNECT))
                    continue;

                writer.write(message + "\n");
                writer.flush();

                String response = reader.readLine();
                if(response.equalsIgnoreCase("ok"))
                    System.out.println("<--- Server received the message --->");
            }

            writer.write("<end>\n");
            writer.flush();

            scanner.close();
            socket.close();
            writer.close();
            
            System.out.println("<--- Disconnected --->");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }   
}
