package eu.execom.toolbox1sugarorm.service;

import android.util.Log;

import java.util.List;

import eu.execom.toolbox1sugarorm.model.Message;
import eu.execom.toolbox1sugarorm.model.User;

public class MessageService {

    private static final String TAG = MessageService.class.getName();

    public static List<Message> getMessagesFromUser(User user) {
        final List<Message> messages = Message.find(Message.class, "author = ?", String.valueOf(user.getId()));
        Log.i(TAG, "Messages from user: " + user.getName() + ": " + messages);
        return messages;
    }

    public static List<Message> getMessagesForUser(User user){
        final String whereClause = "SELECT * FROM Message INNER JOIN Connection ON Message.author = Connection.to_user WHERE Connection.from_user = ? ORDER BY date_time DESC";
        final List<Message> messagesForUser = Message.findWithQuery(Message.class, whereClause, String.valueOf(user.getId()));
        Log.i(TAG, "Messages for " + user.getName() + ": " + messagesForUser);
        return messagesForUser;
    }
}
