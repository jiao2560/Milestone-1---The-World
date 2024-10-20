import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import item.ImItem;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import player.PlayerImpl;
import space.ImSpace;

/**
 * Test class for PlayerImpl.
 */
public class PlayerImplTest {

  private PlayerImpl player;
  private TestSpace startingSpace;
  private TestSpace neighborSpace;
  private TestItem item1;
  private TestItem item2;
  
  /**
   * Sets up the test environment before each test case is executed.
   * This method initializes the necessary components, including spaces, items, 
   * and a player, to ensure consistent and predictable conditions for each test.
   * 
   * A starting space and a neighboring space are created for testing player movement.
   * Two items are created and placed in the starting space for item-related testing.
   * A player is initialized within the starting space to test player interactions.
   */
  @Before
  public void setUp() {
    startingSpace = new TestSpace("Starting Space");
    neighborSpace = new TestSpace("Neighbor Space");
    item1 = new TestItem("Item1");
    item2 = new TestItem("Item2");

    startingSpace.addItem(item1);
    startingSpace.addNeighbor(neighborSpace);

    player = new PlayerImpl("TestPlayer", startingSpace, 2, false);
  }

  @Test
  public void testGetName() {
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  public void testGetCurrentSpace() {
    assertEquals(startingSpace, player.getCurrentSpace());
  }

  @Test
  public void testMoveTo() {
    player.moveTo(neighborSpace);
    assertEquals(neighborSpace, player.getCurrentSpace());
  }

  @Test
  public void testPickUpItemSuccess() {
    player.pickUpItem(item1);
    assertTrue(player.getItems().contains(item1));
    assertFalse(startingSpace.getItems().contains(item1));
  }

  @Test
  public void testPickUpItemExceedsMax() {
    player.pickUpItem(item1);
    player.pickUpItem(item2);

    // Try adding a third item (exceeds maxItems)
    TestItem extraItem = new TestItem("ExtraItem");
    startingSpace.addItem(extraItem);

    player.pickUpItem(extraItem);
    assertFalse(player.getItems().contains(extraItem));
  }

  @Test
  public void testCanCarryMoreItems() {
    assertTrue(player.canCarryMoreItems());
    player.pickUpItem(item1);
    assertTrue(player.canCarryMoreItems());

    player.pickUpItem(item2);
    assertFalse(player.canCarryMoreItems());
  }

  @Test
  public void testLookAround() {
    player.lookAround();
    // Manual observation: Output should indicate the items and neighbors.
  }

  @Test
  public void testMoveToNeighbor() {
    player.moveToNeighbor(new ArrayList<>());
    assertEquals(neighborSpace, player.getCurrentSpace());
  }
  
  @Test
  public void testLookAroundWithNoItemsOrNeighbors() {
    // Ensure both items and neighbors are empty
    startingSpace.getItems().clear();
    startingSpace.getNeighbors().clear();

    // Capture the output
    player.lookAround();
    // Manual validation: Output should show "Items here: 
    // None" and "Neighboring spaces: None"
  }
  
  

  @Test
  public void testLookAroundWithItemsAndNeighbors() {
    // Add items and neighbors to the space
    startingSpace.addItem(item1);
    startingSpace.addItem(item2);
    startingSpace.addNeighbor(neighborSpace);

    // Capture the output
    player.lookAround();
    // Manual validation: Output should list the items and the neighboring space names
  }


  @Test
  public void testPickUpAvailableItem() {
    player.pickUpAvailableItem();
    assertTrue(player.getItems().contains(item1));
  }

  @Test
  public void testPickUpRandomItem() {
    player.pickUpRandomItem();
    assertTrue(player.getItems().contains(item1));
  }

  @Test
  public void testDisplayPlayerDescription() {
    player.displayPlayerDescription();
    // Manual observation: Output should indicate player name and carried items.
  }

  // Helper classes for testing
  private static class TestItem implements ImItem {
    private final String name;

    public TestItem(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return name;
    }

    @Override
    public int getDamage() {
      // TODO Auto-generated method stub
      return 0;
    }
  }

  private static class TestSpace implements ImSpace {
    private final String name;
    private final List<ImItem> items = new ArrayList<>();
    private final List<ImSpace> neighbors = new ArrayList<>();

    public TestSpace(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public List<ImItem> getItems() {
      return items;
    }

    @Override
    public List<ImSpace> getNeighbors() {
      return neighbors;
    }

    @Override
    public void removeItem(ImItem item) {
      items.remove(item);
    }

    public void addItem(ImItem item) {
      items.add(item);
    }

    public void addNeighbor(ImSpace space) {
      neighbors.add(space);
    }

    @Override
    public int[] getCoordinates() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void addPlayer(Player player) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void removePlayer(Player player) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public List<Player> getPlayers() {
      // TODO Auto-generated method stub
      return null;
    }
  }
  
  @Test
  public void testPickUpRandomItemWithNoItems() {
    // Ensure the space has no items
    startingSpace.getItems().clear();

    // Redirect System.out to capture output (optional)
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Call the method
    player.pickUpRandomItem();

    // Restore System.out
    System.setOut(System.out);

    // Verify that no items were picked up
    assertTrue(player.getItems().isEmpty());
    String output = outContent.toString();
    assertFalse(output.contains("picked up"));  // No item should be picked up
  }
  
  @Test
  public void testPickUpAvailableItemWithNoItems() {
    // Ensure the space has no items
    startingSpace.getItems().clear();

    // Redirect System.out to capture output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Call the method
    player.pickUpAvailableItem();

    // Restore System.out
    System.setOut(System.out);

    // Verify that the output indicates no items are available
    String output = outContent.toString();
    assertTrue(output.contains("No items to pick up."));
  }
  
  @Test
  public void testMoveToNeighborWithNoNeighbors() {
    // Ensure the space has no neighbors
    startingSpace.getNeighbors().clear();

    // Redirect System.out to capture output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Call the method
    player.moveToNeighbor(new ArrayList<>());

    // Restore System.out
    System.setOut(System.out);

    // Verify that the output indicates no neighbors are available
    String output = outContent.toString();
    assertTrue(output.contains("No neighbors to move to."));
  }
  
  @Test
  public void testDisplayPlayerDescriptionWithItems() {
    // Add items to the player
    player.pickUpItem(item1);
    player.pickUpItem(item2);

    // Redirect System.out to capture output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Call the method
    player.displayPlayerDescription();

    // Restore System.out
    System.setOut(System.out);

    // Verify that the output shows the player's items
    String output = outContent.toString();
    assertTrue(output.contains("Player: TestPlayer"));
    assertTrue(output.contains("Current space: Starting Space"));
    assertTrue(output.contains("Carrying:"));
    assertTrue(output.contains(" - Item1"));
    assertTrue(output.contains(" - Item2"));
  }
}

