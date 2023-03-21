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

  private int getMarqued() {
    return this.state;
  }

  private void setMarqued() {
    if (getMarqued() == 0) {
      this.state = 1;
    } else if (getMarqued() == 1) {
      this.state = 0;
    }
  }

  private Direction seekNeighbor(Cell neighbor) {
    for (Direction d : this.neighbor.keySet()) {
      if (this.neighbor.get(d) == neighbor) {
        return d;
      }
    }
    return null;
  }
}
