package TestsClasses;

import org.junit.Assert;
import org.junit.Test;

import static Classes.Levenshtein.LevenshteinDistance;

public class TestLevenshtein
{
    @Test
    public void TestLevenshteinDistance()
    {
        String firstWord = "Levenshtein";
        String secondWord = "Levaisthein";

        int distance = LevenshteinDistance(firstWord, secondWord);

        Assert.assertEquals(4, distance);
    }
}
