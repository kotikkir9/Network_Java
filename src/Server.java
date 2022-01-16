import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;

public class Server extends Thread {
    
    private LinkedList<Worker> workers;
    private int port;
    private boolean stopServer;
    private ServerSocket server;
    
    public Server(int port) {
        this.port = port;
        this.stopServer = false;

        this.workers = new LinkedList<>();
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port, 10);

            server.setSoTimeout(1000);
            System.out.println("[Server] - Online");

            while(!stopServer) {
                try {
                    Socket socket = server.accept();

                    Worker worker = new Worker(this, socket);
                    workers.add(worker);
                    worker.start();

                } catch (SocketTimeoutException ex) {
                    
                }
            }
        } catch(Exception e) {
            System.out.println("Something went wrong!");
        }

        System.out.println("[Server] - Offline");
    }

    public boolean shutDown() {
        if(!workers.isEmpty()) {
            System.out.println("[Server] - Unable to shutdown the server, one or more process(es) are still running!");
            return stopServer;
        } else {
            System.out.println("[Server] - Shutting down...");
            stopServer = true;
            // try {
            //     server.close();
            // } catch (Exception e) {
                
            // }

            return stopServer;
        }
    }

    public void removeWorker(Worker worker) {
        workers.remove(worker);
    }
}
