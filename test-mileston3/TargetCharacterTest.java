import static org.junit.Assert.assertEquals;

import character.TargetCharacter;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@code TargetCharacter} class. Verifies that the target character's
 * properties and methods, such as movement, health management, and damage tracking,
 * work as expected.
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
   * Tests that the character is initialized with the correct name,
   * health, previous health, and starting position.
   */
  @Test
  public void testInitialization() {
    assertEquals("Doctor Lucky", target.getName());
    assertEquals(20, target.getHealth());
    assertEquals(20, target.getPreviousHealth());
    assertEquals(0, target.getCurrentSpace());
  }

  /**
   * Tests that {@code moveToNextSpace()} increments the character's position by one.
   */
  @Test
  public void testMoveToNextSpace() {
    target.moveToNextSpace();
    assertEquals(1, target.getCurrentSpace());
  }

  /**
   * Tests that {@code moveToSpace(int moveIndex)} sets the character's
   * position to the specified index.
   */
  @Test
  public void testMoveToSpace() {
    target.moveToSpace(5);
    assertEquals(5, target.getCurrentSpace());
  }

  /**
   * Tests that {@code takeDamage(int damage)} reduces the character's health by
   * the specified amount and updates previous health.
   */
  @Test
  public void testTakeDamage() {
    target.takeDamage(5);
    assertEquals(15, target.getHealth());
    assertEquals(20, target.getPreviousHealth());  // Verify previous health is updated correctly
  }

  /**
   * Tests that taking damage greater than current health does not result
   * in negative health; health should be set to zero.
   */
  @Test
  public void testTakeExcessiveDamage() {
    target.takeDamage(25);
    assertEquals(0, target.getHealth());           // Health should not go below zero
    assertEquals(20, target.getPreviousHealth());  
  }

  /**
   * Tests that {@code getName()} returns the correct name of the character.
   */
  @Test
  public void testGetName() {
    assertEquals("Doctor Lucky", target.getName());
  }

  /**
   * Tests that {@code getHealth()} returns the current health of the character.
   */
  @Test
  public void testGetHealth() {
    assertEquals(20, target.getHealth());
  }

  /**
   * Tests that {@code getPreviousHealth()} returns the health of the character
   * before the last attack.
   */
  @Test
  public void testGetPreviousHealth() {
    target.takeDamage(8);
    assertEquals(12, target.getHealth());
    assertEquals(20, target.getPreviousHealth());  // Previous health should be the initial health
  }
}
