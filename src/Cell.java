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

  public State getState() {
    return this.state;
  }

  public boolean isMine() {
    return getValue() == -1;
  }

  public String toString() {
    if (this.getState() == State.COVERED) {
      return new String(Character.toChars(0x2B1C));
    } else if (this.getState() == State.FLAGGED) {
      return new String(Character.toChars(0x1F6A9));
    } else if (this.getState() == State.UNCOVERED && !this.isMine()) {
      return String.format(this.getValue() + "\uFE0F");
    } else {
      return new String(Character.toChars(0x1F4A5));
    }
  }

  public void toggleFlagged() {
    if (getState() == State.FLAGGED) {
      this.state = State.COVERED;
    } else if (getState() == State.COVERED) {
      this.state = State.FLAGGED;
    }
  }

  public void connect(final Cell neighbor, final Direction dir) {
    this.neighborhood.put(dir, neighbor);
    if (neighbor.getNeighbor(Direction.opposite(dir)) == null) {
      neighbor.connect(this, Direction.opposite(dir));
    }
  }

  public boolean uncover() {
    if (this.getState() != State.FLAGGED && this.getState() == State.COVERED) {
      this.state = State.UNCOVERED;
      if (this.getValue() == 0) {
        for (final Direction d : this.neighborhood.keySet()) {
          this.neighborhood.get(d).uncover();
        }
      } else if (this.isMine()) {
        return true;
      }
    }
    return false;
  }

  private Cell getNeighbor(final Direction d) {
    return this.neighborhood.get(d);
  }
}
