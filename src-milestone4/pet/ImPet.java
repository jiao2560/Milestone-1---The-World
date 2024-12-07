package pet;

import space.ImSpace;

/**
 * Interface for the pet in the game.
 */
public interface ImPet {
  /**
   * Moves the pet to the specified space.
   *
   * @param space the space to move the pet to.
   */
  void moveTo(ImSpace space);

  /**
   * Retrieves the current space where the pet is located.
   *
   * @return the current space of the pet.
   */
  ImSpace getCurrentSpace();

  /**
   * Gets the name of the pet.
   *
   * @return the pet's name.
   */
  String getName();

  /**
   * Retrieves the name of the pet's owner.
   *
   * @return the owner's name.
   */
  String getOwnerName();

}
