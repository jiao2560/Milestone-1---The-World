import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import world.World;
import space.ImSpace;
import space.Space;
import item.ImItem;
import item.Item;
import character.TargetCharacter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WorldParsingTest {
  private World world;

  @Before
  public void setUp() throws IOException {
    // Reading world configuration from a test file
    BufferedReader reader = new BufferedReader(new FileReader("test_world_parsing.txt"));

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
  }

  @Test
  public void testWorldParsing() {
    // Verify the world was parsed and created correctly
    assertNotNull(world);
    assertEquals("Sample World", world.getName());
    assertEquals(2, world.getSpaces().size());
    assertEquals("Armory", world.getSpaces().get(0).getName());
  }
}
