Milestone 2 - Doctor Lucky's Mansion
This project is a continuation of the interactive game Doctor Lucky's Mansion, which simulates the movement of players (human and AI) through different spaces, collecting items, interacting with the environment, and tracking progress through a game loop.

Running the Project via JAR File
JAR File Location
The JAR file for this project, milestone2.jar, can be found in the res/ directory of this repository.

How to Run the JAR File
1. Download the JAR file: Download the milestone2.jar file from the res/ directory.

2. Ensure Java is installed: Verify Java is installed on your machine by running "java -version"

3. Run the JAR file: Open a terminal (or command prompt), navigate to the directory containing milestone2.jar, and run "java -jar milestone2.jar"

4. Input File: The world configuration file mansion.txt is embedded within the JAR, so no separate input is required. The game will automatically load this file at startup.



Game Instructions

Once the game starts, you will interact with the game via on-screen prompts. The game tracks player turns, updates player status, and saves world maps.

How to Use the JAR File:
1. Game Start
   The game will load the world configuration and initialize with a human and AI player.  
   The initial world map will be saved as `world_map_initial.png` in the same directory.

2. Player Commands  
   During your turn, you will be prompted to select an action:
   - 1: Move to a neighboring space
   - 2: Pick up an item from the current space
   - 3: Look around the current space
   - 4: Display your player description

3. Valid Input
   Enter a valid space index or action number. If the input is invalid, the system will notify you.

4. Health and Damage 
   Moving into a space may trigger item interactions, reducing your health if damaging items are present.

5. World Map Generation  
   After each move, the world map will be updated and saved as `world_map.png`.

6. Game Over Condition  
   The game ends after 20 turns or if all players' health reaches zero.


Example Run:
Initial world map saved as 'world_map_initial.png'.
Turn 0: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
4
Player: Player1
Current space: Armory
Carrying: No items
Turn 1: AI's turn.
AI (AI) is thinking...
AI moved to Dining Hall
Turn 2: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
3
Looking around from space: Armory
Items here: 
 - Revolver (Damage: 3)
Neighboring spaces: Billiard Room Dining Hall Drawing Room 
Turn 3: AI's turn.
AI (AI) is thinking...
AI moved to Kitchen
Turn 4: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
2
Select an item to pick up:
0: Revolver
0
Player1 picked up Revolver
Turn 5: AI's turn.
AI (AI) is thinking...
AI picked up Sharp Knife
Turn 6: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
4
Player: Player1
Current space: Armory
Carrying:
 - Revolver (Damage: 3)
Turn 7: AI's turn.
AI (AI) is thinking...
AI picked up Crepe Pan
Turn 8: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
3
Looking around from space: Armory
Items here: None
Neighboring spaces: Billiard Room Dining Hall Drawing Room 
Turn 9: AI's turn.
AI (AI) is thinking...
AI looks around.
Looking around from space: Kitchen
Items here: None
Neighboring spaces: Dining Hall Parlor Wine Cellar 
Turn 10: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
1
Player1 moved to Drawing Room
Turn 11: AI's turn.
AI (AI) is thinking...
Turn 12: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
1
Player1 moved to Dining Hall
Turn 13: AI's turn.
AI (AI) is thinking...
Turn 14: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
1
Player1 moved to Armory
Turn 15: AI's turn.
AI (AI) is thinking...
Turn 16: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
1
Player1 moved to Drawing Room
Turn 17: AI's turn.
AI (AI) is thinking...
Turn 18: Player1's turn.
It's your turn, Player1. Choose an action:
1. Move to a neighboring space
2. Pick up an item
3. Look around
4. Display player description
1
Player1 moved to Dining Hall
Turn 19: AI's turn.
AI (AI) is thinking...
AI looks around.
Looking around from space: Kitchen
Items here: None
Neighboring spaces: Dining Hall Parlor Wine Cellar 
Game Over! Thanks for playing!

Key Features Demonstrated

This example run demonstrates the following Milestone 2 requirements:

1. Adding Players  
   - A human-controlled player and an AI-controlled player are added to the game.

2. Player Movement  
   - The player moves between spaces, such as from the **Armory** to the **Drawing Room**.

3. Item Pickup  
   - The player picks up items like the **Revolver**, and the AI picks up a **Sharp Knife**.

4. Looking Around  
   - The player looks around the **Armory**, seeing items and neighboring spaces.

5. Displaying Player Description  
   - The player's current status, including their name, current space, and carried items, is displayed.

6. Turn-Based Gameplay 
   - The game alternates turns between the human and AI players, with clear feedback after each command.

7. Displaying Space Information  
   - Information about the player’s current space, such as items and neighbors, is provided.

8. Graphical Map Generation  
   - The initial and updated world maps are saved as `world_map_initial.png` and `world_map.png` respectively.

9. Game End Condition 
   - The game continues until the maximum number of turns is reached or a player’s health drops to zero.

File Locations
- JAR File: `res/milestone2.jar`
- Initial World Map: `res/world_map_initial.png`
- Example Run Log: `res/milestone2-example runs.txt`


End of Milestone 2
This milestone builds on the previous one by introducing additional mechanics such as player item management, AI decision-making, and enhanced world interactions.
Enjoy playing **Doctor Lucky's Mansion** and exploring the expanded game mechanics introduced in this milestone!

