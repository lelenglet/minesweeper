import java.util.EnumMap;

class Cell {
  private int value;
  private State state;
  private EnumMap<Direction, Cell> neighborhood;

  Cell(int v) {
    this.value = v;
    this.state = 0;
    this.neighbor = new EnumMap<Direction, Cell>(Direction.class);
  }

  protected void setValue(int v) {
    this.value = v;
  }

  public int getValue() {
    return this.value;
  }

  public boolean isMine() {
    return getValue() == -1;
  }

  public State getState() {
    return this.state;
  }

  protected void addNeighbor(Cell neighbor, Direction dir) {
    this.neighbor.put(dir, neighbor);
  }

  public Direction seekNeighbor(Cell neighbor) {
    for (Direction d : this.neighbor.keySet()) {
      if (this.neighbor.get(d) == neighbor) {
        return d;
      }
    }
    return null;
  }

  public void mineInNeighborhood() {
    if (this.getValue() != -1) {
      for (Direction d : this.neighbor.keySet()) {
        if (this.neighbor.get(d).getValue() == -1) {
          this.value++;
        }
      }
    }
  }

  protected boolean revealCell() {
    if (this.getState() == 0) {
      this.state = -1;
      if (this.getValue() == 0) {
        for (Direction d : this.neighbor.keySet()) {
          this.neighbor.get(d).revealCell();
        }
      } else if (this.isMine()) {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    return String.format(this.getValue() + "\uFE0F ");
  }
}
