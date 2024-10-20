import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.GameController;
import item.ImItem;
import item.Item;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Test class for GameController.
 */
public class GameControllerTest {

  private GameController gameController;
  private World world;
  private PlayerImpl player1;
  private PlayerImpl player2;
  private ImSpace space1;
  private ImSpace space2;
  
  /**
   * Sets up the test environment before each test case is executed.
   * This method initializes spaces, items, a world, players, and the 
   * game controller 
   * to ensure consistent conditions for testing game mechanics.
   * 
   * Two spaces (Room A and Room B) are created and set as 
   * neighbors to test player movement.
   * A world is initialized with the spaces and items for testing 
   * world-related interactions.
   * A GameController is initialized with a maximum of 3 turns.
   * Two players (one human and one AI) are added to the game to 
   * test player interactions and turns.
   */
  @Before
  public void setUp() {
    // Set up spaces
    space1 = new Space("Room A", 0, 0, 5, 5);
    space2 = new Space("Room B", 6, 0, 10, 5);
    space1.addNeighbor(space2);
    space2.addNeighbor(space1);

    // Create world with spaces and items
    List<ImSpace> spaces = new ArrayList<>();
    spaces.add(space1);
    spaces.add(space2);

    List<ImItem> items = new ArrayList<>();
    items.add(new Item("Sword", 15));
    items.add(new Item("Shield", 10));

    world = new World(10, 10, "Test World", spaces, items, null);

    // Initialize the GameController with max turns = 3
    gameController = new GameController(world, 3);

    // Initialize players
    player1 = new PlayerImpl("Player1", space1, 2, false);  // Human player
    player2 = new PlayerImpl("Player2", space2, 2, true);   // AI player

    // Add players to the game
    gameController.addPlayer(player1);
    gameController.addPlayer(player2);
  }

  @Test
  public void testAddPlayer() {
    List<PlayerImpl> players = gameController.getPlayers();
    assertEquals(2, players.size());
    assertTrue(players.contains(player1));
    assertTrue(players.contains(player2));
  }

  @Test
  public void testGetCurrentTurn() {
    assertEquals(0, gameController.getCurrentTurn());

    gameController.playTurn();
    assertEquals(1, gameController.getCurrentTurn());

    gameController.playTurn();
    assertEquals(2, gameController.getCurrentTurn());
  }

  @Test
  public void testIsGameOver() {
    assertFalse(gameController.isGameOver());

    gameController.playTurn(); // Turn 1
    gameController.playTurn(); // Turn 2
    gameController.playTurn(); // Turn 3

    assertTrue(gameController.isGameOver());
  }

  @Test
  public void testPlayTurn() {
    gameController.playTurn();  // Turn 1: Player1's turn
    assertEquals(1, gameController.getCurrentTurn());

    gameController.playTurn();  // Turn 2: Player2's turn
    assertEquals(2, gameController.getCurrentTurn());

    gameController.playTurn();  // Turn 3: Player1's turn again
    assertEquals(3, gameController.getCurrentTurn());
    assertTrue(gameController.isGameOver());
  }

  @Test
  public void testPlayTurnWithMaxTurns() {
    // Play the max number of turns
    gameController.playTurn(); // Turn 1
    gameController.playTurn(); // Turn 2
    gameController.playTurn(); // Turn 3

    // Verify the game is over
    assertTrue(gameController.isGameOver());

    // Attempt to play another turn
    gameController.playTurn(); // Should not increase the turn count
    assertEquals(3, gameController.getCurrentTurn());
  }
  
  @Test
  public void testIsGameOverExactTurnLimit() {
    // Play until the last allowed turn (turn 3, as maxTurns is 3)
    gameController.playTurn(); // Turn 1
    gameController.playTurn(); // Turn 2
    gameController.playTurn(); // Turn 3

    // Verify the game is over at the exact turn limit
    assertTrue(gameController.isGameOver());
  }
}