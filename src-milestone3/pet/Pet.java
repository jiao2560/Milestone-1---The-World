package pet;

import character.ImTargetCharacter;
import space.ImSpace;
import world.World;

/**
 * Represents the pet in the game that is associated with the target character.
 * The pet can move between spaces, making its current space invisible to others.
 */
public class Pet implements ImPet {
  private final String name;
  private ImSpace currentSpace;
  private final ImTargetCharacter owner;

  /**
   * Constructs a Pet with the specified name and owner.
   * 
   * @param name   the name of the pet
   * @param owner  the target character to whom this pet belongs
   */
  // Modify the Pet constructor to use the World instance for initial position
  public Pet(String name, ImTargetCharacter owner, World world) {
    this.name = name;
    this.owner = owner;
    this.currentSpace = world.getSpace(owner.getCurrentSpace()); 
  }


  @Override
  public void moveTo(ImSpace space) {
    this.currentSpace = space;
  }

  @Override
  public ImSpace getCurrentSpace() {
    return this.currentSpace;
  }
  
  /**
   * Retrieves the name of the player.
   *
   * @return the player's name as a {@code String}
   */
  public String getName() {
    return this.name;
  }
}
