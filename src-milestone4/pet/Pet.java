package pet;

import character.ImTargetCharacter;
import space.ImSpace;

/**
 * Represents the pet in the game that is associated with the target character.
 * The pet can move between spaces, making its current space invisible to others.
 */
public class Pet implements ImPet {
  private final String name;
  private ImSpace currentSpace;
  private final ImTargetCharacter owner;

  /**
   * Constructs a Pet with the specified name, owner, and initial space.
   * 
   * @param name         the name of the pet
   * @param owner        the target character to whom this pet belongs
   * @param startingSpace the initial space where the pet is located
   */
  public Pet(String name, ImTargetCharacter owner, ImSpace startingSpace) {
    this.name = name;
    this.owner = owner;
    this.currentSpace = startingSpace;
  }

  @Override
  public void moveTo(ImSpace space) {
    this.currentSpace = space;
  }

  @Override
  public ImSpace getCurrentSpace() {
    return this.currentSpace;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getOwnerName() {
    return this.owner.getName();
  }

  /**
   * Placeholder for wandering logic (e.g., depth-first traversal of spaces).
   * This method can be implemented for extra functionality in the game.
   */
  public void wander() {
    // Implement wandering logic, if required, in future milestones
  }
}

