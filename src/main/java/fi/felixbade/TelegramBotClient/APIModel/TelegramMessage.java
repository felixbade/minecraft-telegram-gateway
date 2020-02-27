package fi.felixbade.TelegramBotClient.APIModel;

public class TelegramMessage {
    public int message_id;
    public TelegramUser from;
    public int date;
    public TelegramChat chat;
    public TelegramUser forward_from;
    public String text;
    public TelegramMessageEntity[] entities;
    public TelegramMessageEntity[] caption_entities;
    public TelegramPhotoSize[] photo;
    public TelegramSticker sticker;
    public TelegramLocation location;
    public String caption;
}
