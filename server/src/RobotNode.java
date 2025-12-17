import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotNode {
    private double x;
    private double y;
    private double angle;
    private String robotId;

    public RobotNode() {

    }

    public RobotNode(String robotId) {
        this.x = 0;
        this.y = 0;
        this.robotId = robotId;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public String getRobotId() {
        return "Robot: " + robotId;
    }

    public double getXpos() {
        return x;
    }

    public double getYpos() {
        return y;
    }

    public double getAngle() {
        return angle;
    }
}
