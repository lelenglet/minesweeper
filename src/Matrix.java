/**
 * Interface for a matrix of elements.
 *
 * @param <E> the type of the elements in the matrix.
 */
public interface Matrix<E> {

  /**
   * Returns the element at the specified position in this matrix.
   *
   * @param row    the row index of the element to return
   * @param column the column index of the element to return
   * @return the element at the specified position in this matrix
   */
  public abstract E get(int row, int column);

  /**
   * Set the element at the specified position in this matrix with the specified
   * element.
   *
   * @param row    the row index of the position for the element to set
   * @param column the column index of the position for the element to set
   * @param value  the element to be stored at the specified position
   */
  public abstract void set(int row, int column, E value);

  /**
   * Returns the number of rows in this matrix.
   *
   * @return the number of rows in this matrix
   */
  public abstract int getNbRows();

  /**
   * Returns the number of columns in this matrix.
   *
   * @return the number of columns in this matrix
   */
  public abstract int getNbColumns();
}
