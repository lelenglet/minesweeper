import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;

public class Gui{
  JFrame f;
  Plate gamePlate;
  Container pane;
  private final Scanner input = new Scanner(System.in);


  Gui() {
    this.f = new JFrame("Minesweeper - Antoine Chevalier | Léa Lenglet");
    f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.gray);
    f.setVisible(true);
    this.pane = f.getContentPane();
  }

  /**
   * Clear all elements on the frame
   */
  public void clearScreen() {
    pane.removeAll();
    pane.revalidate();
  }

  private void menu() {
    pane.setLayout(new GridLayout(2, 3,100,100));

    JLabel label = new JLabel();
    label.setLayout(new GridLayout(2, 1, 50, 50));
    JLabel title = new JLabel();
    title.setText("MINESWEEPER");
    title.setHorizontalTextPosition(JLabel.CENTER);
    title.setFont(new Font("Lucida Sans",Font.PLAIN,40));
    title.setVerticalAlignment(JLabel.CENTER);

    JLabel author = new JLabel();
    author.setText("by Lenglet Léa and Chevalier Antoine");
    author.setHorizontalTextPosition(JLabel.CENTER);
    author.setVerticalAlignment(JLabel.CENTER);
    label.add(title);
    label.add(author);
    label.setPreferredSize(new Dimension(500, 500));

    JLabel buttons = new JLabel();
    buttons.setLayout(new GridLayout(3, 1, 50, 50));

    JButton nGame = new JButton("New Game");
    nGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newGame();
      }
    });

    JButton load = new JButton("Load Game");
    load.setSize(100, 50);

    load.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadGame();
      }
    });

    JButton exit = new JButton("Exit");
    exit.setSize(100, 50);
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        f.dispose();
      }
    });

    buttons.add(nGame);
    buttons.add(load);
    buttons.add(exit);

    pane.add(new JLabel());
    pane.add(label);
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(buttons);


    System.out.println("\nNew game or load a saved game ? [n/l] (press q to quit)\n");
    System.out.print("> ");
    char action = input.next().charAt(0);

    if (action == 'l') {
      loadGame();
    } else if (action == 'n') {
      newGame();
    }
  }

  public void newGame() {
    clearScreen();

    pane.setLayout(new GridLayout(4,3,100,100));

    JLabel rows = new JLabel();
    rows.setLayout(new GridLayout(2, 1, 300, 50));
    JLabel columns = new JLabel();
    columns.setLayout(new GridLayout(2, 1, 300, 50));
    JLabel mines = new JLabel();
    mines.setLayout(new GridLayout(2, 1, 300, 50));

    JLabel r = new JLabel();
    r.setText("Enter the number of rows in grid");
    JTextField inputRows = new JTextField();
    inputRows.setSize(50, 50);
    rows.add(r);
    rows.add(inputRows);
    pane.add(rows);

    JLabel c = new JLabel();
    c.setText("Enter the number of columns in grid");
    JTextField inputColumns = new JTextField();
    inputColumns.setSize(50, 50);
    columns.add(c);
    columns.add(inputColumns);

    JLabel p = new JLabel();
    p.setText("Enter the percentage of t in grid");
    JTextField inputMine = new JTextField();
    inputMine.setSize(50, 50);
    mines.add(p);
    mines.add(inputMine);

    JButton submit = new JButton("Next");
    submit.setSize(100, 50);
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int nbRows, nbColumns, minesPercentage;
        nbRows=Integer.parseInt(inputRows.getText());
        nbColumns=Integer.parseInt(inputColumns.getText());
        minesPercentage=Integer.parseInt(inputMine.getText());
        gamePlate = new Plate(nbRows, nbColumns, minesPercentage);
      }
    });

    pane.add(new JLabel());
    pane.add(rows);
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(columns);
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(mines);
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(submit);

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

  private void loadGame() {
    //this.gamePlate = gamePlate.load();
  }

  /**
   * Display the grid in game's window
   */
  public void displayGrid() {
    clearScreen();
    /*f.setLayout(new GridLayout(tab.getHeight(), tab.getWidth()));
    iterator it = tab.iterator();
    for (int i = 0; i < (tab.getHeight() * tab.getWidth()); i++) {
      it.next();
    }*/

    JLabel label = new JLabel();

    label.setLayout(new GridLayout(gamePlate.getNbRows(), gamePlate.getNbColumns()));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Cell c = new Cell();
        JButton grid = new JButton();
        grid.setSize(20, 20);
        grid.setBackground(Color.lightGray);
        if (c.getState() == State.UNCOVERED) {
          grid.setText("" + c.getValue());
          grid.setBackground(Color.LIGHT_GRAY);
          grid.setEnabled(false);
        }
        label.add(grid);
      }
    }
    pane.add(label);
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(new JLabel());
  }

  public void setGamePlate(Plate gamePlate) {
    this.gamePlate = gamePlate;
  }

  public void gameLost() {
    clearScreen();

    f.setLayout(null);
    JLabel title = new JLabel();
    title.setText("MINESWEEPER");
    title.setHorizontalTextPosition(JLabel.CENTER);
    title.setFont(new Font("Lucida Sans",Font.PLAIN,40));
    title.setVerticalAlignment(JLabel.CENTER);

  }

  public void gameWon() {}

  public static void main(String[] args) {
    Gui ui = new Gui();
    ui.play();
  }
}
