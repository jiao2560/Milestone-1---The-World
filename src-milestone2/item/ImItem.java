package item;

/**
 * This interface represents an item that can be found in a space within the world.
 * Each item has an associated damage value and a name.
 */
public interface ImItem {

  /**
   * Retrieves the damage that this item can do if used to attack the target character.
   * 
   * @return the damage value of the item
   */
  int getDamage();

  /**
   * Gets the name of the item.
   * 
   * @return the name of the item
   */
  String getName();
}