import java.io.*;
import java.util.Random;

public class Plate implements Serializable {
  public final PlateMatrix matrix;

  Plate(final int nbRows, final int nbColumns, final int minesPercentage) {
    this.matrix = new PlateMatrix(nbRows, nbColumns);
    this.addCells();
    this.connectAllCells();
    this.setMines((nbRows * nbColumns) * minesPercentage / 100);
    this.updateCellsValues();
  }

  public Cell getCell(final int x, final int y) {
    return matrix.get(x, y);
  }

  public int getNbRows() {
    return this.matrix.getNbRows();
  }

  public int getNbColumns() {
    return this.matrix.getNbColumns();
  }

  private void addCells() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        matrix.set(i, j, new Cell());
      }
    }
  }

  private void connectAllCells() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        if (i > 0) {
          this.matrix.get(i, j).connect(this.matrix.get(i - 1, j), Direction.NORTH);
          if (j > 0) {
            this.matrix.get(i, j).connect(this.matrix.get(i - 1, j - 1), Direction.NORTH_WEST);
          }
          if (j < this.matrix.getNbColumns() - 1) {
            this.matrix.get(i, j).connect(this.matrix.get(i - 1, j + 1), Direction.NORTH_EAST);
          }
        }
        if (j < this.matrix.getNbColumns() - 1) {
          this.matrix.get(i, j).connect(this.matrix.get(i, j + 1), Direction.EAST);
        }
      }
    }
  }

  private void setMines(int nbMines) {
    int cpt = 0;
    final Random r = new Random();
    while (cpt < nbMines) {
      final int x = r.nextInt(this.matrix.getNbRows());
      final int y = r.nextInt(this.matrix.getNbColumns());
      if (this.matrix.get(x, y).getValue() == 0) {
        this.matrix.get(x, y).setMine();
        cpt++;
      }
    }
  }

  private void updateCellsValues() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        matrix.get(i, j).setValue();
      }
    }
  }

  public boolean checkWin() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        if (matrix.get(i, j).getValue() > 0 && matrix.get(i, j).getState() == State.COVERED) {
          return false;
        }
      }
    }
    return true;
  }

  public void revealAll() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        matrix.get(i, j).uncover();
      }
    }
  }
}
