import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import character.ImTargetCharacter;
import item.ImItem;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Unit tests for the {@code Space} class. Verifies that space attributes and
 * behaviors, such as managing items, players, neighbors, and coordinates,
 * function as expected.
 */
public class SpaceTest {

  private Space space;
  private ImItem item;
  private Player player;

  /**
   * Sets up a new instance with initial values for testing.
   */
  @Before
  public void setUp() {
    space = new Space(1, "Lobby", 0, 0, 2, 2);
    item = new TestItem("Key", 5);
    player = new TestPlayer("Player1");
  }

  /**
   * Tests that the space is initialized with the correct name, id, and coordinates.
   */
  @Test
  public void testInitialization() {
    assertEquals("Lobby", space.getName());
    assertEquals(1, space.getId());

    int[] coordinates = space.getCoordinates();
    assertEquals(0, coordinates[0]);
    assertEquals(0, coordinates[1]);
    assertEquals(2, coordinates[2]);
    assertEquals(2, coordinates[3]);
  }

  /**
   * Tests that correctly adds an item to the space.
   */
  @Test
  public void testAddItem() {
    space.addItem(item);
    assertTrue(space.getItems().contains(item));
  }

  /**
   * Tests that correctly removes an item from the space.
   */
  @Test
  public void testRemoveItem() {
    space.addItem(item);
    space.removeItem(item);
    assertFalse(space.getItems().contains(item));
  }

  /**
   * Tests that correctly adds a player to the space.
   */
  @Test
  public void testAddPlayer() {
    space.addPlayer(player);
    assertTrue(space.getPlayers().contains(player));
  }

  /**
   * Tests that correctly removes a player from the space.
   */
  @Test
  public void testRemovePlayer() {
    space.addPlayer(player);
    space.removePlayer(player);
    assertFalse(space.getPlayers().contains(player));
  }

  /**
   * Tests that {@code getNeighbors()} initially returns an empty list of neighbors.
   */
  @Test
  public void testGetNeighborsInitiallyEmpty() {
    assertTrue(space.getNeighbors().isEmpty());
  }

  /**
   * Tests that {@code addNeighbor(ImSpace space)} correctly adds a neighboring space.
   */
  @Test
  public void testAddNeighbor() {
    Space neighbor = new Space(2, "Hallway", 3, 0, 5, 2);
    space.addNeighbor(neighbor);
    assertTrue(space.getNeighbors().contains(neighbor));
  }

  /**
   * Tests that {@code getCoordinates()} returns the correct coordinates of the space.
   */
  @Test
  public void testGetCoordinates() {
    int[] coordinates = space.getCoordinates();
    assertEquals(4, coordinates.length);
    assertEquals(0, coordinates[0]); // upper-left row
    assertEquals(0, coordinates[1]); // upper-left col
    assertEquals(2, coordinates[2]); // lower-right row
    assertEquals(2, coordinates[3]); // lower-right col
  }

  // Inner classes for minimal implementations of ImItem and Player for testing

  class TestItem implements ImItem {
    private String name;
    private int damage;
    
    /**
     * Constructs a new {@code TestItem} with the specified name and damage.
     *
     * @param name   the name of the item
     * @param damage the damage value associated with the item
     */
    public TestItem(String name, int damage) {
      this.name = name;
      this.damage = damage;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public int getDamage() {
      return damage;
    }
  }

  class TestPlayer implements Player {
    private String name;
    
    /**
     * Constructs a new {@code TestPlayer} with the specified name.
     *
     * @param name the name of the player
     */
    public TestPlayer(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public ImSpace getCurrentSpace() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public List<ImItem> getItems() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public int getMaxItems() {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public void moveTo(ImSpace space) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void pickUpItem(ImItem item) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public boolean canCarryMoreItems() {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean lookAround(World world) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean attemptKill(ImTargetCharacter target, 
        World world, List<PlayerImpl> allPlayers) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean canSee(Player other) {
      // TODO Auto-generated method stub
      return false;
    }
  }
}
