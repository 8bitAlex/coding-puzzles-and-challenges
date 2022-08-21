package org.salerno.games;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class BoggleTest {

    private static final Boggle boggle = new Boggle(getLexiconFromFile());

    @Test
    public void shouldSolveBoard() {
        final HashSet<String> solution = boggle.solveBoard(3, 3, "yoxrbaved");
        assertTrue(solution.size() >= 31);
        // true
        assertTrue(solution.contains("abed"));
        assertTrue(solution.contains("aero"));
        assertTrue(solution.contains("aery"));
        assertTrue(solution.contains("bad"));
        assertTrue(solution.contains("bade"));
        assertTrue(solution.contains("bead"));
        assertTrue(solution.contains("bed"));
        assertTrue(solution.contains("boa"));
        assertTrue(solution.contains("bore"));
        assertTrue(solution.contains("bored"));
        assertTrue(solution.contains("box"));
        assertTrue(solution.contains("boy"));
        assertTrue(solution.contains("bread"));
        assertTrue(solution.contains("bred"));
        assertTrue(solution.contains("bro"));
        assertTrue(solution.contains("broad"));
        assertTrue(solution.contains("byre"));
        assertTrue(solution.contains("dab"));
        assertTrue(solution.contains("derby"));
        assertTrue(solution.contains("orb"));
        assertTrue(solution.contains("orbed"));
        assertTrue(solution.contains("ore"));
        assertTrue(solution.contains("read"));
        assertTrue(solution.contains("red"));
        assertTrue(solution.contains("road"));
        assertTrue(solution.contains("rob"));
        assertTrue(solution.contains("robe"));
        assertTrue(solution.contains("robed"));
        assertTrue(solution.contains("verb"));
        assertTrue(solution.contains("very"));
        assertTrue(solution.contains("yore"));
        // false
        assertFalse(solution.contains("robbed"));
        assertFalse(solution.contains("abroad"));
        assertFalse(solution.contains("be"));
        assertFalse(solution.contains("boar"));
        assertFalse(solution.contains("board"));
        assertFalse(solution.contains("dove"));
    }

    @Test
    public void shouldHandleBadData() {
        // not enough letters
        HashSet<String> solution = boggle.solveBoard(3, 3, "yoxrba");
        assertSame(0, solution.size());
        // no letters
        solution = boggle.solveBoard(3, 3, "");
        assertSame(0, solution.size());
        // null
        solution = boggle.solveBoard(3, 3, null);
        assertSame(0, solution.size());
        // bad size
        solution = boggle.solveBoard(1, 3, "yoxrbaved");
        assertSame(0, solution.size());
        solution = boggle.solveBoard(3, 1, "yoxrbaved");
        assertSame(0, solution.size());
        solution = boggle.solveBoard(-1, -3, "yoxrbaved");
        assertSame(0, solution.size());
    }

    @Test
    public void shouldHandleLargeDataSet() {
        final HashSet<String> solution = boggle.solveBoard(9, 9, "fwirqbndymclxnxqqkjqygtuucdtdudeodrdylmkzschbkhqlinbtcvdynngjunaryjkitvswpfisnoij");
        assertTrue(solution.size() > 0);
    }

    /* ---------------------------------- Private Methods ---------------------------------- **/

    private static HashSet<String> getLexiconFromFile() {
        final String path = "resources/AmericanEnglishLexicon.txt";
        final HashSet<String> result = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null) {
                result.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
