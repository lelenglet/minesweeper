import java.util.EnumMap;

class Cell {
  private int value;
  private State state;
  private final EnumMap<Direction, Cell> neighborhood;

  Cell() {
    this.state = State.COVERED;
    this.neighborhood = new EnumMap<Direction, Cell>(Direction.class);
  }

  public void setValue() {
    if (this.getValue() != -1) {
      for (final Direction d : this.neighborhood.keySet()) {
        if (this.neighborhood.get(d).getValue() == -1) {
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
    return String.format(this.getValue() + "\uFE0F ");
  }

  public void setMine() {
    this.value = -1;
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
