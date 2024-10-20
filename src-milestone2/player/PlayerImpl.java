package player;

import item.ImItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import space.ImSpace;

/**
 * This class implements the {@code Player} interface, representing a player in the game.
 * Players can be either human or AI-controlled, move between spaces, collect items,
 * and interact with the game world.
 */
public class PlayerImpl implements Player {
  private String name;
  private ImSpace currentSpace;
  private List<ImItem> items;
  private int maxItems;  
  private boolean isAi;  // New flag to distinguish between AI and human players
  private Scanner scanner = new Scanner(System.in);
  private Random random = new Random();
  
  /**
   * Constructs a {@code PlayerImpl} object with the specified properties.
   *
   * @param name the name of the player
   * @param startingSpace the initial space where the player starts
   * @param maxItems the maximum number of items the player can carry
   * @param isAi {@code true} if the player is AI-controlled, {@code false} otherwise
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
  public void moveTo(ImSpace space) {
    this.currentSpace = space;
    System.out.println(name + " moved to " + space.getName());
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

  @Override
  public void lookAround() {
    System.out.println("Looking around from space: " + currentSpace.getName());

    // Display items in the current space
    List<ImItem> itemsHere = currentSpace.getItems();
    if (itemsHere.isEmpty()) {
      System.out.println("Items here: None");
    } else {
      System.out.println("Items here: ");
      for (ImItem item : itemsHere) {
        System.out.println(" - " + item);  // Uses the toString() method of Item
      }
    }

    // Display neighbors of the current space
    List<ImSpace> neighbors = currentSpace.getNeighbors();
    if (neighbors.isEmpty()) {
      System.out.println("Neighboring spaces: None");
    } else {
      System.out.print("Neighboring spaces: ");
      for (ImSpace neighbor : neighbors) {
        System.out.print(neighbor.getName() + " ");
      }
      System.out.println();  // New line after printing all neighbors
    }
  }

  /**
   * Displays a detailed description of the player, including their current space 
   * and the items they are carrying.
   */
  public void displayPlayerDescription() {
    System.out.println("Player: " + name);
    System.out.println("Current space: " + currentSpace.getName());

    if (items.isEmpty()) {
      System.out.println("Carrying: No items");
    } else {
      System.out.println("Carrying:");
      for (ImItem item : items) {
        System.out.println(" - " + item);
      }
    }
  }
  
  /**
   * Executes the player's turn. If the player is AI-controlled, it takes an AI turn.
   * Otherwise, the player takes a human turn with manual input.
   *
   * @param worldSpaces the list of spaces in the game world
   */
  public void takeTurn(List<ImSpace> worldSpaces) {
    if (isAi) {
      takeAiTurn(worldSpaces);
    } else {
      takeHumanTurn(worldSpaces);
    }
  }
  
  /**
   * Executes a human player's turn by presenting available actions and 
   * handling the player's input.
   *
   * @param worldSpaces the list of spaces in the game world
   */
  private void takeHumanTurn(List<ImSpace> worldSpaces) {
    System.out.println("It's your turn, " + name + ". Choose an action:");
    System.out.println("1. Move to a neighboring space");
    System.out.println("2. Pick up an item");
    System.out.println("3. Look around");
    System.out.println("4. Display player description");

    int choice = scanner.nextInt();

    switch (choice) {
      case 1:
        moveToNeighbor(worldSpaces);
        break;
      case 2:
        pickUpAvailableItem();
        break;
      case 3:
        lookAround();
        break;
      case 4:
        displayPlayerDescription();
        break;
      default:
        System.out.println("Invalid choice!");
        break;
    }
  }
  
  /**
   * Executes an AI player's turn by selecting an action at random.
   *
   * @param worldSpaces the list of spaces in the game world
   */
  private void takeAiTurn(List<ImSpace> worldSpaces) {
    System.out.println(name + " (AI) is thinking...");
    int action = random.nextInt(3);

    switch (action) {
      case 0:
        moveToNeighbor(worldSpaces);
        break;
      case 1:
        pickUpRandomItem();
        break;
      case 2:
        System.out.println(name + " looks around.");
        lookAround();
        break;
      default:
        break;
    }
  }

  /**
   * Moves the player to a randomly selected neighboring space.
   * If no neighbors are available, a message is displayed.
   *
   * @param worldSpaces the list of spaces in the game world
   */
  public void moveToNeighbor(List<ImSpace> worldSpaces) {
    List<ImSpace> neighbors = currentSpace.getNeighbors();
    if (!neighbors.isEmpty()) {
      ImSpace newSpace = neighbors.get(random.nextInt(neighbors.size()));
      moveTo(newSpace);
    } else {
      System.out.println("No neighbors to move to.");
    }
  }
  
  /**
   * Allows the player to pick up an available item from the current space.
   * If no items are available, a message is displayed.
   */
  public void pickUpAvailableItem() {
    List<ImItem> itemsHere = currentSpace.getItems();
    if (!itemsHere.isEmpty()) {
      System.out.println("Select an item to pick up:");
      for (int i = 0; i < itemsHere.size(); i++) {
        System.out.println(i + ": " + itemsHere.get(i).getName());
      }
      int choice = scanner.nextInt();
      pickUpItem(itemsHere.get(choice));
    } else {
      System.out.println("No items to pick up.");
    }
  }
  
  /**
   * Allows the AI player to pick up a random item from the current space.
   * If no items are available, the method does nothing.
   */
  public void pickUpRandomItem() {
    List<ImItem> itemsHere = currentSpace.getItems();
    if (!itemsHere.isEmpty()) {
      pickUpItem(itemsHere.get(random.nextInt(itemsHere.size())));
    }
  }
}
