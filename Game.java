import java.util.Random;

public class Game {

  static Cell[][] grid;
  static Random random = new Random();

  public static void main(String[] args) {

    grid = new Cell[16][10];

    // Initialising the game. Cells are created and mines placed randomly
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 16; j++) {
        grid[j][i] = new Cell();
        if (random.nextInt(100) < 20)
          grid[j][i].setMine();
      }
    }



    // Finding out the number of bordering mines for each cell
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 16; j++) {
        int borderMines = 0;
        for (int m = 0; m < 10; m++) {
          for (int n = 0; n < 16; n++) {
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
  }

  // This is used to see if a bordering cell contains a mine
  public static boolean isMine(int cellX, int cellY, int otherX, int otherY) {

    // We loop through all cells untill we reach cells a distance of 1 away, if
    // this cell contains a mine then the check passes
    if ((cellX - otherX < 2) && (cellX - otherX > -2)
        && (cellY - otherY < 2) && (cellY - otherY > -2)
        && grid[otherX][otherY].getMineStatus())
      return true;
    return false;
  } // isNeighbour

  // Prints out the visual of the game
  public static void printGrid() {

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 16; j++) {
        System.out.print("|");
        System.out.print(grid[j][i]);
      }
      System.out.print("|");
      System.out.println();
    }
  }

}
