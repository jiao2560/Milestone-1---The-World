package space;

import item.ImItem;
import java.util.ArrayList;
import java.util.List;
import player.Player;

/**
 * This class represents a space in the world. 
 * A space has a name, coordinates, and contains a list of items, players, and neighboring spaces.
 * Spaces form the structure of the game world, allowing players to move and interact with items.
 */
public class Space implements ImSpace {

  private String name;
  private int upperLeftRow;
  private int upperLeftCol;
  private int lowerRightRow;
  private int lowerRightCol;
  private List<ImItem> items;
  private List<Player> players;
  private List<ImSpace> neighbors;

  /**
   * Constructs a {@code Space} object with the specified name and coordinates.
   *
   * @param name           the name of the space
   * @param upperLeftRow   the row index of the upper-left corner of the space
   * @param upperLeftCol   the column index of the upper-left corner of the space
   * @param lowerRightRow  the row index of the lower-right corner of the space
   * @param lowerRightCol  the column index of the lower-right corner of the space
   */
  public Space(String name, int upperLeftRow, int upperLeftCol, 
               int lowerRightRow, int lowerRightCol) {
    this.name = name;
    this.upperLeftRow = upperLeftRow;
    this.upperLeftCol = upperLeftCol;
    this.lowerRightRow = lowerRightRow;
    this.lowerRightCol = lowerRightCol;
    this.items = new ArrayList<>();
    this.players = new ArrayList<>();
    this.neighbors = new ArrayList<>();
  }

  /**
   * Adds an item to this space.
   *
   * @param item the {@code ImItem} to add
   */
  @Override
  public void addItem(ImItem item) {
    items.add(item);
  }

  /**
   * Removes an item from this space.
   *
   * @param item the {@code ImItem} to remove
   */
  @Override
  public void removeItem(ImItem item) {
    items.remove(item);
  }

  /**
   * Retrieves the list of items currently in this space.
   *
   * @return a {@code List} of {@code ImItem} objects present in the space
   */
  @Override
  public List<ImItem> getItems() {
    return items;
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
}
