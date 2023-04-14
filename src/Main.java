import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("*** Bienvenue au portail ***");
        while (true) {
            JavaClient client = new JavaClient("127.0.0.1", 1337);
        }
    }
}
