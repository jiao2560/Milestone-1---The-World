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

Key Features Demonstrated
World Initialization: The game begins with the world "Doctor Lucky's Mansion" and the target character "Doctor Lucky" starting in the "Armory" space.
Invalid Inputs: The game handles invalid inputs such as moving to the current space or out-of-bound indices.
Moving Between Spaces: The target character moves between various spaces, such as the "Lilac Room" and "Lancaster Room," taking damage if there are damaging items in the space.
Health Tracking: The character's health is tracked throughout the game, reducing based on the damage in each space.
Game Over Condition: The game ends when the character's health drops to 0, demonstrating the game-over logic.
Graphical World Map: After each move, the world map is updated to reflect the new position of the target character, with item and neighbor details printed on the map.

File Locations
Example Run File: The full example run is provided in res/run1_gameplay.txt.
World Map Images: The initial and updated world maps are saved as world_map_initial.png and world_map.png after each move.
