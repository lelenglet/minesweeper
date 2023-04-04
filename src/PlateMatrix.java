import java.util.ArrayList;
import java.util.List;

public class PlateMatrix implements Matrix<Cell> {
  private final List<List<Cell>> matrix;

  public PlateMatrix(final int nbRows, final int nbColumns) {
    matrix = (List<List<Cell>>) new ArrayList<List<Cell>>();
    for (int i = 0; i < nbRows; i++) {
      matrix.add(new ArrayList<Cell>());
      for (int j = 0; j < nbColumns; j++) {
        matrix.get(i).add(null);
      }
    }
  }

  public Cell get(final int row, final int column) {
    return matrix.get(row).get(column);
  }

  public void set(final int row, final int column, final Cell cell) {
    matrix.get(row).set(column, cell);
  }

  public int getNbRows() {
    return matrix.size();
  }

  public int getNbColumns() {
    return matrix.get(0).size();
  }
}
