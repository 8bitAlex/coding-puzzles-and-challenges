package org.salerno.datastructure;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A Search Tree that finds words and partial words.
 * <br><br>
 * Some performance considerations: To maximize performance search for partial words in the tree and do not continue
 * searching if the partial word does not return a match. This allows branch trimming. Backed by HashMaps.
 *
 * @author alex.salerno@me.com
 * @since Aug-2022
 */
public class WordSearchTree {

    final HashMap<Character, Node> rootCache = new HashMap<>();

    /* ---------------------------------- Constructor Methods ---------------------------------- **/

    private WordSearchTree() {
        // nop
    }

    /* ---------------------------------- Public Methods ---------------------------------- **/

    /**
     * @param wordList A list of words, such as a lexicon or dictionary, that populate the tree for searching
     * @return {@code WordSearchTree} instance
     */
    public static WordSearchTree asTree(final HashSet<String> wordList) {
        if(wordList == null) return null;
        final WordSearchTree tree = new WordSearchTree();

        // for each word
        for(String word : wordList) {
            // get each character
            final char[] chars = word.toLowerCase().toCharArray();
            // key off first letter
            Node root = tree.rootCache.get(chars[0]);
            if(root == null) {
                root = new Node();
                tree.rootCache.put(chars[0], root);
            }
            // add the rest of the characters
            Node node = root;
            for(int i = 1; i < chars.length; i++) {
                node = node.addChild(chars[i]);
            }
            node.isWordEnd = true;
        }

        return tree;
    }

    /**
     * @param word A word
     * @return {@code True} if the word is found in the Tree, else {@code False}
     */
    public boolean isWord(final String word) {
        final Node node = findNodeByWord(word);
        if(node != null) return node.isWordEnd();
        return false;
    }

    /**
     * Searches the tree for a partial match against the given word. A partial match means that the given word
     * is a substring of a complete word.
     * <br><br>
     * Example. Search for the partial word 'Tes' will return {@code True} because it would match with the
     * word 'Test' or 'Tesseract'. But the partial word 'asdlkfjuk' will return {@code False} because it is
     * not a substring of any known word.
     * @param word A word
     * @return {@code True} if the partial word is found in the Tree, else {@code False}
     */
    public boolean isPartialWord(final String word) {
        final Node node = findNodeByWord(word);
        return node != null;
    }

    /* ---------------------------------- Private Methods ---------------------------------- **/

    /**
     * @param word A word
     * @return The final {@code Node} that references the given word
     */
    private Node findNodeByWord(final String word) {
        if(word == null || word.length() == 0) return null;

        final char[] chars = word.toLowerCase().toCharArray();
        Node node = rootCache.get(chars[0]);
        if(node == null) return null;
        for(int i = 1; i < chars.length; i++) {
            node = node.get(chars[i]);
            if(node == null) return null;
        }

        return node;
    }

    /* ---------------------------------- Node Class ---------------------------------- **/

    /**
     * Represents a single element in the {@code WordSearchTree}
     */
    private static class Node {

        // instance vars
        private boolean isWordEnd = false;
        private final HashMap<Character, Node> children = new HashMap<>();

        private Node() {
            // nop
        }

        /**
         * @param character A character
         * @return The child {@code Node} that contains the given character, if not found returns {@code null}
         */
        private Node get(final char character) {
            return this.children.get(character);
        }

        /**
         * @param character A character that is a child of the current {@code Node}s character
         * @return The child {@code Node} that uses the given character
         */
        private Node addChild(final char character) {
            Node child = children.get(character);
            if(child == null) child = new Node();
            children.put(character, child);
            return child;
        }

        /**
         * @return {@code True} if the {@code Node} is the end of a word, else {@code False}
         */
        private boolean isWordEnd() {
            return isWordEnd;
        }

    }

}
