import static org.junit.Assert.assertEquals;

import item.Item;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Item.
 */
public class ItemTest {

  private Item item;

  @Before
  public void setUp() {
    // Initialize an item with name "Revolver" and damage 3
    item = new Item("Revolver", 3);
  }

  @Test
  public void testGetName() {
    // Verify that the item's name is correctly returned
    assertEquals("Revolver", item.getName());
  }

  @Test
  public void testGetDamage() {
    // Verify that the item's damage value is correctly returned
    assertEquals(3, item.getDamage());
  }

  @Test
  public void testToString() {
    // Verify that the string representation is formatted correctly
    assertEquals("Revolver (Damage: 3)", item.toString());
  }
}
