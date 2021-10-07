package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        // TODO 1 : implémenter correction de "répétition de lettres ou son absence", actuellement "cha" ne proposera pas "chat".
        // TODO 2 : chercher des optimisations, l'algorithme etant très très lent.
        // TODO 3 : utiliser le multi-threading pour les memes raisons

              Corrector correction = new Corrector();
        correction.setReference("C:\\Users\\Adam\\IdeaProjects\\TP2-Correcteur\\texts\\dico.txt");

        try {
            for (String word : Files.readAllLines(Paths.get("C:\\Users\\Adam\\IdeaProjects\\TP2-Correcteur\\texts\\fautes.txt"))) {
                System.out.println(Corrector.Correction(word, "C:\\Users\\Adam\\IdeaProjects\\TP2-Correcteur\\texts\\dico.txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
