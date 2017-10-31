import java.util.*;
import java.io.*;
import java.util.*;

//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author: Drew Mills                                              //
// Date : 02/04/17                                                  //
//------------------------------------------------------------------//

/**
 * Sample Board
 * <p/>
 * 0 1 2 3
 * 0 - - - -
 * 1 - - - -
 * 2 - - - -
 * 3 - - - -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column : grid[row][column]
 */

public class Board {
  public final int NUM_START_TILES = 2;
  public final int TWO_PROBABILITY = 90;
  public final int GRID_SIZE;


  private final Random random;
  private int[][] grid;
  private int score;

    // TODO PSA3
    // Constructs a fresh board with random tiles
  public Board(Random random, int boardSize) {
    this.random = random; // FIXed
    GRID_SIZE = boardSize; // FIXed
    score = 0; //declare score
    //initialize a 2D array
    grid = new int[GRID_SIZE][GRID_SIZE];
    String string = "";
    // add a random tile
    for(int k=0; k<NUM_START_TILES; k++){
      addRandomTile();
    }
  }

  // TODO PSA3
  // Construct a board based off of an input file
  public Board(Random random, String inputBoard) throws IOException {
    this.random = random; // FIXed
    //open a new file using its path
    File input = new File( inputBoard );
    // reads in file
    Scanner scanIn = new Scanner(input);
    // reads in gird size and score
    GRID_SIZE = scanIn.nextInt();
    score = scanIn.nextInt();
    //initialize a 2D array
    grid = new int[GRID_SIZE][GRID_SIZE];
    //loops through and finds values
    while(scanIn.hasNext()){
      for(int i=0; i<grid.length; i++){
        for(int j=0; j<grid[i].length; j++){
          grid[i][j] = scanIn.nextInt();
        }
      }
    }
  }

  // TODO PSA3
  // Saves the current board to a file
  public void saveBoard(String outputBoard) throws IOException {
    File output = new File( outputBoard );
    PrintWriter printWriter = new PrintWriter (output);
    printWriter.println (GRID_SIZE);
    printWriter.println (score);
    for(int i=0; i<grid.length; i++){
      for(int j=0; j<grid[i].length; j++){
        printWriter.print(grid[i][j] + " ");
      }
      printWriter.println(""); // adds a space
    }
    printWriter.close ();
  }

  // TODO PSA3
  // Adds a random tile (of value 2 or 4) to a
  // random empty space on the board
  public void addRandomTile() {
    int value = 100;
    int count = 0;
    int totalAvailable = -1;
    //for all tiles on the board, count the number of available tiles
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        //if value in location is 0, add to count
        if (grid[i][j]==0){
          count++;
        }
      }
    }
    if(count == 0){
      return;
    }
    //get a random int called location between 0 and count-1
    int location = random.nextInt(count);
    //get a random int called value between 0 and 99
    value = random.nextInt(value);
    //Walk the board keeping count of the empty spaces found
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){

        //if value in location is 0, add to count
        if (grid[i][j] == 0){
          totalAvailable++;

          //System.out.println("location is " + location);
          if(location == totalAvailable){
            if(value < TWO_PROBABILITY){
              grid[i][j] = 2;
            }
            else{
              grid[i][j] = 4;
            }
          }
        } // changed
      }
    }
  }
  // TODO PSA3
  // Flip the board horizontally or vertically,
  // Rotate the board by 90 degrees clockwise or 90 degrees counter-clockwise.
  public void flip(int change) {
    if(change >= 6 || change <= 0 ){
      System.out.println("change was equal to value between 1 and 4");
      return;
    }
    //Flip horizontally
    if(change == 1){
      for (int i=0; i<grid.length;i++){
        for(int j=0; j<grid[i].length/2;j++){
          int tmp = grid[i][j];
          grid[i][j] = grid[i][grid[i].length-j-1];
          grid[i][grid[i].length-j-1] = tmp;
        }
      }
    }
    //Flip vertically
    if(change == 2){
      for (int i=0; i<grid.length/2;i++){
        for(int j=0; j<grid[i].length;j++){
          int tmp = grid[i][j];
          grid[i][j] = grid[grid.length-i-1][j];
          grid[grid.length-i-1][j] = tmp;
        }
      }
    }
    //Flip clockwise
    //Rotate along diagonal
    if(change == 3){
      for (int i=0; i<grid.length;i++){
        for(int j=0; j<grid.length-1-i;j++){
          //swap
          int temp = grid[i][j];
          grid[i][j] = grid[grid.length -1 - j][grid.length-1-i];
          grid[grid.length -1 - j][grid.length-1-i] = temp;
        }
      }
      //Flip horizontally
      for (int i=0; i<grid.length/2;i++){
        for(int j=0; j<grid[i].length;j++){
          //swap
          int tmp = grid[i][j];
          grid[i][j] = grid[grid.length-i-1][j];
          grid[grid.length-i-1][j] = tmp;
        }
      }
    }
    //Flip counter-clockwise
    //Rotate along diagonal
    if(change == 4){
      for (int i=0; i<grid.length;i++){
        for(int j=0; j<grid.length-1-i;j++){
          //swap
          int temp = grid[i][j];
          grid[i][j] = grid[grid.length -1 - j][grid.length-1-i];
          grid[grid.length -1 - j][grid.length-1-i] = temp;
        }
      }
      //Flip vertically
      for (int i=0; i<grid.length;i++){
        for(int j=0; j<grid[i].length/2;j++){
          //swap
          int tmp = grid[i][j];
          grid[i][j] = grid[i][grid[i].length-j-1];
          grid[i][grid[i].length-j-1] = tmp;
        }
      }
      //Flip Diagonally
      if(change == 5){
        for (int i=0; i<grid.length;i++){
          for(int j=0; j<grid.length-1-i;j++){
            //swap
            int temp = grid[i][j];
            grid[i][j] = grid[grid.length -1 - j][grid.length-1-i];
            grid[grid.length -1 - j][grid.length-1-i] = temp;
          }
        }
      }
    }
  }

  //Complete this method ONLY if you want to attempt at getting the extra credit
  //Returns true if the file to be read is in the correct format, else return
  //false
  public static boolean isInputFileCorrectFormat(String inputFile) {
  //The try and catch block are used to handle any exceptions
  //Do not worry about the details, just write all your conditions inside the
  //try block
    try {
      //write your code to check for all conditions and return true if it satisfies
      //all conditions else return false
      return true;
    }
    catch (Exception e)
    {
      return false;
    }
  }

  // Need to change this for PSA4
  //helper method to determine if can move left
  private boolean canMoveLeft(){
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        //check if in bounds
        if (0 <= j-1){
          //if value in location is 0, or same true
          //(grid[i][j-1]==0 || grid[i][j-1]==grid[i][j])
          if((grid[i][j-1]==grid[i][j] && grid[i][j] !=0) || (grid[i][j] !=0 && grid[i][j-1]==0)){
            return true;
          }
        }
      }
    }
    return false;
  }
  //helper method to determine if can move right
  private boolean canMoveRight(){
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        //check if in bounds, if inbounds
        if(j+1 < grid.length){
          //if value in location is 0, or same true
          if ((grid[i][j+1]==grid[i][j] && grid[i][j] !=0) || (grid[i][j] !=0 && grid[i][j+1]==0)){
            return true;
          }
        }
      }
    }
    return false;
  }
  //helper method to determine if can move up
  private boolean canMoveUp(){
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        if(0 <= i-1){
          //if value in location is 0, or same true
          //(grid[i-1][j]==0 || grid[i-1][j]==grid[i][j])
          if ((grid[i-1][j]==grid[i][j] && grid[i][j] !=0) || (grid[i][j] !=0 && grid[i-1][j]==0)){
            return true;
          }
        }
      }
    }
    return false;
  }
  //helper method to determine if can move down
  private boolean canMoveDown(){
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        if(i+1 < grid.length){
          //if value in location is 0, or same true
          //(grid[i+1][j]==0 || grid[i+1][j]==grid[i][j])
          if ((grid[i+1][j]==grid[i][j] && grid[i][j] !=0) || (grid[i][j] !=0 && grid[i+1][j]==0)){
            return true;
          }
        }
      }
    }
    return false;
  }
  // Checks a move Operation
  public boolean canMove(Direction direction){
    //canMove up?
    if(direction == Direction.UP){
      if(canMoveUp()){
        //System.out.println("you can move UP");
        return true;
      }
    }
    //canMove left?
    if(direction == Direction.LEFT){
      if(canMoveLeft()){
        //System.out.println("you can move Left");
        return true;
      }
    }
    //canMove Right?
    if(direction == Direction.RIGHT){
      if(canMoveRight()){
        //System.out.println("you can move Right");
        return true;
      }
    }
    //canMove Down?
    if(direction == Direction.DOWN){
      if(canMoveDown()){
        //System.out.println("you can move Down");
        return true;
      }
    }
    return false;
  }

  //helper method to move Right
  private boolean MoveRight(){
    ArrayList<Integer> row = new ArrayList<Integer>();
    // For a 4x4 matrix length is 4
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        //captures on zero tiles and store in arrayList
        if(grid[i][j] != 0){
          row.add(grid[i][j]);
          //System.out.println("you captured a "+ grid[i][j]);
          //System.out.println("Arraylist is  "+ row.toString());
        }
      }
      //merges tiles if possible
      for(int count = row.size()-1; count>0 ;count--){
        if(row.get(count) == row.get(count-1)){
          int merged = row.get(count) + row.get(count);
            score += merged;
            //System.out.println("merged is "+ merged);
            row.set(count, merged);
            row.remove(count-1);

        }

      }
      //set row to zero
      //System.out.println("after merges Arraylist is  "+ row.toString());
      if (!row.isEmpty()) {
        //make row all zeros
        for (int count=grid.length-1; count>=0; count--) {
          grid[i][count] = 0;
          //System.out.println("grid count is "+ grid[i][count]);
        }
      }
      //add back into row
      int countB = grid.length-1;
      for(int countAl = row.size()-1; countAl>=0; countAl --, countB--){
        grid[i][countB] = row.get(countAl);
      }
      // clear current Arraylist, prep for next row
      row.clear();
    }
    return true;
  }
  // move left helper
  private boolean MoveLeft(){
    ArrayList<Integer> row = new ArrayList<Integer>();
    // For a 4x4 matrix length is 4
    for (int i=0; i<grid.length;i++){
      for(int j=0; j<grid[i].length;j++){
        //captures on zero tiles and store in arrayList
        if(grid[i][j] != 0){
          row.add(grid[i][j]);
          //System.out.println("A "+grid[i][j] + " will be added to Arraylist");
          //System.out.println("The ArrayList is  "+ row.toString());
        }
      }
      //merges tiles if possible
      for(int count = 0; count < row.size()-1; count++){
        if(row.get(count) == row.get(count+1)){
          int merged = row.get(count) + row.get(count);
          //update score
          score += merged;
          row.set(count, merged);
          row.remove(count+1);
        }
      }
      //Goal: set row to zero
      //System.out.println("After merges Arraylist is  "+ row.toString());
      // if row is not zero already
      if (!row.isEmpty()) {
        //make row all zeros
        for (int count=0; count<=grid.length-1; count++) {
          grid[i][count] = 0;
          //System.out.println("row "+ i +" is set to all zeros ");
        }
      }
      //add back into row
      int countB = 0;
      for(int countAl=0; countAl < row.size() ; countAl ++, countB++){
        grid[i][countB] = row.get(countAl);
      }
      // clear current Arraylist, prep for next row
      row.clear();
    }
    return true;
  }
  // move up helper
  private boolean MoveUp(){
    ArrayList<Integer> row = new ArrayList<Integer>();
    // For a 4x4 matrix length is 4
    for (int j=0; j<grid.length;j++){
      for(int i=0; i<grid[j].length;i++){
        //captures on zero tiles and store in arrayList
        if(grid[i][j] != 0){
          row.add(grid[i][j]);
          //System.out.println("A "+ grid[i][j] + " will be added to Arraylist");
          //System.out.println("The ArrayList is  "+ row.toString());
        }
      }
      //merges tiles if possible
      for(int count = 0; count < row.size()-1; count++){
        if(row.get(count) == row.get(count+1)){
          int merged = row.get(count) + row.get(count);
          //update score
          score += merged;
          row.set(count, merged);
          row.remove(count+1);
        }
      }
      //Goal: set row to zero
    //  System.out.println("After merges Arraylist is  "+ row.toString());
      // if row is not all zeros already
      if (!row.isEmpty()) {
        //make row all zeros
        for (int count=0; count<=grid.length-1; count++) {
          grid[count][j] = 0;
          //System.out.println("column "+ j +" is set to all zeros ");
        }
      }
      //add back into row
      int countB = 0;
      for(int countAl=0; countAl < row.size() ; countAl ++, countB++){
        grid[countB][j] = row.get(countAl);
      }
      // clear current Arraylist, prep for next row
      row.clear();
    }
    return true;
  }
  private boolean MoveDown(){
    ArrayList<Integer> row = new ArrayList<Integer>();
    // For a 4x4 matrix length is 4
    for (int j=0; j<grid.length;j++){
      for(int i=0; i<grid[j].length;i++){
        //captures on zero tiles and store in arrayList
        if(grid[i][j] != 0){
          row.add(grid[i][j]);
          //System.out.println("you captured a "+ grid[i][j]);
          //System.out.println("Arraylist is  "+ row.toString());
        }
      }
      //merges tiles if possible
      for(int count = row.size()-1; count>0 ;count--){
        if(row.get(count) == row.get(count-1)){
          int merged = row.get(count) + row.get(count);
            score += merged;
            //System.out.println("merged is "+ merged);
            row.set(count, merged);
            row.remove(count-1);

        }

      }
      //set row to zero
      //System.out.println("after merges Arraylist is  "+ row.toString());
      if (!row.isEmpty()) {
        //make row all zeros
        for (int count=grid.length-1; count>=0; count--) {
          grid[count][j] = 0;
          //System.out.println("grid count is "+ grid[j][count]);
        }
      }
      //add back into row
      int countB = grid.length-1;
      for(int countAl = row.size()-1; countAl>=0; countAl --, countB--){
        grid[countB][j] = row.get(countAl);
      }
      // clear current Arraylist, prep for next row
      row.clear();
    }
    return true;
  }
  // Performs a move Operation
  public boolean move(Direction direction) {
    if(direction == Direction.RIGHT){
      if(canMove(direction)){
        MoveRight();
        //System.out.println("You Moved Right!");
        return true;
      }
      else{
        return false;
      }
    }
    if(direction == Direction.LEFT){
      if(canMove(direction)){
        MoveLeft();
        //System.out.println("You Moved Left!");
        return true;
      }
      else{
        return false;
      }
    }
    if(direction == Direction.UP){
      if(canMove(direction)){
        MoveUp();
        //System.out.println("You Moved Up!");
        return true;
      }
      else{
        return false;
      }
    }
    if(direction == Direction.DOWN){
      if(canMove(direction)){
        MoveDown();
        //System.out.println("You Moved Down!");
        return true;
      }
      else{
        return false;
      }
    }
    return false;
  }
  // Need to change this for PSA4
  // Check to see if we have a game over
  public boolean isGameOver() {
    if(!(canMoveDown())&&!(canMoveUp())&&!(canMoveRight())&&!(canMoveLeft())){
      //System.out.println("Out of moves. Game Over!");
      return true;
    }
    return false;
  }
  // Return the reference to the 2048 Grid
  public int[][] getGrid() {
    return grid;
  }

  // Return the score
  public int getScore() {
    return score;
  }

  @Override
  public String toString() {
    StringBuilder outputString = new StringBuilder();
    outputString.append(String.format("Score: %d\n", score));
    for (int row = 0; row < GRID_SIZE; row++) {
      for (int column = 0; column < GRID_SIZE; column++)
        outputString.append(grid[row][column] == 0 ? "    -" :
        String.format("%5d", grid[row][column]));
        outputString.append("\n");
    }
    return outputString.toString();
  }
}
