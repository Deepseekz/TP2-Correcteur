package Classes;

public class Levenshtein
{
    public static int LevenshteinDistance( String firstWord, String secondWord )
    {
        return LevenshteinDistance(firstWord.toCharArray(), secondWord.toCharArray());
    }

    public static int LevenshteinDistance(char[] firstWord, char[] secondWord) {

        // memoize only previous line of distance matrix
        int[] prev = new int[ secondWord.length + 1 ];

        for(int j = 0; j < secondWord.length + 1; j++ ) {
            prev[ j ] = j;
        }

        for(int i = 1; i < firstWord.length + 1; i++ ) {

            // calculate current line of distance matrix
            int[] curr = new int[ secondWord.length + 1 ];
            curr[0] = i;

            for(int j = 1; j < secondWord.length + 1; j++ ) {
                int d1 = prev[j] + 1;
                int d2 = curr[j - 1] + 1;
                int d3 = prev[j - 1];
                if ( firstWord[ i - 1 ] != secondWord[ j - 1 ] ) {
                    d3 += 1;
                }
                curr[ j ] = Math.min( Math.min( d1, d2 ), d3 );
            }

            // define current line of distance matrix as previous
            prev = curr;
        }
        return prev[ secondWord.length ];
    }
}
