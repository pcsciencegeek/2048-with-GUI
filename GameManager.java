//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  Drew Mills                	                            //
// Date:    01/25/17                                                //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;


// GameManager class Manages the the game by saving
// and loading the borad
public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting

    // TODO PSA3
    // GameManager Constructor
    // Generate new game
    public GameManager(String outputBoard, int boardSize, Random random) {
      //System.out.println("Generating a New Board");
      this.board = new Board(random, boardSize);
      outputFileName = outputBoard;
    }

    // TODO PSA3
    // GameManager Constructor
    // Load a saved game
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        System.out.println("Loading Board from " + inputBoard);
        this.board = new Board(random, inputBoard);
        outputFileName = outputBoard;
    }

    // TODO PSA3
    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      k - Move up
    //      j - Move Down
    //      h - Move Left
    //      l - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
    public void play() throws IOException {
      printControls();
      //System.out.println(toString);
      System.out.println(board);
      //char choice = (char) System.in.read();
      Scanner s = new Scanner(System.in);
      String choice;
      do{
        choice = s.nextLine();
        if(choice.equals("k")) {
          if(board.move(Direction.UP)){
            board.addRandomTile();
          }
        }
        else if(choice.equals("j")) {
          if(board.move(Direction.DOWN)){
            board.addRandomTile();
          }
        }
        else if(choice.equals("h")) {
          if(board.move(Direction.LEFT)){
            board.addRandomTile();
          }
        }
        else if(choice.equals("l")) {
          if(board.move(Direction.RIGHT)){
            board.addRandomTile();
          }
        }
        else{
          printControls();
        }
        System.out.println(board);
      } while((!choice.equals("q")) && (!board.isGameOver()));
      //save game
      board.saveBoard(outputFileName);
      System.out.println("Game is over!");
      return;
    }

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
