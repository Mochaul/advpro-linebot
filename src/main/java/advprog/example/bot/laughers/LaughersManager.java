package advprog.example.bot.laughers;

public class LaughersManager {

    public boolean checkMessageContainsLaughers(String message) {
        return message.contains("wkwk") | message.contains("haha");
    }

    public void saveLaughersDataMessage(String message) {

    }

    public String getTop5LaughersInGroup(long groupId) {
        return null;
    }
}
