package controller;

import java.util.ArrayList;
import java.util.List;
import player.PlayerImpl;
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
  }

  /**
   * Retrieves the list of players in the game.
   *
   * @return a {@code List} of {@code PlayerImpl} objects representing the players
   */
  public List<PlayerImpl> getPlayers() {
    return players;
  }

  /**
   * Retrieves the current turn number.
   *
   * @return the current turn as an integer
   */
  public int getCurrentTurn() {
    return currentTurn;
  }

  /**
   * Adds a player to the game and places them in their starting space.
   *
   * @param player the {@code PlayerImpl} to add
   */
  @Override
  public void addPlayer(PlayerImpl player) {
    players.add(player);
    player.getCurrentSpace().addPlayer(player);
  }

  /**
   * Executes the current player's turn, moves the target character, and advances to the next turn.
   * If the maximum number of turns is reached, the game ends.
   */
  @Override
  public void playTurn() {
    if (currentTurn >= maxTurns) {
      System.out.println("Maximum turns reached. Game over!");
      return;
    }

    PlayerImpl currentPlayer = players.get(currentTurn % players.size());
    System.out.println("Turn " + currentTurn + ": " + currentPlayer.getName() + "'s turn.");
    currentPlayer.takeTurn(world.getSpaces());

    // Move the target character after each turn
    world.moveTargetCharacter();

    currentTurn++;
  }

  /**
   * Checks whether the game is over based on the current turn and the maximum number of turns.
   *
   * @return {@code true} if the game is over; {@code false} otherwise
   */
  @Override
  public boolean isGameOver() {
    return currentTurn >= maxTurns;
  }
}
