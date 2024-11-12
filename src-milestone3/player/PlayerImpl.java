package player;

import character.ImTargetCharacter;
import character.TargetCharacter;
import item.ImItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import space.ImSpace;
import world.World;

/**
 * This class represents a player in the game, which can be either a human or an AI.
 * The player has a name, a current space, a list of items they carry, 
 * and a maximum item capacity. Players can look around, pick up items, 
 * attack the target character, and interact with the pet.
 */
public class PlayerImpl implements Player {
  public boolean isAi;
  private String name;
  private ImSpace currentSpace;
  private List<ImItem> items;
  private int maxItems;
  private Scanner scanner = new Scanner(System.in);
  private Random random = new Random();
  
  /**
   * Constructs a new player with the specified name, 
   * starting space, maximum item capacity, and AI status.
   *
   * @param name the name of the player
   * @param startingSpace the initial space where the player starts
   * @param maxItems the maximum number of items the player can carry
   * @param isAi whether the player is an AI or not
   */
  public PlayerImpl(String name, ImSpace startingSpace, int maxItems, boolean isAi) {
    this.name = name;
    this.currentSpace = startingSpace;
    this.items = new ArrayList<>();
    this.maxItems = maxItems;
    this.isAi = isAi;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ImSpace getCurrentSpace() {
    return currentSpace;
  }

  @Override
  public List<ImItem> getItems() {
    return items;
  }

  @Override
  public int getMaxItems() {
    return maxItems;
  }

  @Override
  public void pickUpItem(ImItem item) {
    if (canCarryMoreItems()) {
      items.add(item);
      currentSpace.removeItem(item);
      System.out.println(name + " picked up " + item.getName());
    } else {
      System.out.println(name + " cannot carry more items!");
    }
  }

  @Override
  public boolean canCarryMoreItems() {
    return items.size() < maxItems;
  }
  
  /**
   * Allows the player to look around and observe neighboring spaces and players.
   * 
   * @param world the world the player is in
   * @return {@code true} if another player is in the same space.
   */
  public boolean lookAround(World world) {
    List<ImItem> itemsHere = currentSpace.getItems();

    // Check visibility of neighboring spaces based on pet's location
    System.out.println("Neighboring spaces:");
    for (ImSpace neighbor : currentSpace.getNeighbors()) {
      if (world.isPetBlockingVisibility(neighbor)) {
        System.out.println(" - " + neighbor.getName() + ": [Blocked by pet]");
      } else {
        System.out.println(" - " + neighbor.getName());
        System.out.println("   - Players: " + neighbor.getPlayers());
        System.out.println("   - Items: " + neighbor.getItems());
      }
    }

    // Check if any players are in the same space as this player
    for (PlayerImpl player : world.getPlayers()) {
      if (player != this && player.getCurrentSpace() == currentSpace) {
        System.out.println("Another player (" + player.getName() + ") is "
            + "in the same space. The attack is seen.");
        return true;  // Attack is seen
      }
    }

    return false;  // No players in the same space
  }
  
  /**
   * Determines if the player’s attack would be visible to other players.
   * 
   * @param world the world the player is in
   * @return {@code true} if another player is in the same space.
   */
  public boolean isAttackVisible(World world) {
    List<PlayerImpl> playersInSameSpace = world.getPlayersInSameSpace(currentSpace);
    return playersInSameSpace.size() > 1;
  }

  @Override
  public void moveTo(ImSpace space) {
    this.currentSpace = space;
    System.out.println(name + " moved to " + space.getName());
  }
  
  /**
   * Attempts to attack the target character, 
   * only proceeding if no other players see the attack.
   *
   * @param target the target character to attack
   * @param world the world the player is in
   * @param allPlayers the list of all players in the game
   * @return {@code true} if the attack was successful, {@code false} 
   */
  @Override
  public boolean attemptKill(ImTargetCharacter target, World world, List<PlayerImpl> allPlayers) {
    // Check if Doctor Lucky is in the same space as the current player
    ImSpace targetSpace = world.getSpace(target.getCurrentSpace());
    if (targetSpace != currentSpace) {
      System.out.println("Doctor Lucky is not in the same space.");
      return false;
    }

    // Check if any other players are in the same space to see the attack
    for (PlayerImpl player : allPlayers) {
      if (player != this && player.getCurrentSpace() == currentSpace) {
        System.out.println("Attack was seen by another player. Attack stopped!");
        return false;
      }
    }

    // Proceed with the attack if it’s not seen
    ImItem highestDamageItem = items.stream()
            .max(Comparator.comparingInt(ImItem::getDamage))
            .orElse(null);
    int damage = (highestDamageItem != null) ? highestDamageItem.getDamage() : 1;

    if (highestDamageItem != null) {
      items.remove(highestDamageItem);
      System.out.println(name + " used " + highestDamageItem.getName() + " to "
          + "attack Doctor Lucky, dealing " + damage + " damage!");
    } else {
      System.out.println(name + " attempts to poke Doctor Lucky, dealing " + damage + " damage.");
    }

    target.takeDamage(damage);
    System.out.println("Doctor Lucky's health is now " + target.getHealth());

    return true;
  }
  
  @Override
  public boolean canSee(Player other) {
    return this.currentSpace == other.getCurrentSpace() 
        || currentSpace.getNeighbors().contains(other.getCurrentSpace());
  }
  
  /**
   * Displays information about the player, including their current space and carried items.
   */
  public void displayPlayerDescription() {
    System.out.println("Player: " + name);
    System.out.println("Current space: " + currentSpace.getName());
    if (items.isEmpty()) {
      System.out.println("Carrying: No items");
    } else {
      System.out.println("Carrying:");
      for (ImItem item : items) {
        System.out.println(" - " + item.getName());
      }
    }
  }
  
  /**
   * Allows the player to take a turn, choosing an action based on AI or human input.
   *
   * @param world the world the player is in
   * @param allPlayers the list of all players in the game
   * @return {@code true} if the player moved the pet, {@code false} otherwise
   */
  public boolean takeTurn(World world, List<PlayerImpl> allPlayers) {
    return isAi ? takeAiTurn(world, allPlayers) : takeHumanTurn(world, allPlayers);
  }
  
  /**
   * Handles a human player's turn by providing action options and executing the chosen action.
   *
   * @param world the world the player is in
   * @param allPlayers the list of all players in the game
   * @return {@code true} if the player moved the pet, {@code false} otherwise
   */
  private boolean takeHumanTurn(World world, List<PlayerImpl> allPlayers) {
    System.out.println("It's your turn, " + name + ". Choose an action:");
    System.out.println("1. Move to a neighboring space");
    System.out.println("2. Pick up an item");
    System.out.println("3. Look around");
    System.out.println("4. Display player description");
    System.out.println("5. Attempt to attack target character");
    System.out.println("6. Move the target character's pet");

    int choice = scanner.nextInt();

    switch (choice) {
      case 1:
        selectAndMoveToNeighbor();
        break;
      case 2:
        pickUpAvailableItem();
        break;
      case 3:
        lookAround(world);
        break;
      case 4:
        displayPlayerDescription();
        break;
      case 5:
        //System.out.println("Debug: Player chose to attack Doctor Lucky.");
        attemptKill(world.getTargetCharacter(), world, allPlayers);
        break;
      case 6:
        return movePet(world);
      default:
        System.out.println("Invalid choice!");
        break;
    }
    return false;
  }



  /**
   * Handles an AI player's turn by deciding and executing an appropriate action.
   *
   * @param world the world the player is in
   * @param allPlayers the list of all players in the game
   * @return {@code false} as the AI does not move the pet
   */
  private boolean takeAiTurn(World world, List<PlayerImpl> allPlayers) {
    System.out.println(name + " (AI) is thinking...");
    
    ImTargetCharacter targetCharacter = world.getTargetCharacter();
    ImSpace targetSpace = world.getSpace(targetCharacter.getCurrentSpace());

    // If the AI is in the same space as Doctor Lucky
    // attempt to attack with the highest damage item
    if (currentSpace == targetSpace) {
      System.out.println(name + " attempts to attack Doctor Lucky!");
      attemptKill(targetCharacter, world, allPlayers);
    } else {
      // Prioritize finding an item if no weapon is available
      if (items.isEmpty()) {
        pickUpRandomItem();
      } else {
        // Move to a neighboring space to get closer to Doctor Lucky or explore
        autoMoveToNeighbor();
      }
    }

    return false; // No pet movement by AI
  }

  /**
   * AI chooses a neighboring space at random and moves to it without human input.
   */
  private void autoMoveToNeighbor() {
    List<ImSpace> neighbors = currentSpace.getNeighbors();
    if (!neighbors.isEmpty()) {
      ImSpace chosenNeighbor = neighbors.get(random.nextInt(neighbors.size())); 
    } else {
      System.out.println(name + " has no neighboring spaces to move to.");
    }
  }

  /**
   * Moves the pet to a neighboring space, either based on AI choice or human input.
   *
   * @param world the world the player is in
   * @return {@code true} if the pet was moved successfully, {@code false} otherwise
   */
  private boolean movePet(World world) {
    List<ImSpace> petNeighbors = world.getPet().getCurrentSpace().getNeighbors();
    
    if (isAi) {
      if (!petNeighbors.isEmpty()) {
        ImSpace newPetSpace = petNeighbors.get(random.nextInt(petNeighbors.size()));
        world.movePetToSpace(newPetSpace);
        return true;
      }
    } else {
      System.out.println("Select a space to move the pet to:");
      for (int i = 0; i < petNeighbors.size(); i++) {
        System.out.println(i + ": " + petNeighbors.get(i).getName());
      }

      int choice = scanner.nextInt();
      if (choice >= 0 && choice < petNeighbors.size()) {
        world.movePetToSpace(petNeighbors.get(choice));
        return true;
      } else {
        System.out.println("Invalid choice. Pet remains in current space.");
      }
    }
    return false;
  }
  
  /**
   * Allows the human player to select and move to a neighboring space.
   */
  private void selectAndMoveToNeighbor() {
    List<ImSpace> neighbors = currentSpace.getNeighbors();
    if (neighbors.isEmpty()) {
      System.out.println("No neighbors to move to.");
      return;
    }
    
    System.out.println("Select a neighboring space to move to:");
    for (int i = 0; i < neighbors.size(); i++) {
      System.out.println(i + ": " + neighbors.get(i).getName());
    }

    int choice = scanner.nextInt();
    if (choice >= 0 && choice < neighbors.size()) {
      moveTo(neighbors.get(choice));
    } else {
      System.out.println("Invalid choice. Staying in current space.");
    }
  }
  
  /**
   * Allows the player to pick up an item from their current space, chosen by human input.
   */
  public void pickUpAvailableItem() {
    List<ImItem> itemsHere = currentSpace.getItems();
    if (itemsHere.isEmpty()) {
      System.out.println("No items to pick up.");
      return;
    }

    System.out.println("Select an item to pick up:");
    for (int i = 0; i < itemsHere.size(); i++) {
      System.out.println(i + ": " + itemsHere.get(i).getName());
    }

    int choice = scanner.nextInt();
    if (choice >= 0 && choice < itemsHere.size()) {
      pickUpItem(itemsHere.get(choice));
    } else {
      System.out.println("Invalid choice. No item picked up.");
    }
  }
  
  /**
   * Allows the player to pick up a random item from their current space without human input.
   */
  public void pickUpRandomItem() {
    List<ImItem> itemsHere = currentSpace.getItems();
    if (!itemsHere.isEmpty()) {
      pickUpItem(itemsHere.get(random.nextInt(itemsHere.size())));
    }
  }
}
