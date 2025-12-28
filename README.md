# SkinFighters
Enables skins for Vault Fighters in Vault Hunters 3rd edition.<br>
By default, skins used for fighters are skins from the VHSMP members.<br>
Fighters will have stars next to their name that show their tier (can be disabled in config)<br>

Commands:<br>
`/skinfighters info` - show possible skins fighter can spawn with, chance for fighter to spawn with skin and current name source<br>
`/skinfighters add <name>` - add skin to the possible skin list<br>
if you supply invalid username fighter will look like Steve or Alex<br>
`/skinfighters remove <name>` - remove skin from the possible skin list<br>
`/skinfighters removeAll` - remove all skins from the possible skin list<br>
`/skinfighters chance <number>` - change the chance of fighter with skin spawning.<br>
`/skinfighters source <source>` - set source of skin names, allowed values are CONFIG, URL, CONFIG_AND_URL<br>
`/skinfighters skinsFromURL setURL <url>` - specify URL to file with skin names (this file must contain names separated by newline - [example file](https://gist.githubusercontent.com/radimous/80afb71c68b83268abffe1d4bfbf7e28/raw/0e5df818f5195f14de0e5f90f8329b98d9f0bf7a/gistfile1.txt))<br>
you must put this link into double quotes (example: `/skinfighters skinsFromURL setURL "https://www.mywhitelist.tld/skinnames"`)<br>
`/skinfighters skinsFromURL getURL` - see current URL <br>
`/skinfighters skinsFromURL reload` - gets new data from URL and updates the skins<br>
`/skinfighters skinsFromURL removeAll` - removes all possible skins that it got from URL<br>

If you want to have your twitch subs as skins, you can use: https://whitelist.gorymoon.se/ and get the minecraft newline format url (link ends with /minecraft_nl)<br>

Install this mod on server or client.<br>

Getting the mod from curseforge is recommended: [curseforge page](https://www.curseforge.com/minecraft/mc-mods/skin-fighters) <br>
You can also build the mod yourself by using `./gradlew build` or grab .jar from releases.
