import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class JavaClient {

    public Socket clientSocket;
    RegistrationForm fiche;

    public JavaClient(String IP, int port) throws IOException, ClassNotFoundException {
        this.clientSocket = new Socket(IP, port);
        ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
        //BufferedWriter writer = new BufferedWriter(os);
        ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

        // Envoie au serveur les lignes tapée sur la console
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();

        // Prends les informations du client pour créer l'instance de registrationForm
        if (line.equals("INSCRIRE")) {
            System.out.println("Veuillez saisir votre prénom:  ");
            String prenom = scanner.nextLine();
            System.out.println("Veuillez saisir votre nom:  ");
            String nom = scanner.nextLine();
            System.out.println("Veuillez saisir votre email:  ");
            String email = scanner.nextLine();
            System.out.println("Veuillez saisir votre matricule:  ");
            String matricule = scanner.nextLine();
            System.out.println("Veuillez saisir le code du cours:  ");
            String code = scanner.nextLine();

            File listeCours = new File("src/data/cours.txt");
            Scanner scan = new Scanner(listeCours);
            Course course = null;
            while (scan.hasNextLine()) {
                String cours = scan.nextLine();
                String[] ligne = cours.split("\\s");
                String sigle = ligne[0];
                if (Objects.equals(sigle, code)) {
                    course = new Course(ligne[1], ligne[0], ligne[2]);
                }
            }
            fiche = new RegistrationForm(prenom, nom, email, matricule, course);
        }
        System.out.println("Envoi de : " + line);
        os.writeObject(line);

        if (line.equals("INSCRIRE")) {
            os.writeObject(fiche);
            System.out.println(is.readObject());
        }
        // Vider le buffer : envoyer la requête tout de suite
        os.flush();



        //ArrayList<Course> test = (ArrayList<Course>) is.readObject();
        //System.out.println(test.toString());
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

