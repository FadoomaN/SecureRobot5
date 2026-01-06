import javax.swing.*;
import java.awt.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class TestServerWindow {

    private JFrame mainWindow;
    private JPanel mainPanel;
    private JTextField comField;
    private JButton butt;

    private final int prt;
    private final String ip;

    public TestServerWindow(String ip, int prt) {
        this.ip = ip;
        this.prt = prt;
        mainWindow = new JFrame("UDP Sender");
        setupTestServerWindow();
    }

    private void setupTestServerWindow() {
        mainWindow.setLayout(null);
        mainWindow.setBounds(900, 600, 400, 150);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);

        setupTestServerPanel();

        mainWindow.setVisible(true);
        mainWindow.repaint();
        mainWindow.revalidate();
    }

    private void setupTestServerPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);
        mainPanel.setBounds(5, 5, 375, 105);
        mainPanel.setBackground(Color.black);
        mainWindow.add(mainPanel);

        comField = new JTextField();
        comField.setVisible(true);
        comField.setBounds(10, 30, 270, 40);
        comField.addActionListener(e -> sendToESP()); // Enter i fältet
        mainPanel.add(comField);

        butt = new JButton("Send");
        butt.setVisible(true);
        butt.setBounds(300, 30, 60, 40);
        butt.addActionListener(e -> sendToESP());
        mainPanel.add(butt);

        mainPanel.repaint();
        mainPanel.revalidate();
    }

    private void sendToESP() {
        String msg = comField.getText().trim();

        if (msg.isEmpty()) {
            System.out.println("[UDP] Tomt meddelande – skickar inget.");
            return;
        }


        String targetIp = (ip == null || ip.isBlank()) ? "255.255.255.255" : ip;

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);

            byte[] data = (msg + "\n").getBytes(StandardCharsets.UTF_8);
            InetAddress target = InetAddress.getByName(targetIp);

            DatagramPacket packet = new DatagramPacket(data, data.length, target, prt);
            socket.send(packet);

            System.out.println("[UDP] Sent to " + targetIp + ":" + prt + " -> " + msg);

            comField.setText("");
            comField.requestFocus();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new TestServerWindow("0.0.0.0", 5000);
    }
}
