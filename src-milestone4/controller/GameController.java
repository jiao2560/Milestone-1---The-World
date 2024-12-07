package controller;

import character.ImTargetCharacter;
import item.ImItem;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import player.PlayerImpl;
import space.ImSpace;
import view.GameView;
import world.World;

/**
 * Manages the game loop, players, and interactions within the game world for a GUI.
 */
public class GameController implements ImGameController {
  private int doctorEscapeCount;
  private int currentTurn;
  private boolean targetKilled;
  private final int maxTurns;
  private final World world;
  private final List<PlayerImpl> players;
  private final int maxAllowedEscapes = 1;
  private GameView view; // Removed 'final'
 
  /**
   * Constructs a GameController to manage the game's logic, and interactions.
   *
   * @param world     the {@link World} object representing the game world
   * @param maxTurns  the maximum number of turns allowed in the game
   * @param view      the {@link GameView} object representing the interface;
   *                  can be {@code null} if no view is provided
   */
  public GameController(World world, int maxTurns, GameView view) {
    this.world = world;
    this.maxTurns = maxTurns;
    this.view = view;
    this.players = new ArrayList<>();
    this.currentTurn = 0;
    this.targetKilled = false;
    this.doctorEscapeCount = 0;

    if (view != null) {
      setupListeners();
    }
  }
  
  @Override
  public void setView(GameView view) {
    this.view = view;
    setupListeners();
  }
  
  @Override
  public List<ImSpace> getHumanPlayerNeighbors() {
    for (PlayerImpl player : players) {
      if (!player.isAi) { // Find the human player
        return world.getNeighbors(player.getCurrentSpace());
      }
    }
    return new ArrayList<>(); // Return empty if no human player
  }
  
  @Override
  public boolean movePlayerTo(ImSpace targetSpace) {
    for (PlayerImpl player : players) {
      if (!player.isAi) { // Find the human player
        ImSpace currentSpace = player.getCurrentSpace();
        List<ImSpace> neighbors = world.getNeighbors(currentSpace);

        if (neighbors.contains(targetSpace)) {
          player.moveTo(targetSpace);
          world.updatePlayerLocations(); // Sync player locations in the world
          view.logMessage("Moved to: " + targetSpace.getName());
          view.updateGameMap(world.generateMap()); // Update the map display
          return true;
        } else {
          view.logMessage("Invalid move! You can only move to neighboring spaces.");
          return false;
        }
      }
    }
    return false; // No human player found
  }

  @Override
  public void addPlayer(PlayerImpl player) {
    this.players.add(player); // Add to the controller's list
    world.getPlayers().add(player); // Synchronize with the world's players list
    world.updatePlayerLocations(); // Update the space-to-players map
    System.out.println("Player added: " + player.getName() + " to "
        + "space: " + player.getCurrentSpace().getName());
  }

  @Override
  public void playTurn() {
    if (isGameOver()) {
      view.logMessage("Game Over! Thanks for playing!");
      return;
    }

    // Get the current player
    PlayerImpl currentPlayer = players.get(currentTurn % players.size());

    // Update the turn information on the UI
    if (view != null) { // Ensure the view is accessible
      view.updateTurnInfo("Turn " + currentTurn + ": "
          + "" + "" + currentPlayer.getName() + " (Room: "
              + "" + currentPlayer.getCurrentSpace().getName() + ")");
    }

    // Log the current turn
    view.logMessage("Turn " + currentTurn + ": "
        + "" + currentPlayer.getName() + "'s turn in space "
            + "" + currentPlayer.getCurrentSpace().getName());

    // Display Doctor Lucky's info
    displayDoctorLuckyInfo();

    // Handle AI or human turn
    if (currentPlayer.isAi) {
      handleAiTurn(currentPlayer);
    } else {
      view.logMessage("Waiting for " + currentPlayer.getName() + " to take an action.");
    }

    // Increment the turn counter
    currentTurn++;
  }
  
  @Override
  public boolean attemptAttack() {
    for (PlayerImpl player : players) {
      if (!player.isAi) { // Find the human player
        ImSpace currentSpace = player.getCurrentSpace();
        ImTargetCharacter targetCharacter = world.getTargetCharacter();
        ImSpace targetSpace = world.getSpace(targetCharacter.getCurrentSpace());

        // Check if the player is in the same room as the target
        if (currentSpace.equals(targetSpace)) {
          // Check if the player has a weapon
          List<ImItem> weapons = player.getItems();
          if (weapons.isEmpty()) {
            view.logMessage("You have no weapon to attack Doctor Lucky.");
            return false;
          }

          // Perform the attack
          ImItem bestWeapon = weapons.stream()
                  .max((a, b) -> Integer.compare(a.getDamage(), b.getDamage()))
                  .orElse(null);

          int damage = (bestWeapon != null) ? bestWeapon.getDamage() : 1;
          player.getItems().remove(bestWeapon); // Remove weapon after use

          view.logMessage("You attacked Doctor "
              + "Lucky with " + bestWeapon.getName() + " (Damage: "
                  + "" + damage + "). Doctor Lucky's remaining health: "
                      + "" + targetCharacter.getHealth());

          // Check if Doctor Lucky is defeated
          if (targetCharacter.getHealth() <= 0) {
            targetKilled = true;
            view.logMessage("Congratulations! You defeated Doctor Lucky!");
          }
          return true;
        } else {
          view.logMessage("You are not in the same room as Doctor Lucky. "
              + "Move to his room to attack.");
          return false;
        }
      }
    }
    return false; // No human player found
  }

  @Override
  public boolean isGameOver() {
    return targetKilled || currentTurn >= maxTurns;
  }

  @Override
  public boolean isAttackSeen(PlayerImpl attacker) {
    ImSpace attackerSpace = attacker.getCurrentSpace();
    for (PlayerImpl player : players) {
      if (player != attacker && player.getCurrentSpace() == attackerSpace) {
        view.logMessage("Attack by " + attacker.getName() + " was seen by another player.");
        return true;
      }
    }
    return false;
  }

  @Override
  public List<PlayerImpl> getPlayers() {
    return players;
  }

  @Override
  public int getCurrentTurn() {
    return currentTurn;
  }
    
  @Override
  public String getPlayerDescription(PlayerImpl player) {
    StringBuilder description = new StringBuilder();
    description.append("Name: ").append(player.getName()).append("\n");
    description.append("Room: ").append(player.getCurrentSpace().getName()).append("\n");

    if (player.getItems().isEmpty()) {
      description.append("Weapon: None");
    } else {
      description.append("Weapon: ");
      description.append(player.getItems().stream()
              .map(item -> item.getName() + " (Damage: " + item.getDamage() + ")")
              .collect(Collectors.joining(", ")));
    }

    return description.toString();
  }
  
  @Override
  public void displayDoctorLuckyInfo() {
    ImTargetCharacter targetCharacter = world.getTargetCharacter();
    ImSpace doctorRoom = world.getSpace(targetCharacter.getCurrentSpace());
    view.logMessage("Doctor Lucky's current health: " + targetCharacter.getHealth());
    view.logMessage("Doctor Lucky is currently in room: " + doctorRoom.getName());
  }
  
  @Override
  public void handleAiTurn(PlayerImpl currentPlayer) {
    view.logMessage(currentPlayer.getName() + " (AI) is taking an action...");
    boolean actionTaken = currentPlayer.takeTurn(world, players);
    if (actionTaken) {
      processAttackResult(currentPlayer, actionTaken, world.getTargetCharacter());
    }
    endTurn();
  }
  
  @Override
  public void endTurn() {
    currentTurn++;
    if (doctorEscapeCount < maxAllowedEscapes) {
      handleDoctorLuckyMovement(players.get(currentTurn % players.size()));
    }
    playTurn();
  }
  
  @Override
  public void processAttackResult(PlayerImpl currentPlayer, 
      boolean attackOccurred, ImTargetCharacter targetCharacter) {
    if (targetCharacter.getHealth() <= 0) {
      if (currentPlayer.isAi) {
        view.logMessage("AI wins! Doctor Lucky has been "
            + "killed by the computer player. Game over!");
      } else {
        view.logMessage(currentPlayer.getName() + " wins! Doctor "
            + "Lucky has been killed by the human player. Game over!");
      }
      targetKilled = true;
    } else if (attackOccurred) {
      view.logMessage("Doctor Lucky attacked but "
          + "still alive with health: " + targetCharacter.getHealth());
    }
  }
  
  @Override
  public void handleDoctorLuckyMovement(PlayerImpl currentPlayer) {
    if (targetKilled || doctorEscapeCount >= maxAllowedEscapes) {
      view.logMessage("Doctor Lucky cannot escape further and remains in place.");
      return;
    }

    ImTargetCharacter targetCharacter = world.getTargetCharacter();
    ImSpace targetSpace = world.getSpace(targetCharacter.getCurrentSpace());
    ImSpace playerSpace = currentPlayer.getCurrentSpace();

    if (playerSpace == targetSpace || targetSpace.getNeighbors().contains(playerSpace)) {
      List<ImSpace> neighbors = targetSpace.getNeighbors();
      if (!neighbors.isEmpty()) {
        ImSpace newSpace = neighbors.get(new Random().nextInt(neighbors.size()));
        targetCharacter.moveToSpace(world.getSpaces().indexOf(newSpace));
      }
    }

    // Update the map with the new positions
    view.updateGameMap(world.generateMap());
  }

  @Override
  public void setupListeners() {
    if (view != null) {
      view.setNewGameListener(e -> {
        // Debug message 1: Generating the map
        System.out.println("Generating map with dimensions: " 
            + world.getCols() * 50 + "x" + world.getRows() * 50);
  
        currentTurn = 0;
        doctorEscapeCount = 0;
        targetKilled = false;
  
        // Generate the initial map
        BufferedImage initialMap = getWorldMap();
  
        // Debug message 2: Retrieved map dimensions
        System.out.println("Retrieved world map with dimensions: " 
            + initialMap.getWidth() + "x" + initialMap.getHeight());
  
        // Start a new game in the view
        view.startNewGame(initialMap);
      });
    }
  }
  
  @Override
  public List<String> getItemsInCurrentSpace() {
    for (PlayerImpl player : players) {
      if (!player.isAi) { // Find the human player
        ImSpace currentSpace = player.getCurrentSpace();
        
        // Debug: Log items in the current space
        System.out.println("DEBUG: Current space: " + currentSpace.getName());
        System.out.println("DEBUG: Items in current space: " + currentSpace.getItems());

        return currentSpace.getItems().stream()
                .map(item -> item.getName() + " (Damage: " + item.getDamage() + ")")
                .collect(Collectors.toList());
      }
    }
    System.out.println("DEBUG: No human player found.");
    return new ArrayList<>(); // Return empty if no human player
  }
  
  @Override
  public boolean pickUpItem(String itemName) {
    for (PlayerImpl player : players) {
      if (!player.isAi) { // Find the human player
        ImSpace currentSpace = player.getCurrentSpace();
        
        // Debug: Log items in the space
        System.out.println("DEBUG: Attempting to pick up item: " + itemName);
        System.out.println("DEBUG: Items in space: " + currentSpace.getItems());

        List<ImItem> itemsInSpace = currentSpace.getItems();
        
        // Extract raw name from itemName (removing "(Damage: X)")
        String rawName = itemName.split(" \\(Damage:")[0];

        ImItem itemToPick = itemsInSpace.stream()
                .filter(item -> item.getName().equals(rawName))
                .findFirst()
                .orElse(null);

        if (itemToPick != null) {
          if (player.canCarryMoreItems()) {
            player.pickUpItem(itemToPick); // Use the player's method
            
            // Debug: Log success
            System.out.println("DEBUG: Item picked up "
                + "successfully: " + itemToPick.getName());
            view.logMessage("Picked up: " + itemToPick.getName());
            return true;
          } else {
            // Debug: Log inventory full
            System.out.println("DEBUG: Inventory is "
                + "full. Cannot pick up item: " + itemName);
            view.logMessage("Inventory is full. Cannot pick up: " + itemName);
            return false;
          }
        } else {
          // Debug: Log item not found
          System.out.println("DEBUG: Item not found "
              + "in current space: " + rawName);
          view.logMessage("Item not found: " + itemName);
          return false;
        }
      }
    }
    System.out.println("DEBUG: No human player found.");
    return false; // No human player found
  }
    
  @Override
  public BufferedImage getWorldMap() {
    return world.generateMap();
  }
}