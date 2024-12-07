package character;

/**
 * This interface represents the target character in the game world.
 */
public interface ImTargetCharacter {
  /**
   * Moves the target character to the next space in the sequence.
   */
  void moveToNextSpace();

  /**
   * Retrieves the index of the current space character is located.
   *
   * @return the index of the current space.
   */
  int getCurrentSpace();

  /**
   * Reduces the target character's health by the specified damage amount.
   *
   * @param damage the amount of damage to take.
   */
  void takeDamage(int damage);

  /**
   * Gets the name of the target character.
   *
   * @return the target character's name.
   */
  String getName();

  /**
   * Retrieves the current health of the target character.
   *
   * @return the target character's health.
   */
  int getHealth();

  /**
   * Moves the target character to the specified space by index.
   *
   * @param moveIndex the index of the space to move to.
   */
  void moveToSpace(int moveIndex);

  /**
   * Gets the target character's previous health before the last damage.
   *
   * @return the previous health value.
   */
  int getPreviousHealth();

  /**
   * Checks if the target character is alive.
   *
   * @return {@code true} if the target {@code false} otherwise.
   */
  boolean isAlive();

}
