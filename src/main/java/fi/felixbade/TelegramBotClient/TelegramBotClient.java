package fi.felixbade.TelegramBotClient;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import fi.felixbade.TelegramBotClient.APIModel.TelegramUpdate;

public class TelegramBotClient {

    public String token;
    private int lastUpdateId = 0;
    private Gson gson = new Gson();

    public TelegramBotClient(String token) {
        this.token = token;
    }

    public void sendMarkdownMessage(int telegramChatId, String messageToTelegram) {
        sendMessageWithParseMode(telegramChatId, messageToTelegram, "Markdown");
    }

    public void sendMessage(int telegramChatId, String messageToTelegram) {
        sendMessageWithParseMode(telegramChatId, messageToTelegram, "");
    }

    public void sendMessageWithParseMode(int telegramChatId, String messageToTelegram, String parseMode) {
        new Thread(new Runnable() {
            public void run() {
                blockingSendMessage(telegramChatId, messageToTelegram, parseMode);
            }
        }).start();
    }

    public void blockingSendMessage(int telegramChatId, String messageToTelegram, String parseMode) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", this.token);

        JsonObject blob = new JsonObject();
        blob.addProperty("text", messageToTelegram);
        blob.addProperty("chat_id", telegramChatId);
        if (!parseMode.equals("")) {
            blob.addProperty("parse_mode", parseMode);
        }

        try {
            HTTPJsonClient.post(url, blob);
        } catch (java.io.IOException e) {
        }
    }


    public TelegramUpdate[] getNextUpdates() {
        String url = String.format("https://api.telegram.org/bot%s/getUpdates?offset=%d",
        this.token, lastUpdateId+1);

        JsonObject updates = null;
        try {
            updates = HTTPJsonClient.get(url);
        } catch (IOException e) {
            return new TelegramUpdate[0];
        }

        if (updates == null) {
            return new TelegramUpdate[0];
        }

        if (updates.has("result")) {
            JsonArray updatesBlob = updates.getAsJsonArray("result");
            int updateCount = updatesBlob.size();
            List<TelegramUpdate> parsedUpdates = new ArrayList<TelegramUpdate>();

            for (JsonElement blob : updatesBlob) {
                TelegramUpdate update = gson.fromJson(blob, TelegramUpdate.class);
                int updateId = update.update_id;
                if (updateId == this.lastUpdateId) {
                    continue;
                }
                this.lastUpdateId = updateId;
                parsedUpdates.add(update);
            }

            return parsedUpdates.toArray(new TelegramUpdate[parsedUpdates.size()]);
        }

        return new TelegramUpdate[0];
    }

    public static String escapeMarkdown(String text) {
        // https://core.telegram.org/bots/api#markdown-style
        String escapedCharacters = "_*`[";
        //String escapedCharacters = `"//"_*[]()~`>#+-=|{}.!"; // MDv2

        text = text.replace("\\", "\\\\");
        for (char c : escapedCharacters.toCharArray()) {
            String s = Character.toString(c);
            text = text.replace(s, "\\" + s);
        }
        return text;
    }
}
