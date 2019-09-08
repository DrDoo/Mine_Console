import java.util.Random;
import java.util.Scanner;

public class Game {

  // The grid the game is played on and two variables used to determine its size
  static Cell[][] grid;
  static int gridX;
  static int gridY;

  // Booleans used to determine aspects of the game
  static boolean gameWon = false;
  static boolean gameLost = false;
  static boolean gameOver = false;
  static int noOfMines;
  static int minePercentage;
  static String errorMessage = "";
  static String endMessage = "";
  static String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l"
                              ,"m","n","o","p","q","r","s","t","u","v","w","x"
                              ,"y","z"};

  // Tools to help with various parts of the game
  static Random random = new Random();
  static Scanner scanner = new Scanner(System.in);

  /*
███    ███  █████  ██ ███    ██
████  ████ ██   ██ ██ ████   ██
██ ████ ██ ███████ ██ ██ ██  ██
██  ██  ██ ██   ██ ██ ██  ██ ██
██      ██ ██   ██ ██ ██   ████
*/

  public static void main(String[] args) {
    // Initialisation of the game
    initialise();

    // Whist the player has neither won or lost the game they can continue to
    // make movses
    while (!gameWon && !gameLost && !gameOver) {
      playerTurn();
    }

    // After the game is over we reveal all cells that have yet to be revealed,
    // and if the player has incorrectly flagged any cells we mark those too
    for (int i = 0; i < gridX; i++) {
      for (int j = 0; j < gridY; j++) {
        if (!grid[j][i].isRevealed() && !grid[j][i].isFlagged())
          grid[j][i].reveal();
        if (grid[j][i].isFlagged() && !grid[j][i].getMineStatus())
          grid[j][i].markIncorrect();
      }
    }
    // The final state of the grid is displayed along with the ending message
    printGrid();
    System.out.println(endMessage);
  } // main

  /*
██ ███    ██ ██ ████████
██ ████   ██ ██    ██
██ ██ ██  ██ ██    ██
██ ██  ██ ██ ██    ██
██ ██   ████ ██    ██
*/

  // Initialises the game
  public static void initialise() {

    // Player chooses the size of the grid
    System.out.println("Enter the number of columns (less than 26):");
    gridX = scanner.nextInt();
    System.out.println("Enter the number of Rows (less than 26):");
    gridY = scanner.nextInt();
    grid = new Cell[gridX][gridY];

    System.out.println("Enter the percent of cells you wish to be mines:");
    minePercentage = scanner.nextInt();
    noOfMines = 0;

    // Initialising the game. Cells are created and mines placed randomly
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        grid[j][i] = new Cell();
        if (random.nextInt(100) < minePercentage) {
          grid[j][i].setMine();
          noOfMines++;
        }
      }
    }

    // Finding out the number of bordering mines for each cell
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        int borderMines = 0;
        for (int m = 0; m < gridY; m++) {
          for (int n = 0; n < gridX; n++) {
            if (!(m==i && n==j)) {
              if (isMine(j, i, n, m))
                borderMines++;
            } // if
          } // for
        } // for
        grid[j][i].setBorderMines(borderMines);
      }
    }
    printGrid();
  } // initialise

  /*
██ ███████     ███    ███ ██ ███    ██ ███████
██ ██          ████  ████ ██ ████   ██ ██
██ ███████     ██ ████ ██ ██ ██ ██  ██ █████
██      ██     ██  ██  ██ ██ ██  ██ ██ ██
██ ███████     ██      ██ ██ ██   ████ ███████
*/

  // This is used to see if a bordering cell contains a mine
  public static boolean isMine(int cellX, int cellY, int otherX, int otherY) {

    // We loop through all cells untill we reach cells a distance of 1 away, if
    // this cell contains a mine then the check passes
    if ((cellX - otherX < 2) && (cellX - otherX > -2)
        && (cellY - otherY < 2) && (cellY - otherY > -2)
        && grid[otherX][otherY].getMineStatus())
      return true;
    return false;
  } // isMine

  /*
  ██████  ██████  ██ ███    ██ ████████      ██████  ██████  ██ ██████
  ██   ██ ██   ██ ██ ████   ██    ██        ██       ██   ██ ██ ██   ██
  ██████  ██████  ██ ██ ██  ██    ██        ██   ███ ██████  ██ ██   ██
  ██      ██   ██ ██ ██  ██ ██    ██        ██    ██ ██   ██ ██ ██   ██
  ██      ██   ██ ██ ██   ████    ██         ██████  ██   ██ ██ ██████
  */

  // Prints out the visual of the game
  public static void printGrid() {

    // Prints out the column letters in uppercase
    System.out.print("   ");
    for (int colNum = 0; colNum < gridX; colNum++)
      System.out.print(alphabet[colNum].toUpperCase() + " ");
    System.out.println();

    // Prints out the rows along with their row letters in uppercase
    int rowNum = 0;
    for (int i = 0; i < gridY; i++) {
      System.out.print(alphabet[rowNum].toUpperCase() + " ");
      for (int j = 0; j < gridX; j++) {
        System.out.print("|");
        System.out.print(grid[j][i]);
      }
      System.out.print("|");
      System.out.println();
      rowNum++;
    }
  } // printGrid

  /*
  ████████ ██    ██ ██████  ███    ██
     ██    ██    ██ ██   ██ ████   ██
     ██    ██    ██ ██████  ██ ██  ██
     ██    ██    ██ ██   ██ ██  ██ ██
     ██     ██████  ██   ██ ██   ████
  */

  // Takes the players input and does the corresponding action
  public static void playerTurn() {
    System.out.println("There are " + noOfMines + " mines remaining");
    System.out.println("Choose Action, type help for a list of commands");
    // Stores the players input which is then compared to the possible inputs
    String playerAction = scanner.next();

    // Variables to help with choosing the cell the action is happening to
    String chosenCell;
    String[] chosenCoords;
    int chosenX = 0;
    int chosenY = 0;

    // If they choose help they are told the possible actions
    if (playerAction.equals("help")) {
      System.out.println();
      System.out.print("Possible actions are ");
      System.out.print("\u001b[4mr\u001b[0m" + "eveal, "); //reveal
      System.out.println("\u001b[4mf\u001b[0m" + "lag, reset and exit"); //flag
    }
    else if (playerAction.equals("reset"))
      initialise();

    // If they choose flag they then pick the cell, which is then flagged
    else if (playerAction.equals("flag") || playerAction.equals("f")) {
      System.out.println("Choose which cell in the format x,y");
      chosenCell = scanner.next();
      chosenCoords = chosenCell.split(",");
      // We loop through the alphabet array to find the corresponding number to
      // the letter input
      for (int i = 0; i < alphabet.length; i++) {
        if (chosenCoords[0].equals(alphabet[i])) chosenX = i;
        if (chosenCoords[1].equals(alphabet[i])) chosenY = i;
      }
      // If the cell is revealed we prevent the player from flagging it
      if (grid[chosenX][chosenY].isRevealed())
        errorMessage = "You can not flag a revealed cell";
      else {
        if (grid[chosenX][chosenY].isFlagged())
          noOfMines++;
        else
          noOfMines--;
        grid[chosenX][chosenY].flag();
      }
      printGrid();
    } // flag choice


    // If they choose reveal they then pick the cell, which is then revealed
    else if (playerAction.equals("reveal") || playerAction.equals("r")) {
      System.out.println("Choose which cell in the format x,y");
      chosenCell = scanner.next();
      chosenCoords = chosenCell.split(",");
      // We loop through the alphabet array to find the corresponding number to
      // the letter input
      for (int i = 0; i < alphabet.length; i++) {
        if (chosenCoords[0].equals(alphabet[i])) chosenX = i;
        if (chosenCoords[1].equals(alphabet[i])) chosenY = i;
      }
      // If the cell is flagged we prevent the player from revealing it
      if (grid[chosenX][chosenY].isFlagged())
        errorMessage = "You can not reveal a flagged cell";
      else {
        grid[chosenX][chosenY].reveal();
        // If the cell has 0 bordering mines, we open all other empty cells
        // around it
        if (grid[chosenX][chosenY].getBorderingMines() == 0
            && !grid[chosenX][chosenY].getMineStatus())
          openEmpties(chosenX, chosenY);
      }
      printGrid();
      // If the player reveals a mine they lose
      if (grid[chosenX][chosenY].getMineStatus()
          && grid[chosenX][chosenY].isRevealed()) {
        gameLost = true;
        System.out.println("You revealed a mine and were blown up!");
      }
    } // reveal choice

    else if (playerAction.equals("exit"))
      gameOver = true;

    // If they put in some other input it is ignored
    else
      System.out.println("Invalid action");

    // If there is an error message it is printed out, it is coloured red using
    // ANSI escape codes
      if (errorMessage != "")
        System.out.println("\u001B[31m" + errorMessage + "\u001B[0m");
      errorMessage = "";

    if (noOfMines == 0)
      hasPlayerWon();
  } // playerTurn

  /*
  ███████ ███    ███ ██████  ████████ ██ ███████ ███████
  ██      ████  ████ ██   ██    ██    ██ ██      ██
  █████   ██ ████ ██ ██████     ██    ██ █████   ███████
  ██      ██  ██  ██ ██         ██    ██ ██           ██
  ███████ ██      ██ ██         ██    ██ ███████ ███████
  */

  // When opening an empty cell it opens all other cells around it, this
  // includes other empty cells
  public static void openEmpties(int cellX, int cellY) {

    // Loops through all the cells untill we reach a cell a distance of 1 away
    // from the chosen cell, if that cell is not a mine we continue
    for (int i = 0; i < gridX; i++) {
      for (int j = 0; j < gridY; j++) {
        if ((cellX - i < 2) && (cellX - i > -2)
            && (cellY - j < 2) && (cellY - j > -2)
            && !grid[i][j].getMineStatus()) {
          // If that cell has 0 bordering mines and it hasnt been revealed and
          // if isnt the cell we originally chose, we call openEmpties on that
          // cell
          if (grid[i][j].getBorderingMines() == 0
              && !grid[i][j].isRevealed()) {
            if (!(cellX==i && cellY==j)) {
              openEmpties(i, j);
            }
          }
          // We then reveal the cell
          grid[i][j].reveal();
        }
      }
    }
  } // openEmpties

  /*
 ██   ██  █████  ███████     ██     ██  ██████  ███    ██
 ██   ██ ██   ██ ██          ██     ██ ██    ██ ████   ██
 ███████ ███████ ███████     ██  █  ██ ██    ██ ██ ██  ██
 ██   ██ ██   ██      ██     ██ ███ ██ ██    ██ ██  ██ ██
 ██   ██ ██   ██ ███████      ███ ███   ██████  ██   ████
 */

 // Once all the flags have been placed we loop through all the cells to make
 // the flags have been put in the correct spots, if they have the player wins
 // if not they lose
  public static void hasPlayerWon() {

    // Initially we assume the player has won
    gameWon = true;
    endMessage = "Congratulations! You found all the Mines!";

    // Then we loop to make sure none of the flags have been placed incorrectly
    // If they have they lose
    for (int i = 0; i < gridX; i++) {
      for (int j = 0; j < gridY; j++) {
        if (grid[i][j].getMineStatus() && !grid[i][j].isFlagged()) {
          gameLost = true;
          gameWon = false;
          endMessage = "Oh no! You must have flagged an incorrect cell";
        }
      }
    }
  } // hasPlayerWon
} // Game
