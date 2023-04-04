import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Gui {
  JFrame f;
  Plate gamePlate;
  Container pane;

  Gui() {
    this.f = new JFrame("Démineur - Antoine Chevalier | Léa Lenglet");
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

  public void displayMenu() {
    pane.setLayout(new GridLayout(2, 3));

    JLabel label = new JLabel();
    label.setText("DEMINEUR \n by Lenglet Léa and Chevalier Antoine");
    label.setPreferredSize(new Dimension(500, 500));

    JLabel plateSpec = new JLabel();
    plateSpec.setLayout(new GridLayout(3, 1, 50, 50));

    JLabel l = new JLabel();
    JTextField rows = new JTextField();
    rows.setSize(50, 50);
    l.add(rows);
    JTextField columns = new JTextField();
    columns.setSize(50, 50);
    l.add(columns);
    JTextField percentageMine = new JTextField();
    percentageMine.setSize(50, 50);
    l.add(percentageMine);

    JLabel buttons = new JLabel();
    buttons.setLayout(new GridLayout(3, 1, 50, 50));

    JButton nGame = new JButton("New Game");
    nGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int[] parameters = new int[3];
        parameters[0] = Integer.parseInt(rows.getText());
        parameters[1] = Integer.parseInt(columns.getText());
        parameters[2] = Integer.parseInt(percentageMine.getText());
        gamePlate = new Plate(parameters[0], parameters[1], parameters[2]);
      }
    });

    JButton load = new JButton("Load Game");
    load.setSize(100, 50);

    load.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // gamePlate.load();
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

    plateSpec.add(rows);
    plateSpec.add(columns);
    plateSpec.add(percentageMine);

    pane.add(new JLabel());
    pane.add(label);
    pane.add(new JLabel());
    pane.add(new JLabel());
    pane.add(buttons);
    pane.add(plateSpec);
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
    /*f.setLayout(new GridLayout(tab.getHeight(), tab.getWidth()));
    iterator it = tab.iterator();
    for (int i = 0; i < (tab.getHeight() * tab.getWidth()); i++) {
      it.next();
    }*/
    f.setLayout(new GridLayout(3, 3));
    f.add(new JLabel());
    f.add(new JLabel());
    f.add(new JLabel());
    f.add(new JLabel());

    // ImageIcon image = new ImageIcon("manga.jpg");
    JLabel label = new JLabel();
    // label.setIcon(image);
    label.setBackground(Color.blue);

    label.setLayout(new GridLayout(3, 3));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Cell c = new Cell();
        JButton grid = new JButton();
        grid.setSize(20, 20);
        if (c.getState() == State.UNCOVERED) {
          grid.setText("" + c.getValue());
          grid.setEnabled(false);
        }
        label.add(grid);
      }
    }
    f.add(label);
    f.add(new JLabel());
    f.add(new JLabel());
    f.add(new JLabel());
    f.add(new JLabel());
  }

  public void gameWon() {}

  public static void main(String[] args) {
    Gui ui = new Gui();
    ui.displayMenu();
  }
}
