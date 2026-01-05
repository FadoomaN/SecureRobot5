import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;


public class TestServerWindow{

    private JFrame mainWindow;
    private JPanel mainPanel;
    private JTextField comField;
    private JButton butt;

    private int prt;
    private String ip;

    TestServerWindow(String ip, int prt)
    {
        this.ip = ip;
        this.prt = prt;
        mainWindow = new JFrame();
        setupTestServerWindow();
    }



    private void setupTestServerWindow()
    {
        mainWindow.setLayout(null);
        mainWindow.setBounds(900,600, 400,150);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        setupTestServerPanel();


        mainWindow.repaint();
        mainWindow.revalidate();

    }

    private void setupTestServerPanel()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);
        mainPanel.setBounds(5,5, 375, 105);
        mainPanel.setBackground(Color.black);
        mainWindow.add(mainPanel);

        comField = new JTextField();
        comField.setVisible(true);
        comField.setBounds(10, 30, 270, 40);
        comField.addActionListener(e -> sendToESP());
        mainPanel.add(comField);

        butt = new JButton("Test");
        butt.setVisible(true);
        butt.setBounds(300, 30, 60, 40);
        butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendToESP();
            }
        });
        mainPanel.add(butt);



        mainPanel.repaint();
        mainPanel.revalidate();

    }


    private void sendToESP()
    {

        String msgFromTF = comField.getText();

        if (msgFromTF.isEmpty())
        {
            System.out.println("Din mam, finns ingen kommando");
        }

        else
        {
            try (Socket socket = new Socket(ip, prt);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true))
            {

                out.println(msgFromTF);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }


}
