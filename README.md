Milestone-4---The-World
## How to Use the JAR File
Simply download and execute the file as explained above to start the game and follow the prompts.


Milestone-4: Doctor Lucky's Mansion Gameplay Running the Project via JAR File
JAR File Location
The JAR file for this project, milestone4.jar, can be found in the res/ directory of this repository. The required .txt file for the world configuration is included within the JAR, so no additional setup is needed.

How to Run the JAR File
Download the JAR file: Download milestone4.jar from the res/ directory.
Ensure Java is installed: Check that Java is installed by running:
java -version
Running the JAR: Open a terminal, navigate to the directory with the JAR file, and execute:
java -jar milestone4.jar
World Initialization: Upon execution, the game reads the embedded .txt file to set up the mansion world and initializes Doctor Lucky's starting location, health, and pet.
Gameplay Overview
The goal of the game is to be the first player to successfully kill Doctor Lucky. Both human and AI players can attempt to do this under specific conditions, as outlined below.

How to Use the JAR File:

Once you have successfully run the milestone1.jar file, follow these instructions to interact with the game:
1. After running the jar, you will see a welcome screen saying welcome to doctor lucky's mansion! the game was created by leo.

2. There are two buttons below. If you click start game, the system will use default mansion.txt by default. If you click upload world, you need to upload a .txt file to generate a custom world.

3. After entering the start game interface (you can zoom in or out in the upper right corner, or control the interface by scrolling the wheel), you will see the map and the positions of human players, doctors, and computer players.

4. If you use the L key on the keyboard, a window will pop up on the screen to show the neighbors of the current player's room. This is the LookAround function.

5. If you use the P key on the keyboard, a window will pop up on the screen to show the list of weapons you can pick up or to show that there are no weapons in this room. When you pick up a weapon, the right screen will record it, such as "Picked up: 3 Crepe Pan".

6. When you use the A key on the keyboard, your character will attack the doctor, and the attack result will be shown on the right screen. Note that you must be in the same room with the doctor to attack, otherwise a window will pop up on the screen to remind you that you do not have the right to attack.

7. When you click on the character portrait, you can see the character information including weapons, location and name.

8. You can end the game at any time by pressing the x button in the upper right corner.



---


---

## What the Example Run Demonstrates

This example run demonstrates the following functionality as per the project requirements:

1. **Welcome Screen (Requirement 1)**:
   - Upon launching the game, a welcome screen is displayed with a message: "Welcome to Doctor Lucky's Mansion! The game was created by Leo." This is the initial graphical user interface that users see when starting the game.

2. **Start Game and Upload World (Requirement 2)**:
   - The main menu provides two options: 
     - **Start Game** uses the default `mansion.txt` file to generate the world.
     - **Upload World** allows users to upload a custom `.txt` file to create a personalized game world. This demonstrates the ability to handle both default and custom game worlds through file input.

3. **Interactive Game Map (Requirement 3)**:
   - After starting the game, players see a map of the mansion, displaying the positions of human players, Doctor Lucky, and computer players. Users can zoom in and out using interface controls or scroll using the mouse wheel. This fulfills the requirement for an interactive map of the game world.

4. **Look Around Feature (Requirement 4)**:
   - Pressing the **L key** triggers the "Look Around" function, which opens a window showing the neighboring rooms of the current player's room. This allows players to explore the layout of the mansion.

5. **Pick Up Weapon Feature (Requirement 5)**:
   - Pressing the **P key** displays a window listing available weapons in the current room. If there are no weapons, the message will indicate that. When a player picks up a weapon, the right screen updates with a message like "Picked up: 3 Crepe Pan," demonstrating the functionality for item collection.

6. **Attack Feature (Requirement 6)**:
   - Pressing the **A key** allows the player to attack Doctor Lucky. If the player is in the same room as Doctor Lucky, the attack result is shown on the right screen. If the player is not in the same room, a message appears notifying the user that the attack cannot be made.

--

---

