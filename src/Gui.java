import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class Gui implements Ui, ActionListener, MouseListener {
  JFrame f;
  Plate gamePlate;
  JButton[][] cellButtons;
  Container pane;

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
    pane.setVisible(false);
    pane.setVisible(true);
    pane.revalidate();
  }

  /**
   * Display main menu of the game
   */
  private void menu() {
    pane.setLayout(new GridLayout(2, 3, 100, 100));

    JLabel label = new JLabel();
    label.setLayout(new GridLayout(2, 1, 50, 50));
    JLabel title = new JLabel();
    title.setText("MINESWEEPER");
    title.setHorizontalTextPosition(JLabel.CENTER);
    title.setFont(new Font("Lucida Sans", Font.PLAIN, 40));
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
  }

  /**
   * Fonction that allow user to enter the size of the grid
   */
  public void newGame() {
    clearScreen();

    pane.setLayout(new GridLayout(4, 3, 100, 100));

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
        nbRows = Integer.parseInt(inputRows.getText());
        nbColumns = Integer.parseInt(inputColumns.getText());
        minesPercentage = Integer.parseInt(inputMine.getText());
        gamePlate = new Plate(nbRows, nbColumns, minesPercentage);
        initButtons(nbRows, nbColumns);
        displayGrid();
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

  /**
   * Launch the game in Gui mode
   */
  public void play() {
    this.menu();
  }

  /**
   * Initialize the grid of buttons and set addActionListener
   */
  public void initButtons(int rows, int columns) {
    this.cellButtons = new JButton[rows][columns];
    for (int i = 0; i < cellButtons.length; i++) {
      for (int j = 0; j < cellButtons[0].length; j++) {
        cellButtons[i][j] = new JButton();
        cellButtons[i][j].addActionListener(this);
      }
    }
  }

  private void loadGame() {
    // this.gamePlate = gamePlate.load();
  }

  /**
   * Display the grid in game's window
   */
  public void displayGrid() {
    clearScreen();
    pane.setLayout(new GridLayout(gamePlate.getNbRows(), gamePlate.getNbColumns()));
    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      for (int j = 0; j < gamePlate.getNbColumns(); j++) {
        if (gamePlate.getCell(i, j).getState() == State.UNCOVERED) {
          cellButtons[i][j].setText("" + gamePlate.getCell(i, j).getValue());
          cellButtons[i][j].setEnabled(false);
        }
        pane.add(cellButtons[i][j]);
      }
    }
  }

  public void setGamePlate(Plate gamePlate) {
    this.gamePlate = gamePlate;
  }

  public void gameLost() {
    gamePlate.revealAll();
    displayGrid();
    JOptionPane.showMessageDialog(f, "You lost !");
    f.dispose();
  }

  public void gameWon() {
    JOptionPane.showMessageDialog(f, "You won !");
    f.dispose();
  }

  public static void main(String[] args) {
    Gui ui = new Gui();
    ui.play();
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      for (int j = 0; j < gamePlate.getNbColumns(); j++) {
        if (ae.getSource() == cellButtons[i][j]) {
          boolean explode = gamePlate.getCell(i, j).uncover();
          System.out.println(explode);
          displayGrid();
          if (explode) {
            gameLost();
          } else if (gamePlate.checkWin()) {
            gameWon();
          }
        }
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent me) {
    if (SwingUtilities.isRightMouseButton(me)) {
      // TODO : Handle flagging of mines.
    }
  }

  @Override
  public void mousePressed(MouseEvent me) {
    // Do nothing
  }

  @Override
  public void mouseReleased(MouseEvent me) {
    // Do nothing
  }

  @Override
  public void mouseEntered(MouseEvent me) {
    // Do nothing
  }

  @Override
  public void mouseExited(MouseEvent me) {
    // Do nothing
  }
}