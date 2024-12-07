package space;

import item.ImItem;
import java.util.ArrayList;
import java.util.List;
import player.Player;
import player.PlayerImpl;

/**
 * This class represents a space in the world. 
 * A space has a name, coordinates, and contains a list of items, players, and neighboring spaces.
 * Spaces form the structure of the game world, allowing players to move and interact with items.
 */
public class Space implements ImSpace {

  private String name;
  private int id;
  private int upperLeftRow;
  private int upperLeftCol;
  private int lowerRightRow;
  private int lowerRightCol;
  private List<Player> players;
  private List<ImSpace> neighbors;
  
  private List<ImItem> items = new ArrayList<>();

  /**
   * Constructs a {@code Space} object with the specified name, id, and coordinates.
   *
   * @param id             the unique identifier of the space
   * @param name           the name of the space
   * @param upperLeftRow   the row index of the upper-left corner of the space
   * @param upperLeftCol   the column index of the upper-left corner of the space
   * @param lowerRightRow  the row index of the lower-right corner of the space
   * @param lowerRightCol  the column index of the lower-right corner of the space
   */
  public Space(int id, String name, int upperLeftRow, int upperLeftCol, 
               int lowerRightRow, int lowerRightCol) {
    this.id = id;
    this.name = name;
    this.upperLeftRow = upperLeftRow;
    this.upperLeftCol = upperLeftCol;
    this.lowerRightRow = lowerRightRow;
    this.lowerRightCol = lowerRightCol;
    this.items = new ArrayList<>();
    this.players = new ArrayList<>();
    this.neighbors = new ArrayList<>();
  }
  
  @Override
  public List<ImItem> getItems() {
    return new ArrayList<>(items); // Return a copy for immutability
  }
  
  @Override
  public void addItem(ImItem item) {
    items.add(item);
  }
  
  @Override
  public void removeItem(ImItem item) {
    if (items.remove(item)) {
      System.out.println("Removed item: " + item.getName());
    } else {
      System.out.println("Failed to remove item: " + item.getName());
    }
  }

  /**
   * Retrieves the name of this space.
   *
   * @return the name of the space as a {@code String}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Retrieves the coordinates of this space.
   * The returned array contains four values: 
   * [upper-left row, upper-left col, lower-right row, lower-right col].
   *
   * @return an integer array representing the space's coordinates
   */
  @Override
  public int[] getCoordinates() {
    return new int[]{upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol};
  }

  /**
   * Retrieves the neighboring spaces connected to this space.
   *
   * @return a {@code List} of {@code ImSpace} objects representing the neighbors
   */
  @Override
  public List<ImSpace> getNeighbors() {
    return neighbors;
  }

  /**
   * Adds a neighboring space to this space.
   *
   * @param space the {@code ImSpace} to add as a neighbor
   */
  @Override
  public void addNeighbor(ImSpace space) {
    neighbors.add(space);
  }

  /**
   * Adds a player to this space.
   *
   * @param player the {@code Player} to add to the space
   */
  @Override
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Removes a player from this space.
   *
   * @param player the {@code Player} to remove from the space
   */
  @Override
  public void removePlayer(Player player) {
    players.remove(player);
  }

  /**
   * Retrieves the list of players currently in this space.
   *
   * @return a {@code List} of {@code Player} objects present in the space
   */
  @Override
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Retrieves the unique identifier for this space.
   *
   * @return the id of the space
   */
  @Override
  public int getId() {
    return id;
  }
  
  @Override
  public int getUpperLeftRow() {
    return upperLeftRow;
  }

  @Override
  public int getUpperLeftCol() {
    return upperLeftCol;
  }
}