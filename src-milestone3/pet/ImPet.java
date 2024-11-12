package pet;

import space.ImSpace;

/**
 * Interface for the pet in the game.
 */
public interface ImPet {
  /**
   * Moves the pet to a specified space.
   *
   * @param space the space to move the pet to
   */
  void moveTo(ImSpace space);

  /**
   * Retrieves the current space of the pet.
   *
   * @return the current space of the pet as an {@code ImSpace} instance
   */
  ImSpace getCurrentSpace();

}
