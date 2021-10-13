package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // TODO 1 : implémenter correction de "répétition de lettres ou son absence", actuellement "cha" ne proposera pas "chat".
        // TODO 2 : chercher des optimisations, l'algorithme etant très lent.
        // TODO 3 : utiliser le multi-threading pour les memes raisons

        /*
        ArrayList<String> fautes = new ArrayList<>();
        try {
            for (String word : Files.readAllLines(Paths.get("/amuhome/l21201049/IdeaProjects/TP2-Correcteur/texts/minifautes.txt"))) {
                fautes.add(word);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Corrector correction = new Corrector();
        correction.getEntryWords(fautes);
        correction.setReference("/amuhome/l21201049/IdeaProjects/TP2-Correcteur/texts/dico.txt");
        correction.WordsProcessing();
        */

        // System.out.println(Levenshtein.LevenshteinDistance("logarythmique", "algorithmique"));

        Corrector corrector = new Corrector();
        corrector.setReference("/amuhome/l21201049/IdeaProjects/TP2-Correcteur/texts/dico.txt");

        String fautes = "ordinateurss";
        corrector.WordProcessing(fautes);
    }
}
