import javax.swing.*; // bara om du behöver, annars kan tas bort
import java.io.*;
import java.net.*;

public class Server {
    private int port;
    private Log logger;
    private Visual visualizer;

    public Server(int port) {
        this.port = port;
    }

    public void startServer() {

        logger = new Log();
        visualizer = new Visual();

        System.out.println("Starting server...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!serverSocket.isClosed()) {
                System.out.println("Waiting for client to connect");
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client, new RobotNode());
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread {
        private Socket client;
        private RobotNode robotNode;

        public ClientHandler(Socket client, RobotNode robotNode) {
            this.client = client;
            this.robotNode = robotNode;

            // Lägg till roboten i visualizern så den ritas
            visualizer.addRobotToMap(robotNode);
        }

        @Override
        public void run() {
            System.out.println("Client has connected");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

                String message;

                // Läs rad för rad tills klienten stänger kopplingen
                while ((message = br.readLine()) != null) {

                    System.out.println("Message received: " + message);
                    logger.log(message);

                    try {
                        double x = visualizer.getXfromMessage(message);
                        double y = visualizer.getYfromMessage(message);
                        // double angle = visualizer.getAngleFromMessage(message); // när du börjar skicka vinkel

                        robotNode.setPosition(x, y);
                        // robotNode.setAngle(angle);

                    } catch (Exception e) {
                        System.out.println("Kunde inte parsa meddelande: " + message);
                        e.printStackTrace();
                    }

                    // GUI:t repainter ändå via Timer, men detta skadar inte:
                    visualizer.mapPanel.repaint();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Client has disconnected");
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.startServer();
    }
}
