import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import item.ImItem;
import item.Item;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;

/**
 * Test class for Space.
 */
public class SpaceTest {

  private Space space;
  private ImSpace neighbor;
  private ImItem item1;
  private ImItem item2;
  private Player player1;
  private Player player2;
  
  /**
   * Sets up the test environment before each test case is executed.
   * This method initializes spaces, items, and players to ensure that 
   * each test starts with a consistent and controlled setup.
   * 
   * A space and its neighbor are created for testing interactions between spaces.
   * Items are created and available for testing item-related functionalities.
   * Two players are initialized within the same space for player interaction testing.
   */
  @Before
  public void setUp() {
    // Initialize a space and a neighbor
    space = new Space("Room A", 0, 0, 5, 5);
    neighbor = new Space("Room B", 6, 6, 10, 10);

    // Create items
    item1 = new Item("Item 1", 5);
    item2 = new Item("Item 2", 10);

    // Create players
    player1 = new PlayerImpl("Player1", space, 5, false);
    player2 = new PlayerImpl("Player2", space, 5, false);
  }

  @Test
  public void testAddAndRemoveItem() {
    // Add items
    space.addItem(item1);
    space.addItem(item2);

    List<ImItem> items = space.getItems();
    assertTrue(items.contains(item1));
    assertTrue(items.contains(item2));

    // Remove item
    space.removeItem(item1);
    assertFalse(items.contains(item1));
    assertTrue(items.contains(item2));
  }

  @Test
  public void testGetName() {
    assertEquals("Room A", space.getName());
  }

  @Test
  public void testGetCoordinates() {
    int[] coordinates = space.getCoordinates();
    assertArrayEquals(new int[]{0, 0, 5, 5}, coordinates);
  }

  @Test
  public void testAddAndRetrieveNeighbors() {
    space.addNeighbor(neighbor);

    List<ImSpace> neighbors = space.getNeighbors();
    assertTrue(neighbors.contains(neighbor));
  }

  @Test
  public void testAddAndRemovePlayer() {
    // Add players
    space.addPlayer(player1);
    space.addPlayer(player2);

    List<Player> players = space.getPlayers();
    assertTrue(players.contains(player1));
    assertTrue(players.contains(player2));

    // Remove player
    space.removePlayer(player1);
    assertFalse(players.contains(player1));
    assertTrue(players.contains(player2));
  }

  @Test
  public void testGetItems() {
    space.addItem(item1);
    space.addItem(item2);

    List<ImItem> items = space.getItems();
    assertEquals(2, items.size());
    assertTrue(items.contains(item1));
    assertTrue(items.contains(item2));
  }

  @Test
  public void testGetNeighbors() {
    space.addNeighbor(neighbor);

    List<ImSpace> neighbors = space.getNeighbors();
    assertEquals(1, neighbors.size());
    assertTrue(neighbors.contains(neighbor));
  }

  @Test
  public void testGetPlayers() {
    space.addPlayer(player1);
    space.addPlayer(player2);

    List<Player> players = space.getPlayers();
    assertEquals(2, players.size());
    assertTrue(players.contains(player1));
    assertTrue(players.contains(player2));
  }
}
