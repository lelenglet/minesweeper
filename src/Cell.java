import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

/**
 * The Cell class is used to create a new cell.
 * A cell is a square on the grid of the game.
 * It contains a value (number of mines in the neighborhood) and a state
 * (covered, uncovered, flagged).
 * It also contains a neighborhood (cells in the 8 directions).
 */
public class Cell implements Serializable {
  private int value;
  private State state;
  private final EnumMap<Direction, Cell> neighborhood;

  Cell() {
    this.state = State.COVERED;
    this.neighborhood = new EnumMap<Direction, Cell>(Direction.class);
  }

  /**
   * Set the value of the cell to -1.
   */
  public void setMine() {
    this.value = -1;
  }

  /**
   * Set the value of the cell based on the number of mines in the neighborhood.
   */
  public void setValue() {
    if (!this.isMine()) {
      for (final Direction d : this.neighborhood.keySet()) {
        if (this.neighborhood.get(d).isMine()) {
          this.value++;
        }
      }
    }
  }

  /**
   * Get the value of the cell.
   * 
   * @return the value
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Check if the cell is covered.
   * 
   * @return true if the cell is covered
   */
  public boolean isCovered() {
    return this.getState() == State.COVERED;
  }

  /**
   * Check if the cell is uncovered.
   * 
   * @return true if the cell is uncovered
   */
  public boolean isUncovered() {
    return this.getState() == State.UNCOVERED;
  }

  /**
   * Check if the cell is flagged.
   * 
   * @return true if the cell is flagged
   */
  public boolean isFlagged() {
    return this.getState() == State.FLAGGED;
  }

  /**
   * Check if the cell is a mine.
   * 
   * @return true if the cell is a mine
   */
  public boolean isMine() {
    return this.getValue() == -1;
  }

  /**
   * Get a string representation of the cell.
   * 
   * @return the string representation
   */
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

  /**
   * Toggle the state of the cell between COVERED and FLAGGED.
   */
  public void toggleFlagged() {
    this.state = switch (this.getState()) {
      case FLAGGED -> State.COVERED;
      case COVERED -> State.FLAGGED;
      case UNCOVERED -> State.UNCOVERED;
    };
  }

  /**
   * Connect the cell to a neighbor in the given direction.
   * 
   * @param neighbor the neighbor to connect
   * @param dir      the direction of the neighbor
   */
  public void connect(final Cell neighbor, final Direction dir) {
    this.neighborhood.put(dir, neighbor);
    if (neighbor.getNeighbor(Direction.opposite(dir)) == null) {
      neighbor.connect(this, Direction.opposite(dir));
    }
  }

  /**
   * Uncover the cell.
   */
  public void uncover() {
    if (this.isCovered() && !this.isFlagged()) {
      this.state = State.UNCOVERED;
    }
  }

  /**
   * Get the neighbors of the cell.
   * 
   * @return the neighbors
   */
  public Iterable<Cell> getNeighbors() {
    return this.neighborhood.values();
  }

  /**
   * Get the state of the cell.
   * 
   * @return the state
   */
  private State getState() {
    return this.state;
  }

  /**
   * Get the neighbor in the given direction.
   * 
   * @param d the direction of the neighbor
   * @return the neighbor
   */
  private Cell getNeighbor(final Direction d) {
    return this.neighborhood.get(d);
  }
}
