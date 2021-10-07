package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Corrector
{
    private ArrayList<String> words = new ArrayList<>(); // note : l'usage d'une ArrayList facilite le code, mais rends l'execution beaucoup plus longue.
    private String reference;
    private String word;

    public Corrector()
    {

    }

    public Corrector(String word)
    {
        newWord(word);
    }

    public Corrector(String word, String path)
    {
        newWord(word);
        setReference(path);
    }

    public void setReference(String path) {
        this.reference = path;
        DataReader();
    }

    public void newWord(String word){
        this.word = addBrackets(word);
    }

    private ArrayList<String> TrigramsOccurence(ArrayList<String> list){
        ArrayList<String> result = new ArrayList<>();
        Trigram trigrams = new Trigram(this.word);
        for (String word : this.words){
            for (String trigram : trigrams.getTrigrams()) {
                if (word.contains(trigram)) {
                    result.add(word);
                }
            }
        }
        return result;
    }

    private ArrayList<String> TrigramsOccurence(){
        return TrigramsOccurence(this.words);
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
        ArrayList<String> result = new ArrayList<>();
        Set<String> set = new HashSet<>(list); // La collection Set n'accepte pas les doublons
        result.addAll(set);
        return result;
    }

    private ArrayList<String> LevenshteinOrder(ArrayList<String> list){
        ArrayList<String> result = new ArrayList<>();
        int max = Integer.MAX_VALUE;

        for (String word: list){
            int distance = Levenshtein.LevenshteinDistance(word, this.word);

            if (distance < max) {
                result.add(word);
                max = distance;
            }
        }
        Collections.reverse(result);
        return result;
    }

    public ArrayList<String> WordProcessing(){
        ArrayList<String> result = new ArrayList<>();

        // Verification d'une faute et recherche de mots par trigrammes
        if (!WordIsCorrect()){

            result = TrigramsOccurence();
            result = TrigramsSelection(result);
            result = RemoveDuplicate(result);
            result = LevenshteinOrder(result);

            for (int i=5 ; i<result.size() ; i++)
                result.remove(i);


        }
        else {
            result.clear();
        }
        for (String word : result){
            removeBrackets(word);
        }
        System.out.println(words.size());

        return result;
    }

    private boolean WordIsCorrect() {
        return words.contains(word);
    }

    public static ArrayList<String> Correction(String word, String path){
        Corrector corrector = new Corrector(word, path);
        return corrector.WordProcessing();
    }

    public void DataReader(){
        try {
            for (String word : Files.readAllLines(Paths.get(this.reference))) {
                this.words.add(addBrackets(word));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addBrackets(String word){
        return "<" + word + ">";
    }

    private String removeBrackets(String word) {
        String result = word.replaceAll("<|>", "");
        return result;
    }

    @Override
    public String toString() {
        return removeBrackets(this.word);
    }
}
