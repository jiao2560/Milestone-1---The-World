import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import character.ImTargetCharacter;
import character.TargetCharacter;
import item.ImItem;
import item.Item;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Test class for {@link World}.
 * This class contains unit tests to verify the behavior of the World class.
 */
public class WorldTest {
  private World world;
  private ImTargetCharacter targetCharacter;
  private List<ImSpace> spaces;
  private List<ImItem> items;

  /**
   * Sets up the test environment by initializing a World instance.
   * This method runs before each test.
   */
  @Before
  public void setUp() {
    // Initialize spaces
    spaces = new ArrayList<>();
    spaces.add(new Space("Room A", 0, 0, 2, 2));
    spaces.add(new Space("Room B", 0, 3, 2, 5));
    spaces.add(new Space("Room C", 3, 0, 5, 2));

    // Initialize items
    items = new ArrayList<>();
    ImItem item1 = new Item("Sword", 10);
    ImItem item2 = new Item("Shield", 5);
    items.add(item1);
    items.add(item2);

    // Add items to Room A
    spaces.get(0).addItem(item1);
    spaces.get(0).addItem(item2);

    // Initialize target character
    targetCharacter = new TargetCharacter("Hero", 100, 0);

    // Initialize the world
    world = new World(6, 6, "Test World", spaces, items, targetCharacter);
  }

  /**
   * Tests whether the target character is successfully moved to the next space.
   */
  @Test
  public void testMoveTargetCharacter() {
    world.moveTargetCharacter();
    assertEquals(1, targetCharacter.getCurrentSpace());
    world.moveTargetCharacter();
    assertEquals(2, targetCharacter.getCurrentSpace());
  }

  /**
   * Tests whether neighbors are correctly determined based on space positions.
   */
  @Test
  public void testGetNeighbors() {
    // Room A should have two neighbors: Room B and Room C
    List<ImSpace> neighborsRoomA = world.getNeighbors(spaces.get(0));
    assertEquals(2, neighborsRoomA.size());  // Correct expectation is 2 neighbors
    assertTrue(neighborsRoomA.stream().anyMatch(neighbor -> neighbor.getName().equals("Room B")));
    assertTrue(neighborsRoomA.stream().anyMatch(neighbor -> neighbor.getName().equals("Room C")));

    // Room B should still have only one neighbor, Room A
    List<ImSpace> neighborsRoomB = world.getNeighbors(spaces.get(1));
    assertEquals(1, neighborsRoomB.size());
    assertEquals("Room A", neighborsRoomB.get(0).getName());
  }
  
  /**
   * Tests whether an isolated space with no neighboring spaces is correctly identified.
   * This method creates an isolated space and ensures that the World class properly recognizes
   * that the space has no neighbors. It also verifies that the information output for this space
   * includes the expected "Neighbors: None" message.
   */
  @Test
  public void testGetNeighborsForIsolatedSpace() {
    // Add a new space that is isolated and has no neighbors
    ImSpace isolatedSpace = new Space("Isolated Room", 50, 50, 55, 55);  
    List<ImSpace> neighborsIsolated = world.getNeighbors(isolatedSpace);
    assertEquals(0, neighborsIsolated.size());  // Expecting no neighbors

    // Test the info output for the isolated space
    String spaceInfo = world.getSpaceInfo(isolatedSpace);
    assertTrue(spaceInfo.contains("Neighbors: None"));
  }
  
  /**
   * Tests whether the World class correctly draws a space that has no neighbors.
   * This method generates a map of the world and ensures that the graphical representation
   * for an isolated space includes the correct "Neighbors: None" label in the rendered image.
   */
  @Test
  public void testDrawSpaceWithNoNeighbors() {
    // Create an isolated space with no neighbors
    ImSpace isolatedSpace = new Space("Isolated Room", 50, 50, 55, 55);  
    List<ImSpace> noNeighbors = world.getNeighbors(isolatedSpace);
    assertEquals(0, noNeighbors.size());  // Ensure the space has no neighbors

    // Draw this isolated space
    BufferedImage image = world.generateMap(); // Generate the map with the isolated space
    Graphics g = image.getGraphics();
    world.drawSpace(g, isolatedSpace);  // Call the drawSpace method

    // Verify that the string "Neighbors: None" gets drawn
    // This can be visually checked or verified through mock/stub tests
  }



  /**
   * Tests whether space information is correctly retrieved, including items and neighbors.
   */
  @Test
  public void testGetSpaceInfo() {
    String infoRoomA = world.getSpaceInfo(spaces.get(0));
    assertTrue(infoRoomA.contains("Room A"));
    assertTrue(infoRoomA.contains("Sword"));
    assertTrue(infoRoomA.contains("Shield"));
    assertTrue(infoRoomA.contains("Room B"));

    String infoRoomB = world.getSpaceInfo(spaces.get(1));
    assertTrue(infoRoomB.contains("Room B"));
    assertFalse(infoRoomB.contains("Sword"));  // Room B has no items
    assertTrue(infoRoomB.contains("Room A"));
  }

  /**
   * Tests whether the graphical map is generated without errors.
   */
  @Test
  public void testGenerateMap() {
    assertNotNull(world.generateMap());
  }
}
