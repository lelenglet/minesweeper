import java.util.Scanner;

public class Tui implements Ui {
  public Grid gamePlate;
  public static final String WHITE_BACKGROUND = "\033[47m"; // white background
  public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
  public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
  public static final String RESET = "\033[0m"; // Text Reset
  public static final String CLEAR = "\033[H\033[2J"; // console clear
  private Scanner s = new Scanner(System.in);

  public void showMenu() {
    clearScreen();
    System.out.println(
        BLUE_BOLD + "\n _______   _______ .___  ___.  __  .__   __.  _______  __    __  .______");
    System.out.println(
        "|       \\ |   ____||   \\/   | |  | |  \\ |  | |   ____||  |  |  | |   _  \\");
    System.out.println(
        "|  .--.  ||  |__   |  \\  /  | |  | |   \\|  | |  |__   |  |  |  | |  |_)  |");
    System.out.println(
        "|  |  |  ||   __|  |  |\\/|  | |  | |  . `  | |   __|  |  |  |  | |      /");
    System.out.println(
        "|  '--'  ||  |____ |  |  |  | |  | |  |\\   | |  |____ |  `--'  | |  |\\  \\----.");
    System.out.println(
        "|_______/ |_______||__|  |__| |__| |__| \\__| |_______| \\______/  | _| `._____|");
    System.out.println("\t\t by Lenglet Léa and Chevalier Antoine\t\t\n" + RESET);

    System.out.println("\nEnter the number of rows in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    int h = s.nextInt();
    System.out.println("\nEnter the number of columns in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    int w = s.nextInt();
    System.out.println(
        "\nEnter the purcentage of t in grid between 10 and 50 (press 'q' to quit)\n");
    System.out.printf("> ");
    int m = s.nextInt();
    m = (h * w) * m / 100;
    this.plateau = new Grid(h, w, m);
    this.plateau.initGrid();

    int continu = 0;
    while (continu == 0) {
      continu = jouerCoup();
    }
    if (continu == -1) {
      looseGame();
    } else {
      winGame();
    }
  }

  public void loseGame() {
    System.out.println(
        "____    ____  ______    __    __      __        ______     ______        _______. _______     __  ");
    System.out.println(
        "\\   \\  /   / /  __  \\  |  |  |  |    |  |      /  __  \\   /  __  \\      /       ||   ____|   |  | ");
    System.out.println(
        " \\   \\/   / |  |  |  | |  |  |  |    |  |     |  |  |  | |  |  |  |    |   (----`|  |__      |  | ");
    System.out.println(
        "  \\_    _/  |  |  |  | |  |  |  |    |  |     |  |  |  | |  |  |  |     \\   \\    |   __|     |  | ");
    System.out.println(
        "    |  |    |  `--'  | |  `--'  |    |  `----.|  `--'  | |  `--'  | .----)   |   |  |____    |__| ");
    System.out.println(
        "    |__|     \\______/   \\______/     |_______| \\______/   \\______/  |_______/    |_______|   (__) ");
  }

  public void winGame() {
    System.out.println(
        "____    ____  ______    __    __     ____    __    ____  __  .__   __.     __  ");
    System.out.println(
        "\\   \\  /   / /  __  \\  |  |  |  |    \\   \\  /  \\  /   / |  | |  \\ |  |    |  | ");
    System.out.println(
        " \\   \\/   / |  |  |  | |  |  |  |     \\   \\/    \\/   /  |  | |   \\|  |    |  | ");
    System.out.println(
        "  \\_    _/  |  |  |  | |  |  |  |      \\            /   |  | |  . `  |    |  | ");
    System.out.println(
        "    |  |    |  `--'  | |  `--'  |       \\    /\\    /    |  | |  |\\   |    |__| ");
    System.out.println(
        "    |__|     \\______/   \\______/         \\__/  \\__/     |__| |__| \\__|    (__) ");
  }

  public void displayGrid() { // non testée
    System.out.printf(CLEAR);
    System.out.println(this.header);
    for (int i = 0; i < height; i++) {
      System.out.print(WHITE_BACKGROUND + BLACK_BOLD);
      System.out.print(i + "\uFE0F \u007C");
      for (int j = 0; j < width; j++) {
        if (gamePlate[i][j].getState() == 0) {
          System.out.print(new String(Character.toChars(0x2B1C)));
        } else if (gamePlate[i][j].getState() == 1) {
          System.out.print(new String(Character.toChars(0x1F6A9)));
        } else if (gamePlate[i][j].getState() == -1 && gamePlate[i][j].isMine() == false) {
          System.out.printf("%s", gamePlate[i][j].toString());
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
