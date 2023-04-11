import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class JavaClient {

    public Socket clientSocket;

    public JavaClient(String IP, int port) throws IOException {
        this.clientSocket = new Socket(IP, port);
        OutputStreamWriter os = new OutputStreamWriter(clientSocket.getOutputStream());
        BufferedWriter writer = new BufferedWriter(os);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            System.out.println("Envoi de : " + line);
            writer.append(line + "\n");
            writer.flush();
            if(line.equals("exit")) {
                System.out.println("Au revoir.");
                break;
            }
        }
        writer.close();
    }
}
