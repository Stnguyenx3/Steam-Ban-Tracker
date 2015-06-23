# Steam-Ban-Tracker
A program written in Java that can be used to track Steam profiles for bans. The program requests information from the Steam Web API, documentation for the API can be found here https://developer.valvesoftware.com/wiki/Steam_Web_API. The program parses the information returned by the API with Google's Json Parser called Gson. In order to use the API, you must sign up for an API key using https://steamcommunity.com/login/home/?goto=%2Fdev%2Fapikey. A Steam account is required for an API Key.

How to use the program:

You can add players to the program by copy and pasting the CS:GO console output after using the "status" command into the text field of the Add Players tab. You can also add players using their Steam profile URL.

After players have been added to the program, you can navigate to the Recently Added tab which will show the last 10 players added. To view all players being tracked by the program, go to the Browse Players tab. This tab displays every player in a JTable which contains the players ID, the date the player was added, the date the player was last updated, game ban information, and their 64-Bit Steam ID. 

The Games tab currently shows the players of each game and a link to their Steam profile. More improvements may be made to this tab. 

The Check Bans tab allows you to check for VAC or Game bans of all the players currently being tracked by the program. 

The Statistics tab is still a work in progress.

The Settings tab allows users to provide their own Steam Web API key which will be saved to a text file in the same directory as the program.

Some sample screenshots of the program running:

![img1](/screenshots/img1.png?raw=true)

![img2](/screenshots/img2.png?raw=true)

![img3](/screenshots/img3.png?raw=true)

![img4](/screenshots/img4.png?raw=true)
