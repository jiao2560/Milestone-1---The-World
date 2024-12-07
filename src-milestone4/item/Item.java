package item;

/**
 * This class represents an item in the world.
 * Each item has a name and a damage value, which defines 
 * how much damage it does to the target character.
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

  @Override
  public int getDamage() {
    return damage;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Provides a string representation of the item, showing its name and damage value.
   *
   * @return the string representation of the item
   */
  @Override
  public String toString() {
    return name + " (Damage: " + damage + ")";
  }
}