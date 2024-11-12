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
   * Constructs a new world with specified dimensions, spaces, 
   * items, target character, pet, and players.
   *
   * @param rows the number of rows in the world
   * @param cols the number of columns in the world
   * @param name the name of the world
   * @param spaces the list of spaces in the world
   * @param items the list of items in the world
   * @param targetCharacter the target character in the world
   * @param pet the pet in the world
   * @param players the list of players in the world
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
    assignNeighbors();
  }
  
  /**
   * Returns the list of players in the world.
   * 
   * @return a list of players
   */
  public List<PlayerImpl> getPlayers() {
    return players;
  }

  /**
   * Returns the target character in the world.
   *
   * @return the target character as an {@code ImTargetCharacter} instance
   */
  public ImTargetCharacter getTargetCharacter() {
    return targetCharacter;
  }

  /**
   * Returns the pet in the world.
   *
   * @return the pet as a {@code Pet} instance
   */
  public Pet getPet() {
    return this.pet;
  }
  
  /**
   * Returns the neighboring spaces of the given space.
   * 
   * @param space the space to find neighbors for
   * @return a list of neighboring spaces
   */
  @Override
  public List<ImSpace> getNeighbors(ImSpace space) {
    return space.getNeighbors();
  }
  
  /**
   * Retrieves a space by its index.
   * 
   * @param index the index of the space
   * @return the space at the specified index
   * @throws IndexOutOfBoundsException if the index is invalid
   */
  @Override
  public ImSpace getSpace(int index) {
    if (index < 0 || index >= spaces.size()) {
      throw new IndexOutOfBoundsException("Invalid space index: " + index);
    }
    return spaces.get(index);
  }
  
  /**
   * Returns the list of all spaces in the world.
   * 
   * @return a list of spaces
   */
  @Override
  public List<ImSpace> getSpaces() {
    return spaces;
  }
  
  /**
   * Moves the target character to the next space.
   */
  @Override
  public void moveTargetCharacter() {
    targetCharacter.moveToNextSpace();
  }

  /**
   * Moves the pet to a specified space.
   *
   * @param space the new space to move the pet to
   */
  public void movePetToSpace(ImSpace space) {
    pet.moveTo(space);
  }

  /**
   * Checks if the pet is in a given space, making it invisible to neighboring spaces.
   *
   * @param space the {@code ImSpace} to check visibility for
   * @return {@code true} if the pet blocks visibility in this space, {@code false} otherwise
   */
  public boolean isPetBlockingVisibility(ImSpace space) {
    return pet.getCurrentSpace() == space;
  }
  
  /**
   * Checks if the pet makes a space invisible.
   * 
   * @param space the space to check
   * @return {@code true} if the pet blocks visibility, {@code false} otherwise
   */
  @Override
  public boolean petMakesSpaceInvisible(ImSpace space) {
    return pet.getCurrentSpace() == space;
  }
  
  /**
   * Provides information about a specific space, including items, visible neighbors, 
   * and the presence of the target character or pet.
   * 
   * @param space the space to get information about
   * @return a string containing space details
   */
  @Override
  public String getSpaceInfo(ImSpace space) {
    StringBuilder info = new StringBuilder();
    info.append("Space Name: ").append(space.getName()).append("\nItems: ");
    space.getItems().forEach(item -> 
        info.append(item.getName()).append(" (Damage: ").append(item.getDamage()).append("), "));
    
    if (space.getItems().isEmpty()) {
      info.append("None\n");
    }

    // Display neighboring spaces if they are not made invisible by the pet's presence
    List<ImSpace> neighbors = space.getNeighbors();
    info.append("Visible Neighbors: ");
    neighbors.stream().filter(neighbor -> !isPetBlockingVisibility(neighbor))
            .forEach(neighbor -> info.append(neighbor.getName()).append(", "));
    
    if (neighbors.isEmpty()) {
      info.append("None\n");
    }

    // Display target character and pet if present
    if (targetCharacter.getCurrentSpace() == spaces.indexOf(space)) {
      info.append("Target character: ").append(targetCharacter.getName())
          .append(" (Health: ").append(targetCharacter.getHealth()).append(")\n");
    }

    if (pet.getCurrentSpace() == space) {
      info.append("Pet: ").append(pet.getName()).append("\n");
    }
    return info.toString();
  }
  
  /**
   * Generates a graphical map of the world.
   * 
   * @return a BufferedImage representing the world map
   */
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
   * Assigns neighboring spaces to each space in the world.
   */
  private void assignNeighbors() {
    for (ImSpace space : spaces) {
      for (ImSpace candidate : spaces) {
        if (space != candidate && isNeighbor(space, candidate)) {
          space.addNeighbor(candidate);
        }
      }
    }
  }
  
  /**
   * Updates player locations in the world.
   */
  public void updatePlayerLocations() {
    spaceToPlayersMap.clear();
    for (PlayerImpl player : players) {
      ImSpace currentSpace = player.getCurrentSpace();
      spaceToPlayersMap.computeIfAbsent(currentSpace, k -> new ArrayList<>()).add(player);
    }
  }
  
  /**
   * Moves a player to a new space.
   * 
   * @param player the player to move
   * @param newSpace the new space for the player
   */
  public void movePlayer(PlayerImpl player, ImSpace newSpace) {
    ImSpace oldSpace = player.getCurrentSpace();

    // Remove the player from the old space
    if (spaceToPlayersMap.containsKey(oldSpace)) {
      spaceToPlayersMap.get(oldSpace).remove(player);
      if (spaceToPlayersMap.get(oldSpace).isEmpty()) {
        spaceToPlayersMap.remove(oldSpace); 
      }
    }

    // Move the player to the new space
    player.moveTo(newSpace);

    // Add the player to the new space
    spaceToPlayersMap.computeIfAbsent(newSpace, k -> new ArrayList<>()).add(player);

    displayPlayerLocations(); // Debug to verify
  }

  /**
   * Displays the locations of all players in each space for debugging.
   */
  public void displayPlayerLocations() {
    //System.out.println("Debug: Player locations updated in each space:");
    for (Map.Entry<ImSpace, List<PlayerImpl>> entry : spaceToPlayersMap.entrySet()) {
      //System.out.print("  Space: " + entry.getKey().getName() + " - Players: ");
      for (PlayerImpl player : entry.getValue()) {
          //System.out.print(player.getName() + " ");
      }
      //System.out.println();
    }
  }
  
  /**
   * Returns a list of players in the same space.
   * 
   * @param space the space to check
   * @return a list of players in the same space
   */
  public List<PlayerImpl> getPlayersInSameSpace(ImSpace space) {
    return spaceToPlayersMap.getOrDefault(space, new ArrayList<>());
  }
  
  /**
   * Checks if two spaces are neighbors.
   * 
   * @param space1 the first space
   * @param space2 the second space
   * @return {@code true} if they are neighbors, {@code false} otherwise
   */
  public boolean isNeighbor(ImSpace space1, ImSpace space2) {
    int[] coords1 = space1.getCoordinates();
    int[] coords2 = space2.getCoordinates();

    boolean verticallyAligned = (coords1[1] <= coords2[3] && coords1[3] >= coords2[1]);
    boolean verticallyAdjacent = (coords1[2] + 1 == coords2[0]) || (coords2[2] + 1 == coords1[0]);

    boolean horizontallyAligned = (coords1[0] <= coords2[2] && coords1[2] >= coords2[0]);
    boolean horizontallyAdjacent = (coords1[3] + 1 == coords2[1]) || (coords2[3] + 1 == coords1[1]);

    return (verticallyAligned && verticallyAdjacent) 
        || (horizontallyAligned && horizontallyAdjacent);
  }
  
  /**
   * Draws a space on the graphical map.
   * 
   * @param g the graphics context
   * @param space the space to draw
   */
  private void drawSpace(Graphics g, ImSpace space) {
    int[] coordinates = space.getCoordinates();
    int width = (coordinates[3] - coordinates[1] + 1) * 50;
    int height = (coordinates[2] - coordinates[0] + 1) * 50;

    g.setColor(Color.BLACK);
    g.drawRect(coordinates[1] * 50, coordinates[0] * 50, width, height);

    g.setColor(new Color(200, 200, 255));
    g.fillRect(coordinates[1] * 50 + 1, coordinates[0] * 50 + 1, width - 2, height - 2);

    g.setColor(Color.BLACK);
    g.drawString(space.getName(), coordinates[1] * 50 + 20, coordinates[0] * 50 + 20);
  }
  
  /**
   * Sets the pet for the world.
   * 
   * @param pet the pet to set
   */
  public void setPet(Pet pet) {
    this.pet = pet;
  }
}
