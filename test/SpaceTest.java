import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import item.ImItem;
import item.Item;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import space.Space;

/**
 * Test class for {@link Space}.
 * This class contains unit tests to verify the behavior of the Space class.
 */
public class SpaceTest {
  private Space space;
  private ImItem item1;
  private ImItem item2;

  /**
   * Sets up the test environment by initializing a Space instance and adding items to it.
   * This method runs before each test.
   */
  @Before
  public void setUp() {
    // Create a new Space instance before each test
    space = new Space("Kitchen", 0, 0, 3, 3);
    
    // Create test items
    item1 = new Item("Knife", 5);
    item2 = new Item("Pan", 3);
    
    // Add items to space
    space.addItem(item1);
    space.addItem(item2);
  }

  /**
   * Tests whether the name of the space is correctly retrieved.
   */
  @Test
  public void testGetName() {
    assertEquals("Kitchen", space.getName());
  }

  /**
   * Tests whether the coordinates of the space are correctly retrieved.
   */
  @Test
  public void testGetCoordinates() {
    int[] expectedCoordinates = {0, 0, 3, 3};
    assertArrayEquals(expectedCoordinates, space.getCoordinates());
  }

  /**
   * Tests whether items are correctly added and retrieved from the space.
   */
  @Test
  public void testGetItems() {
    List<ImItem> items = space.getItems();
    assertEquals(2, items.size());
    assertTrue(items.contains(item1));
    assertTrue(items.contains(item2));
  }

  @Test
  public void testSpaceParsing() {
    String spaceInfo = "0 0 3 3 Kitchen";  // Simulating space info from a file
    String[] parts = spaceInfo.split(" ");
    int upperLeftRow = Integer.parseInt(parts[0]);
    int upperLeftCol = Integer.parseInt(parts[1]);
    int lowerRightRow = Integer.parseInt(parts[2]);
    int lowerRightCol = Integer.parseInt(parts[3]);
    String name = parts[4];
    
    Space parsedSpace = new Space(name, upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol);
    assertEquals("Kitchen", parsedSpace.getName());
    int[] expectedCoordinates = {0, 0, 3, 3};
    assertArrayEquals(expectedCoordinates, parsedSpace.getCoordinates());
  }
}
