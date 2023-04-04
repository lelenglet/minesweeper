public interface Savable {
  static final String filepath = "../objsave";

  public abstract void save();

  public abstract Object load();
}
