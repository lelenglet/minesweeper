
/**
 * Enumeration of the 8 directions in which a cell can be connected to its neighbors.
 */
public enum Direction {
  NORTH,
  SOUTH,
  EAST,
  WEST,
  NORTH_EAST,
  NORTH_WEST,
  SOUTH_EAST,
  SOUTH_WEST;

  /**
   * Returns the opposite direction.
   *
   * @param d the direction
   * @return the opposite direction
   */
  public static Direction opposite(final Direction d) {
    return switch (d) {
      case NORTH -> SOUTH;
      case SOUTH -> NORTH;
      case EAST -> WEST;
      case WEST -> EAST;
      case NORTH_EAST -> SOUTH_WEST;
      case NORTH_WEST -> SOUTH_EAST;
      case SOUTH_EAST -> NORTH_WEST;
      case SOUTH_WEST -> NORTH_EAST;
    };
  }
}
