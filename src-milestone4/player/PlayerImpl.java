package player;

import character.ImTargetCharacter;
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
 */
public class PlayerImpl implements Player {
  public boolean isAi;
  private String name;
  private ImSpace currentSpace;
  private List<ImItem> items;
  private int maxItems;
  private Random random = new Random();
  
  /**
   * Constructs a {@code PlayerImpl} object with the specified attributes.
   *
   * @param name          the name of the player; must not be {@code null}.
   * @param startingSpace the initial {@code ImSpace} where the player starts.
   * @param maxItems      the maximum number of items the player can hold.
   * @param isAi          {@code true} if the player is controlled by AI.
   * 
   * @throws IllegalArgumentException if {@code name} is {@code null} or empty, 
   *                                  or if {@code startingSpace} is {@code null}, 
   *                                  or if {@code maxItems} is negative.
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
  public void pickUpAvailableItem() {
    List<ImItem> itemsInSpace = currentSpace.getItems();
    if (!itemsInSpace.isEmpty() && canCarryMoreItems()) {
      pickUpItem(itemsInSpace.get(0));
    } else {
      System.out.println("No items to pick up or inventory is full.");
    }
  }

  @Override
  public ImSpace getCurrentSpace() {
    return currentSpace;
  }

  @Override
  public List<ImItem> getItems() {
    return new ArrayList<>(items); // Return a copy for immutability
  }

  @Override
  public int getMaxItems() {
    return maxItems;
  }

  @Override
  public boolean canCarryMoreItems() {
    return items.size() < maxItems;
  }

  @Override
  public void moveTo(ImSpace space) {
    this.currentSpace = space;
  }

  @Override
  public List<String> getNeighborNames() {
    List<String> names = new ArrayList<>();
    for (ImSpace neighbor : currentSpace.getNeighbors()) {
      names.add(neighbor.getName());
    }
    return names;
  }

  @Override
  public void lookAround(World world) {
    System.out.println("Looking around...");
    for (ImSpace neighbor : currentSpace.getNeighbors()) {
      System.out.println(world.getSpaceInfo(neighbor));
    }
  }


  @Override
  public boolean attemptKill(ImTargetCharacter target, World world, List<PlayerImpl> allPlayers) {
    ImSpace targetSpace = world.getSpace(target.getCurrentSpace());
    if (currentSpace != targetSpace) {
      System.out.println("Doctor Lucky is not in the same space.");
      return false;
    }
    for (PlayerImpl player : allPlayers) {
      if (player != this && player.getCurrentSpace() == currentSpace) {
        System.out.println("Other players are present. Attack failed.");
        return false;
      }
    }
    ImItem bestWeapon = items.stream().max(Comparator.comparingInt(ImItem::getDamage)).orElse(null);
    int damage = (bestWeapon != null) ? bestWeapon.getDamage() : 1;
    if (bestWeapon != null) {
      items.remove(bestWeapon);
    }
    target.takeDamage(damage);
    System.out.println("Attack succeeded. Damage: " + damage);
    return true;
  }


  @Override
  public boolean canSee(Player other) {
    return this.currentSpace == other.getCurrentSpace() 
            || currentSpace.getNeighbors().contains(other.getCurrentSpace());
  }

  /**
   * Handles the player's turn.
   *
   * @param world      the game world
   * @param allPlayers the list of all players
   * @return {@code true} if an attack occurred; otherwise, {@code false}
   */
  public boolean takeTurn(World world, List<PlayerImpl> allPlayers) {
    if (isAi) {
      return takeAiTurn(world, allPlayers);
    } else {
      return takeHumanTurn(world, allPlayers);
    }
  }

  private boolean takeAiTurn(World world, List<PlayerImpl> allPlayers) {
    System.out.println(name + " (AI) is thinking...");

    ImTargetCharacter target = world.getTargetCharacter();
    Random random = new Random();
    int choice = random.nextInt(4) + 1; // Randomly select an action (1 to 4)

    switch (choice) {
      case 1: // Move
        moveToRandomNeighbor();
        System.out.println(name + " (AI) moved to: " + currentSpace.getName());
        break;
      case 2: // Look around
        lookAround(world); // Pass the world object
        System.out.println(name + " (AI) looked around.");
        break;
      case 3: // Pick up an item
        List<ImItem> itemsInSpace = currentSpace.getItems();
        if (!itemsInSpace.isEmpty()) {
          pickUpItem(itemsInSpace.get(0)); // AI picks the first available item
          System.out.println(name + " (AI) picked up: " + itemsInSpace.get(0).getName());
        } else {
          System.out.println(name + " (AI) found no items to pick up.");
        }
        break;
      case 4: // Attack
        if (currentSpace == world.getSpace(target.getCurrentSpace())) {
          System.out.println(name + " (AI) is attempting to attack Doctor Lucky.");
          return attemptKill(target, world, allPlayers); // Pass arguments in the correct order
        } else {
          System.out.println(name + " (AI) cannot attack. Doctor Lucky is not in the same room.");
        }
        break;
      default:
        System.out.println(name + " (AI) did nothing due to invalid choice.");
    }
    return false;
  }

  private boolean takeHumanTurn(World world, List<PlayerImpl> allPlayers) {
    System.out.println("Your turn, " + name + "! Select an action:");
    System.out.println("1. Move");
    System.out.println("2. Look around");
    System.out.println("3. Pick up an item");
    System.out.println("4. Attack");

    int choice = new Scanner(System.in).nextInt();

    switch (choice) {
      case 1:
        moveToSelectedNeighbor();
        break;
      case 2:
        lookAround(world); // Pass the world object
        break;
      case 3:
        List<ImItem> availableItems = currentSpace.getItems();
        if (availableItems.isEmpty()) {
          System.out.println("No items available in this space to pick up.");
        } else {
          System.out.println("Available items:");
          for (int i = 0; i < availableItems.size(); i++) {
            System.out.println((i + 1) + ". " + availableItems.get(i).getName());
          }
          System.out.print("Select an item to pick up: ");
          int itemChoice = new Scanner(System.in).nextInt() - 1;
          if (itemChoice >= 0 && itemChoice < availableItems.size()) {
            pickUpItem(availableItems.get(itemChoice));
          } else {
            System.out.println("Invalid selection. No item picked up.");
          }
        }
        break;
      case 4:
        ImTargetCharacter target = world.getTargetCharacter();
        if (currentSpace == world.getSpace(target.getCurrentSpace())) {
          return attemptKill(target, world, allPlayers); // Pass arguments in the correct order
        } else {
          System.out.println("Doctor Lucky is not in the same room. You cannot attack.");
        }
        break;
      default:
        System.out.println("Invalid choice.");
    }
    return false;
  }

  private void moveToSelectedNeighbor() {
    List<ImSpace> neighbors = currentSpace.getNeighbors();
    if (neighbors.isEmpty()) {
      System.out.println("No neighboring spaces to move to.");
      return;
    }

    System.out.println("Available neighboring spaces:");
    for (int i = 0; i < neighbors.size(); i++) {
      System.out.println((i + 1) + ". " + neighbors.get(i).getName());
    }
    System.out.print("Select a space to move to: ");
    int choice = new Scanner(System.in).nextInt() - 1;

    if (choice >= 0 && choice < neighbors.size()) {
      moveTo(neighbors.get(choice));
      System.out.println("Moved to: " + neighbors.get(choice).getName());
    } else {
      System.out.println("Invalid choice. Stay in the current space.");
    }
  }

  private void moveToRandomNeighbor() {
    List<ImSpace> neighbors = currentSpace.getNeighbors();
    if (!neighbors.isEmpty()) {
      ImSpace newSpace = neighbors.get(random.nextInt(neighbors.size()));
      moveTo(newSpace);
    }
  }
  
  @Override
  public void pickUpItem(ImItem item) {
    if (canCarryMoreItems()) {
      items.add(item);
      currentSpace.removeItem(item);
      System.out.println("Picked up item: " + item.getName());
    } else {
      System.out.println("Cannot carry more items.");
    }
  }


  private void pickUpRandomItem() {
    List<ImItem> spaceItems = currentSpace.getItems();
    if (!spaceItems.isEmpty()) {
      ImItem itemToPick = spaceItems.get(0); // Pick the first item
      pickUpItem(itemToPick);
    } else {
      System.out.println("No items available to pick up in the current space.");
    }
  }
}
