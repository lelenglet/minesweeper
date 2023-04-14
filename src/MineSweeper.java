import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MineSweeper implements MouseListener {
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

  public static void main(final String[] args) {
    final MineSweeper game = new MineSweeper();
    game.showMenu();
  }

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
  private void showMenu() {
    clearScreen();
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
   * Initialize the grid of buttons and set addActionListener
   */
  public void initButtons(int rows, int columns) {
    this.cellButtons = new JButton[rows][columns];
    for (int i = 0; i < cellButtons.length; i++) {
      for (int j = 0; j < cellButtons[0].length; j++) {
        cellButtons[i][j] = new JButton();
        cellButtons[i][j].addMouseListener(this);
        cellButtons[i][j].setBackground(Color.white);
      }
    }
  }

  private void saveGame() {
    try (ObjectOutputStream outputStream =
             new ObjectOutputStream(new FileOutputStream("save.ser"))) {
      outputStream.writeObject(gamePlate);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frame, "Failed to save the object to file");
    }
  }

  private void loadGame() {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("save.ser"))) {
      Plate objet = (Plate) inputStream.readObject();
      this.gamePlate = objet;
      initButtons(gamePlate.getNbRows(), gamePlate.getNbColumns());
      displayGrid();
    } catch (ClassNotFoundException | IOException e) {
      JOptionPane.showMessageDialog(frame, "There is no save to load !");
      showMenu();
    }
  }

  /**
   * Display the grid in game's window
   */
  public void displayGrid() {
    clearScreen();
    pane.setLayout(new GridLayout(gamePlate.getNbRows() + 2, gamePlate.getNbColumns() + 2));

    JButton save = new JButton("Save Game");
    save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveGame();
      }
    });
    pane.add(save);

    for (int j = 0; j < gamePlate.getNbColumns() + 1; j++) {
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
        cellButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 30));
        pane.add(cellButtons[i][j]);
      }
      pane.add(new JLabel());
    }
    for (int j = 0; j < gamePlate.getNbColumns() + 2; j++) {
      pane.add(new JLabel());
    }
  }

  public void gameLost() {
    gamePlate.revealAll();
    displayGrid();
    JOptionPane.showMessageDialog(frame, "You lost !");
    showMenu();
  }

  public void gameWon() {
    JOptionPane.showMessageDialog(frame, "You won !");
    showMenu();
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
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}
}
