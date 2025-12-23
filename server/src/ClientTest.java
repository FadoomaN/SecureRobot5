package src;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

enum Direction {
    UP,
    LEFT,
    DOWN,
    RIGHT
}

public class ClientTest {
    public static void main(String[] args) {

        //Scanner input = new Scanner(System.in);

        double x = 10;
        double y = 10;
        double angle = 180;
        Direction dir = Direction.DOWN;

        try (Socket client = new Socket("localhost", 5000)) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            while (!client.isClosed()) {
                //String message = input.nextLine();
                switch (dir) {
                    case UP:
                        y -= 1;
                        if (y > 20 && y <= 30) {
                            angle = (angle - 9) % 360;
                        }
                        if (y == 10) {
                            dir = Direction.LEFT;
                        }
                        break;
                    case LEFT:
                        x -= 1;
                        if (x > 20 && x <= 30) {
                            angle = (angle - 9) % 360;
                        }
                        if (x == 10) {
                            dir = Direction.DOWN;
                        }
                        break;
                    case DOWN:
                        y += 1;
                        if (y >= 100 && y < 110) {
                            angle = (angle - 9) % 360;
                        }
                        if (y == 110) {
                            dir = Direction.RIGHT;
                        }
                        break;
                    case RIGHT:
                        x += 1;
                        if (x >= 100 && x < 110) {
                            angle = (angle - 9) % 360;
                        }
                        if (x == 110) {
                            dir = Direction.UP;
                        }
                        break;
                }
                try {
                    //x += 1;
                    //y += 1;
                    String message = x + "," + y + "*" + angle;
                    bw.write(message);
                    bw.newLine();
                    bw.flush();
                    Thread.sleep(50);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}