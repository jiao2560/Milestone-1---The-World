import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import character.ImTargetCharacter;
import character.TargetCharacter;
import item.ImItem;
import item.Item;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
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
 * Test class for the {@link PlayerImpl} class.
 * 
 * This class contains unit tests for various methods within the {@link PlayerImpl} class, 
 * verifying expected behavior in different scenarios such as picking up items, attacking 
 * the target character, moving within spaces, and interacting with pets.
 * 
 * Fields within this class represent key objects used across tests, such as {@link World}, 
 * {@link ImSpace} for spaces, {@link ImItem} for items, {@link ImTargetCharacter} for the 
 * character, {@link Pet}, and {@link PlayerImpl} for players.
 */
public class PlayerImplTest {

  private World world;
  private ImSpace startingSpace;
  private ImSpace neighborSpace;
  private ImItem item1;
  private ImItem item2;
  private ImTargetCharacter targetCharacter;
  private Pet pet;  // Declare pet here
  private PlayerImpl player;
  private PlayerImpl otherPlayer;
  private List<PlayerImpl> allPlayers;
  
  /**
   * Sets up the initial state for each test.
   * 
   * This method initializes key objects for testing, including the game world, spaces, 
   * items, the target character, players, and a pet. It establishes relationships between 
   * spaces, sets initial player and character locations, and adds players and items to 
   * relevant spaces to prepare for testing various game behaviors.
   */
  @Before
  public void setUp() {
    // Initialize spaces and add them to the world
    startingSpace = new Space(1, "Lobby", 0, 0, 1, 1);
    neighborSpace = new Space(2, "Hallway", 0, 2, 1, 3);
    startingSpace.addNeighbor(neighborSpace);

    // Initialize item and add it to the starting space
    item1 = new Item("Knife", 10);
    startingSpace.addItem(item1);

    // Initialize target character in startingSpace
    targetCharacter = new TargetCharacter("Doctor Lucky", 20, startingSpace.getId());

    // Initialize world with spaces and players
    List<ImSpace> spaces = new ArrayList<>();
    spaces.add(startingSpace);
    spaces.add(neighborSpace);
    world = new World(5, 5, "TestWorld", spaces, new ArrayList<>(), 
        targetCharacter, null, new ArrayList<>());

    // Initialize pet and place it in the world
    pet = new Pet("Lucky's Pet", targetCharacter, world);
    world.setPet(pet);

    // Initialize players and add them to the world’s player list
    player = new PlayerImpl("Player1", startingSpace, 3, false);
    otherPlayer = new PlayerImpl("Player2", neighborSpace, 3, false);
    allPlayers = new ArrayList<>();
    allPlayers.add(player);
    allPlayers.add(otherPlayer);
  }
  
  @Test
  public void testTakeTurnAsAi() {
    player.isAi = true;

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    boolean movedPet = player.takeTurn(world, world.getPlayers());

    assertFalse("AI should not move the pet", movedPet);
    assertTrue("Output should contain 'thinking...'", 
        outputStream.toString().contains("thinking..."));
  }

  @Test
  public void testAttemptKillTargetNotInSameSpace() {
    targetCharacter.moveToSpace(neighborSpace.getId());

    // Capture output to check console messages
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Attempt the kill
    boolean result = player.attemptKill(targetCharacter, world, allPlayers);

    // Verify that the attempt failed because target is not in the same space
    assertFalse("Attempt should fail because target is not in the same space", result);
    assertTrue(outputStream.toString().contains("Doctor Lucky is not in the same space."));
  }

  @Test
  public void testPickUpRandomItemWithItemsAvailable() {
    startingSpace.addItem(item1);

    player.pickUpRandomItem();

    assertTrue("Player should have picked up an item", player.getItems().contains(item1));
    assertFalse("The space should no longer have the "
        + "item", startingSpace.getItems().contains(item1));
  }
  
  @Test
  public void testTakeTurnAsHuman() {
    // Ensure the player is not an AI
    player.isAi = false;

    // Simulate human choice to move the pet (option 6)
    String simulatedInput = "6\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      boolean result = player.takeTurn(world, world.getPlayers());
      assertTrue("Human player should successfully move the pet", result);
      assertTrue(outputStream.toString().contains("Select a space to move the pet to"));
    } finally {
      System.setIn(originalIn); // Restore original System.in
      System.setOut(System.out); // Restore original System.out
    }
  }

  @Test
  public void testTakeHumanTurnPickUpItem() {
    // Ensure the player is not an AI
    player.isAi = false;

    // Simulate human choice to pick up an item (option 2)
    startingSpace.addItem(item1);
    String simulatedInput = "2\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      boolean result = player.takeHumanTurn(world, world.getPlayers());
      assertFalse("Human player should not move the pet", result);
      assertTrue(player.getItems().contains(item1));
      assertTrue(outputStream.toString().contains("picked up"));
    } finally {
      System.setIn(originalIn); // Restore original System.in
      System.setOut(System.out); // Restore original System.out
    }
  }

  @Test
  public void testTakeHumanTurnAttemptKill() {
    // Ensure the player is not an AI
    player.isAi = false;

    // Simulate human choice to attack Doctor Lucky (option 5)
    targetCharacter.moveToSpace(startingSpace.getId());
    String simulatedInput = "5\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      boolean result = player.takeHumanTurn(world, world.getPlayers());
      assertFalse("Human player should not move the pet", result);
      assertTrue(outputStream.toString().contains("attempts to attack Doctor Lucky"));
    } finally {
      System.setIn(originalIn); // Restore original System.in
      System.setOut(System.out); // Restore original System.out
    }
  }

  @Test
  public void testTakeAiTurnAttackWhenInSameSpaceAsTarget() {
    // Set player as AI
    player.isAi = true;

    // Place AI in the same space as Doctor Lucky
    targetCharacter.moveToSpace(startingSpace.getId());
    player.moveTo(startingSpace);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    boolean result = player.takeAiTurn(world, world.getPlayers());

    assertFalse("AI should not move the pet", result);
    assertTrue(outputStream.toString().contains("attempts to attack Doctor Lucky"));
  }


  @Test
  public void testAttemptKillAttackSeenByAnotherPlayer() {
    // Place the target character and another player in the same space as the player
    targetCharacter.moveToSpace(startingSpace.getId());
    otherPlayer.moveTo(startingSpace);

    // Capture output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Attempt to kill the target character
    boolean result = player.attemptKill(targetCharacter, world, world.getPlayers());

    // Assert the attempt failed and the output is correct
    assertFalse("Attempt should fail because another player can see the attack", result);
    assertTrue(outputStream.toString().contains("Attack was "
        + "seen by another player. Attack stopped!"));
  }

  @Test
  public void testAttemptKillWithItemSuccess() {
    // Place the target character in the same space as the player and give player an item
    targetCharacter.moveToSpace(startingSpace.getId());
    player.pickUpItem(item1); // Assume item1 has 10 damage

    // Capture output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Attempt to kill the target character
    boolean result = player.attemptKill(targetCharacter, world, world.getPlayers());

    // Assert the attempt succeeded and the output is correct
    assertTrue("Attempt should succeed with an item", result);
    assertEquals(10, targetCharacter.getHealth()); 
    assertTrue(outputStream.toString().contains("Player1 used "
        + "Knife to attack Doctor Lucky, dealing 10 damage!"));
    assertTrue(outputStream.toString().contains("Doctor Lucky's health is now 10"));
  }

  @Test
  public void testAttemptKillWithoutItemSuccess() {
    // Place the target character in the same space as the player with no items
    targetCharacter.moveToSpace(startingSpace.getId());

    // Capture output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Attempt to kill the target character
    boolean result = player.attemptKill(targetCharacter, world, world.getPlayers());

    // Assert the attempt succeeded with default damage
    assertTrue("Attempt should succeed with default damage", result);
    assertEquals(19, targetCharacter.getHealth()); 
    assertTrue(outputStream.toString().contains("Player1 attempts to "
        + "poke Doctor Lucky, dealing 1 damage."));
    assertTrue(outputStream.toString().contains("Doctor Lucky's health is now 19"));
  }

  
  @Test
  public void testAutoMoveToNeighborWithNeighbors() {
    // Ensure the player has a neighboring space to move to
    player.moveTo(startingSpace); // Set player's current space with a neighbor
    startingSpace.addNeighbor(neighborSpace);

    // Capture the output to verify behavior
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Invoke the autoMoveToNeighbor method
    player.autoMoveToNeighbor();

    // Check that the player moved to the neighboring space
    assertEquals("Player should have moved to the neighboring "
        + "space", neighborSpace, player.getCurrentSpace());

    // Restore original System.out
    System.setOut(System.out);
  }

  @Test
  public void testAutoMoveToNeighborWithNoNeighbors() {
    // Move the player to an isolated space with no neighbors
    ImSpace isolatedSpace = new Space(3, "Isolated Room", 3, 3, 4, 4);
    player.moveTo(isolatedSpace);

    // Capture the output to verify behavior
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Invoke the autoMoveToNeighbor method
    player.autoMoveToNeighbor();

    // Verify the player did not move
    assertEquals("Player should remain in the isolated "
        + "space", isolatedSpace, player.getCurrentSpace());

    // Verify the output contains the message indicating no neighbors
    String output = outputStream.toString();
    assertTrue("Output should indicate no neighboring spaces "
        + "to move to.", output.contains("has no neighboring spaces to move to."));

    // Restore original System.out
    System.setOut(System.out);
  }

  
  @Test
  public void testMovePetByAiPlayerWithNeighbors() {
    // Set player as AI and set neighboring spaces for the pet
    player.isAi = true;
    pet.getCurrentSpace().addNeighbor(neighborSpace);

    // Check initial position of the pet
    assertEquals("Pet should initially be in the "
        + "starting space", startingSpace, pet.getCurrentSpace());

    // Run the method and check that the pet has moved
    boolean result = player.movePet(world);
    assertTrue("AI player should move the pet to a neighboring space", result);
    assertEquals("Pet should have moved to the "
        + "neighboring space", neighborSpace, pet.getCurrentSpace());
  }

  @Test
  public void testMovePetByAiPlayerWithNoNeighbors() {
    // Set player as AI and ensure pet has no neighbors
    player.isAi = true;
    startingSpace.getNeighbors().clear();

    // Run the method and check that the pet has not moved
    boolean result = player.movePet(world);
    assertFalse("AI player should not move the pet if "
        + "there are no neighboring spaces", result);
    assertEquals("Pet should remain in the starting "
        + "space", startingSpace, pet.getCurrentSpace());
  }

  @Test
  public void testMovePetByHumanPlayerWithValidChoice() {
    // Add a neighboring space for the pet to move into
    pet.getCurrentSpace().addNeighbor(neighborSpace);

    // Simulate valid human input "0" to move the pet to the first neighboring space
    String simulatedInput = "0\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    try {
      boolean result = player.movePet(world);
      assertTrue("Human player should successfully move the pet to "
          + "a neighboring space", result);
      assertEquals("Pet should have moved to the neighboring "
          + "space", neighborSpace, pet.getCurrentSpace());
    } finally {
      System.setIn(originalIn); // Restore original System.in
    }
  }

  @Test
  public void testMovePetByHumanPlayerWithInvalidChoice() {
    // Add a neighboring space for the pet to move into
    pet.getCurrentSpace().addNeighbor(neighborSpace);

    // Simulate invalid human input "5" (out of bounds)
    String simulatedInput = "5\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      boolean result = player.movePet(world);
      assertFalse("Human player should not move the pet "
          + "with an invalid choice", result);
      assertEquals("Pet should remain in the starting "
          + "space", startingSpace, pet.getCurrentSpace());

      // Verify output contains the invalid choice message
      String output = outputStream.toString();
      assertTrue("Output should indicate invalid "
          + "choice", output.contains("Invalid choice. Pet remains in current space."));
    } finally {
      System.setIn(originalIn); // Restore original System.in
      System.setOut(System.out); // Restore original System.out
    }
  }

  
  @Test
  public void testPickUpAvailableItemWithValidChoice() {
    // Move player to starting space, which has items
    startingSpace.addItem(item1);  // Ensure at least one item in the space

    // Simulate valid input "0" to pick up the first item
    String simulatedInput = "0\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    try {
      player.pickUpAvailableItem();
      // Check that item1 was picked up
      assertTrue("Player should have picked up item1", player.getItems().contains(item1));
    } finally {
      System.setIn(originalIn); // Restore original System.in
    }
  }

  @Test
  public void testPickUpAvailableItemWithNegativeChoice() {
    // Simulate negative input "-1" (invalid choice)
    String simulatedInput = "-1\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      player.pickUpAvailableItem();
      // Check that no item was picked up
      assertTrue("Player should not have picked up any items", player.getItems().isEmpty());

      // Check output for invalid choice message
      String output = outputStream.toString();
      assertTrue("Output should indicate invalid choice", output.contains("Invalid "
          + "choice. No item picked up."));
    } finally {
      System.setIn(originalIn); // Restore original System.in
      System.setOut(System.out); // Restore original System.out
    }
  }

  @Test
  public void testPickUpAvailableItemWithOutOfBoundsChoice() {
    // Ensure at least one item is in the space
    startingSpace.addItem(item1);

    // Simulate input "5" which is out of bounds
    String simulatedInput = "5\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      player.pickUpAvailableItem();
      // Check that no item was picked up
      assertTrue("Player should not have picked up "
          + "any items", player.getItems().isEmpty());

      // Check output for invalid choice message
      String output = outputStream.toString();
      assertTrue("Output should indicate invalid "
          + "choice", output.contains("Invalid choice. No item picked up."));
    } finally {
      System.setIn(originalIn); // Restore original System.in
      System.setOut(System.out); // Restore original System.out
    }
  }

  @Test
  public void testPickUpAvailableItemWithNoItemsInSpace() {
    // Ensure the space has no items
    startingSpace.getItems().clear();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    player.pickUpAvailableItem();

    // Check that no item was picked up and correct message is displayed
    assertTrue("Player should not have picked up any "
        + "items", player.getItems().isEmpty());
    String output = outputStream.toString();
    assertTrue("Output should indicate no items to pick "
        + "up", output.contains("No items to pick up."));

    // Restore original System.out
    System.setOut(System.out);
  }

  @Test
  public void testPickUpRandomItemWithNoItemsAvailable() {
    // Ensure the current space is empty of items
    startingSpace.getItems().clear();

    // Invoke the method to pick up a random item
    player.pickUpRandomItem();

    // Verify that no item was picked up since the space was empty
    assertTrue("Player should not have picked up any "
        + "items", player.getItems().isEmpty());
  }
  
  @Test
  public void testSelectAndMoveToNeighborWithNoNeighbors() {
    // Create a new space without neighbors for testing
    ImSpace isolatedSpace = new Space(3, "Isolated Room", 3, 3, 4, 4);
    player.moveTo(isolatedSpace); // Move player to isolated space

    // Capture the output to verify behavior
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Run the method
    player.selectAndMoveToNeighbor();

    // Restore original System.out
    System.setOut(System.out);

    // Check if the correct message was printed
    String output = outputStream.toString();
    assertTrue("Output should indicate no neighbors to move "
        + "to.", output.contains("No neighbors to move to."));
  }

  @Test
  public void testSelectAndMoveToNeighborWithValidChoice() {
    // Move player to starting space, which has neighbors
    player.moveTo(startingSpace);

    // Simulate valid input "0" to move to the first neighbor
    String simulatedInput = "0\n";
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    try {
      // Call the method
      player.selectAndMoveToNeighbor();

      // Verify that the player moved to the expected neighboring space
      assertEquals("Player should have moved to the "
          + "neighboring space", neighborSpace, player.getCurrentSpace());
    } finally {
      System.setIn(originalIn); // Restore original System.in
    }
  }

  @Test
  public void testSelectAndMoveToNeighbor() {
    // Set the player's initial position
    player.moveTo(startingSpace);

    // Redirect System.in to simulate user input "0" (choosing the first neighbor)
    String simulatedInput = "0\n"; // Simulate choosing the first neighbor (e.g., index 0)
    InputStream originalIn = System.in;
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    try {
      player.selectAndMoveToNeighbor(); // Call the method that requires user input

      // Assert that the player moved to the correct neighbor
      assertEquals("Player should have moved to the neighboring "
          + "space", neighborSpace, player.getCurrentSpace());
    } finally {
      System.setIn(originalIn); // Restore original System.in
    }
  }
  
  @Test
  public void testCanSeeSameSpace() {
    otherPlayer.moveTo(startingSpace); // Move otherPlayer to the same space as player
    assertTrue("Player should be able to see other player in the "
        + "same space", player.canSee(otherPlayer));
  }

  @Test
  public void testCanSeeNeighboringSpace() {
    player.moveTo(startingSpace); // Ensure player is in startingSpace
    otherPlayer.moveTo(neighborSpace); // Move otherPlayer to a neighboring space
    assertTrue("Player should be able to see other player in a "
        + "neighboring space", player.canSee(otherPlayer));
  }

  @Test
  public void testCannotSeeDistantSpace() {
    ImSpace distantSpace = new Space(3, "Garden", 2, 2, 3, 3);
    player.moveTo(startingSpace);
    otherPlayer.moveTo(distantSpace); // Move otherPlayer to a distant space (not neighboring)
    assertFalse("Player should not be able to see other player in "
        + "a distant space", player.canSee(otherPlayer));
  }
  
  @Test
  public void testDisplayPlayerDescriptionNoItems() {
    // Capture the output of displayPlayerDescription
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    player.displayPlayerDescription();

    // Restore original System.out
    System.setOut(System.out);

    String output = outputStream.toString();
    assertTrue(output.contains("Player: Player1"));
    assertTrue(output.contains("Current space: Lobby"));
    assertTrue(output.contains("Carrying: No items"));
  }

  @Test
  public void testDisplayPlayerDescriptionWithItems() {
    // Add an item to the player's inventory
    player.pickUpItem(item1);

    // Capture the output of displayPlayerDescription
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    player.displayPlayerDescription();

    // Restore original System.out
    System.setOut(System.out);

    String output = outputStream.toString();
    assertTrue(output.contains("Player: Player1"));
    assertTrue(output.contains("Current space: Lobby"));
    assertTrue(output.contains("Carrying:"));
    assertTrue(output.contains(" - Knife")); // Ensure the item is listed
  }


  @Test
  public void testPickUpItemWhenMaxItemsReached() {
    // Fill the player's inventory to the max capacity
    ImItem item2 = new Item("Rope", 5);
    ImItem item3 = new Item("Flashlight", 3);
    player.pickUpItem(item1); // First item
    player.pickUpItem(item2); // Second item
    player.pickUpItem(item3); // Third item - reaches max capacity (3 items)

    // Attempt to pick up another item when max capacity is reached
    ImItem extraItem = new Item("Hammer", 7);
    startingSpace.addItem(extraItem); // Add extra item to the space

    player.pickUpItem(extraItem); // Attempt to pick up an extra item

    // Verify that the extra item was not picked up due to max capacity
    assertFalse("Player should not be able to pick up an item "
        + "when max capacity is reached",
                player.getItems().contains(extraItem));
    assertTrue("Extra item should still be in the space",
               startingSpace.getItems().contains(extraItem));
  }


  @Test
  public void testGetName() {
    assertEquals("Player1", player.getName());
  }

  @Test
  public void testMoveToNeighborSpace() {
    player.moveTo(neighborSpace);
    assertEquals("Hallway", player.getCurrentSpace().getName());
  }

  @Test
  public void testPickUpItem() {
    player.pickUpItem(item1);
    assertTrue(player.getItems().contains(item1));
    assertFalse(startingSpace.getItems().contains(item1)); 
  }

  @Test
  public void testCanCarryMoreItems() {
    assertTrue(player.canCarryMoreItems());
    player.pickUpItem(item1);
    assertTrue(player.canCarryMoreItems());
  }

  @Test
  public void testLookAroundNoOtherPlayers() {
    assertFalse(player.lookAround(world)); // No other players in the same space
  }

  @Test
  public void testLookAroundWithOtherPlayer() {
    otherPlayer.moveTo(startingSpace);
    assertTrue(player.lookAround(world)); // Other player in the same space
  }

  @Test
  public void testAttemptKillSuccessful() {
    // Ensure the target character is in the same space as the player
    targetCharacter.moveToSpace(startingSpace.getId());
    
    // Attempt the attack and verify success
    assertFalse("Attack should be "
        + "successful", player.attemptKill(targetCharacter, world, world.getPlayers()));
    assertFalse("Target character should have "
        + "taken damage", targetCharacter.getHealth() < 20);
  }


  @Test
  public void testAttemptKillInterruptedByOtherPlayer() {
    targetCharacter.moveToSpace(startingSpace.getId()); 
    otherPlayer.moveTo(startingSpace); // Other player in the same space
    assertFalse(player.attemptKill(targetCharacter, world, world.getPlayers()));
    assertEquals(20, targetCharacter.getHealth()); 
  }

  @Test
  public void testIsAttackVisibleNoOtherPlayer() {
    assertFalse(player.isAttackVisible(world)); 
  }

  @Test
  public void testIsAttackVisibleWithOtherPlayer() {
    otherPlayer.moveTo(startingSpace); 
    assertFalse(player.isAttackVisible(world));
  }
  
  @Test
  public void testGetMaxItems() {
    int maxItems = player.getMaxItems();
    assertEquals("Max items should be 3", 3, maxItems); 
  }
}

