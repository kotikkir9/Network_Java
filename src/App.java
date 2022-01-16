import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        String message1 = "82 19 43 2 102 hello 1";
        String message2 = "34 2 6 88 46 yo 3 99";
        
        Server server = new Server(5000);
        Client messi = new Client("Messi", message1, 5000);
        Client ronaldo = new Client("Ronaldo", message2, 5000);

        server.start();
        // Thread.sleep(2000);
        // messi.start();
        // Thread.sleep(2000);
        // ronaldo.start();

        Scanner scanner = new Scanner(System.in);
        
        while(true) {
            scanner.nextLine();
            if(server.shutDown())
                break;
        }   
        scanner.close(); 

        // new LiveClient(5000);
    }
}
