import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Worker extends Thread {

    private static int idCounter = 0;

    private Server server;
    private Socket socket;
    private int workerId;

    private boolean receivedEven = false;
    private boolean receivedOdd = false;


    public Worker(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;

        this.workerId = ++idCounter;
    }

    @Override
    public void run() {
        System.out.println("[Worker " + workerId + "] - New worker created");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String receivedData = "";

            while(!receivedData.equalsIgnoreCase("<end>")) {
                receivedData = reader.readLine();

                writer.write("ok");
                writer.newLine();
                writer.flush();

                if(receivedData.equalsIgnoreCase("<end>"))
                    continue;

                try {
                    int number = Integer.parseInt(receivedData);
                    String result;

                    if(number % 2 == 0) {
                        result = receivedEven ? "even again" : "even";
                        receivedEven = true;
                    } else {
                        result = receivedOdd ? "odd again" : "odd";
                        receivedOdd = true;
                    }

                    System.out.printf("[Worker %d] - Received : %-4s %s%n" , workerId, receivedData, result);

                    
                } catch (NumberFormatException e) {
                    System.out.printf("[Worker %d] - Received : %s - unable to parse the data%n" , workerId, receivedData);
                }   
            }

            socket.close();
        } catch (Exception e) {
            System.out.println("[Worker " + workerId + "] - Something went wrong!");
        }
        
        System.out.println("[Worker " + workerId + "] - Job's done, bye!");
        server.removeWorker(this);
    }
}
