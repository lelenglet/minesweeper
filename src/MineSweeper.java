import java.util.Scanner;

public class MineSweeper {
  public static void main(final String[] args) {
    final MineSweeper g = new MineSweeper();
    g.newGame();
  }

  private Ui ui;
  private Plate gamePlate;
  private final Scanner input = new Scanner(System.in);

  public MineSweeper() {
    chooseUi();
  }

  public void newGame() {
    final int[] parameters = this.ui.displayMenu();

    this.gamePlate = new Plate(parameters[0], parameters[1], parameters[2]);
    this.ui.setGamePlate(gamePlate);

    int continu = 0;
    while (continu == 0) {
      continu = jouerCoup();
    }
    if (continu == -1) {
      this.gamePlate.revealAll();
      this.ui.gameLost();
    } else {
      this.ui.gameWon();
    }
  }

  public int jouerCoup() {
    int returnValue = 0;
    this.ui.displayGrid();
    System.out.println("Choose a box (x y)");
    final int x = input.nextInt();
    final int y = input.nextInt();
    System.out.println("Choose an action : r for reveal / m for mark");
    final char action = input.next().charAt(0);
    if (action == 'm') {
      this.gamePlate.getCell(x, y).toggleFlagged();
    } else if (action == 'a') {
      this.gamePlate.revealAll();
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

  private void chooseUi() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("\n Launch GUI ? [y/n] (press q to quit)\n");
    System.out.print("> ");

    final char choice = input.next().charAt(0);
    if (choice == 'y') {
      this.ui = new Gui();
    } else if (choice == 'n') {
      this.ui = new Tui();
    } else if (choice == 'q') {
      return;
    } else {
      throw new IllegalArgumentException();
    }
  }
}
