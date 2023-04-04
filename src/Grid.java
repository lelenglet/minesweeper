import java.util.Random;

public class Grid {
  public static final String WHITE_BACKGROUND = "\033[47m"; // white background
  public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
  public static final String RESET = "\033[0m"; // reset background color
  public static final String CLEAR = "\033[H\033[2J"; // console clear
  private final GridMatrix gamePlate;
  private final int nbMines;
  private final String header;

  Grid(final int nbRows, final int nbColumns, final int nbMines) {
    this.gamePlate = new GridMatrix(nbRows, nbColumns);
    this.nbMines = nbMines;
    this.header = createHeader();
  }

  public Cell getCell(final int x, final int y) {
    return gamePlate.get(x, y);
  }

  public void initGrid() {
    for (int i = 0; i < this.gamePlate.getNbRows(); i++) {
      for (int j = 0; j < this.gamePlate.getNbColumns(); j++) {
        gamePlate.set(i, j, new Cell());
      }
    }
    this.connectAll();
    this.addMine();
    this.updateGrid();
  }

  public void connectAll() {
    for (int i = 0; i < this.gamePlate.getNbRows(); i++) {
      for (int j = 0; j < this.gamePlate.getNbColumns(); j++) {
        if (i > 0) {
          this.gamePlate.get(i, j).connect(this.gamePlate.get(i - 1, j), Direction.NORTH);
          if (j > 0) {
            this.gamePlate.get(i, j).connect(
                this.gamePlate.get(i - 1, j - 1), Direction.NORTH_WEST);
          }
          if (j < this.gamePlate.getNbColumns() - 1) {
            this.gamePlate.get(i, j).connect(
                this.gamePlate.get(i - 1, j + 1), Direction.NORTH_EAST);
          }
        }
        if (j < this.gamePlate.getNbColumns() - 1) {
          this.gamePlate.get(i, j).connect(this.gamePlate.get(i, j + 1), Direction.EAST);
        }
      }
    }
  }

  public void addMine() {
    int cpt = 0;
    final Random r = new Random();
    while (cpt < this.nbMines) {
      final int x = r.nextInt(this.gamePlate.getNbRows());
      final int y = r.nextInt(this.gamePlate.getNbColumns());
      if (this.gamePlate.get(x, y).getValue() == 0) {
        this.gamePlate.get(x, y).setMine();
        cpt++;
      }
    }
  }

  public void updateGrid() {
    for (int i = 0; i < this.gamePlate.getNbRows(); i++) {
      for (int j = 0; j < this.gamePlate.getNbColumns(); j++) {
        gamePlate.get(i, j).setValue();
      }
    }
  }

  public boolean checkWin() {
    for (int i = 0; i < this.gamePlate.getNbRows(); i++) {
      for (int j = 0; j < this.gamePlate.getNbColumns(); j++) {
        if (gamePlate.get(i, j).getValue() > 0 && gamePlate.get(i, j).getState() == State.COVERED) {
          return false;
        }
      }
    }
    return true;
  }

  public void displayGrid() { // non testée
    System.out.printf(CLEAR);
    System.out.println(this.header);
    for (int i = 0; i < this.gamePlate.getNbRows(); i++) {
      System.out.print(WHITE_BACKGROUND + BLACK_BOLD);
      System.out.print(i + "\uFE0F \u007C");
      for (int j = 0; j < this.gamePlate.getNbColumns(); j++) {
        if (gamePlate.get(i, j).getState() == State.COVERED) {
          System.out.print(new String(Character.toChars(0x2B1C)));
        } else if (gamePlate.get(i, j).getState() == State.FLAGGED) {
          System.out.print(new String(Character.toChars(0x1F6A9)));
        } else if (gamePlate.get(i, j).getState() == State.UNCOVERED
            && gamePlate.get(i, j).isMine() == false) {
          System.out.printf("%s", gamePlate.get(i, j).toString());
        } else {
          System.out.print(new String(Character.toChars(0x1F4A5)));
        }
      }
      System.out.println(RESET);
    }
  }

  private String createHeader() {
    final StringBuilder header =
        new StringBuilder(WHITE_BACKGROUND + BLACK_BOLD + "\u0000 \u0000 \u0000 ");

    for (int i = 0; i < this.gamePlate.getNbColumns(); i++) {
      header.append(i + "\uFE0F ");
    }
    header.append("\n"
        + "\u0000 \u0000 \u0000 ");
    for (int i = 0; i < this.gamePlate.getNbColumns(); i++) {
      header.append("\u2015 ");
    }
    return header.toString();
  }

  public void revealAll() {
    for (int i = 0; i < this.gamePlate.getNbRows(); i++) {
      for (int j = 0; j < this.gamePlate.getNbColumns(); j++) {
        gamePlate.get(i, j).reveal();
      }
    }
    this.displayGrid();
  }
}
