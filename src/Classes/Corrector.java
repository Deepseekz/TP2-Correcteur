package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Corrector
{
    private final ArrayList<String> wordsRef = new ArrayList<>(); // note : l'usage d'une ArrayList facilite le code, mais rends l'execution beaucoup plus longue.
    private String reference;
    private final ArrayList<String> entryWords = new ArrayList<>();

    public Corrector()
    {

    }


    public Corrector(ArrayList<String> entryWords, String path)
    {
        getEntryWords(entryWords);
        setReference(path);
    }

    public void setReference(String path) {
        this.reference = path;
        DataReader();
    }

    public void getEntryWords(ArrayList<String> entryWords){
        for (String word : entryWords){
            this.entryWords.add(addBrackets(word));
        }
    }

    private ArrayList<String> TrigramsOccurence(ArrayList<String> list, String providedWord){
        ArrayList<String> result = new ArrayList<>();
        Trigram trigrams = new Trigram(providedWord);
        for (String word : this.wordsRef){
            for (String trigram : trigrams.getTrigrams()) {
                if (word.contains(trigram)) {
                    result.add(word);
                }
            }
        }
        return result;
    }

    private ArrayList<String> TrigramsOccurence(String providedWord){
        return TrigramsOccurence(this.wordsRef, providedWord);
    }

    private ArrayList<String> TrigramsSelection(ArrayList<String> list){
        ArrayList<String> result = new ArrayList<>();
        int max = 0;
        for (String word: list){
            int occurence = Collections.frequency(list, word);
            if (occurence >= max){
                result.add(word);
                max = occurence;
            }
        }
        Collections.reverse(result);
        return result;
    }

    private ArrayList<String> RemoveDuplicate(ArrayList<String> list) {
        Set<String> set = new HashSet<>(list); // La collection Set n'accepte pas les doublons
        return new ArrayList<>(set);
    }

    private ArrayList<String> LevenshteinOrder(ArrayList<String> list, String providedWord){
        ArrayList<String> result = new ArrayList<>();
        int max = Integer.MAX_VALUE;

        for (String word : list){
            int distance = Levenshtein.LevenshteinDistance(word, providedWord);

            if (distance < max) {
                result.add(word);
                max = distance;
            }
        }
        Collections.reverse(result);
        return result;
    }

    public ArrayList<ArrayList<String>> WordsProcessing(){
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String word : this.entryWords){
            result.add(WordProcessing(word));
        }
        return result;
    }

    public ArrayList<String> WordProcessing(String providedWord){
        ArrayList<String> result = new ArrayList<>();

        // Verification d'une faute et recherche de mots par trigrammes
        if (!WordIsCorrect(providedWord)){

            result = TrigramsOccurence(providedWord);
            result = TrigramsSelection(result);
            result = RemoveDuplicate(result);
            result = LevenshteinOrder(result, providedWord);

            for (int i=5 ; i<result.size() ; i++)
                result.remove(i);


        }
        else {
            result.clear();
        }
        for (String word : result){
            removeBrackets(word);
        }

        DebugInfos(providedWord, result);
        return result;
    }

    private void DebugInfos(String providedWord, ArrayList<String> corrections) {
        System.out.println("faute: " + providedWord);
        System.out.println("nb de mots en reference: " + wordsRef.size());
        System.out.println("nb de mots en entrée: " + entryWords.size());
        System.out.println("correction proposées: " + corrections + "\n");
    }

    private boolean WordIsCorrect(String word) {
        return wordsRef.contains(word);
    }

    public void DataReader(){
        try {
            for (String word : Files.readAllLines(Paths.get(this.reference))) {
                this.wordsRef.add(addBrackets(word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addBrackets(String word){
        return "<" + word + ">";
    }

    private String removeBrackets(String word) {
        return word.replaceAll("[<>]", "");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String word : entryWords){
            result.append(removeBrackets(word)).append("\n");
        }
        return result.toString();
    }
}
