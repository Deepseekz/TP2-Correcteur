package Classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class Corrector
{
    private ArrayList<String> words = new ArrayList<>();
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
        this.reference = path;
        DataReader();
    }

    public void setReference(String path) {
        this.reference = path;
        DataReader();
    }

    public void newWord(String word){
        this.word = addBrackets(word);
    }

    public String WordProcessing(){
        String result = this.word;
        String correct = "";
        if (!WordIsCorrect()){
            ArrayList<String> resemblingWords = new ArrayList<>();
            Trigram trigrams = new Trigram(this.word);
            correct += " est faux";
            for (String word : this.words){
                for (String trigram : trigrams.getTrigrams()) {
                    if (word.contains(trigram)) {
                        resemblingWords.add(word);
                    }
                }
            }
            System.out.println(resemblingWords);
        }
        else {
            correct += " est juste";
        }
        return removeBrackets(result) + correct;
    }

    private boolean WordIsCorrect() {
        return words.contains(word);
    }

    public static String Correction(String word){
        Corrector corrector = new Corrector(word);
        return corrector.WordProcessing();
    }

    public void DataReader(){
        try {
            for (String line : Files.readAllLines(Paths.get(this.reference))) {
                this.words.add(addBrackets(line));
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
