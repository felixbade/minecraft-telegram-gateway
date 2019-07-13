package fi.felixbade.TelegramGateway;

public class TelegramUser {
    public int id;
    public boolean is_bot;
    public String first_name;
    public String last_name;

    public String getName() {
        if (first_name != null) {
            if (last_name != null) {
                return first_name + " " + last_name;
            }
            return first_name;
        } else {
            if (last_name != null) {
                return last_name;
            }
            return "";
        }
    }
}
