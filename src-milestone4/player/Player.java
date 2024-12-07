package player;

import character.ImTargetCharacter;
import item.ImItem;
import java.util.List;
import space.ImSpace;
import world.World;

/**
 * This interface defines the behaviors and properties of a player in the game.
 * A player can move between spaces, collect items, attempt actions on target characters,
 * and inspect their surroundings.
 */
public interface Player {
  /**
   * Gets the name of the player.
   *
   * @return the player's name.
   */
  String getName();

  /**
   * Retrieves the player's current space.
   *
   * @return the space where the player is currently located.
   */
  ImSpace getCurrentSpace();

  /**
   * Gets the list of items the player is carrying.
   *
   * @return a list of items.
   */
  List<ImItem> getItems();

  /**
   * Retrieves the maximum number of items the player can carry.
   *
   * @return the maximum number of items.
   */
  int getMaxItems();

  /**
   * Moves the player to the specified space.
   *
   * @param space the space to move the player to.
   */
  void moveTo(ImSpace space);

  /**
   * Picks up the specified item and adds it to the player's inventory.
   *
   * @param item the item to pick up.
   */
  void pickUpItem(ImItem item);

  /**
   * Checks if the player can carry more items.
   *
   * @return {@code true} if the player can carry more items.
   */
  boolean canCarryMoreItems();

  /**
   * Retrieves the names of the neighboring spaces.
   *
   * @return a list of neighboring space names.
   */
  List<String> getNeighborNames();

  /**
   * Allows the player to look around and inspect world.
   *
   * @param world the game world to inspect.
   */
  void lookAround(World world);

  /**
   * Attempts to attack the target character in the specified world.
   *
   * @param target     the target character to attack.
   * @param world      the game world where the attempt occurs.
   * @param allPlayers the list of all players in the game.
   * @return {@code true} if the attack is successful, {@code false} otherwise.
   */
  boolean attemptKill(ImTargetCharacter target, 
      World world, List<PlayerImpl> allPlayers);

  /**
   * Checks if the player can see another player.
   *
   * @param other the other player to check visibility for.
   * @return {@code true} if the other player is visible, {@code false} otherwise.
   */
  boolean canSee(Player other);

  /**
   * Picks up an available item in the player's current space.
   */
  void pickUpAvailableItem();
}
