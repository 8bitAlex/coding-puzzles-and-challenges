package org.salerno.puzzles.games;

import org.salerno.model.trees.WordSearchTree;

import java.awt.*;
import java.util.HashSet;
import java.util.Stack;

/**
 * Boggle Board Solver implementation
 * <br><br>
 * Boggle is a word game invented by Allan Turoff and originally distributed by Parker Brothers.
 * The game is played using a plastic grid of lettered dice, in which players look for words in
 * sequences of adjacent letters. (Wikipedia)
 * @author alex.salerno@me.com
 * @since Aug-2022
 */
public class Boggle {

    // instance vars
    /** Searchable Lexicon */
    final WordSearchTree wordSearchTree;

    // constants
    /**
     * Minimum number of letters that can constitute a word
     */
    private static final int MIN_NUM_LETTERS = 3;

    /* ---------------------------------- Constructor Methods ---------------------------------- **/

    public Boggle(final HashSet<String> validWords) {
        wordSearchTree = WordSearchTree.asTree(validWords);
    }

    /* ---------------------------------- Public Methods ---------------------------------- **/

    /**
     * @param width The Boggle board width, starting with 1
     * @param height The Boggle board height, starting with 1
     * @param boardLetters A string of letters representing the Boggle letters starting from the upper-left of the board from left to right
     * @return A list of words that are valid solutions
     */
    public HashSet<String> solveBoard(final int width, final int height, final String boardLetters) {
        HashSet<String> results = new HashSet<>();
        final long boardArea = (long) width * height;
        if(boardLetters == null
                || boardLetters.length() <= 0
                || width <= 0 || height <= 0
                || boardArea != boardLetters.length()
        ) return results;

        final Board board = new Board(width, height, boardLetters);
        final char[] chars = boardLetters.toCharArray();
        // get all solution words for each starting position
        // from (0,0) to (BOARD_WIDTH,BOARD_HEIGHT)
        for(int i = 0; i < chars.length; i++) {
            results.addAll(getWords(board, i%width, Math.floorDiv(i,height)));
        }
        return results;
    }

    /* ---------------------------------- Private Methods ---------------------------------- **/

    /**
     * @param board The Boggle board to check for solutions against
     * @param x X coordinate of root letter
     * @param y Y coordinate of root letter
     * @return A list of words that are valid solutions from the root letter
     */
    private HashSet<String> getWords(final Board board, final int x, final int y ) {
        final HashSet<String> result = new HashSet<>();
        final Stack<Point> stack = new Stack<>();
        // set root
        final Point root = new Point(x,y);
        stack.push(root);
        // search for valid words
        traverseBoard(board, stack, result, root);
        return result;
    }

    /**
     * A dfs recursive method that traverses all possible permutations of words on the Boggle board.
     *
     * @param board The Boggle board to check for solutions against
     * @param stackOut An out variable that tracks the current character
     * @param resultOut An out variable that contains the results of the search
     * @param point The current point (x,y) to search the board from
     */
    private void traverseBoard(final Board board, final Stack<Point> stackOut, final HashSet<String> resultOut, final Point point) {
        // get valid moves from current point
        HashSet<Point> moves = board.getValidMoves(point.x,point.y);
        if(moves.size() == 0) stackOut.pop();
        // for each possible move
        for(Point move : moves) {
            // is move already traversed
            if(stackOut.contains(move)) continue;
            else stackOut.push(move);

            // get current list of letters as string (word?)
            final String word = board.getWordByPoints(stackOut);
            // if the current string is not part of a word, trim this branch (performance!)
            if(!wordSearchTree.isPartialWord(word)) {
                stackOut.pop();
                continue;
            }
            // if it's a word, add to results
            if(stackOut.size() >= MIN_NUM_LETTERS && wordSearchTree.isWord(word)) resultOut.add(board.getWordByPoints(stackOut));
            // traverse from this point
            traverseBoard(board, stackOut, resultOut, move);
        }
        stackOut.pop();
    }

    /* ---------------------------------- Board Class ---------------------------------- **/

    /**
     * Represents the Boggle board
     */
    private static class Board {

        // instance vars
        final int width;
        final int height;
        final String boardLetters;

        private Board(final int width, final int height, final String boardLetters) {
            this.width = width;
            this.height = height;
            this.boardLetters = boardLetters;
        }

        /**
         * @param x X coordinate of location
         * @param y Y coordinate of location
         * @return {@code char} from (x,y) location
         */
        private char getCharAtLocation(final int x, final int y) {
            final int index = (y * this.width) + x;
            final char[] chars = boardLetters.toCharArray();
            if(index > chars.length-1) return '\0';
            return boardLetters.toCharArray()[index];
        }

        /**
         * @param points An ordered {@code Stack} of points that build the desired word
         * @return A word built from given locations
         */
        private String getWordByPoints(final Stack<Point> points) {
            StringBuilder builder = new StringBuilder();
            for(Point point : points) {
                builder.append(getCharAtLocation(point.x, point.y));
            }
            return builder.toString();
        }

        /**
         *
         * @param x X coordinate of location
         * @param y Y coordinate of location
         * @return {@code True} if the given coordinates are within the boards playable space, else {@code False}
         */
        private boolean isValidMove(final int x, final int y) {
            return x >= 0 && y >= 0 && x <= this.width-1 && y <= this.height-1;
        }

        /**
         * @param x X coordinate of location
         * @param y Y coordinate of location
         * @return A {@code Set} of {@code Point}s that are valid play-spaces from the given location
         */
        private HashSet<Point> getValidMoves(final int x, final int y) {
            final HashSet<Point> results = new HashSet<>();
            for(int i = -1; i < 2; i++ ) {
                for(int j = -1; j < 2; j++) {
                    if(i == 0 && j == 0) continue;
                    if(this.isValidMove(x+i, y+j)) {
                        results.add(new Point(x+i, y+j));
                    }
                }
            }
            return results;
        }

    }

}
