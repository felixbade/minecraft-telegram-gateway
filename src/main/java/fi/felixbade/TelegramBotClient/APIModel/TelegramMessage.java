package fi.felixbade.TelegramBotClient.APIModel;

public class TelegramMessage {
    public int message_id;
    public TelegramUser from;
    public int date;
    public TelegramChat chat;
    public TelegramUser forward_from;
    public String text;
    public TelegramPhotoSize[] photo;
    public TelegramSticker sticker;
    public String caption;
}
