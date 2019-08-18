import java.util.Random;

public class Game {

  static Cell[][] grid;
  static Random random = new Random();

  public static void main(String[] args) {

    grid = new Cell[16][10];

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 16; j++) {
        grid[j][i] = new Cell();
        if (random.nextInt(100) < 20)
          grid[j][i].setMine();
      }
    }

    printGrid();
  }

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
