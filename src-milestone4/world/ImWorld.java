package world;

import character.ImTargetCharacter;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import pet.Pet;
import player.PlayerImpl;
import space.ImSpace;

/**
 * This interface represents the world in which spaces, items, and characters exist.
 * It provides methods for retrieving information about spaces, managing neighboring spaces, 
 * moving the target character, and generating a graphical representation of the world.
 */
public interface ImWorld {
  /**
   * Retrieves the neighboring spaces of the given space.
   *
   * @param space the space whose neighbors are to be retrieved.
   * @return a list of neighboring spaces.
   */
  List<ImSpace> getNeighbors(ImSpace space);

  /**
   * Gets detailed information about the specified space.
   *
   * @param space the space to retrieve information about.
   * @return a string containing space information.
   */
  String getSpaceInfo(ImSpace space);

  /**
   * Moves the target character to a new location based on game logic.
   */
  void moveTargetCharacter();

  /**
   * Generates a graphical map representation of the world.
   *
   * @return a {@code BufferedImage} of the world map.
   */
  BufferedImage generateMap();

  /**
   * Retrieves all spaces in the world.
   *
   * @return a list of all spaces.
   */
  List<ImSpace> getSpaces();

  /**
   * Gets the space at the specified index.
   *
   * @param index the index of the space.
   * @return the space at the given index.
   */
  ImSpace getSpace(int index);

  /**
   * Moves the pet to the specified space.
   *
   * @param space the space to move the pet to.
   */
  void movePetToSpace(ImSpace space);

  /**
   * Checks if the pet makes a specific space invisible.
   *
   * @param space the space to check.
   * @return {@code true} pet makes the space invisible, {@code false} otherwise.
   */
  boolean petMakesSpaceInvisible(ImSpace space);

  /**
   * Gets the current locations of players mapped to their respective spaces.
   *
   * @return a map of spaces to lists of players in those spaces.
   */
  Map<ImSpace, List<PlayerImpl>> getPlayerLocations();

  /**
   * Retrieves the current location of the target character.
   *
   * @return the space where the target character is located.
   */
  ImSpace getTargetCharacterLocation();

  /**
   * Retrieves the current location of the pet.
   *
   * @return the space where the pet is located.
   */
  ImSpace getPetLocation();

  /**
   * Retrieves the spaces visible from the specified space.
   *
   * @param space the space to check visibility from.
   * @return a list of visible spaces.
   */
  List<ImSpace> getVisibleSpaces(ImSpace space);

  /**
   * Gets the name of the world.
   *
   * @return the name of the world.
   */
  String getName();

  /**
   * Gets the number of rows in the world.
   *
   * @return the number of rows.
   */
  int getRows();

  /**
   * Gets the number of columns in the world.
   *
   * @return the number of columns.
   */
  int getCols();

  /**
   * Moves a player to a specified space.
   *
   * @param player   the player to move.
   * @param newSpace the space to move the player to.
   */
  void movePlayer(PlayerImpl player, ImSpace newSpace);

  /**
   * Updates the current locations of all players in the world.
   */
  void updatePlayerLocations();

  /**
   * Gets the pet in the world.
   *
   * @return the pet.
   */
  Pet getPet();

  /**
   * Gets the target character in the world.
   *
   * @return the target character.
   */
  ImTargetCharacter getTargetCharacter();

  /**
   * Retrieves all players in the world.
   *
   * @return a list of players.
   */
  List<PlayerImpl> getPlayers();

  /**
   * Displays the current locations of all players.
   */
  void displayPlayerLocations();
}
