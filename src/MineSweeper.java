import java.util.Scanner;

public class MineSweeper {
  public static void main(final String[] args) {
    final MineSweeper g = new MineSweeper();
    g.run();
  }

  private Ui ui;
  private final Scanner input = new Scanner(System.in);

  public void run() {
    this.chooseUi();
    this.ui.play();
  }

  private void chooseUi() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    System.out.println("\nLaunch GUI ? [y/n] (press q to quit)\n");
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
