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
   * Constructs a new {@code World} object with the specified rows, 
   * columns, spaces, items, and target character.
   *
   * @param rows            the number of rows in the world grid
   * @param cols            the number of columns in the world grid
   * @param name            the name of the world
   * @param spaces          a list of spaces in the world
   * @param items           a list of items available in the world
   * @param targetCharacter the target character present in the world
   */
  public World(int rows, int cols, String name, List<ImSpace> spaces, 
               List<ImItem> items, ImTargetCharacter targetCharacter) {
    this.rows = rows;
    this.cols = cols;
    this.name = name;
    this.spaces = spaces;
    this.items = items;
    this.targetCharacter = targetCharacter;

    // Assign neighbors to spaces during initialization
    assignNeighbors();
  }
  
  /**
   * Assigns neighbors to each space based on adjacency within the grid.
   */
  private void assignNeighbors() {
    for (ImSpace space : spaces) {
      for (ImSpace candidate : spaces) {
        if (space != candidate && isNeighbor(space, candidate)) {
          space.addNeighbor(candidate);  // Add the neighbor to the space
        }
      }
    }
  }

  @Override
  public List<ImSpace> getSpaces() {
    return spaces;
  }

  @Override
  public ImSpace getSpace(int index) {
    if (index < 0 || index >= spaces.size()) {
      throw new IndexOutOfBoundsException("Invalid space index: " + index);
    }
    return spaces.get(index);
  }

  @Override
  public List<ImSpace> getNeighbors(ImSpace space) {
    return space.getNeighbors();  // Use the neighbors stored in the space
  }
  
  /**
   * Determines if two spaces are neighbors based on their coordinates.
   *
   * @param space1 the first space
   * @param space2 the second space
   * @return {@code true} if the spaces are neighbors, {@code false} otherwise
   */
  public boolean isNeighbor(ImSpace space1, ImSpace space2) {
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

    boolean verticallyAligned = (upperLeftCol1 <= lowerRightCol2 
        && lowerRightCol1 >= upperLeftCol2);
    boolean verticallyAdjacent = (lowerRightRow1 + 1 == upperLeftRow2) 
        || (lowerRightRow2 + 1 == upperLeftRow1);

    boolean horizontallyAligned = (upperLeftRow1 <= lowerRightRow2 
        && lowerRightRow1 >= upperLeftRow2);
    boolean horizontallyAdjacent = (lowerRightCol1 + 1 == upperLeftCol2) 
        || (lowerRightCol2 + 1 == upperLeftCol1);

    return (verticallyAligned && verticallyAdjacent) 
        || (horizontallyAligned && horizontallyAdjacent);
  }

  @Override
  public void moveTargetCharacter() {
    targetCharacter.moveToNextSpace();
  }

  @Override
  public String getSpaceInfo(ImSpace space) {
    StringBuilder info = new StringBuilder();
    info.append("Space Name: ").append(space.getName()).append("\nItems: ");
    space.getItems().forEach(item -> 
        info.append(item.getName()).append(" (Damage: ").append(item.getDamage()).append("), "));
    if (space.getItems().isEmpty()) {
      info.append("None\n");
    }
    List<ImSpace> neighbors = space.getNeighbors();
    info.append("Neighbors: ");
    neighbors.forEach(neighbor -> info.append(neighbor.getName()).append(", "));
    if (neighbors.isEmpty()) {
      info.append("None\n");
    }
    if (targetCharacter.getCurrentSpace() == spaces.indexOf(space)) {
      info.append("Target character: ").append(targetCharacter.getName())
          .append(" (Health: ").append(targetCharacter.getHealth()).append(")\n");
    }
    return info.toString();
  }

  @Override
  public BufferedImage generateMap() {
    BufferedImage image = new BufferedImage(cols * 50, rows * 50, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    for (ImSpace space : spaces) {
      drawSpace(g, space);
    }
    return image;
  }
  
  /**
   * Draws a space on the graphical map.
   *
   * @param g     the {@code Graphics} object used to draw the space
   * @param space the {@code ImSpace} to be drawn
   */
  public void drawSpace(Graphics g, ImSpace space) {
    int[] coordinates = space.getCoordinates();
    int upperLeftRow = coordinates[0];
    int upperLeftCol = coordinates[1];
    int lowerRightRow = coordinates[2];
    int lowerRightCol = coordinates[3];

    int width = (lowerRightCol - upperLeftCol + 1) * 50;
    int height = (lowerRightRow - upperLeftRow + 1) * 50;

    g.setColor(Color.BLACK);
    g.drawRect(upperLeftCol * 50, upperLeftRow * 50, width, height);

    g.setColor(new Color(200, 200, 255));
    g.fillRect(upperLeftCol * 50 + 1, upperLeftRow * 50 + 1, width - 2, height - 2);

    g.setColor(Color.BLACK);
    g.setFont(g.getFont().deriveFont(14f));
    g.drawString(space.getName(), (upperLeftCol * 50) + width / 2 - 20,
        (upperLeftRow * 50) + height / 2);

    g.setColor(Color.RED);
    g.setFont(g.getFont().deriveFont(10f));
    int itemY = (upperLeftRow * 50) + height / 2 + 15;
    for (ImItem item : space.getItems()) {
      g.drawString(item.getName() + " (Damage: " + item.getDamage() + ")", 
          (upperLeftCol * 50) + width / 2 - 20, itemY);
      itemY += 12;
    }

    g.setColor(Color.BLUE);
    int neighborY = itemY + 15;
    List<ImSpace> neighbors = space.getNeighbors();
    if (!neighbors.isEmpty()) {
      g.drawString("Neighbors: ", (upperLeftCol * 50) + width / 2 - 20, neighborY);
      neighborY += 12;
      for (ImSpace neighbor : neighbors) {
        g.drawString(neighbor.getName(), (upperLeftCol * 50) + width / 2 - 20, neighborY);
        neighborY += 12;
      }
    } else {
      g.drawString("Neighbors: None", (upperLeftCol * 50) + width / 2 - 20, neighborY);
    }
  }
}

