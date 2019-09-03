public class Cell {

  // Variables the hold the state of the Cell
  private boolean isMine;
  private int borderingMines;
  private boolean revealed;
  private boolean flagged;

  // Each cell is initialised as not being revealed or flagged and is not a mine
  public Cell() {
    isMine = false;
    flagged = false;
    revealed = false;
  }

  // Method to set the number of bordering mines the cell has
  public void setBorderMines(int numberOfBorderMines) {
    borderingMines = numberOfBorderMines;
  }

  // Method to change a cell into a mine
  public void setMine() {
    isMine = true;
  }

  // Method to flag a cell, preventing it from being opened
  public void flag() {
    if (revealed)
      System.out.println("You can not flag a revealed cell");
    else
      flagged = !flagged;
  }

  // Method to reveal a cell
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
  public boolean getMineStatus() {
    return isMine;
  }
  
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
