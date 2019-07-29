package fi.felixbade.TelegramBotClient.APIModel;

public class TelegramMessage {
    public int message_id;
    public TelegramUser from;
    public int date;
    public TelegramChat chat;
    public String text;
    public String caption;
}
