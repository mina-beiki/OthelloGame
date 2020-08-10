package com.company;

import java.util.ArrayList;

/**
 * The Player class is consisted of a players data in the game ,
 * Which is its color value and list of tokens it has .
 * Each player can be either white or black , in this game the first player
 * is always considered white and 2nd player is considered black . In game with
 * computer second player is the computer which as mentioned , is considered black .
 *
 * @author Mina Beiki
 * @version 3.4.2020
 */
public class Player {
    private int colorValue;
    //list of the tokens the player has :
    private ArrayList<Token> tokens;

    /**
     * Makes a new player with the given x and y and also its color value .
     * The given x and y are for the first tokens which are in the center .
     * for white player is (4,4) and (5,5) and for black is (5,4) and (4,5) .
     * The color value is the unicode chosen for the tokens of the player .
     *
     * @param colorValue color value of tokens for the player
     * @param x          X and Y of the first tokens of the player , which are the tokens in the center .
     * @param y          X and Y of the first tokens of the player , which are the tokens in the center .
     */
    public Player(int colorValue, int x, int y) {
        int x2, y2;
        //makes a new list of tokens for that player :
        tokens = new ArrayList<Token>();
        this.colorValue = colorValue;
        //Initializing the first tokens on map :
        Token token1 = new Token(colorValue, x, y);
        tokens.add(token1);
        //first take the 4 centeral places on the map (for white and black , each seperated operations):
        if (x == 4 && y == 4) {
            x2 = x + 1;
            y2 = y + 1;
        } else {
            x2 = x - 1;
            y2 = y + 1;
        }
        Token token2 = new Token(colorValue, x2, y2);
        tokens.add(token2);
    }

    /**
     * Gets the tokens of a player .
     *
     * @return tokens of a player
     */
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    /**
     * Gets the color value of all tokens for a player.
     *
     * @return color value number of a player
     */
    public int getColorValue() {
        return colorValue;
    }

    /**
     * adds a new token to list of tokens for a player . (All of the
     * conditions are checked in addNewToken method in PlayingSystem class .
     *
     * @param newToken new token to be added
     */
    public void addToken(Token newToken) {
        tokens.add(newToken);
    }

    /**
     * gets the number of all tokens for a player
     *
     * @return number of tokens
     */
    public int getNumberOfTokens() {
        return tokens.size();
    }

}
