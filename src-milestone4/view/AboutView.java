package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * AboutView displays the initial welcome page for the game.
 */
public class AboutView extends JFrame implements ImAboutView {
  /**
   * Constructs a new {@code AboutView} instance that serves as the welcome screen.
   * the game and options to start a new game or upload a world configuration file.
   *
   * @param startGameCallback a {@code Runnable} callback that is triggered. 
   * @param uploadWorldCallback a {@code Consumer<File>} callback that is triggered. 
   */
  public AboutView(Runnable startGameCallback, Consumer<File> uploadWorldCallback) {
    setTitle("Doctor Lucky's Mansion - Welcome");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLayout(new BorderLayout());

    // Welcome message
    JLabel welcomeLabel = new JLabel("Welcome to Doctor Lucky's "
        + "Mansion!", SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

    // About information
    JTextArea aboutText = new JTextArea();
    aboutText.setText("This game was created by Leo.\n"
            + "Instructions:\n"
            + "- Click Start Game to begin.\n"
            + "- Click Upload World to load a new world specification.\n"
            + "- Enjoy the game!");
    aboutText.setEditable(false);
    aboutText.setWrapStyleWord(true);
    aboutText.setLineWrap(true);

    // Start Game button
    JButton startGameButton = new JButton("Start Game");
    startGameButton.addActionListener(e -> startGameCallback.run());

    // Upload World button
    JButton uploadWorldButton = new JButton("Upload World");
    uploadWorldButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        uploadWorldCallback.accept(selectedFile);
      }
    });

    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(startGameButton);
    buttonPanel.add(uploadWorldButton);

    // Layout components
    add(welcomeLabel, BorderLayout.NORTH);
    add(new JScrollPane(aboutText), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }
}