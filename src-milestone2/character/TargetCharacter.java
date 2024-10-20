package character;

/**
 * This class represents the target character in the world. 
 * The character moves between spaces and can take damage from items.
 */
public class TargetCharacter implements ImTargetCharacter {
  private String name;
  private int health;
  private int currentPosition; // Position index of the target character in the list of spaces

  /**
   * Constructs a new target character with the specified name, health, and starting position.
   * 
   * @param name the name of the target character
   * @param health the initial health of the target character
   * @param currentPosition the starting position of the target character in the world
   */
  public TargetCharacter(String name, int health, int currentPosition) {
    this.name = name;
    this.health = health;
    this.currentPosition = currentPosition;
  }

  /**
   * Moves the character to the next space in the ordered list of spaces.
   */
  @Override
  public void moveToNextSpace() {
    currentPosition++;
  }
  
  /**
   * Moves the character to the specified space index.
   * 
   * @param moveIndex the index of the space to move to
   */
  public void moveToSpace(int moveIndex) {
    this.currentPosition = moveIndex;
  }
  
  /**
   * Gets the current space index of the target character.
   * 
   * @return the index of the current space
   */
  @Override
  public int getCurrentSpace() {
    return currentPosition;
  }
  
  /**
   * Reduces the health of the target character by the specified damage amount.
   * 
   * @param damage the amount of damage to inflict on the target character
   */
  @Override
  public void takeDamage(int damage) {
    this.health -= damage;
    if (this.health < 0) {
      this.health = 0;
    }
  }
  
  /**
   * Gets the name of the target character.
   * 
   * @return the name of the character
   */
  @Override
  public String getName() {
    return this.name;
  }
  
  /**
   * Gets the current health of the target character.
   * 
   * @return the health of the character
   */
  @Override
  public int getHealth() {
    return health;
  }
}
