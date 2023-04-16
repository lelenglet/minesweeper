import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * The Minesweeper class is used to handle the graphical display of the game.
 */

public class MineSweeper implements MouseListener {
  public static void main(final String[] args) {
    final MineSweeper game = new MineSweeper();
    game.showMenu();
  }

  private JFrame frame;
  private Plate gamePlate;
  private JButton[][] cellButtons;
  private Container pane;

  final private Color[] COLORS = {
      new Color(187, 225, 182), // vert clair
      new Color(216, 226, 188), new Color(237, 227, 194), new Color(255, 221, 200),
      new Color(255, 188, 161), new Color(255, 156, 123), new Color(255, 125, 85),
      new Color(236, 87, 61), new Color(208, 36, 46) // rouge clair
  };

  MineSweeper() {
    this.frame = new JFrame("Minesweeper - Antoine Chevalier | Léa Lenglet");
    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    frame.setSize(1000, 1000);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(Color.gray);
    frame.setVisible(true);
    this.pane = frame.getContentPane();
  }

  /**
   * Execute functions based on the type of mouse button clicked.
   *
   * @param e Mouse-specific event performed
   */
  @Override
  public void mouseClicked(final MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      for (int i = 0; i < gamePlate.getNbRows(); i++) {
        for (int j = 0; j < gamePlate.getNbColumns(); j++) {
          if (e.getSource() == cellButtons[i][j]) { // Si le bouton cliqué est le courant
            final boolean explode = gamePlate.uncoverCell(i, j);
            displayGrid();
            if (explode) {
              gameLost();
            } else if (gamePlate.checkWin()) {
              gameWon();
            }
          }
        }
      }
    } else if (e.getButton() == MouseEvent.BUTTON3) {
      for (int i = 0; i < gamePlate.getNbRows(); i++) {
        for (int j = 0; j < gamePlate.getNbColumns(); j++) {
          if (e.getSource() == cellButtons[i][j]) { // Si le bouton cliqué est le courant
            gamePlate.toggleFlagCell(i, j);
            displayGrid();
          }
        }
      }
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {}

  @Override
  public void mouseExited(final MouseEvent e) {}

  @Override
  public void mousePressed(final MouseEvent e) {}

  @Override
  public void mouseReleased(final MouseEvent e) {}

  /**
   * Clear all elements on the frame.
   */
  private void clearScreen() {
    pane.removeAll();
    pane.setVisible(false);
    pane.setVisible(true);
    pane.revalidate();
  }

  /**
   * Fonction that allow user to enter grid's parameters and init it.
   */
  private void newGame() {
    int nbRows;
    int nbColumns;
    int minesPercentage;

    do {
      nbRows = this.askInt("Enter the number of rows (max 30):");
      if (nbRows == Integer.MAX_VALUE) {
        return;
      }
    } while (nbRows <= 0 || nbRows > 30);

    do {
      nbColumns = this.askInt("Enter the number of columns (max 30):");
      if (nbColumns == Integer.MAX_VALUE) {
        return;
      }
    } while (nbColumns <= 0 || nbColumns > 30);

    do {
      minesPercentage =
          this.askInt("Enter the percentage of mines in the grid (between 10 and 50):");
      if (minesPercentage == Integer.MAX_VALUE) {
        return;
      }
    } while (minesPercentage < 10 || minesPercentage > 50);

    gamePlate = new Plate(nbRows, nbColumns, minesPercentage);
    initButtons(nbRows, nbColumns);
    this.gamePlate.startChrono();
    displayGrid();
  }

  /**
   * Initialize the grid of buttons and set addActionListener.
   */
  private void initButtons(final int row, final int column) {
    this.cellButtons = new JButton[row][column];
    for (int i = 0; i < cellButtons.length; i++) {
      for (int j = 0; j < cellButtons[0].length; j++) {
        cellButtons[i][j] = new JButton();
        cellButtons[i][j].addMouseListener(this);
        cellButtons[i][j].setBackground(Color.white);
      }
    }
  }

  /**
   * Display the grid in game's window.
   */
  private void displayGrid() {
    clearScreen();
    pane.setLayout(new GridLayout(gamePlate.getNbRows() + 2, gamePlate.getNbColumns() + 2));

    for (int j = 0; j < gamePlate.getNbColumns() + 2; j++) {
      pane.add(new JLabel());
    }
    for (int i = 0; i < gamePlate.getNbRows(); i++) {
      pane.add(new JLabel());
      for (int j = 0; j < gamePlate.getNbColumns(); j++) {
        if (gamePlate.getCell(i, j).isUncovered()) {
          if (gamePlate.getCell(i, j).isMine()) {
            cellButtons[i][j].setBackground(Color.red);
            cellButtons[i][j].setEnabled(false);
          } else {
            cellButtons[i][j].setBackground(COLORS[gamePlate.getCell(i, j).getValue()]);
            cellButtons[i][j].setEnabled(false);
          }
        }
        cellButtons[i][j].setText(gamePlate.getCell(i, j).toString());
        cellButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 25));
        pane.add(cellButtons[i][j]);
      }
      pane.add(new JLabel());
    }

    for (int j = 0; j < (gamePlate.getNbColumns() + 2) / 2 - 1; j++) {
      pane.add(new JLabel());
    }
    final Box box = Box.createHorizontalBox();
    box.add(Box.createHorizontalGlue());
    final JButton save = new JButton("Save");
    save.setSize(new Dimension(50, 25));
    save.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        saveGame();
      }
    });
    box.add(save);
    pane.add(box, BorderLayout.SOUTH);
    for (int j = 0; j < (gamePlate.getNbColumns() + 2) / 2 - 1; j++) {
      pane.add(new JLabel());
    }
  }

  /**
   * Display the loss screen.
   */
  private void gameLost() {
    gamePlate.revealAll();
    displayGrid();
    JOptionPane.showMessageDialog(frame, "You lost !");
    showMenu();
  }

  /**
   * Display the win screen.
   */
  private void gameWon() {
    JOptionPane.showMessageDialog(frame, "You won in " + this.gamePlate.getTime() + " seconds!");
    showMenu();
  }

  /**
   * Save the gamePlate in a file.
   */
  private void saveGame() {
    final String m = JOptionPane.showInputDialog("Enter a file name : ");
    this.gamePlate.stopChrono();
    try (ObjectOutputStream outputStream =
             new ObjectOutputStream(new FileOutputStream(m + ".ser"))) {
      outputStream.writeObject(gamePlate);
    } catch (final IOException e) {
      JOptionPane.showMessageDialog(frame, "Failed to save the object to file");
    }
    this.gamePlate.startChrono();
  }

  /**
   * Load the file that contains a gamePlate.
   */
  private void loadGame() {
    final String m = JOptionPane.showInputDialog("Enter a file name : ");
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(m + ".ser"))) {
      final Plate loadedGamePlate = (Plate) inputStream.readObject();
      this.gamePlate = loadedGamePlate;
      initButtons(gamePlate.getNbRows(), gamePlate.getNbColumns());
      this.gamePlate.startChrono();
      displayGrid();
    } catch (ClassNotFoundException | IOException e) {
      JOptionPane.showMessageDialog(frame, "There is no save to load !");
      showMenu();
    }
  }

  /**
   * Ask an integer to the user.
   *
   * @param message the message to display to the user
   * @return the integer entered by the user
   */
  private int askInt(final String message) {
    String input;

    input = JOptionPane.showInputDialog(message);
    if (input == null) {
      return Integer.MAX_VALUE;
    } else {
      try {
        return Integer.parseInt(input);
      } catch (final NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Invalid type of value !");
        return askInt(message);
      }
    }
  }

  /**
   * Display main menu of the game.
   */
  private void showMenu() {
    clearScreen();
    pane.setLayout(new GridLayout(2, 3, 100, 100));
    final JLabel label = new JLabel();
    label.setLayout(new GridLayout(2, 1, 20, 20));
    final JLabel title = new JLabel();
    final Icon icon = new ImageIcon("logo.png");
    title.setIcon(icon);
    title.setPreferredSize(new Dimension(50, 10));
    title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    title.setVerticalAlignment(JLabel.CENTER);

    final JLabel author = new JLabel();
    author.setText("by Lenglet Léa and Chevalier Antoine");
    author.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    author.setHorizontalTextPosition(JLabel.CENTER);
    author.setFont(new Font("Arial", Font.BOLD, 16));
    author.setVerticalAlignment(JLabel.CENTER);
    label.add(title);
    label.add(author);
    label.setPreferredSize(new Dimension(500, 500));

    final JLabel buttons = new JLabel();
    buttons.setLayout(new GridLayout(3, 1, 50, 50));

    final JButton nGame = new JButton("New Game");
    nGame.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        newGame();
      }
    });

    final JButton load = new JButton("Load Game");
    load.setSize(100, 50);

    load.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        loadGame();
      }
    });

    final JButton exit = new JButton("Exit");
    exit.setSize(100, 50);
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        frame.dispose();
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
}
