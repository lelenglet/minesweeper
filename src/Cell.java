import java.io.Serializable;
import java.util.EnumMap;

public class Cell implements Serializable {
  private int value;
  private State state;
  private final EnumMap<Direction, Cell> neighborhood;

  Cell() {
    this.state = State.COVERED;
    this.neighborhood = new EnumMap<Direction, Cell>(Direction.class);
  }

  public void setMine() {
    this.value = -1;
  }

  public void setValue() {
    if (!this.isMine()) {
      for (final Direction d : this.neighborhood.keySet()) {
        if (this.neighborhood.get(d).isMine()) {
          this.value++;
        }
      }
    }
  }

  public int getValue() {
    return this.value;
  }

  private State getState() {
    return this.state;
  }

  public boolean isCovered() {
    return this.getState() == State.COVERED;
  }

  public boolean isUncovered() {
    return this.getState() == State.UNCOVERED;
  }

  public boolean isFlagged() {
    return this.getState() == State.FLAGGED;
  }

  public boolean isMine() {
    return this.getValue() == -1;
  }

  public String toString() {
    return switch (this.getState()) {
      case COVERED -> new String();
      case FLAGGED -> new String(Character.toChars(0x2691));
      case UNCOVERED -> {
        yield this.isMine() ? new String(Character.toChars(0x1F4A5))
            : new String("" + this.getValue());
      }
    };
  }

  public void toggleFlagged() {
    this.state = switch (this.getState()) {
      case FLAGGED -> State.COVERED;
      case COVERED -> State.FLAGGED;
      case UNCOVERED -> State.UNCOVERED;
    };
  }

  public void connect(final Cell neighbor, final Direction dir) {
    this.neighborhood.put(dir, neighbor);
    if (neighbor.getNeighbor(Direction.opposite(dir)) == null) {
      neighbor.connect(this, Direction.opposite(dir));
    }
  }

  public boolean uncover() {
    if (this.isCovered() && !this.isFlagged()) {
      this.state = State.UNCOVERED;

      if (this.getValue() == 0) {
        for (final Direction d : this.neighborhood.keySet()) {
          this.neighborhood.get(d).uncover();
        }
      }
      return this.isMine();
    } else {
      return false;
    }
  }

  private Cell getNeighbor(final Direction d) {
    return this.neighborhood.get(d);
  }
}
