public class Cell {

  // Variables the hold the state of the Cell
  private boolean isMine;
  private int borderingMines;
  private boolean revealed;
  private boolean flagged;

  // Constructor only needs the number of mines that border this cell. Each cell
  // is initialised as not being revealed or flagged
  public Cell(int borderMines, boolean mineStatus) {
    isMine = mineStatus;
    borderingMines = borderMines;
    flagged = false;
    revealed = false;
  }

  public void flag() {
    if (revealed)
      System.out.println("You can not flag a revealed cell");
    else
      flagged = !flagged;
  }

  public void reveal() {
    if (flagged)
      System.out.println("You can not reveal a flagged cell");
    else
      revealed = true;
  }

  // The toString method is used to display the cell on the console
  public String toString() {
    if (revealed && !isMine)
      return ("" + borderingMines);
      if (revealed && isMine)
        return "*";
    if (flagged)
      return "F";
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
