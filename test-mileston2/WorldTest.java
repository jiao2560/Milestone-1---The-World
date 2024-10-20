import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import character.ImTargetCharacter;
import character.TargetCharacter;
import item.ImItem;
import item.Item;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Test class for the World class.
 */
public class WorldTest {

  private World world;
  private ImTargetCharacter targetCharacter;
  private List<ImSpace> spaces;
  private List<ImItem> items;
  
  /**
   * Sets up the test environment before each test case is executed.
   * This method creates spaces, items, and a target character, and initializes 
   * the world with these elements to ensure consistent testing conditions.
   */
  @Before
  public void setUp() {
    spaces = new ArrayList<>();
    // Create spaces
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5);
    ImSpace space2 = new Space("Room B", 6, 0, 10, 5);
    ImSpace space3 = new Space("Room C", 0, 6, 5, 10);
  
    spaces.add(space1);
    spaces.add(space2);
    spaces.add(space3);

    // Create items
    ImItem item1 = new Item("Item 1", 10);
    ImItem item2 = new Item("Item 2", 15);
    items = new ArrayList<>();
    items.add(item1);
    items.add(item2);

    // Place items in spaces
    space1.addItem(item1);
    space2.addItem(item2);

    // Create target character
    targetCharacter = new TargetCharacter("Target", 100, 0);

    // Initialize world
    world = new World(15, 15, "Test World", spaces, items, targetCharacter);
  }
  
  
  @Test
  public void testDrawSpaceWithNeighbors() {
    // Setup: Ensure space A has neighbors
    ImSpace spaceA = world.getSpace(0);
    List<ImSpace> neighborsA = spaceA.getNeighbors();
    assertFalse(neighborsA.isEmpty()); // Verify the non-empty branch is covered

    // Test: Ensure neighbors are drawn correctly (just checking that neighbors exist in logic)
    String spaceInfo = world.getSpaceInfo(spaceA);
    assertTrue(spaceInfo.contains("Neighbors: Room B, Room C"));
  }
  
  @Test
  public void testIsNeighborVerticallyAlignedAndAdjacent() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5); // Upper space
    ImSpace space2 = new Space("Room B", 6, 0, 10, 5); // Directly below Room A

    assertTrue(world.isNeighbor(space1, space2)); // Vertically aligned and adjacent
  }

  @Test
  public void testIsNotNeighborNotAligned() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5); 
    ImSpace space2 = new Space("Room D", 10, 10, 15, 15); // Far away from Room A

    assertFalse(world.isNeighbor(space1, space2)); // Not aligned or adjacent
  }

  @Test
  public void testIsNotNeighborAlignedButNotAdjacent() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5); 
    ImSpace space2 = new Space("Room E", 8, 0, 12, 5); // Same column but not adjacent

    assertFalse(world.isNeighbor(space1, space2)); // Vertically aligned but not adjacent
  }

  @Test
  public void testIsNeighborHorizontallyAlignedAndAdjacent() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5);  // Left space
    ImSpace space2 = new Space("Room B", 0, 6, 5, 10); // Directly to the right of Room A

    assertTrue(world.isNeighbor(space1, space2)); // Horizontally aligned and adjacent
  }

  @Test
  public void testIsNotNeighborHorizontallyAlignedButNotAdjacent() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5);  
    ImSpace space2 = new Space("Room C", 0, 8, 5, 12); // Same row but not adjacent

    assertFalse(world.isNeighbor(space1, space2)); // Horizontally aligned but not adjacent
  }

  @Test
  public void testIsNeighborHorizontallyAlignedNotVerticallyAligned() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5);  
    ImSpace space2 = new Space("Room D", 1, 6, 5, 10); // Slightly offset in row alignment

    assertTrue(world.isNeighbor(space1, space2)); // Not horizontally aligned perfectly
  }

  @Test
  public void testIsNeighborHorizontallyNotAlignedAndNotAdjacent() {
    ImSpace space1 = new Space("Room A", 0, 0, 5, 5);  
    ImSpace space2 = new Space("Room E", 6, 10, 10, 15); // Not aligned or adjacent

    assertFalse(world.isNeighbor(space1, space2)); // Not aligned or adjacent
  }

  @Test
  public void testDrawSpaceWithoutNeighbors() {
    // Setup: Create an isolated space with no neighbors
    ImSpace isolatedSpace = new Space("Isolated Room", 12, 12, 14, 14);
    List<ImSpace> emptyNeighbors = isolatedSpace.getNeighbors();
    assertTrue(emptyNeighbors.isEmpty()); // Verify the empty branch is covered

    // Test: Ensure "Neighbors: None" is correctly handled
    String spaceInfo = world.getSpaceInfo(isolatedSpace);
    assertTrue(spaceInfo.contains("Neighbors: None"));
  }


  @Test
  public void testGetSpaces() {
    List<ImSpace> retrievedSpaces = world.getSpaces();
    assertEquals(3, retrievedSpaces.size());
    assertTrue(retrievedSpaces.containsAll(spaces));
  }

  @Test
  public void testGetSpaceByIndex() {
    assertEquals("Room A", world.getSpace(0).getName());
    assertEquals("Room B", world.getSpace(1).getName());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetSpaceWithInvalidIndex() {
    world.getSpace(5); // Invalid index
  }
  
  @Test
  public void testGetSpaceByValidIndexEdgeCases() {
    // Test the first valid index (0)
    assertEquals("Room A", world.getSpace(0).getName());

    // Test the last valid index (size - 1)
    assertEquals("Room C", world.getSpace(spaces.size() - 1).getName());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetSpaceWithInvalidNegativeIndex() {
    // Test with a negative index to ensure it throws an exception
    world.getSpace(-1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testGetSpaceWithOutOfRangeIndex() {
    // Test with an index greater than the number of spaces
    world.getSpace(spaces.size()); // This is an invalid index
  }


  @Test
  public void testGetNeighbors() {
    List<ImSpace> neighborsOfSpace1 = world.getNeighbors(spaces.get(0));
    assertTrue(neighborsOfSpace1.contains(spaces.get(1)));
    assertTrue(neighborsOfSpace1.contains(spaces.get(2)));
  }

  @Test
  public void testMoveTargetCharacter() {
    assertEquals(0, targetCharacter.getCurrentSpace());
    world.moveTargetCharacter();
    assertEquals(1, targetCharacter.getCurrentSpace());
  }

  @Test
  public void testGetSpaceInfo() {
    String spaceInfo = world.getSpaceInfo(spaces.get(0));
    assertTrue(spaceInfo.contains("Room A"));
    assertTrue(spaceInfo.contains("Item 1 (Damage: 10)"));
    assertTrue(spaceInfo.contains("Neighbors: Room B, Room C"));
  }

  @Test
  public void testGenerateMap() {
    BufferedImage map = world.generateMap();
    assertNotNull(map);
    assertEquals(750, map.getWidth());  // 15 * 50
    assertEquals(750, map.getHeight()); // 15 * 50
  }

  @Test
  public void testAssignNeighbors() {
    ImSpace spaceA = world.getSpace(0);
    ImSpace spaceB = world.getSpace(1);
    ImSpace spaceC = world.getSpace(2);

    List<ImSpace> neighborsA = spaceA.getNeighbors();
    assertTrue(neighborsA.contains(spaceB));
    assertTrue(neighborsA.contains(spaceC));
  }
}
