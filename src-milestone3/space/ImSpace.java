package space;

import item.ImItem;
import java.util.List;
import player.Player;

/**
 * This interface represents a space in the world. 
 * A space may contain items, and it is connected to other spaces in the world.
 */
public interface ImSpace {

  /**
   * Adds an item to the space.
   * 
   * @param item the item to be added to the space
   */
  void addItem(ImItem item);

  /**
   * Removes an item from the space.
   * 
   * @param item the item to be removed from the space
   */
  void removeItem(ImItem item);

  /**
   * Retrieves all items present in the space.
   * 
   * @return a list of items in the space
   */
  List<ImItem> getItems();

  /**
   * Gets the name of the space.
   * 
   * @return the name of the space
   */
  String getName();

  /**
   * Retrieves the upper-left and lower-right coordinates of the space.
   * 
   * @return an array representing [upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol]
   */
  int[] getCoordinates();

  /**
   * Retrieves the neighboring spaces.
   * 
   * @return a list of spaces that are neighbors of this space
   */
  List<ImSpace> getNeighbors();

  /**
   * Adds a neighboring space to this space.
   * 
   * @param space the neighboring space to be added
   */
  void addNeighbor(ImSpace space);

  /**
   * Adds a player to the space.
   * 
   * @param player the player to be added
   */
  void addPlayer(Player player);

  /**
   * Removes a player from the space.
   * 
   * @param player the player to be removed
   */
  void removePlayer(Player player);

  /**
   * Retrieves all players present in the space.
   * 
   * @return a list of players in this space
   */
  List<Player> getPlayers();
  
  /**
   * Gets the unique identifier for the space.
   *
   * @return the id of the space
   */
  int getId();
}
