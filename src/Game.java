import java.util.Scanner;

public class Game {
  public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
  public static final String RESET = "\033[0m"; // Text Reset
  private Grid plateau;
  private Scanner s = new Scanner(System.in);

  public void newGame() {
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
    int h = s.nextInt();
    System.out.println("\nEnter the number of columns in grid (press 'q' to quit)\n");
    System.out.printf("> ");
    int w = s.nextInt();
    System.out.println("\nEnter the purcentage of t in grid (press 'q' to quit)\n");
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

  public int jouerCoup() {
    int returnValue = 0;
    this.plateau.displayGrid();
    System.out.println("Choose a box (x y)");
    int x = s.nextInt();
    int y = s.nextInt();
    System.out.println("Choose an action : r for reveal / m for mark");
    char action = s.next().charAt(0);
    if (action == 'm') {
      this.plateau.getCell(x, y).setMarqued();
    } else {
      boolean mine = this.plateau.getCell(x, y).revealCell();
      if (!mine) {
        returnValue = -1;
      } else {
        if (this.plateau.checkWin()) {
          returnValue = 1;
        }
      }
    }
    return returnValue;
  }

  public void looseGame() {
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

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public static void main(String[] args) {
    Game g = new Game();
    g.newGame();
  }
}
