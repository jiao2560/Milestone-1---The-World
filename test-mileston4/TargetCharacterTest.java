import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import character.TargetCharacter;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@code TargetCharacter} class. Verifies correct functionality
 * of character movement, health management, and state tracking.
 */
public class TargetCharacterTest {

  private TargetCharacter target;

  /**
   * Sets up a new {@code TargetCharacter} instance with initial values
   * before each test.
   */
  @Before
  public void setUp() {
    target = new TargetCharacter("Doctor Lucky", 20, 0);
  }

  /**
   * Tests that the character is initialized with the correct properties.
   */
  @Test
  public void testInitialization() {
    assertEquals("Doctor Lucky", target.getName());
    assertEquals(20, target.getHealth());
    assertEquals(20, target.getPreviousHealth());
    assertEquals(0, target.getCurrentSpace());
    assertTrue(target.isAlive());
  }

  /**
   * Tests that {@code moveToNextSpace()} correctly increments the character's position.
   */
  @Test
  public void testMoveToNextSpace() {
    target.moveToNextSpace();
    assertEquals(1, target.getCurrentSpace());
  }

  /**
   * Tests that {@code moveToSpace(int moveIndex)} sets the position correctly.
   */
  @Test
  public void testMoveToSpace() {
    target.moveToSpace(5);
    assertEquals(5, target.getCurrentSpace());
  }

  /**
   * Tests that {@code takeDamage(int damage)} reduces health and updates previous health.
   */
  @Test
  public void testTakeDamage() {
    target.takeDamage(5);
    assertEquals(15, target.getHealth());
    assertEquals(20, target.getPreviousHealth());
  }

  /**
   * Tests that health cannot drop below zero when taking excessive damage.
   */
  @Test
  public void testTakeExcessiveDamage() {
    target.takeDamage(25);
    assertEquals(0, target.getHealth());
    assertEquals(20, target.getPreviousHealth());
    assertTrue(!target.isAlive());
  }

  /**
   * Tests {@code getName()} for correct name retrieval.
   */
  @Test
  public void testGetName() {
    assertEquals("Doctor Lucky", target.getName());
  }

  /**
   * Tests {@code getHealth()} for current health retrieval.
   */
  @Test
  public void testGetHealth() {
    assertEquals(20, target.getHealth());
  }

  /**
   * Tests {@code getPreviousHealth()} for tracking health before the last damage.
   */
  @Test
  public void testGetPreviousHealth() {
    target.takeDamage(8);
    assertEquals(12, target.getHealth());
    assertEquals(20, target.getPreviousHealth());
  }

  /**
   * Tests {@code isAlive()} to confirm the character's alive status.
   */
  @Test
  public void testIsAlive() {
    assertTrue(target.isAlive());
    target.takeDamage(20);
    assertTrue(!target.isAlive());
  }
  
  /**
   * Tests that {@code moveToNextSpace()} correctly handles. 
   * to the first space when the position is less than zero.
   */
  @Test
  public void testMoveToNextSpaceHandlesNegativePosition() {
    // Case 1: Normal increment
    target.moveToNextSpace();
    assertEquals(1, target.getCurrentSpace());
    
    // Case 2: Reset to zero if negative
    // Simulate a negative position
    target.moveToSpace(-1); // Directly set to a negative value
    target.moveToNextSpace();
    assertEquals(0, target.getCurrentSpace());
  }

}
