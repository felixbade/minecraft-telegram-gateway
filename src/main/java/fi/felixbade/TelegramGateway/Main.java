package fi.felixbade.TelegramGateway;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fi.felixbade.TelegramBotClient.TelegramBotClient;
import fi.felixbade.TelegramBotClient.APIModel.*;

public class Main extends JavaPlugin implements Listener {

    private static int telegramChatId;
    private static String telegramToken;

    public static TelegramBotClient telegram;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.telegramChatId = this.getConfig().getInt("telegram-chat-id");
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

                    int chatId = message.chat.id;
                    if (chatId == this.telegramChatId) {
                        String formatted = Formatting.formatTelegramMessageToMinecraft(message);
                        Bukkit.broadcastMessage(formatted);

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
        String name = event.getPlayer().getName();
        String messageToTelegram = String.format("%s joined the game", name);
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        String messageToTelegram = String.format("%s left the game", name);
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String messageToTelegram = event.getDeathMessage();
        telegram.sendMessage(telegramChatId, messageToTelegram);
    }
}
