/**
 * A matrix inteface.
 *
 * @param E : type of the matrix element
 */
public interface Matrix<E> {
  /**
   * Get the fetched matrix element.
   */
  public E get(int row, int column);

  public void set(int row, int column, E value);

  public int getNbRows();

  public int getNbColumns();
}
