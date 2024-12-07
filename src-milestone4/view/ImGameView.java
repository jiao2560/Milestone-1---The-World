package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Interface for the game view in the "Doctor Lucky's Mansion" game.
 * This interface defines the essential methods that view must provide.
 * Rendering the game state, communicating updates to the user.
 */
public interface ImGameView {
  /**
   * Sets the listener for starting a new game.
   *
   * @param actionListener the action listener for the new game action.
   */
  public void setNewGameListener(ActionListener actionListener);

  /**
   * Displays the game view.
   */
  public void display();

  /**
   * Starts a new game with the provided world map.
   *
   * @param worldMap the image representing the game world.
   */
  public void startNewGame(BufferedImage worldMap);

  /**
   * Updates the turn information displayed to the user.
   *
   * @param info the turn information as a string.
   */
  public void updateTurnInfo(String info);

  /**
   * Updates the game map with the provided image.
   *
   * @param worldMap the updated image of the game world.
   */
  public void updateGameMap(BufferedImage worldMap);

  /**
   * Logs a message to the game view.
   *
   * @param message the message to be logged.
   */
  public void logMessage(String message);
}
