import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class JavaClient extends ListeCours {

    public Socket clientSocket;
    RegistrationForm fiche;
    ArrayList<Course> arrayCoursSession;
    ObjectOutputStream os;
    ObjectInputStream is;
    Scanner scanner;
    String session;
    int choix2 = 1;
    int fonction = 1;
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private String code;

    public JavaClient(String IP, int port, int fonction) throws IOException, ClassNotFoundException {
        super();
        this.fonction = fonction;
        this.clientSocket = new Socket(IP, port);
         this.os = new ObjectOutputStream(clientSocket.getOutputStream());
        this.is = new ObjectInputStream(clientSocket.getInputStream());
        this.scanner = new Scanner(System.in);

        if (fonction == 1) {
            imprimerSession();
        }
        if (fonction == 2) {
            creationFiche();
        }
        if (fonction == 3) {
            choixCode();
        }
        os.flush();
        os.close();
        is.close();
        clientSocket.close();
    }

    public void imprimerSession() {
        try {
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste " +
                    "des cours:\n" + "1. Automne\n" + "2. Hiver\n" + "3. Ete\n" + "Choix: ");
            int choix = scanner.nextInt();
            if (choix == 1) {
                session = "automne";
                os.writeObject("CHARGER Automne");
            } else if (choix == 2) {
                session = "hiver";
                os.writeObject("CHARGER Hiver");
            } else if (choix == 3) {
                session = "ete";
                os.writeObject("CHARGER Ete");
            } else {
                System.out.println("Ce choix est invalide.");
                imprimerSession();
            }
            this.arrayCoursSession = (ArrayList<Course>) is.readObject();
            setCoursSession(arrayCoursSession);
            for (int i = 0; i < getCoursSession().size(); i++) {
                System.out.println(getCoursSession().get(i).toString());
            }
            System.out.println("Les cours offerts pendant la session d'" + session + " sont:");
            //ArrayList<String> sigleSession = new ArrayList<>();
            for (int i = 0; i < arrayCoursSession.size(); i++) {
                //sigleSession.add(arrayCoursSession.get(i).getCode());
                System.out.println(i + 1 + ". " + arrayCoursSession.get(i).getCode() + "\t" + arrayCoursSession.get(i).getName());
            }

        System.out.println("Choix:\n" + "1. Consulter les cours offert pour une autre session.\n" +
                    "2. Inscription à un cours.\n" + "Choix: ");

            choix2 = scanner.nextInt();
            if (choix2 == 1) {
                JavaClient client = new JavaClient("127.0.0.1", 1337, 1);
            } else if (choix2 == 2) {
                JavaClient client = new JavaClient("127.0.0.1", 1337, 2);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void creationFiche() {
        System.out.println("Veuillez saisir votre prénom:  ");
        this.prenom = scanner.nextLine();
        System.out.println("Veuillez saisir votre nom:  ");
        this.nom = scanner.nextLine();
        System.out.println("Veuillez saisir votre email:  ");
        this.email = scanner.nextLine();
        System.out.println("Veuillez saisir votre matricule:  ");
        this.matricule = scanner.nextLine();
        choixCode();
    }

    public void choixCode() {
        try {
            System.out.println("Veuillez saisir le code du cours:  ");
            this.code = scanner.nextLine();
            boolean codeValide = false;
            for (int i = 0; i < getCoursSession().size(); i++) {
                if (Objects.equals(code, getCoursSession().get(i).getCode())) {
                    codeValide = true;
                    break;
                }
            }
            if (!codeValide) {
                System.out.println("Ce choix de cours est invalide.");
                choixCode();
            }
            InputStream stream = getClass().getClassLoader().getResourceAsStream("data/cours.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String line;
            Course course = null;
            while ((line = in.readLine()) != null) {
                String[] ligne = line.split("\\s");
                String sigle = ligne[0];
                if (Objects.equals(sigle, code)) {
                    course = new Course(ligne[1], ligne[0], ligne[2]);
                }
            }
            fiche = new RegistrationForm(prenom, nom, email, matricule, course);
            os.writeObject("INSCRIRE");
            os.writeObject(fiche);
            System.out.println(is.readObject());
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Getters and setters
    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getCode() {
        return code;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

