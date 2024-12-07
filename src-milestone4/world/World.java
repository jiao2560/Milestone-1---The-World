package world;

import character.ImTargetCharacter;
import item.ImItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pet.Pet;
import player.PlayerImpl;
import space.ImSpace;

/**
 * This class represents the world, containing spaces, items, and the target character.
 * It provides functionality for managing spaces and their neighbors, moving the target 
 * character and pet, and generating a graphical representation of the world.
 */
public class World implements ImWorld {
  private int rows;
  private int cols;
  private String name;
  private List<ImSpace> spaces;
  private List<ImItem> items;
  private ImTargetCharacter targetCharacter;
  private Pet pet;
  private List<PlayerImpl> players;
  private Map<ImSpace, List<PlayerImpl>> spaceToPlayersMap = new HashMap<>();
  

  /**
   * Constructs a {@code World} object, representing the game environment."
   * The world includes spaces, items, a target character, a pet, and players. 
   * It initializes the game's structure and relationships between spaces.
   *
   * @param rows             the number of rows in the world grid
   * @param cols             the number of columns in the world grid
   * @param name             the name of the world
   * @param spaces           a list of {@code ImSpace} objects
   * @param items            a list of {@code ImItem} objects 
   * @param targetCharacter  the {@code ImTargetCharacter} object
   * @param pet              the {@code Pet} object that may influence visibility in spaces
   * @param players          a list of {@code PlayerImpl} objects
   */
  public World(int rows, int cols, String name, List<ImSpace> spaces, 
      List<ImItem> items, ImTargetCharacter targetCharacter, 
      Pet pet, List<PlayerImpl> players) {
    this.rows = rows;
    this.cols = cols;
    this.name = name;
    this.spaces = spaces;
    this.items = items;
    this.targetCharacter = targetCharacter;
    this.pet = pet;
    this.players = players;
    
    // Debug: Initialization verification
    System.out.println("World initialized: " + name);
    System.out.println("Rows: " + rows + ", Columns: " + cols);
    System.out.println("Spaces: " + spaces.size());
    System.out.println("Target Character: "
        + "" + (targetCharacter != null ? targetCharacter.getName() : "None"));
    System.out.println("Players: " + players.size());
    System.out.println("Items: " + items.size());

    assignNeighbors();
  }

  public List<PlayerImpl> getPlayers() {
    return players;
  }
  
  @Override
  public boolean petMakesSpaceInvisible(ImSpace space) {
    return isPetBlockingVisibility(space);
  }
  
  @Override
  public void displayPlayerLocations() {
    System.out.println("Player Locations:");
    for (Map.Entry<ImSpace, List<PlayerImpl>> entry : spaceToPlayersMap.entrySet()) {
      ImSpace space = entry.getKey();
      List<PlayerImpl> players = entry.getValue();
      System.out.print("Space: " + space.getName() + " - Players: ");
      for (PlayerImpl player : players) {
        System.out.print(player.getName() + " ");
      }
      System.out.println();
    }
  }

  public ImTargetCharacter getTargetCharacter() {
    return targetCharacter;
  }

  public Pet getPet() {
    return pet;
  }

  @Override
  public List<ImSpace> getNeighbors(ImSpace space) {
    return space.getNeighbors();
  }

  @Override
  public ImSpace getSpace(int index) {
    if (index < 0 || index >= spaces.size()) {
      throw new IndexOutOfBoundsException("Invalid space index: " + index);
    }
    return spaces.get(index);
  }

  @Override
  public List<ImSpace> getSpaces() {
    return spaces;
  }

  @Override
  public void moveTargetCharacter() {
    targetCharacter.moveToNextSpace();
  }
  
  /**
   * Sets the pet for the world.
   *
   * @param pet the pet to set in the world
   */
  public void setPet(Pet pet) {
    this.pet = pet;
  }
  
  @Override
  public void movePetToSpace(ImSpace space) {
    pet.moveTo(space);
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
    info.append("Visible Neighbors: ");
    neighbors.stream().filter(neighbor -> !isPetBlockingVisibility(neighbor))
            .forEach(neighbor -> info.append(neighbor.getName()).append(", "));
    if (neighbors.isEmpty()) {
      info.append("None\n");
    }

    if (targetCharacter.getCurrentSpace() == spaces.indexOf(space)) {
      info.append("Target character: ").append(targetCharacter.getName())
          .append(" (Health: ").append(targetCharacter.getHealth()).append(")\n");
    }

    if (pet.getCurrentSpace() == space) {
      info.append("Pet: ").append(pet.getName()).append("\n");
    }
    return info.toString();
  }

  private void assignNeighbors() {
    for (ImSpace space : spaces) {
      for (ImSpace candidate : spaces) {
        if (space != candidate && isNeighbor(space, candidate)) {
          space.addNeighbor(candidate);
          // Debug: Neighbor assignment
          System.out.println("Neighbor assigned: "
              + "" + space.getName() + " -> " + candidate.getName());
        }
      }
    }
  }
  
  @Override
  public void updatePlayerLocations() {
    spaceToPlayersMap.clear(); // Clear old mappings
    for (PlayerImpl player : players) {
      ImSpace currentSpace = player.getCurrentSpace();
      spaceToPlayersMap.computeIfAbsent(currentSpace, k -> new ArrayList<>()).add(player);
      System.out.println("Player "
          + "" + player.getName() + " mapped to space: " + currentSpace.getName());
    }
  }

  @Override
  public void movePlayer(PlayerImpl player, ImSpace newSpace) {
    ImSpace oldSpace = player.getCurrentSpace();
    if (spaceToPlayersMap.containsKey(oldSpace)) {
      spaceToPlayersMap.get(oldSpace).remove(player);
      if (spaceToPlayersMap.get(oldSpace).isEmpty()) {
        spaceToPlayersMap.remove(oldSpace); 
      }
    }
    player.moveTo(newSpace);
    spaceToPlayersMap.computeIfAbsent(newSpace, k -> new ArrayList<>()).add(player);
  }

  @Override
  public Map<ImSpace, List<PlayerImpl>> getPlayerLocations() {
    return new HashMap<>(spaceToPlayersMap);
  }

  @Override
  public ImSpace getTargetCharacterLocation() {
    return spaces.get(targetCharacter.getCurrentSpace());
  }

  @Override
  public ImSpace getPetLocation() {
    return pet.getCurrentSpace();
  }

  @Override
  public List<ImSpace> getVisibleSpaces(ImSpace space) {
    List<ImSpace> visibleSpaces = new ArrayList<>();
    for (ImSpace neighbor : space.getNeighbors()) {
      if (!isPetBlockingVisibility(neighbor)) {
        visibleSpaces.add(neighbor);
      }
    }
    return visibleSpaces;
  }
  
  /**
   * Checks if the pet is blocking visibility in a given space.
   * 
   * @param space the space to check
   * @return {@code true} if the pet blocks visibility in this space; {@code false} otherwise
   */
  public boolean isPetBlockingVisibility(ImSpace space) {
    return pet.getCurrentSpace() == space;
  }


  private boolean isNeighbor(ImSpace space1, ImSpace space2) {
    int[] coords1 = space1.getCoordinates();
    int[] coords2 = space2.getCoordinates();

    boolean verticallyAligned = (coords1[1] <= coords2[3] && coords1[3] >= coords2[1]);
    boolean verticallyAdjacent = (coords1[2] + 1 == coords2[0]) || (coords2[2] + 1 == coords1[0]);

    boolean horizontallyAligned = (coords1[0] <= coords2[2] && coords1[2] >= coords2[0]);
    boolean horizontallyAdjacent = (coords1[3] + 1 == coords2[1]) || (coords2[3] + 1 == coords1[1]);

    return (verticallyAligned && verticallyAdjacent) 
        || (horizontallyAligned && horizontallyAdjacent);
  }

  @Override
  public BufferedImage generateMap() {
    BufferedImage image = new BufferedImage(cols * 50, rows * 50, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();

    System.out.println("Generating map...");

    // Fill the map background
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());

    // Draw spaces
    for (ImSpace space : spaces) {
      drawSpace(g, space);
      System.out.println("Space drawn: " + space.getName());
    }

    // Draw Doctor Lucky
    if (targetCharacter != null) {
      drawCharacter(g, spaces.get(targetCharacter.getCurrentSpace()), Color.RED, "DL");
      System.out.println("Doctor Lucky drawn in space: "
          + "" + spaces.get(targetCharacter.getCurrentSpace()).getName());
    } else {
      System.out.println("Doctor Lucky not initialized.");
    }

    // Draw players
    for (PlayerImpl player : players) {
      ImSpace playerSpace = player.getCurrentSpace();
      System.out.println("Player " + player.getName() + " at space: " + playerSpace.getName());
      drawCharacter(g, playerSpace, player.isAi ? Color.BLUE : Color.GREEN, player.getName());
    }

    return image;
  }

  /**
   * Draws a character or player on the map.
   *
   * @param g     the Graphics object
   * @param space the space where the character is located
   * @param color the color of the representation
   * @param label the label to display (e.g., "DL" or player name)
   */
  private void drawCharacter(Graphics g, ImSpace space, Color color, String label) {
    int[] coords = space.getCoordinates();

    // Calculate the center of the space
    int x = coords[1] * 50 + 25; // Center X
    int y = coords[0] * 50 + 25; // Center Y

    // Debug: Character placement
    System.out.println("Drawing " + label + " in space: "
        + "" + space.getName() + " at center (X: " + x + ", Y: " + y + ")");

    // Draw a circle to represent the character
    g.setColor(color);
    g.fillOval(x - 10, y - 10, 20, 20);

    // Draw the label
    g.setColor(Color.BLACK);
    g.drawString(label, x - label.length() * 3, y + 5);
  }
  
  /**
   * Draws a character or player on the map.
   *
   * @param g        the Graphics object
   * @param spaceIdx the index of the space where the character is located
   * @param color    the color for the character's representation
   * @param label    the label to display (e.g., name or "DL")
   */
  private void drawCharacter(Graphics g, int spaceIdx, Color color, String label) {
    if (spaceIdx < 0 || spaceIdx >= spaces.size()) {
      return;
    }

    ImSpace space = spaces.get(spaceIdx);
    int[] coords = space.getCoordinates();

    // Center the character in the space
    int x = (coords[1] + coords[3]) / 2 * 50 + 25; // Center X
    int y = (coords[0] + coords[2]) / 2 * 50 + 25; // Center Y

    // Draw a circle for the character
    g.setColor(color);
    g.fillOval(x - 10, y - 10, 20, 20);

    // Draw the label
    g.setColor(Color.BLACK);
    g.drawString(label, x - label.length() * 3, y + 5);
  }

  /**
   * Draws a space on the map.
   *
   * @param g     the Graphics object
   * @param space the space to draw
   */
  private void drawSpace(Graphics g, ImSpace space) {
    int[] coordinates = space.getCoordinates();
    int x = coordinates[1] * 50; // Upper-left X
    int y = coordinates[0] * 50; // Upper-left Y
    int width = (coordinates[3] - coordinates[1] + 1) * 50; // Width
    int height = (coordinates[2] - coordinates[0] + 1) * 50; // Height

    // Debug: Space coordinates
    System.out.println("Drawing space: "
        + "" + space.getName() + " at (X: " + x + ", Y: " + y + ") with size ("
            + "" + width + "x" + height + ")");

    // Draw the space border
    g.setColor(Color.BLACK);
    g.drawRect(x, y, width, height);

    // Fill the interior
    g.setColor(new Color(200, 200, 255)); // Light blue color
    g.fillRect(x + 1, y + 1, width - 2, height - 2);

    // Draw the space name at the top
    g.setColor(Color.BLACK);
    g.drawString(space.getName(), x + 5, y + 15); // Adjust position for the name
  }
  
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getCols() {
    return this.cols;
  }
} 