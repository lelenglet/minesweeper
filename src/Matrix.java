/**
 * Interface for a matrix of elements.
 *
 * @param <E> the type of the elements in the matrix.
 */
public interface Matrix<E> {
  public abstract E get(int row, int column);

  public abstract void set(int row, int column, E value);

  public abstract int getNbRows();

  public abstract int getNbColumns();
}
