package fi.felixbade.TelegramGateway;

import fi.felixbade.TelegramBotClient.APIModel.*;

public class Formatting {

    public static String formatTelegramToMinecraft(TelegramMessage message) {
        String msg = "§oTelegram message error";
        String name = message.from.getName().replace("§", "&");

        if (message.text != null) {
            msg = String.format("%s: %s",
                    name,
                    convertEmojisToMinecraft(message.text));

        } else if (message.caption != null) {
            msg = String.format("%s: §3[Photo]§r %s",
                    name,
                    convertEmojisToMinecraft(message.caption));

        } else if (message.photo != null) {
            msg = String.format("%s: §3[Photo]§r",
                    name);

        } else if (message.sticker != null) {
            msg = String.format("%s: §a[Sticker]§r %s from %s",
                    name,
                    convertEmojisToMinecraft(message.sticker.emoji),
                    convertEmojisToMinecraft(message.sticker.set_name));

        } else {
            msg = String.format("§o%s sent a non-text message",
                    name);
        }

        return msg;
    }

    public static String convertEmojisToMinecraft(String withEmojis) {
        return (withEmojis
                .replace("😃", "§6=D§r ")
                .replace("😄", "§6:D§r ")
                .replace("😟", "§6D:§r ")
                .replace("😂", "§3X§6D§r ")
                .replace("😆", "§6XD§r ")
                .replace("🤓", "§6:3§r ")
                .replace("😎", "§8B§6)§r ")
                .replace("🤩", "§e⁑§6D§r ")
                .replace("😘", "§6︰§c*§r ")
                .replace("😭", "§3π§6o§3π§r ")
                .replace("😢", "§6︰§3'§6(§r ")
                .replace("😑", "§6⚍§r ")
                .replace("🆘", "§csos§r ")
                .replace("🔥", "§c`§6Δ§c‘§r ")
                .replace("💯", "§4¹ºº§r ")
                .replace("👌", "§65/5§r ")
                .replace("👍", "§6+1§r ")
                .replace("👎", "§6-1§r ")
                .replace("🍑", "§6❦§r ")
                .replace("❤️", "§c‹3§r ")
                .replace("🧡", "§6‹3§r ")
                .replace("💛", "§e‹3§r ")
                .replace("💚", "§2‹3§r ")
                .replace("💙", "§9‹3§r ")
                .replace("💜", "§5‹3§r ")
                .replace("🖤", "§8‹3§r ")
                .replace("💕", "§d‹33§r ")
                .replace("💗", "§d‹‹3§r ")
                .replace("💔", "§c‹/3§r ")
                .replace("❣️", "§c❣️§r ")
        );
    }
}
