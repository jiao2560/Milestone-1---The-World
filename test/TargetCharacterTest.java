import static org.junit.Assert.assertEquals;

import character.TargetCharacter;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link TargetCharacter}.
 * This class contains unit tests to verify the behavior of the TargetCharacter class.
 */
public class TargetCharacterTest {
  private TargetCharacter targetCharacter;

  @Before
  public void setUp() {
    // Create a new TargetCharacter instance before each test
    targetCharacter = new TargetCharacter("Test Character", 50, 0);
  }

  @Test
  public void testGetName() {
    // Test if the character's name is correct
    assertEquals("Test Character", targetCharacter.getName());
  }

  @Test
  public void testMoveToNextSpace() {
    // Test if the character moves to the next space correctly
    targetCharacter.moveToNextSpace();
    assertEquals(1, targetCharacter.getCurrentSpace());
  }

  @Test
  public void testMoveToSpace() {
    // Test if the character moves to a specified space correctly
    targetCharacter.moveToSpace(5);
    assertEquals(5, targetCharacter.getCurrentSpace());
  }

  @Test
  public void testTakeDamage() {
    // Test if the character's health is reduced correctly when taking damage
    targetCharacter.takeDamage(10);
    assertEquals(40, targetCharacter.getHealth());
  }

  @Test
  public void testTakeDamageHealthCannotBeNegative() {
    // Test if the character's health does not drop below zero
    targetCharacter.takeDamage(60); // Exceeding the current health of 50
    assertEquals(0, targetCharacter.getHealth());
  }

  @Test
  public void testGetHealth() {
    // Test if the health of the character is correctly returned
    assertEquals(50, targetCharacter.getHealth());
  }

  @Test
  public void testTargetCharacterParsing() {
    String characterInfo = "50 Test Character";  // Simulating character info from a file
    String[] parts = characterInfo.split(" ");
    int health = Integer.parseInt(parts[0]);
    String name = parts[1] + " " + parts[2];
    
    TargetCharacter parsedCharacter = new TargetCharacter(name, health, 0);
    assertEquals("Test Character", parsedCharacter.getName());
    assertEquals(50, parsedCharacter.getHealth());
  }
}
