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
  static int noOfMines;
  static String errorMessage = "";

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
    while (!gameWon && !gameLost) {
      playerTurn();
    }

    if (gameLost)
      System.out.println("You revealed a mine and were blown up!");

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
    System.out.println("Enter the number of columns:");
    gridX = scanner.nextInt();
    System.out.println("Enter the number of Rows");
    gridY = scanner.nextInt();
    grid = new Cell[gridX][gridY];

    noOfMines = 0;

    // Initialising the game. Cells are created and mines placed randomly
    for (int i = 0; i < gridY; i++) {
      for (int j = 0; j < gridX; j++) {
        grid[j][i] = new Cell();
        if (random.nextInt(100) < 10) {
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

    // Prints out the column numbers
    System.out.print("   ");
    for (int colNum = 0; colNum < gridX; colNum++)
      System.out.print(colNum + " ");
    System.out.println();

    // Prints out the rows along with their row numbers
    int rowNum = 0;
    for (int i = 0; i < gridY; i++) {
      System.out.print(rowNum + " ");
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
    int chosenX;
    int chosenY;

    // If they choose help they are told the possible actions
    if (playerAction.equals("help")) {
      System.out.println();
      System.out.print("Possible actions are ");
      System.out.print("\u001b[4mr\u001b[0m" + "eveal, "); //reveal
      System.out.println("\u001b[4mf\u001b[0m" + "lag and reset"); //flag
    }
    else if (playerAction.equals("reset"))
      initialise();

    // If they choose flag they then pick the cell, which is then flagged
    else if (playerAction.equals("flag") || playerAction.equals("f")) {
      System.out.println("Choose which cell in the format x,y");
      chosenCell = scanner.next();
      chosenX = Integer.parseInt(chosenCell.substring(0, 1));
      chosenY = Integer.parseInt(chosenCell.substring(2));
      // If the cell is revealed we prevent the player from flagging it
      if (grid[chosenX][chosenY].isRevealed())
        errorMessage = "You can not flag a revealed cell";
      else {
        grid[chosenX][chosenY].flag();
        noOfMines--;
      }
      printGrid();
    } // flag choice


    // If they choose reveal they then pick the cell, which is then revealed
    else if (playerAction.equals("reveal") || playerAction.equals("r")) {
      System.out.println("Choose which cell in the format x,y");
      chosenCell = scanner.next();
      chosenX = Integer.parseInt(chosenCell.substring(0, 1));
      chosenY = Integer.parseInt(chosenCell.substring(2));
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
          && grid[chosenX][chosenY].isRevealed())
        gameLost = true;
    } // reveal choice

    // If they put in some other input it is ignored
    else
      System.out.println("Invalid action");

      if (errorMessage != "")
        System.out.println(errorMessage);
      errorMessage = "";
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
} // Game
