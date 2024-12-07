
package character;

/**
 * This class represents the target character in the world. 
 * The character moves between spaces and can take damage from items.
 */
public class TargetCharacter implements ImTargetCharacter {
  private final String name;
  private int health;
  private int previousHealth;
  private int currentPosition;

  /**
   * Constructs a new target character with the specified name, 
   * health, and starting position.
   * 
   * @param name the name of the target character
   * @param health the initial health of the target character
   * @param currentPosition the starting position of the target character in the world
   */
  public TargetCharacter(String name, int health, int currentPosition) {
    this.name = name;
    this.health = health;
    this.previousHealth = health;
    this.currentPosition = currentPosition;
  }

  @Override
  public void moveToNextSpace() {
    currentPosition++;
    // Reset to the first space if the character reaches the end
    if (currentPosition < 0) {
      currentPosition = 0;
    }
  }

  @Override
  public void moveToSpace(int moveIndex) {
    this.currentPosition = moveIndex;
  }

  @Override
  public int getCurrentSpace() {
    return currentPosition;
  }

  @Override
  public void takeDamage(int damage) {
    previousHealth = health;
    health -= damage;
    if (health < 0) {
      health = 0;
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public int getPreviousHealth() {
    return previousHealth;
  }

  /**
   * Checks if the target character is alive.
   * 
   * @return {@code true} if the character's health is above 0, otherwise {@code false}
   */
  @Override
  public boolean isAlive() {
    return health > 0;
  }
}
