package controller;

import java.util.List;
import player.PlayerImpl;
import world.World;

/**
 * This interface defines the methods required for a game controller.
 * It ensures that any implementing class can manage the game loop, players,
 * and world interactions.
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
   * Also moves the target character after each turn.
   */
  void playTurn();

  /**
   * Checks if the game is over based on the maximum number of turns allowed.
   * 
   * @return true if the game is over, false otherwise
   */
  boolean isGameOver();
}
