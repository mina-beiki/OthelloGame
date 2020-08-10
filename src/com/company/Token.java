package com.company;

/**
 * This class represents a token in our game , which has a color and situation.
 * the situation is consisted of x and y . Y is the number between 1 to 8
 * that the player choose and X is the character between A and H that the
 * player choose .The character for X is also converted to a number between
 * 1 to 8 .
 *
 * @author Mina Beiki
 * @version 3.4.2020
 */
public class Token {
    //color value is the unicode of the color chosen for token .
    private int colorValue;
    private int x;
    private int y;

    /**
     * Makes a new token with the given color value , x and y .
     *
     * @param color unicode chosen for color of the token
     * @param x     value of x for the situation of the token
     * @param y     value of y for the situation of the token
     */
    public Token(int color, int x, int y) {
        this.colorValue = color;
        this.x = x;
        this.y = y;
    }

    /**
     * gets the value of color
     *
     * @return color value
     */
    public int getColorValue() {
        return colorValue;
    }

    /**
     * sets the value of color
     *
     * @param colorValue color value
     */
    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }

    /**
     * gets X of a token
     *
     * @return x of token
     */
    public int getX() {
        return x;
    }

    /**
     * sets X of a token
     *
     * @param x of token
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * gets Y of a token
     *
     * @return y of token
     */
    public int getY() {
        return y;
    }

    /**
     * sets Y of a token
     *
     * @param y of token
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Equals method overrided for Token class . Checks if two tokens
     * are the same or not .
     *
     * @param o object to be checked
     * @return true if they are the same and false if they are not .
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return getColorValue() == token.getColorValue() &&
                getX() == token.getX() &&
                getY() == token.getY();
    }

    /**
     * changes the color of a token . If the color is white , it changes it
     * to black and if the color is black it changes it to white .
     */
    public void changeColor() {
        String white = "25EF";
        String black = "25CF";
        //We have to convert the string which is the unicode to a hexadecimal integer :
        int whiteValue = Integer.parseInt(white, 16);
        int blackValue = Integer.parseInt(black, 16);
        if (colorValue == whiteValue)
            this.colorValue = blackValue;
        else if (colorValue == blackValue)
            this.colorValue = whiteValue;
    }

}
