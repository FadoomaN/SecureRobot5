
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class TestFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MapPanel panel = new MapPanel();
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    static class RobotNode {
        double x, y, angle;

        public RobotNode(double x, double y) {
            this.x = x;
            this.y = y;
            this.angle = 0;
        }

        public double getXpos() { return x; }
        public double getYpos() { return y; }
        public double getAngle() { return angle; }

        public void update() {
            angle += 2; // snurra
        }
    }

    static class MapPanel extends JPanel {
        RobotNode robot = new RobotNode(15.72, 40.74); // meter

        public MapPanel() {
            setPreferredSize(new Dimension(750, 750));

            // enkel timer-animation
            new Timer(16, e -> {
                robot.update();
                repaint();
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g.create();

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // bakgrund (grå)
            g2D.setColor(Color.DARK_GRAY);
            g2D.fillRect(0, 0, getWidth(), getHeight());

            // konstanter
            double metersPerPixel = 0.06;
            double scaleX = 750.0 / 2045.0;
            double scaleY = 750.0 / 2201.0;

            // robotvärld → pixlar i originalbild → pixlar i 750x750
            double sx = robot.getXpos() / metersPerPixel * scaleX;
            double sy = robot.getYpos() / metersPerPixel * scaleY;

            AffineTransform old = g2D.getTransform();

            g2D.translate(sx, sy);                      // till rätt skärmposition
            g2D.rotate(Math.toRadians(robot.getAngle())); // rotera runt robotens mitt

            Rectangle2D.Double rect = new Rectangle2D.Double(
                    -2.5, -2.5, 5, 5   // liten fyrkant centrerad
            );

            g2D.setColor(Color.RED);
            g2D.fill(rect);

            g2D.setTransform(old);
            g2D.dispose();
        }
    }
}
