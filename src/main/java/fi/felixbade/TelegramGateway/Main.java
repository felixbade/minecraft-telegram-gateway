package fi.felixbade.TelegramGateway;

import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.chat.TextComponent;

import fi.felixbade.TelegramBotClient.TelegramBotClient;
import fi.felixbade.TelegramBotClient.APIModel.*;

public class Main extends JavaPlugin implements Listener {

    private static long telegramChatId;
    private static String telegramToken;

    public static TelegramBotClient telegram;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.telegramChatId = this.getConfig().getLong("telegram-chat-id");
        this.telegramToken = this.getConfig().getString("telegram-token");

        Logger logger = Bukkit.getLogger();

        if (this.telegramToken.equals("987654321:AAxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyz")) {
            logger.warning("\033[31;1mWarning: Telegram access token has not been configured.\033[m");
        }

        this.telegram = new TelegramBotClient(this.telegramToken);

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            TelegramUpdate[] updates = telegram.getNextUpdates();
            for (TelegramUpdate update : updates) {
                if (update.message != null) {
                    TelegramMessage message = update.message;

                    long chatId = message.chat.id;
                    if (chatId == this.telegramChatId) {
                        TextComponent formatted = Formatting.formatTelegramMessageToMinecraft(message);
                        Bukkit.getServer().spigot().broadcast(formatted);

                    } else {
                        logger.warning(String.format("Message from an unknown chat: %d", chatId));

                        // Avoid infinite loops
                        if (message.from.is_bot) return;

                        String info = String.format("Set `telegram-chat-id` to `%d` in `plugins/TelegramGateway/config.yml` " +
                                "if you want to integrate this chat with the Minecraft chat", chatId);
                        telegram.sendMarkdownMessage(chatId, info);
                    }
                }
            }
        }, 10, 10);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String name = event.getPlayer().getName();
        String message = event.getMessage();
        String messageToTelegram = String.format("<%s> %s", name, message);
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        List<String> onlinePlayers = new ArrayList<String>();
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayers.add(player.getName());
        }
        updatePlayersOnlineInTelegram(onlinePlayers.toArray(new String[onlinePlayers.size()]));

        String name = event.getPlayer().getName();
        String messageToTelegram = String.format("%s joined the game", name);
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        List<String> onlinePlayers = new ArrayList<String>();
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.equals(event.getPlayer())) {
                onlinePlayers.add(player.getName());
            }
        }
        updatePlayersOnlineInTelegram(onlinePlayers.toArray(new String[onlinePlayers.size()]));

        String name = event.getPlayer().getName();
        String messageToTelegram = String.format("%s left the game", name);
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String messageToTelegram = event.getDeathMessage();
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }

    public void updatePlayersOnlineInTelegram(String[] players) {
        String message = "";
        if (players.length == 0) {
            message = "Nobody online in Minecraft";
        } else {
            message = "Online in Minecraft: " + String.join(", ", players);
        }
        telegram.setChatDescription(telegramChatId, message);
    }
}
