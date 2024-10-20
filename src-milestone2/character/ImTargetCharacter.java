package character;

/**
 * This interface represents the target character in the game world.
 * The character can move between spaces in an ordered list, 
 * receive damage from various items, and maintain a health state.
 */
public interface ImTargetCharacter {

  /**
   * Moves the character to the next space in the ordered space list.
   * This allows the character to navigate sequentially through the world.
   */
  void moveToNextSpace();

  /**
   * Retrieves the current space index where the target character is located.
   *
   * @return the index of the current space as an integer
   */
  int getCurrentSpace();

  /**
   * Reduces the health of the target character by the specified amount of damage.
   * This simulates the character being attacked or harmed.
   *
   * @param damage the amount of health to subtract from the character
   *               (must be a non-negative integer)
   */
  void takeDamage(int damage);

  /**
   * Retrieves the name of the target character.
   * 
   * @return the name of the character as a {@code String}
   */
  public String getName();

  /**
   * Retrieves the current health of the target character.
   *
   * @return the remaining health as an integer
   */
  public int getHealth();
}
