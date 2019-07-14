# minecraft-telegram-gateway
A Bukkit/Spigot plugin that links Minecraft chat to a Telegram group.

## Building
- Install Maven
- Install JDK for java 8 (e.g. OpenJDK 8)
- Run `mvn package` in the root of this project
- Maven creates a `target` folder and puts the compiled `TelegramGateway.jar` there.

## Initial plugin setup
- Copy `TelegramGateway.jar` to the `plugins` folder in your Bukkit/Spigot server
- Run the server (or reload the plugins) and it will add `plugins/TelegramGateway/config.yml`.
- You will see a warning in the console that Telegram chat id and access token are not configured.

## Configuring Telegram
- Create the group that you want to use for integrating with Minecraft if it does not exist already.
- Create a Telegram bot for this plugin with [BotFather](https://t.me/botfather). If you have multiple Minecraft severs that use this plugin, each one will need it's own bot.
    - Suggested name for the bot: the hostname/ip of your Minecraft server
- Add the bot to your group chat.
- Set the privacy mode of your new bot to Disabled. Otherwise the bot will not be able to see messages in the group chat.
- Copy–paste the HTTP API access token from BotFather to `config.yml` (more info below).

## Configuring this plugin
- Put your bot access token to `config.yml`.
- Reload the Minecraft server.
- If you have set up everything, the bot should shout the chat id to the Telegram group.
- Put the chat id to `config.yml`.
- Reload the Minecraft server.
- It should now be good to go. If you have any trouble, just create a new issue to [the issue tracker](https://github.com/felixbade/minecraft-telegram-gateway/issues).
