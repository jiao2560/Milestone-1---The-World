package view;

import controller.GameController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import player.PlayerImpl;
import space.ImSpace;
import world.World;

/**
 * GameView provides the graphical user interface for the game.
 */
public class GameView extends JFrame implements ImGameView {

  private JMenuBar menuBar;
  private JPanel mainPanel;
  private JTextArea gameLog;
  private GameController controller;
  private JLabel turnInfoLabel; // JLabel to display turn information
  
  /**
   * provide the graphical user interface for the "Doctor Lucky's Mansion" game.
   * including the menu bar, turn information display, game log, 
   * It also sets up interaction with the game controller.
   *
   * @param controller the {@code GameController} that manages and interactions
   */
  public GameView(GameController controller) {
    this.controller = controller; // Set the controller

    setTitle("Doctor Lucky's Mansion");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());

    // Menu Bar
    menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenuItem newGame = new JMenuItem("New Game");
    JMenuItem quitGame = new JMenuItem("Quit");
    gameMenu.add(newGame);
    gameMenu.add(quitGame);
    menuBar.add(gameMenu);
    setJMenuBar(menuBar);

    // Main Panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    add(mainPanel, BorderLayout.CENTER);

    // Turn Info Label
    turnInfoLabel = new JLabel("Welcome to Doctor Lucky's Mansion!");
    turnInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    turnInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
    add(turnInfoLabel, BorderLayout.NORTH); // Add label to the top of the frame

    // Game Log
    gameLog = new JTextArea();
    gameLog.setEditable(false);
    gameLog.setLineWrap(true);
    JScrollPane logScrollPane = new JScrollPane(gameLog);
    logScrollPane.setPreferredSize(new Dimension(300, 600));
    add(logScrollPane, BorderLayout.EAST);

    // Add Key Listener for "Look Around"
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_L) { // 'L' for "Look Around"
          lookAround();
        }
      }
    });
    
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) { // 'P' for "Pick Up Item"
          pickUpItem();
        }
      }
    });
      
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) { // 'A' for "Attempt Attack"
          boolean success = controller.attemptAttack();
          if (!success) {
            JOptionPane.showMessageDialog(GameView.this, 
                "Attack failed! Ensure you're in the same room as Doctor Lucky and have a weapon.",
                "Attack Attempt", JOptionPane.WARNING_MESSAGE);
          }
        }
      }
    });

    setFocusable(true);
    requestFocusInWindow();

    // About Screen
    showAboutScreen();

    // Action Listeners for Menu
    newGame.addActionListener(e -> {
      BufferedImage initialMap = controller.getWorldMap(); 
      startNewGame(initialMap);
    });
    quitGame.addActionListener(e -> System.exit(0));
  }
    
  private void pickUpItem() {
    List<String> items = controller.getItemsInCurrentSpace();
    if (items.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No items to pick up!"
          + "", "Pick Up Item", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    String itemName = (String) JOptionPane.showInputDialog(
          this,
          "Select an item to pick up:",
          "Pick Up Item",
          JOptionPane.PLAIN_MESSAGE,
          null,
          items.toArray(),
          items.get(0)
    );

    if (itemName != null && !itemName.isEmpty()) {
      boolean success = controller.pickUpItem(itemName);
      if (!success) {
        JOptionPane.showMessageDialog(this, "Failed to pick up item: "
            + "" + itemName, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public void setNewGameListener(ActionListener actionListener) {
    JMenuItem newGameMenuItem = null;

    // Look for the "New Game" menu item in the menu bar
    for (int i = 0; i < menuBar.getMenuCount(); i++) {
      JMenu menu = menuBar.getMenu(i);
      for (int j = 0; j < menu.getItemCount(); j++) {
        JMenuItem menuItem = menu.getItem(j);
        if (menuItem != null && "New Game".equals(menuItem.getText())) {
          newGameMenuItem = menuItem;
          break;
        }
      }
    }

    if (newGameMenuItem != null) {
      newGameMenuItem.addActionListener(actionListener);
    } else {
      System.err.println("New Game menu item not found.");
    }
  }

  /**
   * Displays the neighbors of the player's current space in a dialog box.
   */
  private void lookAround() {
    List<ImSpace> neighbors = controller.getHumanPlayerNeighbors();
    if (neighbors.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No visible neighbors "
          + "to look at!", "Look Around", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    StringBuilder neighborInfo = new StringBuilder("Visible Neighbors:\n");
    for (ImSpace neighbor : neighbors) {
      neighborInfo.append("- ").append(neighbor.getName()).append("\n");
    }

    JOptionPane.showMessageDialog(this, neighborInfo.toString(), "Look "
        + "Around", JOptionPane.INFORMATION_MESSAGE);
  }
  
  @Override
  public void display() {
    setVisible(true);
  }

  private void showAboutScreen() {
    JPanel aboutPanel = new JPanel();
    aboutPanel.setLayout(new BorderLayout());

    JLabel welcomeLabel = new JLabel("Welcome to Doctor Lucky's Mansion!", SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

    JTextArea aboutText = new JTextArea();
    aboutText.setText("This game was created by Leo.\n"
            + "Instructions:\n"
            + "- Use the menu to start a new game or quit.\n"
            + "- Press 'L' to look around your current position.\n"
            + "- Enjoy the game!");
    aboutText.setEditable(false);
    aboutText.setLineWrap(true);
    aboutText.setWrapStyleWord(true);

    aboutPanel.add(welcomeLabel, BorderLayout.NORTH);
    aboutPanel.add(aboutText, BorderLayout.CENTER);

    mainPanel.removeAll();
    mainPanel.add(aboutPanel, BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
  }

  @Override
  public void startNewGame(BufferedImage worldMap) {
    System.out.println("Starting new game in view. Map dimensions: "
            + worldMap.getWidth() + "x" + worldMap.getHeight());

    gameLog.setText(""); // Clear log
    mainPanel.removeAll();

    JLabel mapLabel = new JLabel(new ImageIcon(worldMap));
    mapLabel.setLayout(null); // Allow custom placement of player icons

    // Add player representations as clickable components
    List<PlayerImpl> players = controller.getPlayers();
    for (PlayerImpl player : players) {
      JButton playerIcon = new JButton(player.getName());
      playerIcon.setBounds(player.getCurrentSpace().getUpperLeftCol() * 50,
                           player.getCurrentSpace().getUpperLeftRow() * 50,
                           50, 50); // Position and size of the icon

      playerIcon.addActionListener(e -> {
        String description = controller.getPlayerDescription(player);
        JOptionPane.showMessageDialog(this, description, "Player "
            + "Details", JOptionPane.INFORMATION_MESSAGE);
      });

      mapLabel.add(playerIcon);
    }

    JScrollPane scrollPane = new JScrollPane(mapLabel);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    turnInfoLabel.setText("Game Started! Waiting for the first turn.");
    add(turnInfoLabel, BorderLayout.NORTH);

    mainPanel.revalidate();
    mainPanel.repaint();
  }
  
  @Override
  public void updateTurnInfo(String info) {
    turnInfoLabel.setText(info);
  }
  
  @Override
  public void updateGameMap(BufferedImage worldMap) {
    mainPanel.removeAll();

    JLabel mapLabel = new JLabel(new ImageIcon(worldMap));
    JScrollPane scrollPane = new JScrollPane(mapLabel);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    mainPanel.revalidate();
    mainPanel.repaint();
  }
  
  @Override
  public void logMessage(String message) {
    gameLog.append(message + "\n");
  }
}
