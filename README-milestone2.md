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

