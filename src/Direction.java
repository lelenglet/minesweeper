public enum Direction {
  NORTH,
  SOUTH,
  EAST,
  WEST,
  NORTH_EAST,
  NORTH_WEST,
  SOUTH_EAST,
  SOUTH_WEST;

  public static Direction opposite(Direction d) {
    switch (d) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      case NORTH_EAST:
        return SOUTH_WEST;
      case NORTH_WEST:
        return SOUTH_EAST;
      case SOUTH_EAST:
        return NORTH_WEST;
      case SOUTH_WEST:
        return NORTH_EAST;
      default:
        return null;
    }
  }
}
