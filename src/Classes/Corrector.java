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
        long startTime = System.nanoTime();
        long endTime = 0;
        long totalTime = 0;

        ArrayList<String> result = new ArrayList<>();

        // Verification d'une faute et recherche de mots par trigrammes
        if (!WordIsCorrect(providedWord)){

            result = TrigramsOccurence(providedWord);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("step 1 done in " + totalTime/1000000000);

            result = SizeSelection(result, providedWord.length());

            result = TrigramsSelection(result);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("step 2 done in " + totalTime/1000000000);

            result = RemoveDuplicate(result);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("step 3 done in " + totalTime/1000000000);

            result = LevenshteinOrder(result, providedWord);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("step 4 done in " + totalTime/1000000000);

            for (int i=5 ; i<result.size() ; i++)
                result.remove(i);

            for (String word : result){
                removeBrackets(word);
            }

        }
        else {
            result.clear();
        }


        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Correction done in " + totalTime/1000000000);
        DebugInfos(providedWord, result);
        return result;
    }

    private ArrayList<String> SizeSelection(ArrayList<String> words, int length) {
        ArrayList<String> result = new ArrayList<>();
        for (String word : words){
            if (word.length() + 3 > length || word.length() - 3 < length){
                result.add(word);
            }
        }
        return result;
    }

    private void DebugInfos(String providedWord, ArrayList<String> corrections) {
        System.out.println("faute: " + providedWord);
        System.out.println("nb de mots en reference: " + wordsRef.size());
        System.out.println("nb de mots en entr??e: " + entryWords.size());
        System.out.println("correction propos??es: " + corrections + "\n");
    }

    private boolean WordIsCorrect(String word) {
        return wordsRef.contains(word);
    }

    public void DataReader(){
        try {
            for (String word : Files.readAllLines(Paths.get(this.reference))) {
                this.wordsRef.add(addBrackets(word).toLowerCase(Locale.ROOT));
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
