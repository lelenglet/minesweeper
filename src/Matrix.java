/**
 * A matrix inteface.
 *
 * @param E : type of the matrix element
 */
public interface Matrix<E> {
  /**
   * Get the fetched matrix element.
   */
  public abstract E get(int row, int column);

  public abstract void set(int row, int column, E value);

  public abstract int getNbRows();

  public abstract int getNbColumns();
}
