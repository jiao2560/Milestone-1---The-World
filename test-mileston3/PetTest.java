import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import character.ImTargetCharacter;
import character.TargetCharacter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import pet.Pet;
import player.PlayerImpl;
import space.ImSpace;
import space.Space;
import world.World;

/**
 * Unit tests for the {@code Pet} class.
 */
public class PetTest {

  private Pet pet;
  private ImTargetCharacter owner;
  private ImSpace initialSpace;
  private ImSpace newSpace;
  private World world;

  /**
   * Sets up a new {@code Pet} instance and its dependencies for testing.
   */
  @Before
  public void setUp() {
    // Initialize spaces as ImSpace type
    initialSpace = new Space(0, "Initial Room", 0, 0, 1, 1);
    newSpace = new Space(1, "New Room", 0, 0, 1, 1);

    // Create an ImTargetCharacter instance positioned at initialSpace
    owner = new TargetCharacter("Doctor Lucky", 100, 0);

    // Initialize World with minimal data and matching interface types
    List<ImSpace> spaces = new ArrayList<>();
    spaces.add(initialSpace);
    spaces.add(newSpace);
    world = new World(5, 5, "TestWorld", spaces, 
        Collections.emptyList(), owner, null, Collections.emptyList());

    // Initialize Pet with the created world, owner, and initial position
    pet = new Pet("Buddy", owner, world);
  }

  /**
   * Tests that {@code getName()} returns the correct name of the pet.
   */
  @Test
  public void testGetName() {
    assertEquals("Buddy", pet.getName());
  }

  /**
   * Tests that {@code getCurrentSpace()} returns the initial space based on owner's position.
   */
  @Test
  public void testGetCurrentSpace() {
    assertSame(initialSpace, pet.getCurrentSpace());
  }

  /**
   * Tests that {@code moveTo(ImSpace space)} correctly updates the pet's current space.
   */
  @Test
  public void testMoveTo() {
    pet.moveTo(newSpace);
    assertSame(newSpace, pet.getCurrentSpace());
  }
}

