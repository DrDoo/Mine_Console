public class Cell {

  // Variables the hold the state of the Cell
  private int borderingMines;
  private boolean revealed;
  private boolean flagged;

  // Constructor only needs the number of mines that border this cell. Each cell
  // is initialised as not being revealed or flagged
  public Cell(int borderMines) {
    borderingMines = borderMines;
    flagged = false;
    revealed = false;
  }

  // The toString method is used to display the cell on the console
  public String toString() {
    if (revealed)
      return ("" + borderingMines);
    return "#";
  }

  // Accessor Methods
  public int getBorderingMines() {
    return borderingMines;
  }

  public boolean isRevealed() {
    return revealed;
  }

  public boolean isFlagged() {
    return flagged;
  }

}
