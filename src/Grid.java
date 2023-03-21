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
}
