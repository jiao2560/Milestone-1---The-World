package controller;

import character.ImTargetCharacter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import player.PlayerImpl;
import space.ImSpace;
import world.World;

/**
 * This class manages the game loop, players, and interactions within the game world.
 * It allows adding players, handling turns, and determining when the game is over.
 */
public class GameController implements ImGameController {
  private World world;
  private List<PlayerImpl> players;
  private int maxTurns;
  private int currentTurn;
  private boolean targetKilled;
  private final int maxAllowedEscapes = 1; // Limit Doctor Lucky to one escape
  private int doctorEscapeCount;

  /**
   * Constructs a new {@code GameController} with the specified world and maximum turns.
   *
   * @param world    the game world containing spaces and items
   * @param maxTurns the maximum number of turns allowed for the game
   */
  public GameController(World world, int maxTurns) {
    this.world = world;
    this.players = new ArrayList<>();
    this.maxTurns = maxTurns;
    this.currentTurn = 0;
    this.targetKilled = false;
    this.doctorEscapeCount = 0;
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
  public void playTurn() {
    world.updatePlayerLocations();  // Ensure accurate locations at start of turn
    PlayerImpl currentPlayer = players.get(currentTurn % players.size());
    
    System.out.println("Turn " + currentTurn + ": " + currentPlayer.getName() + "'s turn in "
        + "space " + currentPlayer.getCurrentSpace().getName());
    displayDoctorLuckyInfo();

    currentPlayer.takeTurn(world, players);

    world.displayPlayerLocations();  

    if (doctorEscapeCount < maxAllowedEscapes) {
      handleDoctorLuckyMovement(currentPlayer);
    }

    currentTurn++;
    if (currentTurn >= maxTurns && !targetKilled) {
      System.out.println("Maximum turns reached. Game over! The target character escaped.");
      targetKilled = true;
    }
  }
  
  /**
   * Adds a player to the game and updates their position in the world.
   *
   * @param player the player to add
   */
  public void addPlayer(PlayerImpl player) {
    players.add(player);
    world.movePlayer(player, player.getCurrentSpace()); 
  }


  // Updated lookAround method
  private boolean lookAround(PlayerImpl currentPlayer) {
    ImSpace currentSpace = currentPlayer.getCurrentSpace();
    // Iterate through all players to check if any player is in the same space
    for (PlayerImpl player : players) {
      if (player != currentPlayer) {
        ImSpace playerSpace = player.getCurrentSpace();

        // Compare the spaces
        if (playerSpace == currentSpace) {
          return true;  // Attack is seen
        }
      }
    }

    // If no player is in the same space, return false
    return false;  // No players in the same space
  }

  /**
   * Checks if an attack by the given player is seen by other players in the game.
   * The attack is only seen if another player is in the same room or has line of sight.
   */
  @Override
  public boolean isAttackSeen(PlayerImpl attacker) {
    ImSpace attackerSpace = attacker.getCurrentSpace();
    for (PlayerImpl player : players) {
      if (player != attacker && player.getCurrentSpace() == attackerSpace) {
        // Attack is only seen if another player is in the same space as the attacker
        System.out.println("Attack by " + attacker.getName() + " was seen by another player.");
        return true;
      }
    }
    return false;
  }

  /**
   * Process the result of an attack attempt on Doctor Lucky.
   */
  private void processAttackResult(PlayerImpl currentPlayer, 
      boolean attackOccurred, ImTargetCharacter targetCharacter) {
    if (attackOccurred && targetCharacter.getHealth() <= 0) {
      System.out.println(currentPlayer.getName() + " wins! Doctor "
          + "Lucky has been killed. Game over!");
      targetKilled = true;
    }
  }



  /**
   * Starts the game loop and keeps calling playTurn() until the game ends.
   */
  public void startGameLoop() {
    System.out.println("Starting the game loop...");
    while (!isGameOver()) {
      playTurn();
    }
    System.out.println("Game Over! Thanks for playing!");
  }


  /**
   * Displays Doctor Lucky's initial health at the start of the game.
   */
  private void displayDoctorLuckyHealth() {
    ImTargetCharacter targetCharacter = world.getTargetCharacter();
    System.out.println("Doctor Lucky's initial health: " + targetCharacter.getHealth());
  }
    
  private void displayDoctorLuckyInfo() {
    ImTargetCharacter targetCharacter = world.getTargetCharacter();
    ImSpace doctorRoom = world.getSpace(targetCharacter.getCurrentSpace());
    System.out.println("Doctor Lucky's current health: " + targetCharacter.getHealth());
    System.out.println("Doctor Lucky is currently in room: " + doctorRoom.getName());
  }


  /**
   * Handles Doctor Lucky's escape attempt if players are nearby.
   */
  private void handleDoctorLuckyMovement(PlayerImpl currentPlayer) {
    if (targetKilled || doctorEscapeCount >= maxAllowedEscapes) {
      System.out.println("Doctor Lucky cannot escape further and remains in place.");
      return; 
    }

    ImTargetCharacter targetCharacter = world.getTargetCharacter();
    ImSpace targetSpace = world.getSpace(targetCharacter.getCurrentSpace());
    ImSpace playerSpace = currentPlayer.getCurrentSpace();

    System.out.println("Doctor Lucky is currently in " + targetSpace.getName() + " with "
        + "current health: " + targetCharacter.getHealth());

    if (playerSpace == targetSpace || targetSpace.getNeighbors().contains(playerSpace)) {
      System.out.println("Doctor Lucky feels threatened and tries to escape!");

      List<ImSpace> neighbors = targetSpace.getNeighbors();
      if (!neighbors.isEmpty()) {
        ImSpace newSpace = neighbors.get(new Random().nextInt(neighbors.size()));
        targetCharacter.moveToSpace(newSpace.getId());
        System.out.println("Doctor Lucky escapes to " + newSpace.getName() + " with "
            + "current health: " + targetCharacter.getHealth());
        doctorEscapeCount++; // Increment to enforce the single escape limit
      }
    } else {
      System.out.println("Doctor Lucky stays in " + targetSpace.getName() + " as "
          + "he is not threatened.");
    }
  }



  @Override
  public boolean isGameOver() {
    return currentTurn >= maxTurns || targetKilled;
  }
}
