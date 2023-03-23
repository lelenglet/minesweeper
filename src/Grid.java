package src;

public class Grid {
  private int height;
  private int width;
  private Cell[][] gamePlate;
  private int nbMine;

  Grid(int h, int w, int m) {
    this.height = h;
    this.width = w;
    this.gamePlate = new Cell[h][w];
    this.nbMine = m;
  }

  public void displayGrid() { // non testée
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (gamePlate[i][j].getState() == 0) {
          System.out.println("\u25A9");
        } else if (gamePlate[i][j].getState() == 1) {
          System.out.println("\u2691");
        } else if (gamePlate[i][j].getState() == -1 && gamePlate[i][j].isMine() == false) {
          System.out.println(gamePlate[i][j].getValue());
        } else {
          System.out.println("\u1F4A3");
        }
      }
      System.out.println("\n");
    }
  }
}
