# Milestone-1---The-World


Running the Project via JAR File

JAR File Location: The JAR file for this project, `milestone1.jar`, can be found in the `res/` directory of this repository.

How to Run the JAR File

1. Download the JAR file: First, download the `milestone1.jar` file from the `res/` directory.

2. Ensure Java is installed: Make sure you have Java installed on your machine. You can check this by running the following command:
   java -version

3. Running the JAR: Open a terminal (or command prompt) and navigate to the folder where you downloaded the `milestone1.jar` file. Then run the following command:
   java -jar milestone1.jar

4. Input File: The game requires a world specification file to work. The file `mansion.txt` is embedded in the JAR file, so you don’t need to provide it separately. The game will automatically read the embedded file and start the game.

5. User Input: Once the game starts, follow the on-screen prompts to move the target character by entering the space index. The game will generate a graphical representation of the world map after the command " java -jar milestone1.jar" has been executed.

6. Ending the Game: The game continues until the target character's health drops to zero or below, at which point the game will end.
Running the Project via JAR File




How to Use the JAR File:

Once you have successfully run the milestone1.jar file, follow these instructions to interact with the game:

1. Game Start: The game will load the world configuration from an embedded file (mansion.txt) or an external file (if provided as an argument). It will display information about the target character, the world, and generate the initial world map saved as world_map_initial.png in the same directory.

2. Player Commands:
After the game starts, you will be prompted to enter the space index where you want the target character to move.
Example: If you want to move to space 2, type 2 and press enter.

3. Valid Input:
Enter a valid space index (e.g., 0, 1, 2, etc.) within the bounds of the spaces available. The system will notify you if the index is invalid or if you are already in the selected space. The game will calculate if any items in the new space deal damage to your character, and it will reduce the character's health accordingly.

4. Health and Damage:
If the target character takes damage from items in the new space, the system will show the total damage taken and the remaining health.
If there are no damaging items in the space, the system will notify you.

5. Ending the Game:
The game will continue until your target character’s health drops to zero or below, at which point the game will end.
The game will generate and save the updated world map as world_map.png after each move.

6. World Map Generation:
Each time you move to a new space, the world map will be updated and saved as an image (world_map.png), which shows the character’s current position.







Doctor Lucky's Mansion - Example Run
Introduction
This example run demonstrates the functionality of the game, "Doctor Lucky's Mansion," which simulates the movement of a target character through different spaces in the mansion. The target character starts with a set health and encounters damaging items in certain spaces. This run demonstrates how the game handles moving between spaces, taking damage, updating the world map, and eventually ending the game when the character's health reaches zero.

Example Run
The following is an example run of the program, showcasing the functionality:

Welcome to the world: Doctor Lucky's
Target character: Doctor Lucky (Health: 50)
Target character starts in: Armory
Initial world map generated: world_map_initial.png

Enter the space index to move to (0 to 20): 0
You are already in this space. Please choose another.

Enter the space index to move to (0 to 20): 21
Invalid space index. Please enter a valid index between 0 and 20

Enter the space index to move to (0 to 20): 11
Moved to space: Lilac Room
You took 2 damage. Remaining health: 48
World map updated: world_map.png

Enter the space index to move to (0 to 20): 9
Moved to space: Lancaster Room
You took 3 damage. Remaining health: 45
World map updated: world_map.png

Enter the space index to move to (0 to 20): 12
Moved to space: Master Suite
You took 2 damage. Remaining health: 43
World map updated: world_map.png

Enter the space index to move to (0 to 20): 3
Moved to space: Dining Hall
No damaging items in this space. Current health: 43
World map updated: world_map.png

Enter the space index to move to (0 to 20): 4
Moved to space: Drawing Room
You took 2 damage. Remaining health: 41
World map updated: world_map.png

Enter the space index to move to (0 to 20): 9
Moved to space: Lancaster Room
You took 3 damage. Remaining health: 38
World map updated: world_map.png

Enter the space index to move to (0 to 20): 11
Moved to space: Lilac Room
You took 2 damage. Remaining health: 36
World map updated: world_map.png

[... similar moves with health decreasing]

Enter the space index to move to (0 to 20): 11
Moved to space: Lilac Room
You took 2 damage. Remaining health: 1
World map updated: world_map.png

Enter the space index to move to (0 to 20): 9
Moved to space: Lancaster Room
You took 3 damage. Remaining health: 0

Game Over! Your health dropped to 0.
Thanks for playing!



Example Run Demonstration:

World Initialization: The game begins with the world "Doctor Lucky's Mansion" and the target character "Doctor Lucky" starting in the "Armory" space.

Invalid Inputs: The game handles user input errors, such as moving to the current space or providing out-of-bound indices, prompting the user to try again.

Moving Between Spaces: The target character moves between various spaces (e.g., "Lilac Room" and "Lancaster Room"), taking damage if damaging items are present.

Health Tracking: The character's health is consistently tracked and displayed, decreasing as they encounter damaging items.

Game Over Condition: The game demonstrates a proper game-over scenario, ending when the character's health drops to 0.


Milestone 2 - Doctor Lucky's Mansion This project is a continuation of the interactive game Doctor Lucky's Mansion, which simulates the movement of players (human and AI) through different spaces, collecting items, interacting with the environment, and tracking progress through a game loop.

Running the Project via JAR File JAR File Location The JAR file for this project, milestone2.jar, can be found in the res/ directory of this repository.

How to Run the JAR File

Download the JAR file: Download the milestone2.jar file from the res/ directory.

Ensure Java is installed: Verify Java is installed on your machine by running "java -version"

Run the JAR file: Open a terminal (or command prompt), navigate to the directory containing milestone2.jar, and run "java -jar milestone2.jar"

Input File: The world configuration file mansion.txt is embedded within the JAR, so no separate input is required. The game will automatically load this file at startup.

Game Instructions

Once the game starts, you will interact with the game via on-screen prompts. The game tracks player turns, updates player status, and saves world maps.

How to Use the JAR File:

Game Start The game will load the world configuration and initialize with a human and AI player.
The initial world map will be saved as world_map_initial.png in the same directory.

Player Commands
During your turn, you will be prompted to select an action:

1: Move to a neighboring space
2: Pick up an item from the current space
3: Look around the current space
4: Display your player description
Valid Input Enter a valid space index or action number. If the input is invalid, the system will notify you.

Health and Damage Moving into a space may trigger item interactions, reducing your health if damaging items are present.

World Map Generation
After each move, the world map will be updated and saved as world_map.png.

Game Over Condition
The game ends after 20 turns or if all players' health reaches zero.

Example Run: Initial world map saved as 'world_map_initial.png'. Turn 0: Player1's turn. It's your turn, Player1. Choose an action:

Move to a neighboring space
Pick up an item
Look around
Display player description 4 Player: Player1 Current space: Armory Carrying: No items Turn 1: AI's turn. AI (AI) is thinking... AI moved to Dining Hall Turn 2: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 3 Looking around from space: Armory Items here:
Revolver (Damage: 3) Neighboring spaces: Billiard Room Dining Hall Drawing Room Turn 3: AI's turn. AI (AI) is thinking... AI moved to Kitchen Turn 4: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 2 Select an item to pick up: 0: Revolver 0 Player1 picked up Revolver Turn 5: AI's turn. AI (AI) is thinking... AI picked up Sharp Knife Turn 6: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 4 Player: Player1 Current space: Armory Carrying:
Revolver (Damage: 3) Turn 7: AI's turn. AI (AI) is thinking... AI picked up Crepe Pan Turn 8: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 3 Looking around from space: Armory Items here: None Neighboring spaces: Billiard Room Dining Hall Drawing Room Turn 9: AI's turn. AI (AI) is thinking... AI looks around. Looking around from space: Kitchen Items here: None Neighboring spaces: Dining Hall Parlor Wine Cellar Turn 10: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 1 Player1 moved to Drawing Room Turn 11: AI's turn. AI (AI) is thinking... Turn 12: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 1 Player1 moved to Dining Hall Turn 13: AI's turn. AI (AI) is thinking... Turn 14: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 1 Player1 moved to Armory Turn 15: AI's turn. AI (AI) is thinking... Turn 16: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 1 Player1 moved to Drawing Room Turn 17: AI's turn. AI (AI) is thinking... Turn 18: Player1's turn. It's your turn, Player1. Choose an action:
Move to a neighboring space
Pick up an item
Look around
Display player description 1 Player1 moved to Dining Hall Turn 19: AI's turn. AI (AI) is thinking... AI looks around. Looking around from space: Kitchen Items here: None Neighboring spaces: Dining Hall Parlor Wine Cellar Game Over! Thanks for playing!
Key Features Demonstrated

This example run demonstrates the following Milestone 2 requirements:

Adding Players

A human-controlled player and an AI-controlled player are added to the game.
Player Movement

The player moves between spaces, such as from the Armory to the Drawing Room.
Item Pickup

The player picks up items like the Revolver, and the AI picks up a Sharp Knife.
Looking Around

The player looks around the Armory, seeing items and neighboring spaces.
Displaying Player Description

The player's current status, including their name, current space, and carried items, is displayed.
Turn-Based Gameplay

The game alternates turns between the human and AI players, with clear feedback after each command.
Displaying Space Information

Information about the player’s current space, such as items and neighbors, is provided.
Graphical Map Generation

The initial and updated world maps are saved as world_map_initial.png and world_map.png respectively.
Game End Condition

The game continues until the maximum number of turns is reached or a player’s health drops to zero.
File Locations

JAR File: res/milestone2.jar
Initial World Map: res/world_map_initial.png
Example Run Log: res/milestone2-example runs.txt
End of Milestone 2 This milestone builds on the previous one by introducing additional mechanics such as player item management, AI decision-making, and enhanced world interactions. Enjoy playing Doctor Lucky's Mansion and exploring the expanded game mechanics introduced in this milestone!

Graphical World Map: After each move, the world map is updated, showing the new position of the target character and relevant details about neighboring spaces and items. This reflects the game's graphical output during each step.

File Locations
Example Run File: The full example run is provided in res/run1_gameplay.txt.
World Map Images: The initial and updated world maps are saved as world_map_initial.png and world_map.png after each move.


Here's an updated README for Milestone 3, incorporating all requirements and example runs.

---

# Milestone-3: Doctor Lucky's Mansion Gameplay

## Running the Project via JAR File

### JAR File Location
The JAR file for this project, `milestone3.jar`, can be found in the `res/` directory of this repository. The required `.txt` file for the world configuration is included within the JAR, so no additional setup is needed.

### How to Run the JAR File

1. **Download the JAR file**: Download `milestone3.jar` from the `res/` directory.
2. **Ensure Java is installed**: Check that Java is installed by running:
   ```bash
   java -version
   ```
3. **Running the JAR**: Open a terminal, navigate to the directory with the JAR file, and execute:
   ```bash
   java -jar milestone3.jar
   ```
4. **World Initialization**: Upon execution, the game reads the embedded `.txt` file to set up the mansion world and initializes Doctor Lucky's starting location, health, and pet.

### Gameplay Overview
The goal of the game is to be the first player to successfully kill Doctor Lucky. Both human and AI players can attempt to do this under specific conditions, as outlined below.

## Player Commands
Once the game starts, players can interact with it through the following commands:

1. **Move to a neighboring space**: Allows players to move between connected rooms.
2. **Pick up an item**: Collect items to increase attack effectiveness.
3. **Look around**: Observe the current space, seeing nearby rooms and occupants/items within line of sight, excluding rooms obscured by Doctor Lucky's pet.
4. **Display player description**: Shows player details, including items held.
5. **Attempt to attack target character**: Make an attempt on Doctor Lucky's life if conditions are met.
6. **Move the target character's pet**: Position the pet to strategically block visibility.

## Example Gameplay Scenarios
Below are examples of key scenarios within gameplay, each demonstrating various functionalities:

### 1. **Computer Player Winning**
   - The AI successfully kills Doctor Lucky by inflicting damage over several turns while remaining unseen by other players.

### 2. **Human Player Winning**
   - The human player, equipped with items, strategically avoids detection and kills Doctor Lucky.

### 3. **Target Character Escaping**
   - If Doctor Lucky survives until the maximum number of turns is reached, he escapes, ending the game with no winner.

### 4. **Pet Blocking Visibility**
   - Doctor Lucky’s pet is moved by a player, preventing other players from seeing into certain rooms, enabling unseen actions.

## Example Runs
Example text files showcasing various game endings and interactions are available in the `res/` directory:

- `computer_player_wins.txt`: Demonstrates AI winning by killing Doctor Lucky.
- `human_player_wins.txt`: Shows a human player successfully killing Doctor Lucky.
- `targetCharacter_escapes.txt`: Doctor Lucky escapes after the maximum number of turns.
- Each file documents the sequence of commands and results, highlighting the pet’s role, attacks, item usage, and visibility mechanics.

## World Map Generation
A map (`world_map.png`) showing the characters' positions is updated with each move and saved in the directory. The initial map, `world_map_initial.png`, is created when the game starts.

## Game Rules Recap

1. **Visibility**: An attack is only successful if no other player can see it.
2. **Attempting Attacks**:
   - Players without items "poke" Doctor Lucky, causing minor damage.
   - Players with items use them to cause higher damage but lose the item upon use.
3. **Ending Conditions**:
   - A player kills Doctor Lucky to win.
   - Doctor Lucky escapes if he survives the maximum turns.
   - The pet can be strategically moved by players to block visibility of certain rooms.

## Documentation

This project follows Javadoc standards:
- Each class and interface has a clear, descriptive comment.
- Public methods have detailed comments on purpose, parameters, return values, and side effects.

Milestone-4---The-World
## How to Use the JAR File
Simply download and execute the file as explained above to start the game and follow the prompts.

Milestone-4: Doctor Lucky's Mansion Gameplay
Running the Project via JAR File
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
