package world;

import java.awt.image.BufferedImage;
import java.util.List;
import space.ImSpace;


/**
 * This interface represents the world in which spaces, items, and characters exist.
 * It provides methods to determine neighboring spaces, move the target character, 
 * and retrieve information about spaces and items.
 */
public interface ImWorld {
  /**
   * Gets the neighboring spaces of a given space.
   * Neighboring spaces are spaces that share a "wall" with the specified space.
   * 
   * @param space the space for which to determine the neighbors
   * @return a list of neighboring spaces
   */
  List<ImSpace> getNeighbors(ImSpace space);

  /**
   * Displays information about a specified space in the world. 
   * This information includes the name of the space, the items in the space, 
   * and the spaces that can be seen from the specified space.
   * 
   * @param space the space for which to display information
   * @return a string containing the space's details
   */
  String getSpaceInfo(ImSpace space);

  /**
   * Moves the target character around the world in order of the space list.
   * The character starts in space 0 and moves to the next space in sequence.
   */
  void moveTargetCharacter();

  /**
   * Creates a graphical representation of the world in the form of a BufferedImage.
   * 
   * @return a BufferedImage representing the world map
   */
  BufferedImage generateMap();
}
