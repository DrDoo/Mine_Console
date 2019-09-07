public class Cell {

  // Variables the hold the state of the Cell
  private boolean isMine;
  private int borderingMines;
  private String textColour;
  private boolean revealed;
  private boolean flagged;

  // These are the colours used to change the text colour in the terminal
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_BRIGHT_RED = "\u001b[38;5;9m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_ORANGE = "\u001b[38;5;202m";
  public static final String ANSI_GRAY = "\u001b[38;5;245m";// Actually White
  public static final String ANSI_CYAN = "\u001B[36m";

  public static final String ANSI_YELLOW = "\u001B[33m"; // For the Flag
  public static final String ANSI_RED = "\u001B[31m"; // For the mine

  // Each cell is initialised as not being revealed or flagged and is not a mine
  public Cell() {
    isMine = false;
    flagged = false;
    revealed = false;
  }

  // Method to set the number of bordering mines the cell has
  public void setBorderMines(int numberOfBorderMines) {
    borderingMines = numberOfBorderMines;
    switch(numberOfBorderMines) {
      case 0:
        textColour = ANSI_RESET;
        break;
      case 1:
        textColour = ANSI_BLUE;
        break;
      case 2:
        textColour = ANSI_GREEN;
        break;
      case 3:
        textColour = ANSI_BRIGHT_RED;
        break;
      case 4:
        textColour = ANSI_PURPLE;
        break;
      case 5:
        textColour = ANSI_BLACK;
        break;
      case 6:
        textColour = ANSI_ORANGE;
        break;
      case 7:
        textColour = ANSI_GRAY;
        break;
      case 8:
        textColour = ANSI_CYAN;
        break;
    }
  }

  // Method to change a cell into a mine
  public void setMine() {
    isMine = true;
  }

  // Method to flag a cell, preventing it from being opened
  public void flag() {
    flagged = !flagged;
  }

  // Method to reveal a cell
  public void reveal() {
    revealed = true;
  }

  // The toString method is used to display the cell on the console
  public String toString() {
    if (revealed && !isMine){
      if (borderingMines == 0)
        return " ";
      return (textColour + borderingMines + ANSI_RESET);
    }
    if (revealed && isMine)
      return (ANSI_RED + "*" + ANSI_RESET);
    if (flagged)
      return (ANSI_YELLOW + "F" + ANSI_RESET);
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
