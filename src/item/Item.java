package item;

/**
 * This class represents an item in the world.
 * Each item has a name and a damage value, which defines how much damage do to the targetcharacter.
 */
public class Item implements ImItem {
  private String name;
  private int damage;

  /**
   * Constructs a new item with the specified name and damage value.
   * 
   * @param name the name of the item
   * @param damage the amount of damage this item can deal to the target character
   */
  public Item(String name, int damage) {
    this.name = name;
    this.damage = damage;
  }

  /**
   * Retrieves the damage that this item can do if used to attack the target character.
   * 
   * @return the damage value of the item
   */
  @Override
  public int getDamage() {
    return damage;
  }

  /**
   * Gets the name of the item.
   * 
   * @return the name of the item
   */
  @Override
  public String getName() {
    return name;
  }
}
