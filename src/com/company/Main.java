package com.company;


import java.util.ArrayList;
import java.util.Scanner;

//GAME STARTS WITH BLACK PLAYER .
/**
 * This class if for testing and running the game . A new game is made in it
 and checks which kind of game the player wants it to be : Player vs player
 or Player vs computer .
 *
 */
public class Main {


    public static void main(String[] args) {
        String white = "25EF";
        String black = "25CF";
        int whiteValue = Integer.parseInt(white, 16);
        int blackValue = Integer.parseInt(black, 16);

        PlayingSystem myGame = new PlayingSystem(whiteValue, blackValue);
        Scanner sc = new Scanner(System.in);
        myGame.printMenu();
        int input = sc.nextInt();
        //Player vs player game :
        if (input == 1) {
            myGame.setTurn(1);
            System.out.println();
            myGame.printBoard();
            while (myGame.checkEndGame()) {
                ArrayList<ArrayList<Integer>> availablePlaces = new ArrayList<>();

                System.out.println();
                int turn = myGame.playTurn();
                //prints the turn every time :
                /*Because of the rules of the game , game starts with black player , and black player is my
                * second player , so when turn is 2 , the game starts but I should print player 1 , although the color
                * is for player 2 . */
                if(turn==2)
                    System.out.println("player 1 :");
                else if(turn==1)
                    System.out.println("player 2 :");

                //find available and legal places :
                availablePlaces = myGame.findAvailablePlaces(turn);


                //gets x and y f new token to be put in :
                int y = sc.nextInt();
                String str = sc.next();
                char c = str.charAt(0);
                int x = (int) c;
                x -= 64;

                //if there is no available moves , prints pass :
                if (availablePlaces.size() == 0) {
                    System.out.println("pass");
                    continue;
                }

                int colorValue = 0;
                if (turn == 1)
                    colorValue = whiteValue;
                else if (turn == 2)
                    colorValue = blackValue;

                myGame.addNewToken(colorValue, y, c, turn, availablePlaces);
                //updates the game and flips surrounded tokens :
                System.out.println();
                myGame.updateBoard(turn, 1, 0, x, y);
                myGame.updateBoard(turn, 0, 1, x, y);
                myGame.updateBoard(turn, 1, 1, x, y);
                myGame.updateBoard(turn, -1, 1, x, y);
                System.out.println();
                myGame.printBoard();
            }
            //printing the result :
            System.out.println("Player 1 : " + myGame.getPlayer1().getNumberOfTokens());
            System.out.println("Player 2 : " + myGame.getPlayer2().getNumberOfTokens());
            if (myGame.getPlayer1().getNumberOfTokens() > myGame.getPlayer2().getNumberOfTokens())
                System.out.println("Winner is : player 1 !");
            else if (myGame.getPlayer1().getNumberOfTokens() < myGame.getPlayer2().getNumberOfTokens())
                System.out.println("Winner is : player 2 !");
            else
                System.out.println("It's a tie !");
        }
        //player vs computer game :
        if (input == 2) {
            myGame.setTurn(1);
            int colorValue;
            System.out.println();
            myGame.printBoard();
            System.out.println();
            while (myGame.checkEndGame()) {
                ArrayList<ArrayList<Integer>> availablePlaces1 = new ArrayList<>();
                int turn = myGame.playTurn();
                //always turn 1 is for player and turn 2 is for computer
                if (turn == 2) {
                    colorValue = blackValue;
                    System.out.println("Your turn :");
                    //gets x and y from player :
                    int y = sc.nextInt();
                    String str = sc.next();
                    char c = str.charAt(0);
                    int x;
                    availablePlaces1 = myGame.findAvailablePlaces(turn);
                    if (availablePlaces1.size() == 0) {
                        System.out.println("pass");
                        break;
                    }
                    myGame.addNewToken(colorValue, y, c, turn, availablePlaces1);
                    x = (int) c;
                    x -= 64;

                    //updates board and flip surrounded tokens :
                    myGame.updateBoard(turn, 1, 0, x, y);
                    myGame.updateBoard(turn, 0, 1, x, y);
                    myGame.updateBoard(turn, 1, 1, x, y);
                    myGame.updateBoard(turn, -1, 1, x, y);
                    myGame.printBoard();
                    System.out.println();
                }
                if (turn == 1) {
                    ArrayList<ArrayList<Integer>> availablePlaces2 = new ArrayList<>();
                    colorValue = whiteValue;
                    System.out.println("Computer turn :");
                    char compXchar = 'a';
                    int compY = 0;
                    availablePlaces2 = myGame.findAvailablePlaces(turn);
                    if (availablePlaces2.size() == 0) {
                        System.out.println("pass");
                        break;
                    }

                    //generates random x and y for computer :
                    compXchar = myGame.generateRandomX();
                    compY = myGame.generateRandomY();
                    int compX = (int) compXchar;
                    compX -= 64;
                    //checks if the random x and y are legal and if not generate till it is legal and in available places :
                    while (true) {
                        if (myGame.checkRandomPlace(compX, compY, availablePlaces2)) {
                            break;
                        }
                        compXchar = myGame.generateRandomX();
                        compY = myGame.generateRandomY();
                        compX = (int) compXchar;
                        compX -= 64;
                    }


                    myGame.addNewToken(colorValue, compY, compXchar, turn, availablePlaces2);

                    myGame.updateBoard(turn, 1, 0, compX, compY);
                    myGame.updateBoard(turn, 0, 1, compX, compY);
                    myGame.updateBoard(turn, 1, 1, compX, compY);
                    myGame.updateBoard(turn, -1, 1, compX, compY);
                    myGame.printBoard();
                    System.out.println();
                }

            }
            //prints the results:
            System.out.println("You : " + myGame.getPlayer1().getNumberOfTokens());
            System.out.println("Computer : " + myGame.getPlayer2().getNumberOfTokens());
            if (myGame.getPlayer1().getNumberOfTokens() > myGame.getPlayer2().getNumberOfTokens())
                System.out.println("Winner is : You !");
            else if (myGame.getPlayer1().getNumberOfTokens() < myGame.getPlayer2().getNumberOfTokens())
                System.out.println("Winner is : Computer !");
            else
                System.out.println("It's a tie !");
        }


    }
}
