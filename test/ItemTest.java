import static org.junit.Assert.assertEquals;

import item.Item;
import org.junit.Before;
import org.junit.Test;
/**
 * Test class for {@link Item}.
 * This class contains unit tests to verify the behavior of the Item class.
 */

public class ItemTest {
  private Item item;

  /**
   * Sets up the test environment by initializing an Item instance.
   * This method runs before each test.
   */
  @Before
  public void setUp() {
    // Create a new Item instance before each test
    item = new Item("Sword", 10);
  }

  /**
   * Tests whether the name of the item is correctly retrieved.
   */
  @Test
  public void testGetName() {
    assertEquals("Sword", item.getName());
  }

  /**
   * Tests whether the damage value of the item is correctly retrieved.
   */
  @Test
  public void testGetDamage() {
    assertEquals(10, item.getDamage());
  }
}

