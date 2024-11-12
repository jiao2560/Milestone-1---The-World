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
   * Retrieves the maximum number of items the player can carry.
   *
   * @return the maximum number of items as an integer
   */
  int getMaxItems();

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
  public boolean lookAround(World world);

  /**
   * Allows the player to attempt an attack on the target character.
   *
   * @param target the target character to attempt an attack on
   * @param world  the world object to access space information
   * @param allPlayers the list of all players in the game
   * @return {@code true} if the attack was successful; otherwise, {@code false}
   */
  boolean attemptKill(ImTargetCharacter target, World world, List<PlayerImpl> allPlayers);

  /**
   * Checks if this player can see another specified player.
   *
   * @param other the player to check visibility of
   * @return {@code true} if this player can see the other player; otherwise, {@code false}
   */
  boolean canSee(Player other);
}


