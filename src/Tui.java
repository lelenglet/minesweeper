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

  public void displayGrid() { // non testée
    this.header = createHeader();

    System.out.printf(CLEAR);
    System.out.println(header);
    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      System.out.print(WHITE_BACKGROUND + BLACK_BOLD);
      System.out.print(i + "\uFE0F \u007C");
      for (int j = 0; j < gamePlate.getNbColumns(); j++) {
        if (gamePlate.getCell(i, j).getState() == State.COVERED) {
          System.out.print(new String(Character.toChars(0x2B1C)));
        } else if (gamePlate.getCell(i, j).getState() == State.FLAGGED) {
          System.out.print(new String(Character.toChars(0x1F6A9)));
        } else if (gamePlate.getCell(i, j).getState() == State.UNCOVERED
            && gamePlate.getCell(i, j).isMine() == false) {
          System.out.printf("%s", gamePlate.getCell(i, j).toString());
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

  public void gameLost() {
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

  public void gameWon() {
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

  public int[] displayMenu() {
    final int[] parameters = new int[3];

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
    parameters[0] = input.nextInt();
    System.out.println("\nEnter the number of columns in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    parameters[1] = input.nextInt();
    System.out.println("\nEnter the percentage of t in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    parameters[2] = input.nextInt();

    return parameters;
  }

  private String createHeader() {
    final StringBuilder headerBuilder =
        new StringBuilder(WHITE_BACKGROUND + BLACK_BOLD + "\u0000 \u0000 \u0000 ");

    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      headerBuilder.append(i + "\uFE0F ");
    }
    headerBuilder.append("\n"
        + "\u0000 \u0000 \u0000 ");
    for (int i = 0; i < gamePlate.getNbColumns(); i++) {
      headerBuilder.append("\u2015 ");
    }
    return headerBuilder.toString();
  }

  public void setGamePlate(Plate gamePlate) {
    this.gamePlate = gamePlate;
  }
}
