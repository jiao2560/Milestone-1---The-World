import static org.junit.Assert.assertEquals;

import character.TargetCharacter;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for TargetCharacter.
 */
public class TargetCharacterTest {

  private TargetCharacter targetCharacter;

  @Before
  public void setUp() {
    // Initialize a target character with name "Doctor Lucky", 50 health, and position 0.
    targetCharacter = new TargetCharacter("Doctor Lucky", 50, 0);
  }

  @Test
  public void testGetName() {
    assertEquals("Doctor Lucky", targetCharacter.getName());
  }

  @Test
  public void testGetHealth() {
    assertEquals(50, targetCharacter.getHealth());
  }

  @Test
  public void testTakeDamage() {
    targetCharacter.takeDamage(10);
    assertEquals(40, targetCharacter.getHealth());

    targetCharacter.takeDamage(50);  // Exceed remaining health
    assertEquals(0, targetCharacter.getHealth());  // Health should not go below 0
  }

  @Test
  public void testMoveToNextSpace() {
    targetCharacter.moveToNextSpace();
    assertEquals(1, targetCharacter.getCurrentSpace());

    targetCharacter.moveToNextSpace();
    assertEquals(2, targetCharacter.getCurrentSpace());
  }

  @Test
  public void testMoveToSpace() {
    targetCharacter.moveToSpace(5);
    assertEquals(5, targetCharacter.getCurrentSpace());

    targetCharacter.moveToSpace(10);
    assertEquals(10, targetCharacter.getCurrentSpace());
  }

  @Test
  public void testGetCurrentSpace() {
    assertEquals(0, targetCharacter.getCurrentSpace());  // Initial position
  }
}
