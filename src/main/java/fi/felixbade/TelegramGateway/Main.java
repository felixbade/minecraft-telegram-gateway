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

        if (this.telegramChatId == -123456789) {
            logger.warning("\033[31;1mWarning: Telegram chat ID has not been configured.\033[m");
        }

        telegram = new TelegramBotClient(telegramToken);

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            TelegramUpdate[] updates = telegram.getNextUpdates();
            for (TelegramUpdate update : updates) {
                TelegramMessage message = update.message;

                String msg = "§oTelegram message error";
                String name = message.from.getName().replace("§", "&");

                if (message.text != null) {
                    msg = String.format("%s: %s",
                    name,
                    message.text);
                } else if (message.caption != null) {
                    msg = String.format("%s: §3[Photo]§r %s",
                    name,
                    message.caption);
                } else {
                    msg = String.format("§o%s sent a non-text message",
                    name);
                }

                Bukkit.broadcastMessage(msg);
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
