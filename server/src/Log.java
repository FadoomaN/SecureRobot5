import javax.swing.*;
import java.awt.*;

public class Log {

    private JFrame logWindow;
    private JTextArea logs;
    private boolean autoIsOn;

    public Log() {
        logWindow = new JFrame("Log");
        logs = new JTextArea();
        setupWindow();
    }

    private void setupWindow() {
        logWindow.setBounds(0, 0, 1400, 350);

        logs.setEditable(false);
        logs.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        JScrollPane scroll = new JScrollPane(logs);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton autoScrollButton = new JButton("Autoscroll");
        autoScrollButton.setBounds(logWindow.getWidth() - 110, logWindow.getHeight() - 100, 100, 50);
        autoIsOn = true;

        autoScrollButton.addActionListener(e -> { autoIsOn = !autoIsOn; });

        logWindow.add(autoScrollButton);
        logWindow.add(scroll);
        logWindow.setVisible(true);
    }

    public void log(String text) {
        //logs.append(robotNode.getRobotId() + ": " + text + "\n");
        logs.append((text + "\n"));
        if (autoIsOn) {
            logs.setCaretPosition(logs.getDocument().getLength());
        }
    }
}

