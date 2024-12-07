package driver;

import character.TargetCharacter;
import controller.GameController;
import item.ImItem;
import item.Item;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import pet.Pet;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import view.AboutView;
import view.GameView;
import world.World;

/**
 * The {@code GameDriver} class is the entry point for the game application.
 * <p>
 * It initializes the game by loading a default world .
 * The class sets up the game logic, players, through the "About View."
 * </p>
 */
public class GameDriver {
  /**
   * The main method initializes the game application.
   * <p>
   * It sets up the graphical user interface.
   * to either load a default game world or upload a custom configuration file.
   * and view are configured and displayed based on the chosen world.
   * </p>
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(
              GameDriver.class.getResourceAsStream("/driver/mansion.txt")))) {

        // Parse the default world from the file
        World defaultWorld = parseWorld(reader);

        // Create the About View
        AboutView aboutView = new AboutView(
            () -> {
              // Create the game view for the default world
              GameController controller = new GameController(defaultWorld, 20, null);

              // Add players to the controller
              controller.addPlayer(new PlayerImpl("Player1", defaultWorld.getSpace(1), 5, false));
              controller.addPlayer(new PlayerImpl("AI", defaultWorld.getSpace(2), 5, true));
              defaultWorld.updatePlayerLocations(); // Ensure players are mapped to spaces

              GameView gameView = new GameView(controller);
              controller.setView(gameView);

              // Generate the initial map
              BufferedImage initialMap = defaultWorld.generateMap();
              gameView.startNewGame(initialMap);

              // Show the game view
              gameView.display();
            },

            (File uploadedFile) -> {
              try (BufferedReader fileReader = new BufferedReader(new FileReader(uploadedFile))) {
                // Parse the uploaded world
                World newWorld = parseWorld(fileReader);
  
                // Create the game view for the uploaded world
                GameController controller = new GameController(newWorld, 20, null);
  
                // Add players to the controller
                controller.addPlayer(new PlayerImpl("Player1"
                    + "", newWorld.getSpace(2), 5, false)); // Human Player
                controller.addPlayer(new PlayerImpl("AI"
                    + "", newWorld.getSpace(3), 5, true)); // AI Player
  
                GameView gameView = new GameView(controller);
                controller.setView(gameView);
  
                // Generate the initial map
                BufferedImage initialMap = newWorld.generateMap();
                gameView.startNewGame(initialMap);
  
                // Show the game view
                gameView.display();
              } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to load the world file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
              }
            });

        // Show the About View
        aboutView.setVisible(true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private static World parseWorld(BufferedReader reader) throws IOException {
    // Read world description
    String[] worldInfo = reader.readLine().split(" ");
    int rows = Integer.parseInt(worldInfo[0]);
    int cols = Integer.parseInt(worldInfo[1]);
    String worldName = joinStrings(worldInfo, 2, worldInfo.length);

    System.out.println("World Info: rows=" + rows + ", cols=" + cols + ", name=" + worldName);

    // Read target character information
    String[] characterInfo = reader.readLine().split(" ");
    int characterHealth = Integer.parseInt(characterInfo[0]);
    String characterName = joinStrings(characterInfo, 1, characterInfo.length);

    TargetCharacter targetCharacter = new TargetCharacter(characterName, characterHealth, 0);
    System.out.println("Parsed Target Character: name="
        + "" + targetCharacter.getName() + ", health=" + targetCharacter.getHealth());

    // Read pet information
    String petName = reader.readLine().trim();
    System.out.println("Pet Name: " + petName);

    // Parse spaces
    int numberOfSpaces = Integer.parseInt(reader.readLine());
    System.out.println("Number of Spaces: " + numberOfSpaces);

    List<ImSpace> spaces = new ArrayList<>();
    for (int i = 0; i < numberOfSpaces; i++) {
      String line = reader.readLine().trim().replaceAll(" +", " ");
      String[] spaceInfo = line.split(" ");

      int upperLeftRow = Integer.parseInt(spaceInfo[0]);
      int upperLeftCol = Integer.parseInt(spaceInfo[1]);
      int lowerRightRow = Integer.parseInt(spaceInfo[2]);
      int lowerRightCol = Integer.parseInt(spaceInfo[3]);
      String spaceName = joinStrings(spaceInfo, 4, spaceInfo.length);

      System.out.println("Parsed Space: name=" + spaceName + ", upperLeft=("
          + "" + upperLeftRow + upperLeftCol + "), lowerRight=("
              + "" + lowerRightRow + ", " + lowerRightCol + ")");

      Space space = new Space(i, spaceName, 
          upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol);
      spaces.add(space);
    }

    // Debug: Verify spaces
    for (ImSpace space : spaces) {
      System.out.println("DEBUG: Space added - " + space.getName());
    }

    // Parse items and assign them to spaces
    String itemLine;
    List<ImItem> items = new ArrayList<>();
    while ((itemLine = reader.readLine()) != null && !itemLine.isBlank()) {
      String[] itemInfo = itemLine.trim().split(" ");
      int damage = Integer.parseInt(itemInfo[0]);
      String itemName = joinStrings(itemInfo, 1, itemInfo.length);

      // Assuming items are mapped to spaces by index
      int spaceIndex = items.size() % spaces.size(); 
      ImItem item = new Item(itemName, damage);
      items.add(item);

      spaces.get(spaceIndex).addItem(item); // Add item to space

      // Debug: Log item details
      System.out.println("DEBUG: Item added - "
          + "" + itemName + " (Damage: " + damage + ") to space "
              + "" + spaces.get(spaceIndex).getName());
    }

    // Create the world
    World world = new World(rows, cols, worldName, 
        spaces, items, targetCharacter, null, new ArrayList<>());

    return world;
  }

  private static String joinStrings(String[] parts, int start, int end) {
    StringBuilder builder = new StringBuilder();
    for (int i = start; i < end; i++) {
      builder.append(parts[i]);
      if (i < end - 1) {
        builder.append(" ");
      }
    }
    return builder.toString();
  }
} 