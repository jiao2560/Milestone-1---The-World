package controller;

import java.util.List;
import player.PlayerImpl;
import world.World;

/**
 * This interface defines the methods required for a game controller.
 * It ensures that any implementing class can manage the game loop, players,
 * and interactions within the game world.
 */
public interface ImGameController {

  /**
   * Adds a player to the game and places them in their starting space.
   *
   * @param player the player to add to the game
   */
  void addPlayer(PlayerImpl player);

  /**
   * Plays a single turn in the game, alternating between players.
   * Also moves the target character and the pet after each turn.
   */
  void playTurn();

  /**
   * Checks if the game is over based on the maximum number of turns allowed.
   *
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Checks if an attack by the given player is seen by other players in the game.
   *
   * @param attacker the player attempting an attack
   * @return true if the attack is seen by others, false otherwise
   */
  boolean isAttackSeen(PlayerImpl attacker);

  /**
   * Retrieves the list of all players in the game.
   *
   * @return a {@code List} of {@code PlayerImpl} objects representing the players
   */
  List<PlayerImpl> getPlayers();

  /**
   * Retrieves the current turn number.
   *
   * @return the current turn as an integer
   */
  int getCurrentTurn();
}

