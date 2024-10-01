package space;

import item.ImItem;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a space in the world. 
 * A space has a name, coordinates, and contains a list of items.
 */
public class Space implements ImSpace {

  private String name;
  private int upperLeftRow;
  private int upperLeftCol;
  private int lowerRightRow;
  private int lowerRightCol;
  private List<ImItem> items;

  /**
   * Constructs a new space with the specified name and coordinates.
   * 
   * @param name the name of the space
   * @param upperLeftRow the row index of the upper left corner of the space
   * @param upperLeftCol the column index of the upper left corner of the space
   * @param lowerRightRow the row index of the lower right corner of the space
   * @param lowerRightCol the column index of the lower right corner of the space
   */
  public Space(String name, int upperLeftRow, int upperLeftCol, 
      int lowerRightRow, int lowerRightCol) {
    this.name = name;
    this.upperLeftRow = upperLeftRow;
    this.upperLeftCol = upperLeftCol;
    this.lowerRightRow = lowerRightRow;
    this.lowerRightCol = lowerRightCol;
    this.items = new ArrayList<>();
  }

  /**
   * Adds an item to the space.
   * 
   * @param item the item to be added to the space
   */
  @Override
  public void addItem(ImItem item) {
    items.add(item);
  }

  /**
   * Retrieves all items present in the space.
   * 
   * @return a list of items in the space
   */
  @Override
  public List<ImItem> getItems() {
    return items;
  }

  /**
   * Gets the name of the space.
   * 
   * @return the name of the space
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Retrieves the upper-left and lower-right coordinates of the space.
   * This can be useful when determining whether two spaces are neighbors.
   * 
   * @return an array representing [upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol]
   */
  @Override
  public int[] getCoordinates() {
    return new int[] {upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol};
  }
}
