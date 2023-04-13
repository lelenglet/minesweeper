import java.io.*;
import java.util.Scanner;

public class Tui implements Ui {
  public static final String WHITE_BACKGROUND = "\033[47m"; // white background
  public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
  public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
  public static final String RESET = "\033[0m"; // Text Reset
  public static final String CLEAR = "\033[H\033[2J"; // console clear
  public String header;
  public Plate gamePlate;
  private final Scanner input = new Scanner(System.in);

  public Tui() {}

  private void menu() {
    this.clearScreen();

    System.out.println("\nNew game or load a saved game ? [n/l] (press q to quit)\n");
    System.out.print("> ");
    char action = input.next().charAt(0);

    if (action == 'l') {
      loadGame();
    } else if (action == 'n') {
      newGame();
    }
  }

  public void play() {
    this.menu();

    int continu = 0;
    while (continu == 0) {
      continu = jouerCoup();
    }
    if (continu == -1) {
      this.gamePlate.revealAll();
      this.gameLost();
    } else {
      this.gameWon();
    }
  }

  public int jouerCoup() {
    int returnValue = 0;
    this.displayGrid();
    System.out.println("Choose a box (x y)");
    final int x = input.nextInt();
    final int y = input.nextInt();
    System.out.println("Choose an action : r for reveal / m for mark");
    final char action = input.next().charAt(0);
    if (action == 'm') {
      this.gamePlate.getCell(x, y).toggleFlagged();
    } else if (action == 'a') {
      this.gamePlate.revealAll();
    } else if (action == 's') {
      saveGame();
    } else {
      final boolean explode = this.gamePlate.getCell(x, y).uncover();
      if (explode) {
        returnValue = -1;
      } else {
        if (this.gamePlate.checkWin()) {
          returnValue = 1;
        }
      }
    }
    return returnValue;
  }

  private void saveGame() {
    try {
      FileOutputStream fos = new FileOutputStream("objectSave.ser");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(gamePlate);
      oos.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadGame() {
    try {
      FileInputStream fis = new FileInputStream("objectSave.ser");
      ObjectInputStream ois = new ObjectInputStream(fis);
      Plate objet = (Plate) ois.readObject();
      ois.close();
      fis.close();
      this.gamePlate = objet;
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  private void displayGrid() { // non testée
    this.header = createHeader();

    System.out.printf(CLEAR);
    System.out.println(header);
    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      System.out.print(WHITE_BACKGROUND + BLACK_BOLD);
      System.out.print(i + "\uFE0F \u007C");
      for (int j = 0; j < gamePlate.getNbColumns(); j++) {
        System.out.print(gamePlate.getCell(i, j).toString());
      }
      System.out.println(RESET);
    }
  }

  private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private void gameLost() {
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

  private void gameWon() {
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
  private void newGame() {
    this.clearScreen();
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
    int nbRows = input.nextInt();
    System.out.println("\nEnter the number of columns in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    int nbColumns = input.nextInt();
    System.out.println("\nEnter the percentage of t in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    int minesPercentage = input.nextInt();
    this.gamePlate = new Plate(nbRows, nbColumns, minesPercentage);
  }
  private String createHeader() {
    final StringBuilder headerBuilder =
        new StringBuilder(WHITE_BACKGROUND + BLACK_BOLD + "\u0000 \u0000 \u0000 \u0000 ");

    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      headerBuilder.append(i + "\uFE0F");
    }
    headerBuilder.append("\n"
        + "\u0000 \u0000 \u0000 \u0000 ");
    for (int i = 0; i < gamePlate.getNbColumns(); i++) {
      headerBuilder.append("\u2015 ");
    }
    return headerBuilder.toString();
  }
}
