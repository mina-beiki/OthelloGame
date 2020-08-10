package com.company;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the most important class in this game . It controls the whole
 * game , prints the board (map) , updates it and flip the surrounded tokens ,
 * adds a new toke with checking all the conditions , find the available places
 * for a new token to be added , checks if the game has finished or not and has the control
 * of turns along the game .
 *
 * @author Mina Beiki
 * @version 3.4.2020
 */

public class PlayingSystem {
    //list of all tokens in the game :
    private ArrayList<Token> allTokens;
    private Player player1; //white tokens
    private Player player2; //black tokens
    private int turn;

    /**
     * Makes a new game with the given color value for each player .
     * Makes a new list of all tokens for the game and adds the players .
     *
     * @param color1Value color value number for first player
     * @param color2Value color value number for second player
     */
    public PlayingSystem(int color1Value, int color2Value) {
        player1 = new Player(color1Value, 4, 4);
        player2 = new Player(color2Value, 5, 4);
        allTokens = new ArrayList<>();
        allTokens.addAll(player1.getTokens());
        allTokens.addAll(player2.getTokens());
        turn = 1; //starting with player1
    }

    /**
     * Prints the board (map) of our game . Checks each token x and y
     * with the for loops parameters , if the are equal it means there
     * is a token in that place .
     */
    public void printBoard() {
        //upper border :
        for (int i = 0; i < 8; i++) {
            System.out.print("....");
        }
        System.out.println(".");
        //the map :
        for (int i = 1; i < 9; i++) { // i stands for y
            for (int j = 1; j < 9; j++) { // j stands for x
                int flag = 0;
                for (Token token : allTokens) {
                    if (j == token.getX() && i == token.getY()) {
                        System.out.print("| " + (char) token.getColorValue() + " ");
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    System.out.print("|   ");
                }
                //for the last column we need a border :
                if (j == 8)
                    System.out.print("|");
            }
            System.out.println();
        }
        //lower border :
        for (int i = 0; i < 8; i++) {
            System.out.print("''''");
        }
        System.out.print("'");


    }

    /**
     * Prints all tokens shape which shows their color , and also x and y for each of them .
     */
    public void printData() {
        for (Token token : allTokens) {
            System.out.println((char) token.getColorValue() + " x=" + token.getX() + " y=" + token.getY());
        }
    }

    /**
     * Has control over the turns of players in the game . If
     * the last turn is 1 then it changes to 2 and also does the
     * opposite .
     *
     * @return player turn to be played
     */
    public int playTurn() {
        if (turn == 1) {
            turn = 2;
            return 2;
        } else {
            turn = 1;
            return 1;
        }
    }

    /**
     * This method finds the available and legal places for a token to be put .
     * Due to the rules of the game for a player , the token should be put in a place
     * that surrounds the other players tokens .
     *
     * @param turn player turn
     * @return an array list which is made of array lists which in each of them the first index is the x and the second
     * is the y for available places for new token to be put .
     */
    public ArrayList<ArrayList<Integer>> findAvailablePlaces(int turn) {
        int i = 0, j = 0;
        ArrayList<ArrayList<Integer>> availablePlaces = new ArrayList<>();
        for (int k = 0; k < 4; k++) {
            //Straight line :
            if (k == 0) {
                i = 1;
                j = 0;
            }
            //perpendicular line :
            if (k == 1) {
                i = 0;
                j = 1;
            }
            //diagonal line from left down to right up :
            if (k == 2) {
                i = 1;
                j = 1;
            }
            //diagonal line from right down to left up :
            if (k == 3) {
                i = -1;
                j = 1;
            }
            int flag1 = 0;
            int flag2 = 0;
            //if it's black player turn:
            if (turn == 2) {
                for (Token token : allTokens) {
                    flag1 = 0;
                    flag2 = 0;
                    int yUpper = token.getY();
                    int yLower = token.getY();
                    int xBackward = token.getX();
                    int xForward = token.getX();
                    //checking if there is any token of black in front which surrounds them :
                    //checks if the token is white :
                    if (token.getColorValue() == player1.getColorValue()) {
                        //while there is a token front of it
                        while (checkTokenExistence(xForward, yUpper)) {
                            if (getTokenColorValue(xForward, yUpper) == player2.getColorValue()) {
                                //if we have a black token in front of it :
                                flag1 = 1;
                                break;
                            }
                            xForward += i;
                            yUpper -= j;

                        }
                        //checking if there is any token of black in back which surrounds them :
                        while (checkTokenExistence(xBackward, yLower)) {
                            if (getTokenColorValue(xBackward, yLower) == player2.getColorValue()) {
                                //if we have a black token in back of it :
                                flag2 = 1;
                                break;
                            }
                            xBackward -= i;
                            yLower += j;

                        }
                        //if there is a token of black in front which surrounds them , we put the black token
                        //in the other side of line(back) :
                        if (flag1 == 1 && flag2 == 0) {
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xBackward);
                            temp.add(yLower);
                            availablePlaces.add(temp);
                        }
                        //if there is a token of black in back which surrounds them , we put the black token
                        //in the other side of line (front):
                        if (flag1 == 0 && flag2 == 1) {
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xForward);
                            temp.add(yUpper);
                            availablePlaces.add(temp);
                        }

                    }

                }
            }

            //if its white player turn :
            if (turn == 1) {
                for (Token token : allTokens) {
                    flag1 = 0;
                    flag2 = 0;
                    int yUpper = token.getY();
                    int yLower = token.getY();
                    int xBackward = token.getX();
                    int xForward = token.getX();
                    //checking if there is any token of white in front which surrounds them :
                    //checks if the token is black :
                    if (token.getColorValue() == player2.getColorValue()) {
                        //while there is a token front of it
                        while (checkTokenExistence(xForward, yUpper)) {
                            if (getTokenColorValue(xForward, yUpper) == player1.getColorValue()) {
                                //if we have a white token in front of it :
                                flag1 = 1;
                                break;
                            }
                            xForward += i;
                            yUpper += j;

                        }
                        //while there is a token front of it
                        while (checkTokenExistence(xBackward, yLower)) {
                            if (getTokenColorValue(xBackward, yLower) == player1.getColorValue()) {
                                //if we have a white token in back of it :
                                flag2 = 1;
                                break;
                            }
                            xBackward -= i;
                            yLower -= j;

                        }
                        //if there is a token of white in front which surrounds them , we put the white token
                        //in the other side of line(back) :
                        if (flag1 == 1 && flag2 == 0) {
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xBackward);
                            temp.add(yLower);
                            availablePlaces.add(temp);
                        }
                        //if there is a token of white in back which surrounds them , we put the white token
                        //in the other side of line(front) :
                        if (flag1 == 0 && flag2 == 1) {
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xForward);
                            temp.add(yUpper);
                            availablePlaces.add(temp);
                        }

                    }
                }
            }
        }

        return availablePlaces;
    }

    /**
     * Gets the color value (unicode) of the token in the specified place with given x and y.
     *
     * @param x x of the token
     * @param y y of the token
     * @return unicode ( color value ) number
     */
    public int getTokenColorValue(int x, int y) {
        for (Token token : allTokens) {
            if (token.getX() == x && token.getY() == y) {
                return token.getColorValue();
            }
        }
        //we don't have any token in that place :
        return 0;
    }

    /**
     * Checks if a token exist in the specified place with given x and y .
     *
     * @param x x of the place
     * @param y y of the place
     * @return if a token is in there , true and if not ,false .
     */
    public boolean checkTokenExistence(int x, int y) {
        for (Token token : allTokens) {
            if (token.getX() == x && token.getY() == y) {
                return true;
            }
        }
        //if the token doesn't exist :
        return false;
    }

    /**
     * Puts a new token to our board which checking all of the conditions . If the token is not repeated , and
     * the place is in legal places found in findAvailablePlaces method and if the x and y are in the limitations
     * of the map and not beyond them.
     *
     * @param colorValue      color value number of the new token
     * @param y               y value of the token
     * @param c               character chosen for x
     * @param turn            player turn
     * @param availablePlaces available and legal places which the token can be put in
     */
    public void addNewToken(int colorValue, int y, char c, int turn, ArrayList<ArrayList<Integer>> availablePlaces) {
        int flag1 = 0;
        int flag2 = 0;
        int x = (int) c;
        x -= 64;
        Token newToken = new Token(colorValue, x, y);
        //check if the token already exist :
        for (Token token : allTokens) {
            if (newToken.equals(token)) {
                flag1 = 1;//it means we already have a token exactly like this one in our list .
                break;
            }
        }
        //check if the token has a legal place or not :
        for (ArrayList<Integer> it : availablePlaces) {
            if (it.get(0) == x && it.get(1) == y) {
                flag2 = 1;
                break;
            }
        }
        //if the token is not repeated :
        if (flag1 == 0) {
            // if the place is included in available places list:
            if (flag2 == 1) {
                if (newToken.getX() < 9 && newToken.getX() > 0) {
                    if (newToken.getY() < 9 && newToken.getY() > 0) {
                        //if the place player has chosen is legal :
                        allTokens.add(newToken);
                        if (turn == 1)
                            player1.addToken(newToken);
                        else if (turn == 2)
                            player2.addToken(newToken);
                    }
                }
            }
        }
        if (flag2 == 0)
            System.out.println("Your chosen place is not legal !");
    }

    /**
     * This is a method for updating our board ( map ) , means when we put a new token we give the
     * x and y of it to this method , and it checks if the new token surrounds the opposite color
     * tokens with tokens of himself , player can flip the opposite colors surrounded in between .
     * i and j are the parameters which specify the line moving in , this method should be checked
     * for i=1 and j=0 which is the straight line , i=0 and j=1 which is perpendicular line , i=1 and j=1
     * for diagonal line which starts from left down and ends in right up and at last i=-1 and j=1
     * which is for diagonal line which starts from right down and ends in left up .
     *
     * @param turn player turn
     * @param i    parameter for specifying the line we are checking the tokens
     * @param j    parameter for specifying the line we are checking the tokens
     * @param x    x of new token which was put
     * @param y    y of new token which was put
     */

    public void updateBoard(int turn, int i, int j, int x, int y) {
        int flag = 0, ctr = 0, blackTokensBetween = 0, whiteTokensBetween = 0;
        //if it's white player turn:
        if (turn == 1) {
            for (Token token : allTokens) {
                blackTokensBetween = 0;
                ctr = 0;
                flag = 0;
                int yUpper = token.getY();
                int yLower = token.getY();
                int xBackward = token.getX();
                int xForward = token.getX();
                //finds the token :
                if (token.getX() == x && token.getY() == y) {
                    //black between saves the x and y of surrounded black tokens
                    ArrayList<ArrayList<Integer>> blackBetween = new ArrayList<>();
                    //while there is a token front of it
                    while (checkTokenExistence(xForward, yUpper)) {
                        //if the token is black:
                        if (getTokenColorValue(xForward, yUpper) == player2.getColorValue()) {
                            //number of black tokens between:
                            blackTokensBetween++;
                            //puts it in black between to be flipped later :
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xForward);
                            temp.add(yUpper);
                            blackBetween.add(temp);
                        } else if (getTokenColorValue(xForward, yUpper) == player1.getColorValue()) {
                            //first time checking, the color is white and we want to find the white token after the
                            //first one , so we check if it's not the first time the token is white :
                            if (ctr > 0)
                                flag = 1;
                        }
                        xForward += i;
                        yUpper -= j;
                        ctr++;
                    }
                    //if there is a white token in front and black tokens are surrounded :
                    if (flag == 1 && blackTokensBetween > 0) {
                        for (ArrayList<Integer> it : blackBetween) {
                            flip(it.get(0), it.get(1));
                        }
                    }
                    //Algorithm is exactly like upper lines but we are checking for the back of token :
                    blackTokensBetween = 0;
                    ctr = 0;
                    flag = 0;
                    blackBetween.clear();
                    //while there is a token back of it
                    while (checkTokenExistence(xBackward, yLower)) {
                        //if the token is black:
                        if (getTokenColorValue(xBackward, yLower) == player2.getColorValue()) {
                            //number of black tokens between:
                            blackTokensBetween++;
                            //puts it in black between to be flipped later :
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xBackward);
                            temp.add(yLower);
                            blackBetween.add(temp);
                        } else if (getTokenColorValue(xBackward, yLower) == player1.getColorValue()) {
                            //first time checking, the color is white and we want to find the white token before the
                            //first one , so we check if it's not the first time the token is white :
                            if (ctr > 0)
                                flag = 1;
                        }
                        xBackward -= i;
                        yLower += j;
                        ctr++;
                    }
                    //if there is a white token in back and black tokens are surrounded :
                    if (flag == 1 && blackTokensBetween > 0) {
                        for (ArrayList<Integer> it : blackBetween) {
                            flip(it.get(0), it.get(1));
                        }
                    }
                }
            }
        }
        //if its black player turn :
        if (turn == 2) {
            //finds the token :
            for (Token token : allTokens) {
                whiteTokensBetween = 0;
                ctr = 0;
                flag = 0;
                int yUpper = token.getY();
                int yLower = token.getY();
                int xBackward = token.getX();
                int xForward = token.getX();
                if (token.getX() == x && token.getY() == y) {
                    //x and y of the surrounded white tokens in between :
                    ArrayList<ArrayList<Integer>> whiteBetween = new ArrayList<>();
                    //while there is a token front of it
                    while (checkTokenExistence(xForward, yUpper)) {
                        //if the token is white:
                        if (getTokenColorValue(xForward, yUpper) == player1.getColorValue()) {
                            //number of white tokens in between:
                            whiteTokensBetween++;
                            //puts it in white between to be flipped later :
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xForward);
                            temp.add(yUpper);
                            whiteBetween.add(temp);
                        } else if (getTokenColorValue(xForward, yUpper) == player2.getColorValue()) {
                            //first time checking, the color is white and we want to find the black token after the
                            //first one , so we check if it's not the first time the token is black :
                            if (ctr > 0)
                                flag = 1;
                        }
                        xForward += i;
                        yUpper -= j;
                        ctr++;
                    }
                    //if there is a black token in front and white tokens are surrounded :
                    if (flag == 1 && whiteTokensBetween > 0) {
                        for (ArrayList<Integer> it : whiteBetween) {
                            flip(it.get(0), it.get(1));
                        }
                    }
                    //Algorithm is exactly like upper lines but we are checking for the back of token :
                    whiteTokensBetween = 0;
                    ctr = 0;
                    flag = 0;
                    whiteBetween.clear();
                    //while there is a token front of it
                    while (checkTokenExistence((xBackward), yLower)) {
                        //if the token is white:
                        if (getTokenColorValue(xBackward, yLower) == player1.getColorValue()) {
                            //number of white tokens in between:
                            whiteTokensBetween++;
                            //puts it in white between to be flipped later :
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(xBackward);
                            temp.add(yLower);
                            whiteBetween.add(temp);
                        } else if (getTokenColorValue(xBackward, yLower) == player2.getColorValue()) {
                            //first time checking, the color is white and we want to find the black before the
                            //first one , so we check if it's not the first time the token is black :
                            if (ctr > 0)
                                flag = 1;
                        }
                        xBackward -= i;
                        yLower += j;
                        ctr++;
                    }
                    //if there is a black token in back and white tokens are surrounded :
                    if (flag == 1 && whiteTokensBetween > 0) {
                        for (ArrayList<Integer> it : whiteBetween) {
                            flip(it.get(0), it.get(1));
                        }
                    }
                }
            }
        }
    }

    /**
     * Flips the token and changes it color .
     *
     * @param x x of the token
     * @param y y of the token
     */
    public void flip(int x, int y) {
        for (Token t : allTokens) {
            if (t.getX() == x && t.getY() == y) {
                t.changeColor();
            }
        }
    }

    /**
     * Checks if the game has ended or not , by checking if the map is not ful and
     * if there is still available places for new token to be put in .
     *
     * @return true , if the game hasn't finished and false , if the game has finished .
     */
    public boolean checkEndGame() {
        ArrayList<ArrayList<Integer>> availablePlaces1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> availablePlaces2 = new ArrayList<>();

        availablePlaces1 = this.findAvailablePlaces(1);
        availablePlaces2 = this.findAvailablePlaces(2);

        if (allTokens.size() != 64) {
            if (availablePlaces1.size() != 0 && availablePlaces2.size() != 0)
                return true;
            else
                return false;
        } else
            return false;

    }

    /**
     * Sets the turn parameter .
     *
     * @param turn turn to be set
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Gets player 1
     *
     * @return player 1
     */

    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets player 2
     *
     * @return player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Prints the menu for when the game is started .User can choose between
     * multi player game and game between user and computer .
     */
    public void printMenu() {
        System.out.println("Welcome to Othello Game !");
        System.out.println();
        System.out.println("choose one of the modes : (Just type the index number)");
        System.out.println("1. player vs player ");
        System.out.println("2. player vs computer ");

    }

    /**
     * When playing with the computer , for computer's turn it generates a random x .
     *
     * @return random x generated
     */
    public char generateRandomX() {
        Random random = new Random();
        char c = (char) (random.nextInt(8) + 65);
        return c;
    }

    /**
     * When playing with the computer , for computer's turn it generates a random y .
     *
     * @return random y generated
     */
    public int generateRandomY() {
        Random random = new Random();
        int y = random.nextInt(8) + 1;
        return y;
    }

    /**
     * Checks if the given place is in available places or not .
     *
     * @param x               x of the given place
     * @param y               y of the given place
     * @param availablePlaces available places
     * @return if it exits in the available places , it returns true and if not , it returns false.
     */

    public boolean checkRandomPlace(int x, int y, ArrayList<ArrayList<Integer>> availablePlaces) {
        for (ArrayList<Integer> it : availablePlaces) {
            if (it.get(0) == x && it.get(1) == y)
                return true;
        }
        return false;

    }

}
