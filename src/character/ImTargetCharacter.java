package character;

/**
 * This interface represents the target character within the world.
 * The character can move between spaces and take damage from items.
 */
public interface ImTargetCharacter {
  /**
   * Moves the character to the next space in the ordered space list.
   */
  void moveToNextSpace();

  /**
   * Gets the current space the target character is in.
   * 
   * @return the index of the current space
   */
  int getCurrentSpace();

  /**
   * Reduces the health of the target character by the specified damage amount.
   * 
   * @param damage the amount of damage to inflict on the target character
   */
  void takeDamage(int damage);
}