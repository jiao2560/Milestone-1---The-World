import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import character.ImTargetCharacter;
import character.TargetCharacter;
import controller.GameController;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Simple unit tests for the GameController class. Verifies turn handling,
 * game-over conditions, and basic interactions.
 */
public class GameControllerTest {

  private GameController gameController;
  private World mockWorld;
  private ImTargetCharacter mockTargetCharacter;
  private ImSpace startingSpace;
  private PlayerImpl player1;
  private PlayerImpl player2;
  
  /** 
   * Initializes the test environment by setting up the game world, players, 
   * and game controller with minimal configuration for testing.
   */
  @Before
  public void setUp() {
    startingSpace = new Space(1, "Starting Space", 0, 0, 1, 1);
    mockTargetCharacter = new TargetCharacter("Doctor Lucky", 20, 0);

    // Create a world with minimal setup: a single starting space and target character
    List<ImSpace> spaces = new ArrayList<>();
    spaces.add(startingSpace);
    mockWorld = new World(5, 5, "TestWorld", spaces, 
        Collections.emptyList(), mockTargetCharacter, null, new ArrayList<>());

    gameController = new GameController(mockWorld, 10);

    player1 = new PlayerImpl("Player1", startingSpace, 3, false);
    player2 = new PlayerImpl("Player2", startingSpace, 3, false);

    // Add players to the game controller
    gameController.addPlayer(player1);
    gameController.addPlayer(player2);
  }
  
  @Test
  public void testPlayTurnMaxTurnsReachedTargetNotKilled() {
    gameController.currentTurn = gameController.maxTurns - 1; 
    gameController.targetKilled = false;  // Ensure target is not killed

    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run playTurn to reach max turns
    gameController.playTurn();

    // Restore original System.out
    System.setOut(originalOut);

    // Verify that the game is over and the expected message is printed
    assertTrue("Game should be over when max turns are reached and "
        + "target not killed", gameController.isGameOver());
    String output = outputStream.toString();
    assertTrue(output.contains("Maximum turns reached. Game over! "
        + "The target character escaped."));
  }

 
  /**
   * Test the handleDoctorLuckyMovement method when Doctor Lucky tries to escape
   * due to a player being in the same space or neighboring space.
   */
  @Test
  public void testHandleDoctorLuckyMovementEscape() {
    // Set up a neighboring space for Doctor Lucky to move into
    ImSpace neighborSpace = new Space(2, "Neighbor Space", 1, 0, 2, 1);
    startingSpace.addNeighbor(neighborSpace); 

    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run handleDoctorLuckyMovement while player is in the same space to trigger escape
    gameController.handleDoctorLuckyMovement(player1);

    // Restore original System.out
    System.setOut(originalOut);

    String output = outputStream.toString();
    assertTrue(output.contains("Doctor Lucky feels threatened and tries to escape!"));
    assertTrue(output.contains("Doctor Lucky escapes to Neighbor Space"));
    assertEquals(1, gameController.doctorEscapeCount); // Check if escape count increments
  }
  
  /**
   * Test the handleDoctorLuckyMovement method when the game is already over 
   * (targetKilled is true), so Doctor Lucky should not try to escape.
   */
  @Test
  public void testHandleDoctorLuckyMovementGameAlreadyOver() {
    // Set the game to be over by marking targetKilled as true
    gameController.targetKilled = true;

    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run handleDoctorLuckyMovement, which should not trigger any escape attempt
    gameController.handleDoctorLuckyMovement(player1);

    // Restore original System.out
    System.setOut(originalOut);

    String output = outputStream.toString();
    assertTrue(output.contains("Doctor Lucky cannot escape further and remains in place."));
    assertEquals(0, gameController.doctorEscapeCount); 
  }

  /**
   * Test the handleDoctorLuckyMovement method when Doctor Lucky does not feel threatened
   * because no player is in the same space or a neighboring space.
   */
  @Test
  public void testHandleDoctorLuckyMovementNoThreat() {
    // Move player to another space that is not neighboring the target's space
    ImSpace distantSpace = new Space(3, "Distant Space", 3, 3, 4, 4);
    player1.moveTo(distantSpace);

    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run handleDoctorLuckyMovement, Doctor Lucky should stay as no player is threatening
    gameController.handleDoctorLuckyMovement(player1);

    // Restore original System.out
    System.setOut(originalOut);

    String output = outputStream.toString();
    assertTrue(output.contains("Doctor Lucky stays in Starting Space as he is not threatened."));
    assertEquals(0, gameController.doctorEscapeCount); // Escape count should not change
  }

  
  /**
   * Test the processAttackResult method when the attack did not occur.
   * Verifies that the game does not end, even if Doctor Lucky's health is 0.
   */
  @Test
  public void testProcessAttackResultNoAttackOccurred() {
    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Restore original System.out
    System.setOut(originalOut);

    // Call processAttackResult with attackOccurred as false and target health at 0
    mockTargetCharacter.takeDamage(20); // Doctor Lucky's health drops to 0
    gameController.processAttackResult(player1, false, mockTargetCharacter);

    // Verify that the game is not over and no message about winning is printed
    assertFalse("Game should not be over when "
        + "attack did not occur", gameController.isGameOver());
    String output = outputStream.toString();
    assertFalse(output.contains("wins! Doctor Lucky has been killed. Game over!"));
  }

  /**
   * Test the processAttackResult method when an attack occurred.
   * Verifies that the game does not end.
   */
  @Test
  public void testProcessAttackResultAttackOccurredHealthAboveZero() {
    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    // Restore original System.out
    System.setOut(originalOut);
    System.setOut(new PrintStream(outputStream));

    // Call processAttackResult with attackOccurred as true but target health above 0
    mockTargetCharacter.takeDamage(10); // Doctor Lucky's health remains above 0
    gameController.processAttackResult(player1, true, mockTargetCharacter);

    // Verify that the game is not over and no message about winning is printed
    assertFalse("Game should not be over when target is not dead", gameController.isGameOver());
    String output = outputStream.toString();
    assertFalse(output.contains("wins! Doctor Lucky has been killed. Game over!"));
  }


  /**
   * Test the handleDoctorLuckyMovement method when Doctor Lucky stays in place
   * as he does not feel threatened by any nearby players.
   */
  @Test
  public void testHandleDoctorLuckyMovementStay() {
    // Move player to another space so Doctor Lucky does not feel threatened
    ImSpace otherSpace = new Space(3, "Other Space", 3, 3, 4, 4);
    player1.moveTo(otherSpace); // Move player1 to a non-neighboring space

    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run handleDoctorLuckyMovement with player in a distant space
    gameController.handleDoctorLuckyMovement(player1);

    // Restore original System.out
    System.setOut(originalOut);

    String output = outputStream.toString();
    assertTrue(output.contains("Doctor Lucky stays in Starting "
        + "Space as he is not threatened."));
    assertEquals(0, gameController.doctorEscapeCount); // Escape count should not change
  }

  @Test
  public void testDisplayDoctorLuckyHealth() {
    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Call the method
    gameController.displayDoctorLuckyHealth();

    // Restore original System.out
    System.setOut(originalOut);

    // Check if the output contains Doctor Lucky's health
    String output = outputStream.toString();
    assertTrue(output.contains("Doctor Lucky's initial health: 20"));
  }

  @Test
  public void testAddPlayer() {
    assertEquals(2, gameController.getPlayers().size());
  }

  @Test
  public void testPlayTurn() {
    gameController.playTurn();
    assertEquals(1, gameController.getCurrentTurn()); // Check turn increment

    // Ensure the turn shifts to the next player in sequence
    PlayerImpl currentPlayer = gameController.getPlayers().get(gameController.getCurrentTurn() 
        % gameController.getPlayers().size());
    assertEquals("Player2", currentPlayer.getName()); 
  }

  @Test
  public void testIsGameOverAtMaxTurns() {
    for (int i = 0; i < 10; i++) {
      gameController.playTurn();
    }
    
    assertTrue(gameController.isGameOver());
  }
  
  
  @Test
  public void testPlayTurnTargetKilledMidGame() {
    // Set up scenario where the target is killed before max turns are reached
    gameController.targetKilled = true;
    gameController.currentTurn = 5;  // Arbitrary turn before reaching max

    // Run playTurn and check if game ends due to targetKilled being true
    gameController.playTurn();

    assertTrue("Game should be over when target is killed, "
        + "even if max turns not reached", gameController.isGameOver());
  }

  /**
   * Test the lookAround method to verify that it detects if other players
   * are in the same space as the current player.
   */
  @Test
  public void testLookAround() {
    // Both players are in the same space initially
    assertTrue(gameController.lookAround(player1));

    // Move player2 to a different space and re-test
    ImSpace newSpace = new Space(2, "Other Space", 2, 2, 3, 3);
    player2.moveTo(newSpace);
    assertFalse(gameController.lookAround(player1));
  }

  /**
   * Test the isAttackSeen method to check if an attack by one player is visible
   * to other players in the same space.
   */
  @Test
  public void testIsAttackSeen() {
    // Both players are in the same space initially
    assertTrue(gameController.isAttackSeen(player1));

    // Move player2 to a different space and re-test
    ImSpace newSpace = new Space(2, "Other Space", 2, 2, 3, 3);
    player2.moveTo(newSpace);
    assertFalse(gameController.isAttackSeen(player1));
  }

  /**
   * Test the processAttackResult method to verify the game ends when
   * Doctor Lucky is killed.
   */
  @Test
  public void testProcessAttackResult() {
    // Set up a successful attack scenario
    mockTargetCharacter.takeDamage(20); // Doctor Lucky's health drops to 0
    gameController.processAttackResult(player1, true, mockTargetCharacter);

    assertTrue("Game should be over when target is killed", gameController.isGameOver());
  }
  
  @Test
  public void testPlayTurnDoctorEscapeNotAllowed() {
    // Set up the escape count to the limit
    gameController.doctorEscapeCount = 1;

    // Run playTurn and ensure escape is not attempted due to max escapes
    gameController.playTurn();

    // Assert game continues since target is not killed, but escape count reached
    assertFalse("Game should not be over when escape count "
        + "is maxed out but turns remain", gameController.isGameOver());
  }

  @Test
  public void testPlayTurnDoctorEscapeAllowed() {
    // Set up with fewer escapes than allowed
    gameController.doctorEscapeCount = 0;

    // Run playTurn and ensure escape is attempted due to lower escape count
    gameController.playTurn();

    assertEquals(1, gameController.getCurrentTurn());
    assertFalse("Game should not be over if max turns not reached "
        + "and target not killed", gameController.isGameOver());
  }

  @Test
  public void testPlayTurnMaxTurnsReached() {
    // Set current turn to just before max and target not killed
    gameController.currentTurn = 9;

    // Run playTurn to reach maxTurns
    gameController.playTurn();

    // Check that the game is over due to max turns being reached
    assertTrue("Game should be over when max turns are reached", gameController.isGameOver());
  }

  @Test
  public void testPlayTurnTargetKilledBeforeMaxTurns() {
    // Simulate target killed before reaching max turns
    gameController.targetKilled = true;
    gameController.currentTurn = 5;  // Some turn before max

    // Run playTurn to check game continues because target already killed
    gameController.playTurn();

    // Since target is killed, we expect game over regardless of turn count
    assertTrue("Game should be over when target is killed "
        + "regardless of turns", gameController.isGameOver());
  }

  /**
   * Test the startGameLoop method to ensure it ends the game after the max turns.
   */
  @Test
  public void testStartGameLoop() {
    GameController limitedTurnsController = new GameController(mockWorld, 2); 
    limitedTurnsController.addPlayer(player1);
    limitedTurnsController.addPlayer(player2);

    limitedTurnsController.startGameLoop();

    assertTrue("Game should be over after reaching "
        + "max turns", limitedTurnsController.isGameOver());
  }
}