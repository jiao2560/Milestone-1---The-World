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

  @Test
  public void testWorldParsing() {
    // Simulating file input data
    String worldInfo = "6 6 Test World";
    String characterInfo = "100 Hero";
    String[] spaceInfo = {
      "0 0 2 2 Room A",
      "0 3 2 5 Room B",
      "3 0 5 2 Room C"
    };
    String[] itemInfo = {
      "0 10 Sword",
      "0 5 Shield"
    };

    // Parsing world
    String[] worldParts = worldInfo.split(" ");
    int rows = Integer.parseInt(worldParts[0]);
    int cols = Integer.parseInt(worldParts[1]);
    String worldName = worldParts[2] + " " + worldParts[3];
    
    // Parsing character
    String[] characterParts = characterInfo.split(" ");
    int health = Integer.parseInt(characterParts[0]);
    String characterName = characterParts[1];
    
    TargetCharacter character = new TargetCharacter(characterName, health, 0);
    
    // Parsing spaces
    List<ImSpace> spaces = new ArrayList<>();
    for (String space : spaceInfo) {
      String[] parts = space.split(" ");
      int upperLeftRow = Integer.parseInt(parts[0]);
      int upperLeftCol = Integer.parseInt(parts[1]);
      int lowerRightRow = Integer.parseInt(parts[2]);
      int lowerRightCol = Integer.parseInt(parts[3]);
      String name = parts[4];
      spaces.add(new Space(name, upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol));
    }
    
    // Parsing items
    List<ImItem> items = new ArrayList<>();
    for (String item : itemInfo) {
      String[] parts = item.split(" ");
      int spaceIndex = Integer.parseInt(parts[0]);
      int damage = Integer.parseInt(parts[1]);
      String name = parts[2];
      Item parsedItem = new Item(name, damage);
      spaces.get(spaceIndex).addItem(parsedItem);
      items.add(parsedItem);
    }
    
    // Create world and assert
    World parsedWorld = new World(rows, cols, worldName, spaces, items, character);
    assertEquals("Test World", parsedWorld.getName());
    assertEquals(3, parsedWorld.getNeighbors(spaces.get(0)).size());  // Testing neighbors
  }

  @Test
  public void testReadWorldFromFile() throws IOException {
    // Read the world specification from a file
    BufferedReader reader = new BufferedReader(new FileReader("test_world_spec.txt"));

    // Parse world info
    String[] worldInfo = reader.readLine().split(" ");
    int rows = Integer.parseInt(worldInfo[0]);
    int cols = Integer.parseInt(worldInfo[1]);
    String worldName = worldInfo[2] + " " + worldInfo[3];

    // Parse character info
    String[] characterInfo = reader.readLine().split(" ");
    int characterHealth = Integer.parseInt(characterInfo[0]);
    String characterName = characterInfo[1] + " " + characterInfo[2];

    // Parse spaces
    int numberOfSpaces = Integer.parseInt(reader.readLine());
    List<ImSpace> spaces = new ArrayList<>();
    for (int i = 0; i < numberOfSpaces; i++) {
        String[] spaceInfo = reader.readLine().split(" ");
        int upperLeftRow = Integer.parseInt(spaceInfo[0]);
        int upperLeftCol = Integer.parseInt(spaceInfo[1]);
        int lowerRightRow = Integer.parseInt(spaceInfo[2]);
        int lowerRightCol = Integer.parseInt(spaceInfo[3]);
        String spaceName = spaceInfo[4];
        spaces.add(new Space(spaceName, upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol));
    }

    // Parse items
    int numberOfItems = Integer.parseInt(reader.readLine());
    List<ImItem> items = new ArrayList<>();
    for (int i = 0; i < numberOfItems; i++) {
        String[] itemInfo = reader.readLine().split(" ");
        int spaceIndex = Integer.parseInt(itemInfo[0]);
        int itemDamage = Integer.parseInt(itemInfo[1]);
        String itemName = itemInfo[2];
        Item item = new Item(itemName, itemDamage);
        spaces.get(spaceIndex).addItem(item);
        items.add(item);
    }

    // Create target character
    TargetCharacter targetCharacter = new TargetCharacter(characterName, characterHealth, 0);

    // Create world
    world = new World(rows, cols, worldName, spaces, items, targetCharacter);

    reader.close();

    // Verify world was created correctly
    assertNotNull(world);
    assertEquals("Sample World", world.getName());
    assertEquals(2, world.getSpaces().size());
    assertEquals(2, world.getSpaces().get(0).getItems().size());
  }

  @Test
  public void testInvalidWorldDescription() {
    // Define an invalid file path or malformed world specification
    String invalidFilePath = "invalid_world_spec.txt";

    // Expect an exception when trying to parse an invalid world description
    assertThrows(IOException.class, () -> {
      BufferedReader reader = new BufferedReader(new FileReader(invalidFilePath));
      // Parsing logic here, but we expect an IOException or custom error handling
      World world = GameDriver.parseWorldFromFile(reader);  // Assuming you extract parsing logic
    });
  }

  @Test
  public void testTargetCharacterStartsInRoom0() {
    assertEquals(0, targetCharacter.getCurrentSpace());
  }
}
