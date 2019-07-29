package fi.felixbade.TelegramGateway;

import fi.felixbade.TelegramBotClient.APIModel.*;

public class Formatting {

    public static String formatTelegramMessageToMinecraft(TelegramMessage message) {
        String msg = "";
        String name = message.from.getName().replace("§", "&");

        if (message.forward_from != null) {
            String fwd_name = message.forward_from.getName().replace("§", "&");
            msg = String.format("§b[Fwd: %s]§r ", fwd_name);
        }

        if (message.text != null) {
            msg += convertEmojisToMinecraft(message.text);

        } else if (message.caption != null) {
            msg += "§3[Photo]§r ";
            msg += convertEmojisToMinecraft(message.caption);

        } else if (message.photo != null) {
            msg += "§3[Photo]§r ";

        } else if (message.sticker != null) {
            msg += String.format("§a[Sticker]§r %s from %s",
                    convertEmojisToMinecraft(message.sticker.emoji),
                    convertEmojisToMinecraft(message.sticker.set_name));
        }


        if (!msg.equals("")) {
            msg = String.format("%s: %s", name, msg);
        } else {
            msg = String.format("§o%s sent a non-text message", name);
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
