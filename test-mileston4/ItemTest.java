
import static org.junit.Assert.assertEquals;

import item.Item;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@code Item} class. Verifies that the item's attributes
 * and methods, such as initialization, getters, and the string representation,
 * function as expected.
 */
public class ItemTest {

  private Item item;

  /**
   * Sets up a new {@code Item} instance with a name and damage value
   * before each test.
   */
  @Before
  public void setUp() {
    item = new Item("Sword", 10);
  }

  /**
   * Tests that the item is initialized with the correct name and damage.
   */
  @Test
  public void testInitialization() {
    assertEquals("Sword", item.getName());
    assertEquals(10, item.getDamage());
  }

  /**
   * Tests that {@code getName()} returns the correct name of the item.
   */
  @Test
  public void testGetName() {
    assertEquals("Sword", item.getName());
  }

  /**
   * Tests that {@code getDamage()} returns the correct damage value of the item.
   */
  @Test
  public void testGetDamage() {
    assertEquals(10, item.getDamage());
  }

  /**
   * Tests that {@code toString()} returns the correct string representation
   * of the item, showing its name and damage.
   */
  @Test
  public void testToString() {
    assertEquals("Sword (Damage: 10)", item.toString());
  }
}