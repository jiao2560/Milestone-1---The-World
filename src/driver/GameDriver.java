package driver;

import character.TargetCharacter;
import item.ImItem;
import item.Item;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * This is the driver class for the Doctor Lucky's Mansion game.
 * It reads the world configuration from a file, sets up the game, 
 * and allows the user to interact with the world by moving the target character.
 */
public class GameDriver {
  /**
   * The main method of the game driver. It reads the input world file, initializes the world, 
   * handles user input to move the character, and updates the world map accordingly.
   *
   * @param args command-line arguments, where the first argument should be the path to the file.
   */
  public static void main(String[] args) {
    BufferedReader reader = null;

    try {
      // Case 1: Check if running in Eclipse with external file provided as an argument
      if (args.length > 0) {
        String filePath = args[0];
        reader = new BufferedReader(new FileReader(filePath));
      } else {
        // Case 2: Load file embedded in JAR (for running in JAR mode)
        InputStream inputStream = GameDriver.class.getResourceAsStream("/mansion.txt");
        if (inputStream == null) {
          System.out.println("File not found inside the JAR: mansion.txt");
          return;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
      }

      // Reading world description (first two lines)
      String[] worldInfo = reader.readLine().split(" ");
      int rows = Integer.parseInt(worldInfo[0]);
      int cols = Integer.parseInt(worldInfo[1]);
      String worldName = worldInfo[2] + " " + worldInfo[3];

      String[] characterInfo = reader.readLine().split(" ");
      int characterHealth = Integer.parseInt(characterInfo[0]);
      String characterName = characterInfo[1] + " " + characterInfo[2];

      // Parse the spaces
      int numberOfSpaces = Integer.parseInt(reader.readLine());
      List<ImSpace> spaces = new ArrayList<>();  // List of ISpace

      for (int i = 0; i < numberOfSpaces; i++) {
        String line = reader.readLine();
        // Trim and replace multiple spaces with a single space
        line = line.trim().replaceAll(" +", " ");
        String[] spaceInfo = line.split(" ");

        if (spaceInfo.length < 5) {
          System.out.println("Error: Line has insufficient data.");
          continue; // Skip this line if there are insufficient elements
        }

        int upperLeftRow = Integer.parseInt(spaceInfo[0]);
        int upperLeftCol = Integer.parseInt(spaceInfo[1]);
        int lowerRightRow = Integer.parseInt(spaceInfo[2]);
        int lowerRightCol = Integer.parseInt(spaceInfo[3]);

        // Build the space name from remaining elements
        StringBuilder spaceNameBuilder = new StringBuilder();
        for (int j = 4; j < spaceInfo.length; j++) {
          spaceNameBuilder.append(spaceInfo[j]);
          if (j < spaceInfo.length - 1) {
            spaceNameBuilder.append(" ");
          }
        }
        String spaceName = spaceNameBuilder.toString();

        ImSpace space = new Space(spaceName, upperLeftRow, 
            upperLeftCol, lowerRightRow, lowerRightCol);
        spaces.add(space);
      }

      // Parse the items
      int numberOfItems = Integer.parseInt(reader.readLine());
      List<ImItem> items = new ArrayList<>();  // List of IItem

      for (int i = 0; i < numberOfItems; i++) {
        String[] itemInfo = reader.readLine().split(" ");
        int spaceIndex = Integer.parseInt(itemInfo[0]);
        int itemDamage = Integer.parseInt(itemInfo[1]);

        // Build the item name based on the number of elements in itemInfo
        StringBuilder itemNameBuilder = new StringBuilder();
        for (int j = 2; j < itemInfo.length; j++) {
          itemNameBuilder.append(itemInfo[j]);
          if (j < itemInfo.length - 1) {
            itemNameBuilder.append(" ");
          }
        }
        String itemName = itemNameBuilder.toString();

        // Create the item and add it to the corresponding space
        ImItem item = new Item(itemName, itemDamage);
        spaces.get(spaceIndex).addItem(item);
        items.add(item);
      }

      // Create the target character in space 0 (before creating the world)
      TargetCharacter targetCharacter = new TargetCharacter(characterName, characterHealth, 0);  

      // Create the world using the correct types
      World world = new World(rows, cols, worldName, spaces, items, targetCharacter);

      // Print initial information
      System.out.println("Welcome to the world: " + worldName);
      System.out.println("Target character: " + targetCharacter.getName() + " (Health: "
          + "" + targetCharacter.getHealth() + ")");
      System.out.println("Target character starts in: " + spaces.get(0).getName());

      // Generate and save the initial world map as an image 
      // (with neighbors in blue and items in red)
      BufferedImage initialMap = world.generateMap();
      File initialOutputfile = new File("world_map_initial.png");
      ImageIO.write(initialMap, "png", initialOutputfile);
      System.out.println("Initial world map generated: world_map_initial.png");

      // Start the game loop
      Scanner scanner = new Scanner(System.in);

      while (true) {
        // Prompt the user for a space index
        System.out.print("Enter the space index to move to (0 to " + (spaces.size() - 1) + "): ");
        int moveIndex = scanner.nextInt();

        // Check if the index is valid
        if (moveIndex == targetCharacter.getCurrentSpace()) {
          System.out.println("You are already in this space. Please choose another.");
          continue;
        }

        if (moveIndex < 0 || moveIndex >= spaces.size()) {
          System.out.println("Invalid space index. Please enter a "
              + "valid index between 0 and " + (spaces.size() - 1));
          continue;
        }

        // Move the character to the new space
        targetCharacter.moveToSpace(moveIndex);
        System.out.println("Moved to space: " + spaces.get(moveIndex).getName());

        // Calculate damage based on items in the space
        List<ImItem> itemsInSpace = spaces.get(moveIndex).getItems();
        if (!itemsInSpace.isEmpty()) {
          int totalDamage = 0;
          for (ImItem item : itemsInSpace) {
            totalDamage += item.getDamage();
          }

          // Reduce the health by total damage
          targetCharacter.takeDamage(totalDamage);
          System.out.println("You took " + totalDamage + " damage. Remaining health: "
              + "" + targetCharacter.getHealth());
        } else {
          System.out.println("No damaging items in this space. Current health: "
              + "" + targetCharacter.getHealth());
        }

        // Check if health is 0 or below
        if (targetCharacter.getHealth() <= 0) {
          System.out.println("Game Over! Your health dropped to 0.");
          break;
        }

        // Generate and save the updated world map as an image
        BufferedImage map = world.generateMap();
        File outputfile = new File("world_map.png");
        ImageIO.write(map, "png", outputfile);
      }

      scanner.close();
      System.out.println("Thanks for playing!");

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
}
