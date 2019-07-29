package fi.felixbade.TelegramBotClient.APIModel;

public class TelegramUpdate {
    public int update_id;
    public TelegramMessage message;
    public TelegramMessage edited_message;
    public TelegramMessage channel_post;
    public TelegramMessage edited_channel_post;
}
