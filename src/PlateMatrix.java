import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * PlateMatrix implements a matrix of Cells.
 */
public class PlateMatrix implements Matrix<Cell>, Serializable {
  private final List<List<Cell>> matrix;

  /**
   * Creates a matrix of nbRows rows and nbColumns columns.
   *
   * @param nbRows    the number of rows
   * @param nbColumns the number of columns
   */
  PlateMatrix(final int nbRows, final int nbColumns) {
    matrix = (List<List<Cell>>) new ArrayList<List<Cell>>();
    for (int i = 0; i < nbRows; i++) {
      matrix.add(new ArrayList<Cell>());
      for (int j = 0; j < nbColumns; j++) {
        matrix.get(i).add(null);
      }
    }
  }

  /**
   * Returns the cell at the specified position in this matrix.
   *
   * @param row    the row index of the cell to return
   * @param column the column index of the cell to return
   * @return the cell at the specified position in this matrix
   */
  public Cell get(final int row, final int column) {
    return matrix.get(row).get(column);
  }

  /**
   * Set the cell at the specified position in this matrix with the specified
   * cell.
   *
   * @param row    the row index of the position for the cell to set
   * @param column the column index of the position for the cell to set
   * @param cell   the cell to be stored at the specified position
   */
  public void set(final int row, final int column, final Cell cell) {
    matrix.get(row).set(column, cell);
  }

  /**
   * Returns the number of rows of this matrix.
   *
   * @return the number of rows of this matrix
   */
  public int getNbRows() {
    return matrix.size();
  }

  /**
   * Returns the number of columns of this matrix.
   *
   * @return the number of columns of this matrix
   */
  public int getNbColumns() {
    return matrix.get(0).size();
  }
}
