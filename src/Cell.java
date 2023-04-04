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

  public Cell getNeighbor(final Direction d) {
    return this.neighborhood.get(d);
  }

  public boolean isMine() {
    return getValue() == -1;
  }

  public String toString() {
    return String.format(this.getValue() + "\uFE0F ");
  }

  protected void setMine() {
    this.value = -1;
  }

  protected void toggleFlagged() {
    if (getState() == State.FLAGGED) {
      this.state = State.COVERED;
    } else if (getState() == State.COVERED) {
      this.state = State.FLAGGED;
    }
  }

  protected void connect(final Cell neighbor, final Direction dir) {
    this.neighborhood.put(dir, neighbor);
    if (neighbor.getNeighbor(Direction.opposite(dir)) == null) {
      neighbor.connect(this, Direction.opposite(dir));
    }
  }

  protected boolean reveal() {
    if (this.getState() == State.COVERED) {
      this.state = State.UNCOVERED;
      if (this.getValue() == 0) {
        for (final Direction d : this.neighborhood.keySet()) {
          this.neighborhood.get(d).reveal();
        }
      } else if (this.isMine()) {
        return false;
      }
    }
    return true;
  }
}
