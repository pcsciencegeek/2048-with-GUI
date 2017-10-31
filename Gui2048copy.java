//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// GUI Driver for 2048                                              //
//                                                                  //
// Author:  Drew Mills             		                              //
// Date:    03/02/17                                                //
//------------------------------------------------------------------//


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Gui2048copy extends Application{
  private String outputBoard; // The filename for where to save the Board
  private Board board; // The 2048 Game Board

  private static final int TILE_WIDTH = 106;

  // Low value tiles (2,4,8,etc)
  private static final int TEXT_SIZE_LOW = 55;
  // Mid value tiles (128, 256, 512)
  private static final int TEXT_SIZE_MID = 45;
  // High value tiles (1024, 2048, Higher)
  private static final int TEXT_SIZE_HIGH = 35;

  // Fill colors for each of the Tile values
  private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
  private static final Color COLOR_2 = Color.rgb(238, 228, 218);
  private static final Color COLOR_4 = Color.rgb(237, 224, 200);
  private static final Color COLOR_8 = Color.rgb(242, 177, 121);
  private static final Color COLOR_16 = Color.rgb(245, 149, 99);
  private static final Color COLOR_32 = Color.rgb(246, 124, 95);
  private static final Color COLOR_64 = Color.rgb(246, 94, 59);
  private static final Color COLOR_128 = Color.rgb(237, 207, 114);
  private static final Color COLOR_256 = Color.rgb(237, 204, 97);
  private static final Color COLOR_512 = Color.rgb(237, 200, 80);
  private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
  private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
  private static final Color COLOR_OTHER = Color.BLACK;
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

  // For tiles >= 8
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
  // For tiles < 8
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);

  private GridPane pane;

  /******************** Add your own Instance Variables here ***************/

  private Text score;
  private int sTotal;
  private int[][] newGrid;
  private Rectangle square;
  private Text value;
  private Scene scene;
  private Text title;

  // Lights, camera, action! This sets the Stage
  @Override
  public void start(Stage primaryStage){

    // Process Arguments and Initialize the Game Board
    processArgs(getParameters().getRaw().toArray(new String[0]));
    newGrid = board.getGrid();
    sTotal = board.getScore();

    // Create a Gridpane
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
    // Set the spacing between the Tiles
    pane.setHgap(15);
    pane.setVgap(15);

    // create a 2d array of Rectangle objects
    Rectangle[][] rectangleGrid = new Rectangle[newGrid.length][newGrid.length];
    Text[][] numGrid = new Text[newGrid.length][newGrid.length];

    //create a rectangle and value and set its properites
     for (int i=0; i<newGrid.length;i++){
      for(int j=0; j<newGrid[i].length;j++){
        //System.out.println("newGrid is " + newGrid[i][j]);
        //System.out.println(Arrays.deepToString(newGrid));
        square = new Rectangle();
        square.setWidth(100);
        square.setHeight(100);
        if(newGrid[i][j] == 0){
          square.setFill(COLOR_EMPTY);
        }
        if(newGrid[i][j] == 2){
          square.setFill(COLOR_2);
        }
        if(newGrid[i][j] == 4){
          square.setFill(COLOR_4);
        }
        if(newGrid[i][j] == 8){
          square.setFill(COLOR_8);
        }
        if(newGrid[i][j] == 16){
          square.setFill(COLOR_16);
        }
        if(newGrid[i][j] == 32){
          square.setFill(COLOR_32);
        }
        if(newGrid[i][j] == 64){
          square.setFill(COLOR_64);
        }
        if(newGrid[i][j] == 128){
          square.setFill(COLOR_128);
        }
        if(newGrid[i][j] == 256){
          square.setFill(COLOR_256);
        }
        if(newGrid[i][j] == 512){
          square.setFill(COLOR_512);
        }
        if(newGrid[i][j] == 1024){
          square.setFill(COLOR_1024);
        }
        if(newGrid[i][j] == 2048){
          square.setFill(COLOR_2048);
        }
        if(newGrid[i][j] > 2048){
          square.setFill(COLOR_OTHER);
        }
        rectangleGrid[i][j] = square;

        //////////////////// Value ///////////////
        value = new Text(Integer.toString(newGrid[i][j]));
        value.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
        if(newGrid[i][j] == 0){
          value.setVisible(false);
        }
        if(newGrid[i][j] >= 8){
          value.setFill(COLOR_VALUE_LIGHT);
        }
        if(newGrid[i][j] < 8){
          value.setFill(COLOR_VALUE_DARK);
        }
        numGrid[i][j] = value;

        // makes the title
        Text title = new Text("2048");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD,
        FontPosture.ITALIC, 50));
        //title.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        //title.setSpacing(50);


        //makes the score, score is text and a var. var gets updated
        score = new Text("Score: " + sTotal);
        score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        // makes the title
        title = new Text("2048");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD,
        FontPosture.ITALIC, 50));


        //makes the score, score is text and a var. var gets updated
        score = new Text("Score: " + sTotal);
        score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        // adds nodes to the pane
        pane.add(title,0,0,1,1);
        pane.add(score,2,0,1,1);
        pane.add(rectangleGrid[i][j],j,i+2);
        pane.add(numGrid[i][j],j,i+2);
        GridPane.setHalignment(value, HPos.CENTER);
        GridPane.setHalignment(score,HPos.CENTER);

       }
     }


    // Create a scene and place it in the stage
    scene = new Scene(pane);
    scene.setOnKeyPressed(new myKeyHandler()); // when key is pressed
    primaryStage.setTitle("Gui2048"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /******************** Add your own Instance Methods Here ****************/
  private class myKeyHandler implements EventHandler<KeyEvent>{
  	@Override
  	public void handle(KeyEvent event){
      /* KeyEvent Processing Code Goes Here */

      //if s key is pressed
      if (event.getCode() == KeyCode.S && !board.isGameOver()) {
        try {
          board.saveBoard(outputBoard);

        }
        catch (IOException e) {
          System.out.println("saveBoard threw an Exception");
        }
      //  board.saveBoard(outputBoard);
      System.out.println("Saving Board to " + outputBoard);
      }

        else if(event.getCode() == KeyCode.UP && !board.isGameOver()) {
          if(board.move(Direction.UP)){
            System.out.println("moving "+ event.getCode());
            board.addRandomTile();
          }
        }
        else if(event.getCode() == KeyCode.DOWN && !board.isGameOver()) {
          if(board.move(Direction.DOWN)){
            System.out.println("moving "+ event.getCode());
            board.addRandomTile();
          }
        }
        else if(event.getCode() == KeyCode.LEFT && !board.isGameOver()) {
          if(board.move(Direction.LEFT)){
            System.out.println("moving "+ event.getCode());
            board.addRandomTile();
          }
        }
        else if(event.getCode() == KeyCode.RIGHT && !board.isGameOver()) {
          if(board.move(Direction.RIGHT)){
            System.out.println("moving "+ event.getCode());
            board.addRandomTile();
          }
        }
        sTotal = board.getScore(); //update score
        // update GUI
        for (int i=0; i<newGrid.length;i++){
         for(int j=0; j<newGrid[i].length;j++){

         }
       }
      //if the game is over
      if(board.isGameOver()){
        System.out.println("Game is over!");
        //mainPane.getChildren().addAll(setTop(), setCenter());


      }
    }
  }

  private Pane getOverlay() {
   StackPane p = new StackPane();
   Rectangle rect = new Rectangle();
           rect.setHeight(newGrid.length);
           rect.setWidth(newGrid.length);
           rect.setFill(Color.TRANSPARENT);

  Text over =new Text("Game Over!");
           over.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
           over.setFill(Color.RED);
    p.getChildren().addAll(rect, over);
    return p;
}



  /*************************** DO NOT EDIT BELOW **************************/

  // The method used to process the command line arguments
  private void processArgs(String[] args){

    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0){
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments
    for(int i = 0; i < args.length; i += 2){
      if(args[i].equals("-i")){
        // We are processing the argument that specifies
        // the input file to be used to set the board
        inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o")){
        // We are processing the argument that specifies
        // the output file to be used to save the board
        outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s")){
        // We are processing the argument that specifies
        // the size of the Board
        boardSize = Integer.parseInt(args[i + 1]);
      }
      else{
       // Incorrect Argument
        printUsage();
        System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null){
      outputBoard = "2048.board";
    }
    // Set the default Board size if none specified or less than 2
    if(boardSize < 2){
      boardSize = 4;
    }
    // Initialize the Game Board
    try{
      if(inputBoard != null)
        board = new Board(new Random(), inputBoard);
      else
        board = new Board(new Random(), boardSize);
    }
    catch (Exception e){
      System.out.println(e.getClass().getName() +
       " was thrown while creating a " + "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
        "Constructor is broken or the file isn't " + "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message
  private static void printUsage(){
    System.out.println("Gui2048");
    System.out.println("Usage:  Gui2048 [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the "+
      "form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a 2048 board that " +
      "should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be " +
     "used to save the 2048 board");
    System.out.println("                If none specified then the " +
      "default \"2048.board\" file will be used");
    System.out.println("  -s [size]  -> Specifies the size of the 2048 " +
      "board if an input file hasn't been");
    System.out.println("                specified.  If both -s and -i " +
      "are used, then the size of the board");
    System.out.println("                will be determined by the input" +
      " file. The default size is 4.");
  }
}
