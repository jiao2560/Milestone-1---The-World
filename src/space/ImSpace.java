package space;

import item.ImItem;
import java.util.List;

/**
 * This interface represents a space in the world. 
 * A space may contain items, and it is connected to other spaces in the world.
 */
public interface ImSpace {

  /**
   * Adds an item to the space.
   * 
   * @param item the item to be added to the space
   */
  void addItem(ImItem item);

  /**
   * Retrieves all items present in the space.
   * 
   * @return a list of items in the space
   */
  List<ImItem> getItems();

  /**
   * Gets the name of the space.
   * 
   * @return the name of the space
   */
  String getName();
    
  /**
   * Retrieves the upper-left and lower-right coordinates of the space.
   * This can be useful when determining whether two spaces are neighbors.
   * 
   * @return an array representing [upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol]
   */
  public int[] getCoordinates();
}