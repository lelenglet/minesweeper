package src;
public class Game {
  public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
  public static final String RESET = "\033[0m"; // Text Reset
  private Grid plateau;

  Game(int hauteur, int largeur, int nbMines) {
    this.plateau = new Grid(hauteur, largeur, nbMines);
  }

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
    System.out.println("\n Choose a level : \n");

    System.out.println("  _       ___       ____      _ _        ___ ");
    System.out.println(" / |     |_  )     |__ /     | | |      | __|");
    System.out.println(" | |      / /       |_ \\     |_  _|     |__ \\");
    System.out.println(" |_|     /___|     |___/       |_|      |___/");

    System.out.println("\nEnter the number of the level (press 'q' to quit)\n");
    System.out.println("> ");
  }
  public static void main(String[] args) {
    Game g = new Game(1, 1, 1);
    g.newGame();
  }
}
