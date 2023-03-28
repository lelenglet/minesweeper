package src;

import java.util.Scanner;

public class Game {
  public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
  public static final String RESET = "\033[0m"; // Text Reset
  private Grid plateau;
  private Scanner s = new Scanner(System.in);

  public void newGame() {
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
  }

  public void jouerCoup() {
    this.plateau.displayGrid();
    System.out.println("Choose a box");
    int x = s.nextInt();
    int y = s.nextInt();
    System.out.println(x + " " + y);
    System.out.println("Choose an action : r for reveal / m for mark");
    char action = s.next().charAt(0);
    System.out.println("c = " + action);
    if (action == 'm') {
      this.plateau.getCell(x, y).setMarqued();
    } else {
      this.plateau.getCell(x, y).revealCell();
    }
  }

  public static void main(String[] args) {
    Game g = new Game();
    g.newGame();
    while (true) {
      g.jouerCoup();
    }
  }
}
