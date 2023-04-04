public interface Ui {
  public abstract int[] displayMenu();

  public abstract void displayGrid();

  public abstract void clearScreen();

  public abstract void gameLost();

  public abstract void gameWon();

  public abstract void setGamePlate(Plate gamePlate);
}
