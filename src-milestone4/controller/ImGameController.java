package controller;

import character.ImTargetCharacter;
import java.awt.image.BufferedImage;
import java.util.List;
import player.PlayerImpl;
import space.ImSpace;
import view.GameView;
import world.World;

/**
 * This interface defines the methods required for a game controller.
 * It ensures that any implementing class can m players,
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
   * Checks if an attack by the given player is see.
   *
   * @param attacker the player attempting an attack
   * @return true if the attack is seen by others, false otherwise
   */
  boolean isAttackSeen(PlayerImpl attacker);

  /**
   * Retrieves the list of all players in the game.
   *
   * @return a {@code List} of {@code PlayerImpl} objects 
   */
  List<PlayerImpl> getPlayers();

  /**
   * Retrieves the current turn number.
   *
   * @return the current turn as an integer
   */
  int getCurrentTurn();
  
  /**
   * Attempts an attack in the game.
   *
   * @return {@code true} if the attack is successful, {@code false} otherwise.
   */
  public boolean attemptAttack();

  /**
   * Gets a description of the specified player.
   *
   * @param player the player whose description is to be retrieved.
   * @return a string description of the player.
   */
  public String getPlayerDescription(PlayerImpl player);

  /**
   * Retrieves the current world map as an image.
   *
   * @return the {@code BufferedImage} of the world map.
   */
  public BufferedImage getWorldMap();

  /**
   * Gets the names of items available in the player's current space.
   *
   * @return a list of item names.
   */
  public List<String> getItemsInCurrentSpace();

  /**
   * Attempts to pick up an item by its name in the current space.
   *
   * @param itemName the name of the item to pick up.
   * @return {@code true} if the item {@code false} otherwise.
   */
  public boolean pickUpItem(String itemName);

  /**
   * Moves the player to the specified target space.
   *
   * @param targetSpace the space to move the player to.
   * @return {@code true} if the player {@code false} otherwise.
   */
  public boolean movePlayerTo(ImSpace targetSpace);

  /**
   * Retrieves the neighboring spaces of the human player.
   *
   * @return a list of neighboring spaces.
   */
  public List<ImSpace> getHumanPlayerNeighbors();

  /**
   * Sets the view for the game.
   *
   * @param view the {@code GameView} to set.
   */
  public void setView(GameView view);
  
  public void setupListeners();

  public void handleDoctorLuckyMovement(PlayerImpl currentPlayer);

  public void processAttackResult(PlayerImpl currentPlayer, 
      boolean attackOccurred, ImTargetCharacter targetCharacter);

  public void endTurn();

  public void handleAiTurn(PlayerImpl currentPlayer);

  public void displayDoctorLuckyInfo();
}


