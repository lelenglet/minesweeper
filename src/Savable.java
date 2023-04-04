public interface Savable {
  static final String filepath = "../objsave";

  /**
   * Serialize the Object in order to
   * save the current grid in the file
   */
  public abstract void save();

  /**
   * Load the grid in the file
   *
   * @return Object : the grid unserialized
   */
  public abstract Object load();
}
