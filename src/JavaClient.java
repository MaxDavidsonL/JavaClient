import server.models.Course;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaClient {

    public Socket clientSocket;

    public JavaClient(String IP, int port) throws IOException, ClassNotFoundException {
        this.clientSocket = new Socket(IP, port);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        //BufferedWriter writer = new BufferedWriter(os);
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

        // Envoie au serveur les lignes tapée sur la console
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();
        System.out.println("Envoi de : " + line);
        os.writeObject(line);
        // Vider le buffer : envoyer la requête tout de suite
        os.flush();

        ArrayList<Course> test = (ArrayList<Course>) is.readObject();
        System.out.println(test.toString());
        os.close();
        is.close();
        clientSocket.close();




        //os.writeObject("CHARGER Hiver")


        /*while (scanner.hasNext()) {
            String line = scanner.nextLine();
            System.out.println("Envoi de : " + line);
            writer.append(line + "\n");
            writer.flush();
            if (line.equals("exit")) {
                System.out.println("Au revoir.");
                break;
            }
        }
            /*while (reader.ready()) {
                System.out.println(reader);
            }
            writer.close();
            //reader.close();
        */
    }
}

