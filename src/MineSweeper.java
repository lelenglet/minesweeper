import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MineSweeper implements MouseListener {
  public static void main(final String[] args) {
    final MineSweeper game = new MineSweeper();
    game.showMenu();
  }

  JFrame frame;
  Plate gamePlate;
  JButton[][] cellButtons;
  Container pane;

  Color[] colors = {
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

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      for (int i = 0; i < gamePlate.getNbRows(); i++) {
        for (int j = 0; j < gamePlate.getNbColumns(); j++) {
          if (e.getSource() == cellButtons[i][j]) { // Si le bouton cliqué est le courant
            boolean explode = gamePlate.getCell(i, j).uncover();
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
            gamePlate.getCell(i, j).toggleFlagged();
            displayGrid();
          }
        }
      }
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

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
      minesPercentage = this.askInt(
          "Enter the percentage of mines in the grid (between 10 and 50):");
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
  private void initButtons(int row, int column) {
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
            cellButtons[i][j].setBackground(colors[gamePlate.getCell(i, j).getValue()]);
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
    Box box = Box.createHorizontalBox();
    box.add(Box.createHorizontalGlue());
    JButton save = new JButton("Save");
    save.setSize(new Dimension(50, 25));
    save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveGame();
      }
    });
    box.add(save);
    pane.add(box, BorderLayout.SOUTH);
    for (int j = 0; j < (gamePlate.getNbColumns() + 2) / 2 - 1; j++) {
      pane.add(new JLabel());
    }
  }

  private void gameLost() {
    gamePlate.revealAll();
    displayGrid();
    JOptionPane.showMessageDialog(frame, "You lost !");
    showMenu();
  }

  private void gameWon() {
    JOptionPane.showMessageDialog(frame, "You won in "
        + this.gamePlate.getTime() / 1000 + " seconds!");
    showMenu();
  }

  private int askInt(String message) {
    String input;

    input = JOptionPane.showInputDialog(message);
    if (input == null) {
      return Integer.MAX_VALUE;
    } else {
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
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
    JLabel label = new JLabel();
    label.setLayout(new GridLayout(2, 1, 20, 20));
    JLabel title = new JLabel();
    Icon icon = new ImageIcon("logo.png");
    title.setIcon(icon);
    title.setPreferredSize(new Dimension(50, 10));
    title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    title.setVerticalAlignment(JLabel.CENTER);

    JLabel author = new JLabel();
    author.setText("by Lenglet Léa and Chevalier Antoine");
    author.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    author.setHorizontalTextPosition(JLabel.CENTER);
    author.setFont(new Font("Arial", Font.BOLD, 16));
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

  /**
   * Save the gamePlate in a file
   */
  private void saveGame() {
    String m = JOptionPane.showInputDialog("Enter a file name : ");
    try (ObjectOutputStream outputStream = new ObjectOutputStream(
        new FileOutputStream(m + ".ser"))) {
      this.gamePlate.stopChrono();
      outputStream.writeObject(gamePlate);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frame, "Failed to save the object to file");
    }
  }

  /**
   * Load the file that contains a gamePlate
   */
  private void loadGame() {
    String m = JOptionPane.showInputDialog("Enter a file name : ");
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(m + ".ser"))) {
      Plate loadedGamePlate = (Plate) inputStream.readObject();
      this.gamePlate = loadedGamePlate;
      initButtons(gamePlate.getNbRows(), gamePlate.getNbColumns());
      this.gamePlate.startChrono();
      displayGrid();
    } catch (ClassNotFoundException | IOException e) {
      JOptionPane.showMessageDialog(frame, "There is no save to load !");
      showMenu();
    }
  }
}
