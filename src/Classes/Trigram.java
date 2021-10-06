package Classes;

import java.util.ArrayList;

public class Trigram {
    private ArrayList<String> trigrams;
    public Trigram(String word){
        trigrams = new ArrayList<>();
        InitialiseTrigrams(word);
    }

    public ArrayList<String> getTrigrams() {
        return trigrams;
    }

    private void InitialiseTrigrams(String word){
        word = "<" + word + ">";
        for(int i=0 ; i<word.length()-2 ; i++){
            trigrams.add(word.substring(i, i+3));
        }
    }

    @Override
    public String toString() {
        return trigrams.toString();
    }
}
