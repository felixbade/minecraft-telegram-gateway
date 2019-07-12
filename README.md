# bukkit-telegram-gateway
A Bukkit/Spigot plugin that links Minecraft chat to a Telegram group

## Building/installation
- Get Maven (required only for building the jar file – not necessary on the Minecraft server)
- Run `mvn package` in the root of this project
- Maven creates a `target` folder. There you should find `TelegramGateway.jar` file. Copy that to the `plugins` folder in your Bukkit/Spigot server
- Run the server and it will add `plugins/TelegramGateway/config.yml`
- Put your bot token and your group chat id to `config.yml`
- Restart the server
- It should work
