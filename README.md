# minecraft-telegram-gateway
A Bukkit/Spigot plugin that links Minecraft chat to a Telegram group.

## Setting up

### Building
- Install Maven
- Install JDK for java 8 (e.g. OpenJDK 8)
- Run `mvn package` in the root of this project
- Maven creates a `target` folder and puts the compiled `TelegramGateway.jar` there.

### Initial plugin setup
- Copy `TelegramGateway.jar` to the `plugins` folder in your Bukkit/Spigot server
- Run the server (or reload the plugins) and it will add `plugins/TelegramGateway/config.yml`.
- You will see a warning in the console that Telegram chat id and access token are not configured.

### Configuring Telegram
- Create the group that you want to use for integrating with Minecraft if it does not exist already.
- Create a Telegram bot for this plugin with [BotFather](https://t.me/botfather). If you have multiple Minecraft severs that use this plugin, each one will need it's own bot.
    - Suggested name for the bot: the hostname/ip of your Minecraft server
- Add the bot to your group chat.
- Set the privacy mode of your new bot to Disabled. Otherwise the bot will not be able to see messages in the group chat.

### Configuring this plugin
- Copy–paste the HTTP API access token from BotFather to `config.yml` (more info below).
- Reload the Minecraft server.
- If you have set up everything, the bot should shout the chat id to the Telegram group.
- Put the chat id to `config.yml`.
- Reload the Minecraft server.
- It should now be good to go. If you have any trouble, just create a new issue to [the issue tracker](https://github.com/felixbade/minecraft-telegram-gateway/issues).

## Vision

Most of my philosophy boils down to this: everything should be **transparent or loveable**.

The idea started from the motivation to make playing Minecraft less lonely and not playing Minecraft less fear-of-missing-out-y. So, ...
- **Maximum integration.** It’s not enough that the Minecraft and Telegram chats are linked, it should feel like *they are the same chat*. Of course, Minecraft and Telegram support a bit different message types, so the integration can’t be perfect. However, both ends should do their best to keep their people in the loop. Minecraft is just one client for Telegram and vice versa.
- **Respect conventions.** The Telegram side should feel as ”telegrammy” as possible and Minecraft as ”minecrafty” as possible. Furthermore, the bridge should try to bring the ”telegramminess” to Minecraft and the ”minecraftiness” to Telegram as much as possible. That might sound like repetition but actually each combination brings it’s very own considerations.
- **Ease of administration.** The gateway should utilise standard practises where applicable. The plugin should also be robust, even over cool features. This also means assisted setup.
- **Zero configuration for participation.** You are just playing Minecraft, why would you care about Telegram? Never underestimate the power of laziness. Client-side mods are out of question.
- **Simplicity is king.** Simplicity over flexibility.

### Things I would love to do if they were technically possible
- Custom pixel graphics for all the emojis
    - Bring Telegram to Minecraft in a Minecraft-y way
- Possibility to send screenshots directly to Telegram with a press of a key (”Whoa, this Pillager came out of nowhere!”)
    - Bring Minecraft to Telegram in a Telegram-y way
