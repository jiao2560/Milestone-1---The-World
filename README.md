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

This example run demonstrates the following core functionality of the game:

1. **Game Initialization**:
   - When the program is run, it displays a welcome screen with a message: "Welcome to Doctor Lucky's Mansion! The game was created by Leo."

2. **Game Setup Options**:
   - The user is presented with two buttons:
     - **Start Game**: Starts the game with the default `mansion.txt` file.
     - **Upload World**: Allows the user to upload a custom `.txt` file to create a personalized game world.

3. **Game Interface**:
   - Upon starting the game, the map of the mansion is displayed. Users can zoom in/out using the controls in the top right or scroll using the mouse wheel. 
   - The map shows the positions of human players, Doctor Lucky, and computer players.

4. **Look Around (L Key)**:
   - Pressing the **L key** displays a window showing the neighboring rooms of the current player's location.

5. **Pick Up Weapons (P Key)**:
   - Pressing the **P key** opens a window that lists weapons available for pickup in the current room, or informs the player if there are no weapons in the room. When a weapon is picked up, it is recorded on the right side of the screen (e.g., "Picked up: 3 Crepe Pan").

6. **Attack Doctor (A Key)**:
   - Pressing the **A key** triggers an attack on Doctor Lucky. If the player is not in the same room as Doctor Lucky, an error window pops up. Otherwise, the attack result is displayed on the screen.

---

