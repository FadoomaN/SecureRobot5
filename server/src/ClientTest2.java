

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientTest2 {
    public static void main(String[] args) {

        //Scanner input = new Scanner(System.in);

        double x = 200;
        double y = 0;
        double angle = 180;

        try (Socket client = new Socket("localhost", 5000)) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            while (!client.isClosed()) {
                //String message = input.nextLine();
                try {
                    //x += 1;
                    y += 1;
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
