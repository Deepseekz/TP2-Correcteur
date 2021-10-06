package Classes;

public class Main {
    public static void main(String[] args) {
        Corrector correction = new Corrector("monolithe");
        correction.setReference("/amuhome/l21201049/IdeaProjects/TP2-Correcteur/texts/dico.txt");
        System.out.println(correction.WordProcessing());

        correction.newWord("monolite");
        System.out.println(correction.WordProcessing());
    }
}
