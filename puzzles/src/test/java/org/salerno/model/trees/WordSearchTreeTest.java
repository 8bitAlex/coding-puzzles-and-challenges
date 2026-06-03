package org.salerno.model.trees;

import org.junit.jupiter.api.Test;
import org.salerno.puzzles.games.BoggleTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordSearchTreeTest {

    private static final WordSearchTree tree = WordSearchTree.asTree(getLexiconFromFile());

    @Test
    public void shouldParseWordList() {
        assertTrue(tree.rootCache.size() > 0);
    }

    @Test
    public void shouldFindWord() {
        assertTrue(tree.isWord("Empire"));
        assertTrue(tree.isWord("Strike"));
        assertTrue(tree.isWord("Back"));
        // big word
        assertTrue(tree.isWord("beautification"));
        // not a word
        assertFalse(tree.isWord("asdf"));
    }

    @Test
    public void shouldFindPartialWord() {
        assertTrue(tree.isPartialWord("Emp"));
        assertTrue(tree.isPartialWord("Strik"));
        assertTrue(tree.isPartialWord("Back"));
        // big word
        assertTrue(tree.isPartialWord("beautificati"));
        // not a word
        assertFalse(tree.isPartialWord("asdf"));
    }

    @Test
    public void shouldHandleBadData() {
        assertFalse(tree.isWord(""));
        assertFalse(tree.isWord(null));
        assertFalse(tree.isPartialWord(""));
        assertFalse(tree.isPartialWord(null));
    }

    /* ---------------------------------- Private Methods ---------------------------------- **/

    private static HashSet<String> getLexiconFromFile() {
        final String resourcePath = "AmericanEnglishLexicon.txt";
        final HashSet<String> result = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(WordSearchTreeTest.class.getClassLoader().getResourceAsStream(resourcePath)))) {
            String line = reader.readLine();
            while(line != null) {
                result.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load lexicon from resource: " + resourcePath, e);
        }
        return result;
    }

}
