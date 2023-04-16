import java.io.Serializable;
import java.util.Random;

/**
 * The Plate class is used to create a new game.
 * It contains the grid of cells and the timer.
 */
public class Plate implements Serializable {
  public Timer chrono;
  public final PlateMatrix matrix;

  Plate(final int nbRows, final int nbColumns, final int minesPercentage) {
    this.matrix = new PlateMatrix(nbRows, nbColumns);
    this.chrono = new Timer();
    this.addCells();
    this.connectAllCells();
    this.setMines((nbRows * nbColumns) * minesPercentage / 100);
    this.updateCellsValues();
  }

  /**
   * Stop the chrono.
   */
  public void stopChrono() {
    this.chrono.stop();
  }

  /**
   * Start the chrono.
   */
  public void startChrono() {
    this.chrono.start();
  }

  /**
   * Return the elapsed time since the start of the chrono.
   *
   * @return the time in seconds
   */
  public long getTime() {
    return this.chrono.getElapsedTime();
  }

  /**
   * Gets the cell according to its position in the matrix.
   * 
   * @return the cell
   */
  public Cell getCell(final int x, final int y) {
    return matrix.get(x, y);
  }

  /**
   * Uncover a cell and check if it's a mine. If it's a mine, it's game over.
   * 
   * @param x the x position of the cell
   * @param y the y position of the cell
   * @return true if it's a mine
   */
  public boolean uncoverCell(final int x, final int y) {
    final Cell cell = this.matrix.get(x, y);
    cell.uncover();
    if (cell.getValue() == 0) {
      this.uncoverNeighbors(cell);
    }
    return cell.isMine();
  }

  /**
   * Toggle the flag on a cell.
   * 
   * @param x the x position of the cell
   * @param y the y position of the cell
   */
  public void toggleFlagCell(final int x, final int y) {
    this.matrix.get(x, y).toggleFlagged();
  }

  /**
   * Gets the number of rows in the matrix.
   *
   * @return numbers of rows
   */
  public int getNbRows() {
    return this.matrix.getNbRows();
  }

  /**
   * Gets the number of columns in the matrix.
   *
   * @return numbers of columns
   */
  public int getNbColumns() {
    return this.matrix.getNbColumns();
  }

  /**
   * Check the victory conditions.
   * 
   * @return true if it's won
   */
  public boolean checkWin() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        if (!this.matrix.get(i, j).isMine() && this.matrix.get(i, j).isCovered()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Reveal all the cells.
   */
  public void revealAll() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        this.uncoverCell(i, j);
      }
    }
  }

  /**
   * Uncover all neighbors of a cell.
   * 
   * @param cell the cell
   */
  private void uncoverNeighbors(final Cell cell) {
    for (final Cell neighbor : cell.getNeighbors()) {
      if (neighbor.isCovered()) {
        neighbor.uncover();
        if (neighbor.getValue() == 0) {
          this.uncoverNeighbors(neighbor);
        }
      }
    }
  }

  /**
   * Set mines randomly on the grid.
   * 
   * @param nbMines the number of mines
   */
  private void setMines(final int nbMines) {
    final Random random = new Random();
    int nbMinesToPlace = nbMines;
    while (nbMinesToPlace > 0) {
      final int x = random.nextInt(this.matrix.getNbRows());
      final int y = random.nextInt(this.matrix.getNbColumns());
      if (!this.matrix.get(x, y).isMine()) {
        this.matrix.get(x, y).setMine();
        nbMinesToPlace--;
      }
    }
  }

  /**
   * Update the value of all cells.
   */
  private void updateCellsValues() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        this.matrix.get(i, j).setValue();
      }
    }
  }

  /**
   * Create all cells and add them to the grid.
   */
  private void addCells() {
    for (int i = 0; i < this.matrix.getNbRows(); i++) {
      for (int j = 0; j < this.matrix.getNbColumns(); j++) {
        this.matrix.set(i, j, new Cell());
      }
    }
  }

  /**
   * Connect all cells to their neighbors.
   */
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
}
