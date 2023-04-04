import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Gui {
  JFrame f;
  Grid gamePlate;
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

    label.setLayout(new GridLayout(gamePlate.getHeight(), gamePlate.getWidth()));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Cell c = new Cell(2);
        JButton grid = new JButton();
        grid.setSize(20, 20);
        grid.setBackground(Color.lightGray);
        if (c.getState() == -1) {
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

  public void displayLose() {
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
        Cell c = new Cell(2);
        JButton grid = new JButton();
        grid.setSize(20, 20);
        if (c.getState() == -1 /*State.UNCOVER*/) {
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

  public static void main(String[] args) {
    Gui game = new Gui();
    game.showMenu();
  }
}
