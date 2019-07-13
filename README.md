# minecraft-telegram-gateway
A Bukkit/Spigot plugin that links Minecraft chat to a Telegram group

## Building
- Install Maven
- Install JDK for java 8 (e.g. OpenJDK 8)
- Run `mvn package` in the root of this project
- Maven creates a `target` folder and puts the compiled `TelegramGateway.jar` there.

## Installation
- Copy `TelegramGateway.jar` to the `plugins` folder in your Bukkit/Spigot server
- Run the server and it will add `plugins/TelegramGateway/config.yml`. You will see a warning in the console that Telegram chat id and token are not configured.
- Put your bot token and your group chat id to `config.yml`
    - todo: help with telegram-side configuration and obtaining the chat id
- Restart the server
- It should work
