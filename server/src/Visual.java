import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Visual {
    private JFrame visualWindow;
    public MapPanel mapPanel;
    private ImageIcon imageIcon;
    private Image bgImage;
    private final int width = 750;
    private final int height = 750;
    private ArrayList<RobotNode> robotNodes;


    public Visual() {
        visualWindow = new JFrame("Visualization");
        mapPanel = new MapPanel();
        robotNodes = new ArrayList<>();
        setupWindow();
    }

    private void setupWindow() {
        visualWindow.add(mapPanel);
        visualWindow.setBounds(0, 300, width + 40, height + 60);
        visualWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visualWindow.pack();
        visualWindow.setVisible(true);
    }

    public void addRobotToMap(RobotNode robotNode) {
        if (!robotNodes.contains(robotNode)) {
            robotNodes.add(robotNode);
        }
    }

    public ArrayList<RobotNode> getRobotNodes() {
        return robotNodes;
    }



    private String getField(String message, int n) {
        String startTag = ":" + n + ":";

        int startTagIndex = message.indexOf(startTag);
        if (startTagIndex == -1) {
            throw new IllegalArgumentException("Hittar inte " + startTag + " i meddelandet: " + message);
        }

        int start = startTagIndex + startTag.length();
        int end = message.indexOf(":", start);

        if (end == -1) {
            end = message.length();
        }

        return message.substring(start, end);
    }

    public double getXfromMessage(String message) {
        String xCord = getField(message, 7);
        System.out.println("xCord: " + xCord);
        return Double.parseDouble(xCord);
    }

    public double getYfromMessage(String message) {
        String yCord = getField(message, 8);
        System.out.println("yCord: " + yCord);
        return Double.parseDouble(yCord);
    }

    public double getAngleFromMessage(String message) {
        String angleStr = getField(message, 9);
        if (angleStr.isEmpty()) {
            return 0.0;
        }
        System.out.println("angle: " + angleStr);
        return Double.parseDouble(angleStr);
    }

    class MapPanel extends JPanel {
        public MapPanel() {
            this.setPreferredSize(new Dimension(width, height));

            imageIcon = new ImageIcon("pics/louvren.png");
            bgImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

            Timer timer = new Timer(10, e -> repaint());
            timer.start();
        }

        public void updateRobots(double x, double y) {
            for (RobotNode robotNode : robotNodes) {
                robotNode.setPosition(x, y);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g.create();

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (bgImage != null) {
                g2D.drawImage(bgImage, 0, 0, this);
            }

            g2D.setColor(Color.RED);

            for (RobotNode r : robotNodes) {

                double sx = r.getXpos() / 0.06 * 0.3667;
                double sy = r.getYpos() / 0.06 * 0.3408;

                AffineTransform old = g2D.getTransform();

                g2D.translate(sx, sy);

                g2D.rotate(Math.toRadians(r.getAngle()));

                Rectangle2D.Double rect = new Rectangle2D.Double(
                        -2.5,
                        -2.5,
                        5,
                        5
                );
                g2D.fill(rect);

                g2D.setTransform(old);
            }

            g2D.dispose();
        }
    }
}
