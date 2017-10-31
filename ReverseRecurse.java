//------------------------------------------------------------------//
// ReverseRecurse.java                                              //
//                                                                  //
// Reads integer values from the keyboard into an                   //
// array whose size is specified by the user and reverse the        //
// elements in the array via two different recursive methods.       //
// One method directly modifies the original array. The other       //
// method returns a new array with the elements reversed            //
// preserving the original array.                                   //
//                                                                  //
// Author:  Drew Mills                	                            //
// Date:    02/28/17                                                //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;

public class ReverseRecurse {
  //you shouldn’t create any instance fields for this class
  /*
  will ask the user for a maximum number of integers expected,
  create an array of integers of that size, read at most that
  many integers from the keyboard using a Scanner object and
  return the initialized array.
  */
  public int[] initArray(){
    int count=0;
    boolean choice = false;
    Scanner in = new Scanner(System.in);
    int size = 0;
    int [] arrayMax = null;
    // get max array size from user
    do{
      //print question
      System.out.print(PSA7Strings.MAX_NUM);
      //if there is something
      if ( in.hasNext() ){
        // if that something is not a number
        if(!in.hasNextInt()){

            System.exit(1);
        }
        //see that it is an int
        else if(in.hasNextInt()){
         size = in.nextInt();
          //if int is greater than 0 make array
          if (size > 0 ){
            arrayMax = new int[size];
            //exit loop
            choice = false;
          }
          // if int is less than 0, print error
          else{
          System.out.print(PSA7Strings.TOO_SMALL);
          choice = true;
          }
        }
      }
    } while(choice);
    choice = false;
    //ask how many elt the user wants
    System.out.printf(PSA7Strings.ENTER_INTS,size);
    //if not int make empty array, and return
    if(!in.hasNextInt()){
        arrayMax = new int[0];
      return arrayMax;
    }
    //collect the elements from the user
    while(count < size && in.hasNext()){
      for (int i = 0; i < size; i++){
        // chatches <Ctrl>+D
        if(!in.hasNextInt()){
          //break out of loop
          break;
        }
        arrayMax[i] = in.nextInt();
        count++;
      }
      count++;
    }
    // copy array and resize to only the number of values > 0
    int n = 0;
    for (int i = 0; i < size; i++) {
      if (arrayMax[i] != 0){
        n++;
      }
    }

    int[] newArray = new int[n];
    int j=0;
    // put into new array
    for (int i = 0; i < arrayMax.length; i++) {
      if (arrayMax[i] != 0){
        newArray[j]=arrayMax[i];
        j++;
      }
    }
    return newArray;
  }
  /*
  cycles through the passed array printing each integer
  on the same line separated by a space. Output a newline
  after all the elements are printed
  */
  public void printArray( int[] array ) {
    if (array.length == 0){
      System.out.println(PSA7Strings.EMPTY);
    }
    else{
      for (int i = 0; i < array.length; i++) {
        System.out.print(array[i] + " ");
      }
      System.out.print("\n");
    }
  }

  /*
   * will directly manipulate the array passed in by exchanging
   * the low and high index values and recurse on the remaining
   * elements of the array by passing modified values of low
   * and high in the recursive call
   */
  public void reverse( int[] originalArray, int low, int high ){
    //null case test
    if(originalArray==null){
      //System.out.println(PSA7Strings.EMPTY);
      return;
    }
    //base case
    if (high<low){
      return;
    }
    //swap
    int temp = originalArray[high];
    originalArray[high] = originalArray[low];
    originalArray[low] = temp;
    //recurse
    //System.out.println(Arrays.toString(originalArray));
    reverse(originalArray,++low,--high);
  }
  /*
   * The following two reverse() methods must be implemented
   * using recursion. Concats the reverse of the end of the array to the
   * first part (i.e. put them at the end of the array)
   */
  public int[] reverse( int[] originalArray ){
    //null test check
    if(originalArray == null){
      return originalArray;
    }
    //check if length 0, return
    if (originalArray.length == 0) {
     return new int[] {};
     //check if length is 1
    }
    else if (originalArray.length == 1) {
     return originalArray;
    }
    else {
     // recursion
     return concat(reverse
     (Arrays.copyOfRange(originalArray, 1, originalArray.length)),
      new int[] { originalArray[0] });
    }
  }
  /*
    This is a helper method, its purpose is to switch elements and return
    the final array
   */
  static int[] concat(int[] first, int[] last ) {
		int[] finalArray = new int[first.length + last.length];
		System.arraycopy(first, 0, finalArray, 0, first.length);
		System.arraycopy(last, 0, finalArray, first.length, last.length);
		return finalArray;
	}
}
