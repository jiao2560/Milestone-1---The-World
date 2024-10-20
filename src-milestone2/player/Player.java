package player;

import item.ImItem;
import java.util.List;
import space.ImSpace;

/**
 * This interface defines the behaviors and properties of a player in the game.
 * A player can move between spaces, collect items, and inspect their surroundings.
 */
public interface Player {

  /**
   * Retrieves the name of the player.
   *
   * @return the name of the player as a {@code String}
   */
  String getName();

  /**
   * Retrieves the current space occupied by the player.
   *
   * @return the current {@code ImSpace} object where the player is located
   */
  ImSpace getCurrentSpace();

  /**
   * Retrieves the list of items the player is currently carrying.
   *
   * @return a {@code List} of {@code ImItem} objects held by the player
   */
  List<ImItem> getItems();

  /**
   * Moves the player to a neighboring space.
   *
   * @param space the {@code ImSpace} object representing the new space to move to
   */
  void moveTo(ImSpace space);

  /**
   * Picks up an item from the current space and adds it to the player's inventory.
   *
   * @param item the {@code ImItem} object to be picked up
   */
  void pickUpItem(ImItem item);

  /**
   * Checks whether the player can carry more items.
   *
   * @return {@code true} if the player can carry more items, {@code false} otherwise
   */
  boolean canCarryMoreItems();

  /**
   * Displays the spaces and items around the player's current location.
   * This method allows the player to "look around" and gather information 
   * about nearby spaces and items.
   */
  void lookAround();
}
