import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Grid implements Savable {
  private int height;
  private int width;
  private Cell[][] gamePlate;
  private int nbMine;
  private String header;
  public static final String WHITE_BACKGROUND = "\033[47m"; // white background
  public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
  public static final String RESET = "\033[0m"; // reset background color
  public static final String CLEAR = "\033[H\033[2J"; // console clear

  Grid(int h, int w, int m) {
    this.height = h;
    this.width = w;
    this.gamePlate = new Cell[h][w];
    this.nbMine = m;
    this.header = createHeader();
  }

  public Cell getCell(int x, int y) {
    return gamePlate[x][y];
  }

  public void initGrid() {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        gamePlate[i][j] = new Cell();
      }
    }
    this.initLink();
    this.addMine();
    this.updateGrid();
  }

  public void initLink() {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (i == 0) {
          if (j == 0) {
            gamePlate[i][j].addNeighbor(gamePlate[i][j + 1], Direction.EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j + 1], Direction.SOUTH_EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j], Direction.SOUTH);
          } else if (j == this.width - 1) {
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j], Direction.SOUTH);
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j - 1], Direction.SOUTH_WEST);
            gamePlate[i][j].addNeighbor(gamePlate[i][j - 1], Direction.WEST);
          } else if (j < this.width - 1 && j > 0) {
            gamePlate[i][j].addNeighbor(gamePlate[i][j + 1], Direction.EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j + 1], Direction.SOUTH_EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j], Direction.SOUTH);
            gamePlate[i][j].addNeighbor(gamePlate[i + 1][j - 1], Direction.SOUTH_WEST);
            gamePlate[i][j].addNeighbor(gamePlate[i][j - 1], Direction.WEST);
          }
        } else if (i == this.height - 1) {
          if (j == 0) {
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j], Direction.NORTH);
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j + 1], Direction.NORTH_EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i][j + 1], Direction.EAST);
          } else if (j == this.width - 1) {
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j], Direction.NORTH);
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j - 1], Direction.NORTH_WEST);
            gamePlate[i][j].addNeighbor(gamePlate[i][j - 1], Direction.WEST);
          } else if (j < this.width - 1 && j > 0) {
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j], Direction.NORTH);
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j + 1], Direction.NORTH_EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i][j + 1], Direction.EAST);
            gamePlate[i][j].addNeighbor(gamePlate[i][j - 1], Direction.WEST);
            gamePlate[i][j].addNeighbor(gamePlate[i - 1][j - 1], Direction.NORTH_WEST);
          }
        } else if (j == 0) {
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j], Direction.NORTH);
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j + 1], Direction.NORTH_EAST);
          gamePlate[i][j].addNeighbor(gamePlate[i][j + 1], Direction.EAST);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j + 1], Direction.SOUTH_EAST);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j], Direction.SOUTH);
        } else if (j == this.width - 1) {
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j], Direction.NORTH);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j], Direction.SOUTH);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j - 1], Direction.SOUTH_WEST);
          gamePlate[i][j].addNeighbor(gamePlate[i][j - 1], Direction.WEST);
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j - 1], Direction.NORTH_WEST);
        } else {
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j], Direction.NORTH);
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j - 1], Direction.NORTH_WEST);
          gamePlate[i][j].addNeighbor(gamePlate[i][j - 1], Direction.WEST);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j - 1], Direction.SOUTH_WEST);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j], Direction.SOUTH);
          gamePlate[i][j].addNeighbor(gamePlate[i + 1][j + 1], Direction.SOUTH_EAST);
          gamePlate[i][j].addNeighbor(gamePlate[i][j + 1], Direction.EAST);
          gamePlate[i][j].addNeighbor(gamePlate[i - 1][j + 1], Direction.NORTH_EAST);
        }
      }
    }
  }

  public void addMine() {
    int cpt = 0;
    Random r = new Random();
    while (cpt < this.nbMine) {
      int x = r.nextInt(this.height);
      int y = r.nextInt(this.width);
      if (this.gamePlate[x][y].getValue() == 0) {
        this.gamePlate[x][y].setValue(-1);
        cpt++;
      }
    }
  }

  public void updateGrid() {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        gamePlate[i][j].setValue();
      }
    }
  }

  private String createHeader() {
    StringBuilder header =
        new StringBuilder(WHITE_BACKGROUND + BLACK_BOLD + "\u0000 \u0000 \u0000 ");

    for (int i = 0; i < this.width; i++) {
      header.append(i + "\uFE0F ");
    }
    header.append("\n"
        + "\u0000 \u0000 \u0000 ");
    for (int i = 0; i < this.width; i++) {
      header.append("\u2015 ");
    }
    return header.toString();
  }

  public boolean checkWin() {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (gamePlate[i][j].getValue() > 0 && gamePlate[i][j].getState() == State.COVERED) {
          return false;
        }
      }
    }
    return true;
  }

  public void displayGrid() { // non testée
    System.out.printf(CLEAR);
    System.out.println(this.header);
    for (int i = 0; i < height; i++) {
      System.out.print(WHITE_BACKGROUND + BLACK_BOLD);
      System.out.print(i + "\uFE0F \u007C");
      for (int j = 0; j < width; j++) {
        if (gamePlate[i][j].getState() == State.COVERED) {
          System.out.print(new String(Character.toChars(0x2B1C)));
        } else if (gamePlate[i][j].getState() == State.FLAGGED) {
          System.out.print(new String(Character.toChars(0x1F6A9)));
        } else if (gamePlate[i][j].getState() == State.UNCOVERED
            && gamePlate[i][j].isMine() == false) {
          System.out.printf("%s", gamePlate[i][j].toString());
        } else {
          System.out.print(new String(Character.toChars(0x1F4A5)));
        }
      }
      System.out.println(RESET);
    }
  }

  public void save() {
    try {
      FileOutputStream fileOut = new FileOutputStream(filepath);
      ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
      objectOut.writeObject(this);
      objectOut.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Object load() {
    try {
      FileInputStream fileInStr = new FileInputStream(filepath);
      ObjectInputStream objInStr = new ObjectInputStream(fileInStr);
      Object building = objInStr.readObject();
      objInStr.close();
      fileInStr.close();
      return building;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    throw new IllegalStateException("Pas de sauvegarde trouvée");
  }
}
