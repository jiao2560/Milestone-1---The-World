package driver;

import character.TargetCharacter;
import controller.GameController;
import item.ImItem;
import item.Item;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * This is the driver class for the Doctor Lucky's Mansion game.
 * It reads the world configuration from a file, initializes the world, sets up players, 
 * and manages the game loop using the {@code GameController}.
 */
public class GameDriver {
  /**
   * The main entry point for the game. This method initializes the game world,
   * sets up the game controller, adds players, and starts the game loop.
   * It also generates and saves an initial world map as a PNG image.
   * 
   * <p>Execution steps include:
   * <ul>
   *   <li>Loading the world configuration from a text file.</li>
   *   <li>Initializing the game world and controller.</li>
   *   <li>Adding a human player and an AI player to the game.</li>
   *   <li>Generating and saving the world map.</li>
   *   <li>Starting the game loop and playing turns until the game ends.</li>
   * </ul>
   *
   * @param args command-line arguments (not used in this application)
   */
  public static void main(String[] args) {
    int maxTurns = 20;  // Set a default value for the maximum number of turns

    BufferedReader reader = null;

    try {
      // Load the mansion.txt file from the classpath
      InputStream inputStream = GameDriver.class.getResourceAsStream("/driver/mansion.txt");
      if (inputStream == null) {
        System.out.println("Error: mansion.txt not found in the driver package.");
        return;
      }

      reader = new BufferedReader(new InputStreamReader(inputStream));
      World world = parseWorld(reader);

      // Set up the game controller
      GameController controller = new GameController(world, maxTurns);

      // Add players (Human and AI)
      controller.addPlayer(new PlayerImpl("Player1", world.getSpace(0), 5, false));
      controller.addPlayer(new PlayerImpl("AI", world.getSpace(1), 5, true));

      // Generate and save the initial world map as a PNG image
      BufferedImage initialMap = world.generateMap();
      ImageIO.write(initialMap, "png", new File("world_map_initial.png"));
      System.out.println("Initial world map saved as 'world_map_initial.png'.");

      // Start the game loop
      while (!controller.isGameOver()) {
        controller.playTurn();
      }

      System.out.println("Game Over! Thanks for playing!");

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Parses the world configuration from the provided {@code BufferedReader}.
   * It reads the world details, spaces, and items from the input stream.
   *
   * @param reader the {@code BufferedReader} used to read the world configuration
   * @return a {@code World} object representing the initialized game world
   * @throws IOException if an I/O error occurs while reading the input stream
   */
  private static World parseWorld(BufferedReader reader) throws IOException {
    // Read world description
    String[] worldInfo = reader.readLine().split(" ");
    int rows = Integer.parseInt(worldInfo[0]);
    int cols = Integer.parseInt(worldInfo[1]);
    String worldName = joinStrings(worldInfo, 2, worldInfo.length);

    // Read target character information
    String[] characterInfo = reader.readLine().split(" ");
    int characterHealth = Integer.parseInt(characterInfo[0]);
    String characterName = joinStrings(characterInfo, 1, characterInfo.length);

    TargetCharacter targetCharacter = new TargetCharacter(characterName, characterHealth, 0);

    // Parse spaces
    int numberOfSpaces = Integer.parseInt(reader.readLine());
    List<ImSpace> spaces = new ArrayList<>();

    for (int i = 0; i < numberOfSpaces; i++) {
      String line = reader.readLine().trim().replaceAll(" +", " ");
      String[] spaceInfo = line.split(" ");

      int upperLeftRow = Integer.parseInt(spaceInfo[0]);
      int upperLeftCol = Integer.parseInt(spaceInfo[1]);
      int lowerRightRow = Integer.parseInt(spaceInfo[2]);
      int lowerRightCol = Integer.parseInt(spaceInfo[3]);
      String spaceName = joinStrings(spaceInfo, 4, spaceInfo.length);

      ImSpace space = new Space(spaceName, upperLeftRow, 
          upperLeftCol, lowerRightRow, lowerRightCol);
      spaces.add(space);
    }

    // Parse items
    int numberOfItems = Integer.parseInt(reader.readLine());
    List<ImItem> items = new ArrayList<>();

    for (int i = 0; i < numberOfItems; i++) {
      String[] itemInfo = reader.readLine().split(" ");
      int spaceIndex = Integer.parseInt(itemInfo[0]);
      int itemDamage = Integer.parseInt(itemInfo[1]);
      String itemName = joinStrings(itemInfo, 2, itemInfo.length);

      ImItem item = new Item(itemName, itemDamage);
      spaces.get(spaceIndex).addItem(item);
      items.add(item);
    }

    // Create and return the World object
    return new World(rows, cols, worldName, spaces, items, targetCharacter);
  }

  /**
   * Utility method to join parts of a string array into a single string.
   *
   * @param parts the array of string parts
   * @param start the starting index (inclusive)
   * @param end the ending index (exclusive)
   * @return the joined string as a single {@code String}
   */
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

