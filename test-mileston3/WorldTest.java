import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
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
import pet.Pet;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Unit test class for the {@link World} class.
 * This test suite initializes various components of the game world,
 * such as spaces, items, players, and a target character. The setup
 * method creates a world instance with these elements and prepares it
 * for further unit tests.
 */
public class WorldTest {

  private World world;
  private ImSpace space1;
  private ImSpace space2;
  private ImSpace space3;
  private ImItem item1;
  private ImItem item2;
  private ImTargetCharacter targetCharacter;
  private Pet pet;
  private PlayerImpl player1;
  private PlayerImpl player2;

  @Before
  public void setUp() {
    // Initialize spaces and set them as neighbors
    space1 = new Space(1, "Lobby", 0, 0, 1, 1);
    space2 = new Space(2, "Hallway", 0, 2, 1, 3);
    space3 = new Space(3, "Kitchen", 2, 0, 3, 1);
    space1.addNeighbor(space2);
    space2.addNeighbor(space1);

    // Initialize items
    item1 = new Item("Knife", 10);
    item2 = new Item("Rope", 5);
    space1.addItem(item1);
    space2.addItem(item2);

    // Initialize target character
    targetCharacter = new TargetCharacter("Doctor Lucky", 20, 0);

    // Initialize the world without the pet initially
    List<ImSpace> spaces = new ArrayList<>();
    spaces.add(space1);
    spaces.add(space2);
    spaces.add(space3);

    List<ImItem> items = new ArrayList<>();
    items.add(item1);
    items.add(item2);

    List<PlayerImpl> players = new ArrayList<>();
    player1 = new PlayerImpl("Player1", space1, 3, false);
    player2 = new PlayerImpl("Player2", space2, 3, false);
    players.add(player1);
    players.add(player2);

    world = new World(5, 5, "TestWorld", spaces, items, targetCharacter, null, players);

    // Initialize the pet now that the world exists
    pet = new Pet("Lucky's Pet", targetCharacter, world);
    world.setPet(pet);  // Set the pet in the world
  }
  
  @Test
  public void testIsNeighborVerticallyAlignedAndAdjacent() {
    // Set up spaces that are vertically aligned and adjacent
    ImSpace spaceA = new Space(1, "SpaceA", 0, 0, 1, 1);
    ImSpace spaceB = new Space(2, "SpaceB", 2, 0, 3, 1);

    assertTrue("SpaceA and SpaceB should be vertically "
        + "aligned and adjacent", world.isNeighbor(spaceA, spaceB));
  }

  @Test
  public void testIsNeighborHorizontallyAlignedAndAdjacent() {
    // Set up spaces that are horizontally aligned and adjacent
    ImSpace spaceA = new Space(1, "SpaceA", 0, 0, 1, 1);
    ImSpace spaceB = new Space(2, "SpaceB", 0, 2, 1, 3); 

    assertTrue("SpaceA and SpaceB should be horizontally "
        + "aligned and adjacent", world.isNeighbor(spaceA, spaceB));
  }

  @Test
  public void testIsNotNeighborVerticallyAlignedButNotAdjacent() {
    // Set up spaces that are vertically aligned but not adjacent
    ImSpace spaceA = new Space(1, "SpaceA", 0, 0, 1, 1);
    ImSpace spaceB = new Space(2, "SpaceB", 3, 0, 4, 1); 

    assertFalse("SpaceA and SpaceB should "
        + "not be neighbors", world.isNeighbor(spaceA, spaceB));
  }

  @Test
  public void testIsNotNeighborHorizontallyAlignedButNotAdjacent() {
    // Set up spaces that are horizontally aligned but not adjacent
    ImSpace spaceA = new Space(1, "SpaceA", 0, 0, 1, 1);
    ImSpace spaceB = new Space(2, "SpaceB", 0, 3, 1, 4); 

    assertFalse("SpaceA and SpaceB should not "
        + "be neighbors", world.isNeighbor(spaceA, spaceB));
  }

  @Test
  public void testMovePlayerLastPlayerLeavingSpace() {
    // Add player1 and player2 to space1 and verify their presence
    world.updatePlayerLocations();
    world.movePlayer(player2, space1);
    assertTrue("Both players should be in space1 "
        + "initially", world.getPlayersInSameSpace(space1).containsAll(List.of(player1, player2)));

    // Move player1 to space2, leaving player2 as the last one in space1
    world.movePlayer(player1, space2);
    assertTrue("Player1 should be moved to "
        + "space2", world.getPlayersInSameSpace(space2).contains(player1));
    assertTrue("Player2 should still be in "
        + "space1", world.getPlayersInSameSpace(space1).contains(player2));

    // Move player2 out of space1, making it empty
    world.movePlayer(player2, space2);
    assertTrue("Player2 should now be "
        + "in space2", world.getPlayersInSameSpace(space2).contains(player2));
    assertFalse("Space1 should be empty "
        + "after moving both players out", world.getPlayersInSameSpace(space1).contains(player2));
    assertFalse("Space1 should be removed "
        + "from spaceToPlayersMap", world.getPlayersInSameSpace(space1).contains(player1));
  }

  @Test
  public void testMovePlayerWhenOldSpaceNotInMap() {
    // Clear the current player mappings to simulate an empty initial state
    world.updatePlayerLocations();

    // Move player1 from space1 to space2
    world.movePlayer(player1, space2);

    // Verify player1 is now in space2 and space1 is not in the map (no players left there)
    List<PlayerImpl> playersInSpace2 = world.getPlayersInSameSpace(space2);
    assertTrue("Player1 should be in space2 after moving", playersInSpace2.contains(player1));
    assertFalse("Space1 should not contain Player1 after "
        + "moving", world.getPlayersInSameSpace(space1).contains(player1));
  }

  
  @Test
  public void testGetSpaceInfoWithNoItems() {
    // Remove items from space1 and verify the output
    space1.getItems().clear();
    String info = world.getSpaceInfo(space1);
    assertTrue("Info should mention no items when the space "
        + "has no items", info.contains("Items: None"));
  }

  @Test
  public void testGetSpaceInfoWithPetBlockingNeighborVisibility() {
    // Set the pet in space2, which is a neighbor of space1, making it invisible
    pet.moveTo(space2);
    String info = world.getSpaceInfo(space1);
    assertFalse("Info should not list space2 as a visible "
        + "neighbor due to pet blocking visibility",
                info.contains("Hallway"));
  }

  @Test
  public void testGetSpaceInfoWithNoNeighbors() {
    // Clear neighbors for space3 and verify the output
    space3.getNeighbors().clear();
    String info = world.getSpaceInfo(space3);
    assertTrue("Info should mention no visible neighbors "
        + "when there are none", info.contains("Visible Neighbors: None"));
  }

  @Test
  public void testGetSpaceInfoWithTargetCharacterPresent() {
    // Move the target character to space1 and verify the output
    targetCharacter.moveToSpace(0); // Assuming space1 is at index 0
    String info = world.getSpaceInfo(space1);
    assertTrue("Info should contain target character details when present in the space",
                info.contains("Target character: Doctor Lucky"));
  }

  @Test
  public void testGetSpaceInfoWithPetPresent() {
    // Set the pet to space1 and verify the output
    pet.moveTo(space1);
    String info = world.getSpaceInfo(space1);
    assertTrue("Info should contain pet details when the pet is "
        + "present in the space", info.contains("Pet: Lucky's Pet"));
  }
  
  /**
   * Test the getSpace method with an invalid index less than 0.
   */
  @Test
  public void testGetSpaceIndexLessThanZero() {
    assertThrows(IndexOutOfBoundsException.class, () -> world.getSpace(-1));
  }

  /**
   * Test the getSpace method with an index equal to or greater than the number of spaces,
   * expecting an IndexOutOfBoundsException.
   */
  @Test
  public void testGetSpaceIndexOutOfBounds() {
    assertThrows(IndexOutOfBoundsException.class, () -> world.getSpace(3));
  }
  
  /**
   * Test the petMakesSpaceInvisible method to verify it correctly identifies if
   * the pet makes the specified space invisible.
   */
  @Test
  public void testPetMakesSpaceInvisible() {
    // Set the pet's position to space1 and check visibility
    pet.moveTo(space1);
    assertTrue("Space1 should be invisible due to pet "
        + "presence", world.petMakesSpaceInvisible(space1));
    assertFalse("Space2 should not be invisible as the "
        + "pet is not in space2", world.petMakesSpaceInvisible(space2));
  }

  /**
   * Test the getSpaces method to verify it returns the correct list of spaces in the world.
   */
  @Test
  public void testGetSpaces() {
    List<ImSpace> spaces = world.getSpaces();
    assertNotNull("Spaces list should not be null", spaces);
    assertEquals("Spaces list should contain 3 spaces", 3, spaces.size());
    assertTrue("Spaces list should contain space1", spaces.contains(space1));
    assertTrue("Spaces list should contain space2", spaces.contains(space2));
    assertTrue("Spaces list should contain space3", spaces.contains(space3));
  }

  /**
   * Test the getName method to verify it returns the correct name of the world.
   */
  @Test
  public void testGetName() {
    assertEquals("World name should be 'TestWorld'", "TestWorld", world.getName());
  }

  /**
   * Test the getRows method to verify it returns the correct number of rows in the world.
   */
  @Test
  public void testGetRows() {
    assertEquals("Number of rows should be 5", 5, world.getRows());
  }

  /**
   * Test the getCols method to verify it returns the correct number of columns in the world.
   */
  @Test
  public void testGetCols() {
    assertEquals("Number of columns should be 5", 5, world.getCols());
  }

  /**
   * Test the getPlayers method to verify it returns the correct list of players.
   */
  @Test
  public void testGetPlayers() {
    List<PlayerImpl> players = world.getPlayers();
    assertNotNull("Players list should not be null", players);
    assertEquals("Players list should contain 2 players", 2, players.size());
    assertTrue("Players list should contain player1", players.contains(player1));
    assertTrue("Players list should contain player2", players.contains(player2));
  }

  /**
   * Test the getTargetCharacter method to verify it returns the correct target character.
   */
  @Test
  public void testGetTargetCharacter() {
    ImTargetCharacter target = world.getTargetCharacter();
    assertNotNull("Target character should not be null", target);
    assertEquals("Target character should be 'Doctor Lucky'", "Doctor Lucky", target.getName());
    assertEquals("Target character should have 20 health", 20, target.getHealth());
  }

  /**
   * Test the getPet method to verify it returns the correct pet instance.
   */
  @Test
  public void testGetPet() {
    Pet retrievedPet = world.getPet();
    assertNotNull("Pet should not be null", retrievedPet);
    assertEquals("Pet's name should be 'Lucky's Pet'", "Lucky's Pet", retrievedPet.getName());
    assertEquals("Pet's current space should be the same as target character's initial space",
            world.getSpace(targetCharacter.getCurrentSpace()), retrievedPet.getCurrentSpace());
  }

  // The rest of your tests remain unchanged
  @Test
  public void testGetSpaceByIndex() {
    assertEquals("Lobby", world.getSpace(0).getName());
    assertEquals("Hallway", world.getSpace(1).getName());
    assertEquals("Kitchen", world.getSpace(2).getName());
  }

  @Test
  public void testGetNeighbors() {
    List<ImSpace> neighbors = world.getNeighbors(space1);
    assertTrue(neighbors.contains(space2));
    assertTrue(neighbors.contains(space3));
  }

  @Test
  public void testMoveTargetCharacter() {
    world.moveTargetCharacter();
    assertEquals(space2, world.getSpace(targetCharacter.getCurrentSpace()));
  }

  @Test
  public void testMovePetToSpace() {
    world.movePetToSpace(space3);
    assertEquals(space3, pet.getCurrentSpace());
  }

  @Test
  public void testPetBlocksVisibility() {
    assertTrue(world.isPetBlockingVisibility(space1));
    assertFalse(world.isPetBlockingVisibility(space2));
  }

  @Test
  public void testGetSpaceInfo() {
    String info = world.getSpaceInfo(space1);
    assertTrue(info.contains("Lobby"));
    assertTrue(info.contains("Knife"));
    assertTrue(info.contains("Lucky's Pet"));
  }

  @Test
  public void testGenerateMap() {
    BufferedImage map = world.generateMap();
    assertEquals(250, map.getWidth());
    assertEquals(250, map.getHeight());
  }

  @Test
  public void testUpdatePlayerLocations() {
    world.updatePlayerLocations();
    List<PlayerImpl> playersInLobby = world.getPlayersInSameSpace(space1);
    assertTrue(playersInLobby.contains(player1));
    assertFalse(playersInLobby.contains(player2));
  }

  @Test
  public void testMovePlayer() {
    world.movePlayer(player1, space2);
    List<PlayerImpl> playersInHallway = world.getPlayersInSameSpace(space2);
    assertTrue(playersInHallway.contains(player1));
  }
}
