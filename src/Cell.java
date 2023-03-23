package src;

import java.util.EnumMap;
import src.Direction;

class Cell {
  private int value;
  private EnumMap<Direction, Cell> neighbor;
  private int state;

  Cell(int v) {
    this.value = v;
    this.state = 0;
    this.neighbor = new EnumMap<Direction, Cell>(Direction.class);
  }

  public int getValue() {
    return this.value;
  }

  public boolean isMine() {
    return getValue() == -1;
  }

  public int getState() {
    return this.state;
  }

  private void setMarqued() {
    if (getState() == 0) {
      this.state = 1;
    } else if (getState() == 1) {
      this.state = 0;
    }
  }

  public Direction seekNeighbor(Cell neighbor) {
    for (Direction d : this.neighbor.keySet()) {
      if (this.neighbor.get(d) == neighbor) {
        return d;
      }
    }
    return null;
  }

  private void addNeighbor(Cell neighbor, Direction dir) {
    this.neighbor.put(dir, neighbor);
  }
}
