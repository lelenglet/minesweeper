import java.util.Scanner;

public class Tui implements Ui {
  public Grid gamePlate;
  public static final String WHITE_BACKGROUND = "\033[47m"; // white background
  public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
  public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
  public static final String RESET = "\033[0m"; // Text Reset
  public static final String CLEAR = "\033[H\033[2J"; // console clear

  public void displayGrid() { // non testée
    StringBuilder h = new StringBuilder(WHITE_BACKGROUND + BLACK_BOLD + "\u0000 \u0000 \u0000 ");

    for (int i = 0; i < gamePlate.getHeight(); i++) {
      h.append(i + "\uFE0F ");
    }
    h.append("\n"
        + "\u0000 \u0000 \u0000 ");
    for (int i = 0; i < gamePlate.getWidth(); i++) {
      h.append("\u2015 ");
    }
    String header = h.toString();

    System.out.printf(CLEAR);
    System.out.println(header);
    for (int i = 0; i < gamePlate.getHeight(); i++) {
      System.out.print(WHITE_BACKGROUND + BLACK_BOLD);
      System.out.print(i + "\uFE0F \u007C");
      for (int j = 0; j < gamePlate.getWidth; j++) {
        if (gamePlate.get(i, j).getState() == 0) {
          System.out.print(new String(Character.toChars(0x2B1C)));
        } else if (gamePlate.get(i, j).getState() == 1) {
          System.out.print(new String(Character.toChars(0x1F6A9)));
        } else if (gamePlate.get(i, j).getState() == -1 && gamePlate.get(i, j).isMine() == false) {
          System.out.printf("%s", gamePlate.get(x, y).toString());
        } else {
          System.out.print(new String(Character.toChars(0x1F4A5)));
        }
      }
      System.out.println(RESET);
    }
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
