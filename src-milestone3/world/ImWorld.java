package world;

import java.awt.image.BufferedImage;
import java.util.List;
import space.ImSpace;

/**
 * This interface represents the world in which spaces, items, and characters exist.
 * It provides methods for retrieving information about spaces, managing neighboring spaces, 
 * moving the target character, and generating a graphical representation of the world.
 */
public interface ImWorld {

  /**
   * Retrieves the neighboring spaces of the specified space.
   * Neighboring spaces share a "wall" or boundary with the given space, 
   * allowing movement between them.
   *
   * @param space the {@code ImSpace} for which to determine the neighbors
   * @return a {@code List} of neighboring {@code ImSpace} objects
   */
  List<ImSpace> getNeighbors(ImSpace space);

  /**
   * Displays detailed information about the specified space.
   * This information includes the space's name, the items it contains, 
   * and the names of spaces that are visible or accessible from it.
   *
   * @param space the {@code ImSpace} for which to display information
   * @return a {@code String} containing the details of the specified space
   */
  String getSpaceInfo(ImSpace space);

  /**
   * Moves the target character to the next space in the sequence.
   * The character begins in space 0 and moves to the next space in order, 
   * cycling back to the first space after reaching the last one.
   */
  void moveTargetCharacter();

  /**
   * Generates a graphical representation of the world as a {@code BufferedImage}.
   * This image can be used to visually represent the layout and elements of the world.
   *
   * @return a {@code BufferedImage} representing the world map
   */
  BufferedImage generateMap();

  /**
   * Retrieves the list of all spaces in the world.
   *
   * @return a {@code List} of {@code ImSpace} objects representing the spaces in the world
   */
  List<ImSpace> getSpaces();

  /**
   * Retrieves a specific space from the world based on its index.
   *
   * @param index the index of the space to retrieve
   * @return the {@code ImSpace} object at the specified index
   */
  ImSpace getSpace(int index);
  
  /**
   * Moves the pet to a specified space.
   *
   * @param space the {@code ImSpace} where the pet should be moved
   */
  void movePetToSpace(ImSpace space);

  /**
   * Determines if a space is invisible due to the presence of the pet.
   *
   * @param space the {@code ImSpace} to check
   * @return {@code true} if the space is invisible due to the pet; otherwise {@code false}
   */
  boolean petMakesSpaceInvisible(ImSpace space);
}
