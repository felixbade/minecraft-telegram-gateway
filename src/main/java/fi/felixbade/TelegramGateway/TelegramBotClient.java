package fi.felixbade.TelegramGateway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class TelegramBotClient {

    public String token;
    private int lastUpdateId = 0;
    private Gson gson = new Gson();

    public TelegramBotClient(String token) {
        this.token = token;
    }

    public void sendMessage(int telegramChatId, String messageToTelegram) {
        new Thread(new Runnable() {
            public void run() {
                blockingSendMessage(telegramChatId, messageToTelegram);
            }
        }).start();
    }

    public void blockingSendMessage(int telegramChatId, String messageToTelegram) {
        System.out.println(messageToTelegram);

        String url = String.format("https://api.telegram.org/bot%s/sendMessage", this.token);

        JsonObject blob = new JsonObject();
        blob.addProperty("text", messageToTelegram);
        blob.addProperty("chat_id", telegramChatId);

        try {
            httpPostJson(url, blob);
        } catch (java.io.IOException e) {}
        }

        public TelegramUpdate[] getNextUpdates() {
            String url = String.format("https://api.telegram.org/bot%s/getUpdates?offset=%d",
            this.token, lastUpdateId+1);

            JsonObject updates = null;
            try {
                updates = httpGetJson(url);
            } catch (IOException e) {
                return new TelegramUpdate[0];
            }

            if (updates == null) {
                return new TelegramUpdate[0];
            }

            if (updates.has("result")) {
                JsonArray updatesBlob = updates.getAsJsonArray("result");
                int updateCount = updatesBlob.size();
                TelegramUpdate[] parsedUpdates = new TelegramUpdate[updateCount];

                int i = 0;
                for (JsonElement blob : updatesBlob) {
                    TelegramUpdate update = gson.fromJson(blob, TelegramUpdate.class);
                    int updateId = update.update_id;
                    if (updateId == this.lastUpdateId) {
                        continue;
                    }
                    this.lastUpdateId = updateId;
                    parsedUpdates[i] = update;
                    i++;
                }

                return parsedUpdates;
            }

            return new TelegramUpdate[0];
        }

        public JsonObject httpGetJson(String url) throws IOException {
            String response = httpGet(url);
            JsonParser parser = new JsonParser();
            return parser.parse(response).getAsJsonObject();
        }

        public String httpGet(String url) throws IOException {
            URL url2;
            try {
                url2 = new URL(url);
            } catch (MalformedURLException e) {
                return "";
            }

            URLConnection connection = url2.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = "";
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }

            reader.close();
            return response;
        }

        public JsonObject httpPostJson(String url, JsonObject data) throws IOException {
            URL url2;
            try {
                url2 = new URL(url);
            } catch (MalformedURLException e) {
                return null;
            }

            String body = gson.toJson(data);

            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            connection.setRequestProperty("Accept", "application/json");

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
            writer.write(body);
            writer.close();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = "";
            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();

            JsonParser parser = new JsonParser();
            return parser.parse(response).getAsJsonObject();
        }
    }
