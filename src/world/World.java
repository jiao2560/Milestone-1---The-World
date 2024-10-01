package world;

import character.ImTargetCharacter;
import item.ImItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import space.ImSpace;

/**
 * This class represents the world, containing spaces, items, and the target character.
 * It provides functionality for managing spaces and their neighbors, moving the target 
 * character, and generating a graphical representation of the world.
 */
public class World implements ImWorld {
  private int rows;
  private int cols;
  private String name;
  private List<ImSpace> spaces;
  private List<ImItem> items;
  private ImTargetCharacter targetCharacter;

  /**
   * Constructs a new world with the specified rows, columns, and name.
   * 
   * @param rows the number of rows in the world
   * @param cols the number of columns in the world
   * @param name the name of the world
   * @param spaces the list of spaces in the world
   * @param items the list of items in the world
   * @param targetCharacter the target character in the world
   */
  public World(int rows, int cols, String name, List<ImSpace> spaces, 
      List<ImItem> items, ImTargetCharacter targetCharacter) {
    this.rows = rows;
    this.cols = cols;
    this.name = name;
    this.spaces = spaces;
    this.items = items;
    this.targetCharacter = targetCharacter;
  }

  /**
   * Gets the neighboring spaces of the specified space. Neighbors are spaces that share a wall.
   * 
   * @param space the space for which to determine neighbors
   * @return a list of neighboring spaces
   */
  @Override
  public List<ImSpace> getNeighbors(ImSpace space) {
    List<ImSpace> neighbors = new ArrayList<>();
    // Iterate through all spaces to check for neighbors
    for (ImSpace candidate : spaces) {
      if (isNeighbor(space, candidate)) {
        neighbors.add(candidate);
      }
    }
    return neighbors;
  }
  

  /**
   * Determines if two spaces are neighbors by checking if they share a wall.
   * Two spaces are neighbors if they are adjacent either vertically or horizontally.
   * 
   * @param space1 the first space
   * @param space2 the second space
   * @return true if the spaces are neighbors, false otherwise
   */
  private boolean isNeighbor(ImSpace space1, ImSpace space2) {
    // Get the coordinates of both spaces
    int[] coords1 = space1.getCoordinates(); 
    int[] coords2 = space2.getCoordinates();

    int upperLeftRow1 = coords1[0];
    int upperLeftCol1 = coords1[1];
    int lowerRightRow1 = coords1[2];
    int lowerRightCol1 = coords1[3];

    int upperLeftRow2 = coords2[0];
    int upperLeftCol2 = coords2[1];
    int lowerRightRow2 = coords2[2];
    int lowerRightCol2 = coords2[3];

    // Check for vertical neighbors (spaces that share a column and are adjacent by rows)
    boolean verticallyAligned = (upperLeftCol1 <= lowerRightCol2 
        && lowerRightCol1 >= upperLeftCol2);
    boolean verticallyAdjacent = (lowerRightRow1 + 1 == upperLeftRow2) 
        || (lowerRightRow2 + 1 == upperLeftRow1);

    // Check for horizontal neighbors (spaces that share a row and are adjacent by columns)
    boolean horizontallyAligned = (upperLeftRow1 <= lowerRightRow2 
        && lowerRightRow1 >= upperLeftRow2);
    boolean horizontallyAdjacent = (lowerRightCol1 + 1 == upperLeftCol2) 
        || (lowerRightCol2 + 1 == upperLeftCol1);

    // Return true if they are vertically or horizontally adjacent and aligned
    return (verticallyAligned && verticallyAdjacent) 
        || (horizontallyAligned && horizontallyAdjacent);
  }


  /**
   * Displays information about the specified space, including its name, the items it contains,
   * and the spaces that can be seen from the space.
   * 
   * @param space the space for which to display information
   * @return a string containing the space's information
   */
  @Override
  public String getSpaceInfo(ImSpace space) {
    StringBuilder info = new StringBuilder();
    info.append("Space Name: ").append(space.getName()).append("\n");

    // Display items in the space
    info.append("Items: ");
    for (ImItem item : space.getItems()) {
      info.append(item.getName()).append(" (Damage: ").append(item.getDamage()).append("), ");
    }
    if (space.getItems().isEmpty()) {
      info.append("None");
    }
    info.append("\n");

    // Display neighboring spaces
    info.append("Neighbors: ");
    List<ImSpace> neighbors = getNeighbors(space);  // Using getNeighbors method from World
    for (ImSpace neighbor : neighbors) {
      info.append(neighbor.getName()).append(", ");
    }
    if (neighbors.isEmpty()) {
      info.append("None");
    }
    info.append("\n");

    return info.toString();
  }



  /**
   * Moves the target character to the next space in the ordered space list.
   */
  @Override
  public void moveTargetCharacter() {
    targetCharacter.moveToNextSpace();
  }

  /**
   * Creates a graphical representation of the world in the form of a BufferedImage.
   * 
   * @return a BufferedImage representing the world map
   */
  @Override
  public BufferedImage generateMap() {
    BufferedImage image = new BufferedImage(cols * 50, rows * 50, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    // Set the background color for the image
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());

    // Draw the spaces, items, and target character
    for (ImSpace space : spaces) {
      drawSpace(g, space);
    }
    return image;
  }

  /**
   * Draws the specified space on the world map using the Graphics object.
   * 
   * @param g the Graphics object used to draw the space
   * @param space the space to draw
   */
  public void drawSpace(Graphics g, ImSpace space) {
    // Get the coordinates of the space
    int[] coordinates = space.getCoordinates();  // No need for casting now
    int upperLeftRow = coordinates[0];
    int upperLeftCol = coordinates[1];
    int lowerRightRow = coordinates[2];
    int lowerRightCol = coordinates[3];

    // Draw the space as a rectangle
    int width = (lowerRightCol - upperLeftCol + 1) * 50;
    int height = (lowerRightRow - upperLeftRow + 1) * 50;

    g.setColor(Color.BLACK); // Border color
    g.drawRect(upperLeftCol * 50, upperLeftRow * 50, width, height);

    // Fill the space with a light color
    g.setColor(new Color(200, 200, 255));
    g.fillRect(upperLeftCol * 50 + 1, upperLeftRow * 50 + 1, width - 2, height - 2);

    // Draw the space name in the center of the space
    g.setColor(Color.BLACK);
    g.setFont(g.getFont().deriveFont(14f));  // Normal font for space name
    g.drawString(space.getName(), (upperLeftCol * 50) 
        + width / 2 - 20, (upperLeftRow * 50) + height / 2);

    // Draw the items and damage in red under the space name
    g.setColor(Color.RED);
    g.setFont(g.getFont().deriveFont(10f));  // Smaller font for items
    int itemYsPosition = (upperLeftRow * 50) 
        + height / 2 + 15;  // Position below the space name

    for (ImItem item : space.getItems()) {
      String itemInfo = item.getName() + " (Damage: " + item.getDamage() + ")";
      g.drawString(itemInfo, (upperLeftCol * 50) + width / 2 - 20, itemYsPosition);
      itemYsPosition += 12;  // Move to the next line for each item
    }

    // Draw the neighbors in blue below the items
    g.setColor(Color.BLUE);
    List<ImSpace> neighbors = getNeighbors(space);  // Get the list of neighboring spaces
    int neighborYsPosition = itemYsPosition + 15;  // Position below the items

    if (!neighbors.isEmpty()) {
      g.drawString("Neighbors: ", (upperLeftCol * 50) + width / 2 - 20, neighborYsPosition);
      neighborYsPosition += 12;  // Move to the next line
      for (ImSpace neighbor : neighbors) {
        g.drawString(neighbor.getName(), (upperLeftCol * 50) + width / 2 - 20, neighborYsPosition);
        neighborYsPosition += 12;  // Move to the next line for each neighbor
      }
    } else {
      g.drawString("Neighbors: None", (upperLeftCol * 50) + width / 2 - 20, neighborYsPosition);
    }
  }
}
