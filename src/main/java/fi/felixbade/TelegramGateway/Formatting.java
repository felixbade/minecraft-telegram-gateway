package fi.felixbade.TelegramGateway;

import fi.felixbade.TelegramBotClient.APIModel.*;

public class Formatting {

    public static String formatTelegramMessageToMinecraft(TelegramMessage message) {
        String msg = "";
        String action = "";
        String name = tgUserToString(message.from);

        if (message.forward_from != null) {
            String fwd_name = tgUserToString(message.forward_from);
            msg = String.format("§b[Fwd: %s]§r ", fwd_name);
        }

        if (message.text != null) {
            String text = message.text;
            if (message.entities != null) {
                text = addBoldAndItalicFormatting(text, message.entities);
            }
            msg += convertEmojisToMinecraft(text);

        } else if (message.caption != null) {
            msg += "§3[Photo]§r ";
            String text = message.caption;
            if (message.caption_entities != null) {
                text = addBoldAndItalicFormatting(text, message.caption_entities);
            }
            msg += convertEmojisToMinecraft(text);

        } else if (message.photo != null) {
            msg += "§3[Photo]§r ";

        } else if (message.sticker != null) {
            msg += String.format("§a[Sticker]§r %s from %s",
                    convertEmojisToMinecraft(message.sticker.emoji),
                    convertEmojisToMinecraft(message.sticker.set_name));

        } else if (message.location != null) {
        	msg += String.format("§3[Location: lat: %s, long: %s]§r",
        			message.location.latitude,
        			message.location.longitude);

        } else if (message.new_chat_members != null) {
            String[] name_list = new String[message.new_chat_members.length];
            for (int i = 0; i < message.new_chat_members.length; i++) {
                name_list[i] = tgUserToString(message.new_chat_members[i]);
            }
            action = String.format("added %s", String.join(", ", name_list));

        } else if (message.left_chat_member != null) {
            action = String.format("removed %s", tgUserToString(message.left_chat_member));

        } else {
            msg += "§7[An unrecognized message type]";
        }

        if (!action.equals("")) {
            return String.format("§e%s %s", name, action);
        } else {
            return String.format("%s: %s", name, msg);
        }
    }

    public static String tgUserToString(TelegramUser user) {
        return user.getName().replace("§", "⅋");
    }

    public static String addBoldAndItalicFormatting(String text, TelegramMessageEntity[] entities) {
        boolean[] boldCharacters = new boolean[text.length()];
        boolean[] italicCharacters = new boolean[text.length()];

        for (int i = 0; i < text.length(); i++) {
            boldCharacters[i] = false;
            italicCharacters[i] = false;
        }

        for (TelegramMessageEntity entity : entities) {
            if (entity.type.equals("bold")) {
                for (int i = 0; i < entity.length; i++) {
                    boldCharacters[entity.offset + i] = true;
                }
            }
            if (entity.type.equals("italic")) {
                for (int i = 0; i < entity.length; i++) {
                    italicCharacters[entity.offset + i] = true;
                }
            }
        }

        String newText = "";
        boolean prevBold = false;
        boolean prevItalic = false;
        for (int i = 0; i < text.length(); i++) {
            boolean bold = boldCharacters[i];
            boolean italic = italicCharacters[i];

            if ((prevBold && !bold) || (prevItalic && !italic)) {
                newText += "§r";
                if (bold) {
                    newText += "§l";
                }
                if (italic) {
                    newText += "§o";
                }
            }
            if (bold && !prevBold) {
                newText += "§l";
            }
            if (italic && !prevItalic) {
                newText += "§o";
            }

            newText += text.substring(i, i+1);

            prevBold = bold;
            prevItalic = italic;
        }

        return newText;
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
