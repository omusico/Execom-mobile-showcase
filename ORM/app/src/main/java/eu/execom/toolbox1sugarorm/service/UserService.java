package eu.execom.toolbox1sugarorm.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;

import eu.execom.toolbox1sugarorm.Toolbox;
import eu.execom.toolbox1sugarorm.model.Connection;
import eu.execom.toolbox1sugarorm.model.User;

public class UserService {

    private static final String TAG = UserService.class.getName();

    public static boolean register(String email, String password, String name) {
        final List<User> users = User.find(User.class, "email = ?", email);
        if (users != null && users.size() > 0){
            Log.i(TAG, "Email already in use");
            return false;
        }

        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.save();

        Log.i(TAG, "User successfully registered.");

        return true;
    }

    public static User login(String email, String password){
        final List<User> users = User.find(User.class, "email = ? and password = ?", email, password);
        if (users == null || users.size() == 0){
            Log.i(TAG, "Incorrect username and/or password.");
            return null;
        } else {
            final User loggedUser = users.get(0);
            Log.i(TAG, "Logging in: " + loggedUser.toString());
            return loggedUser;
        }
    }

    public static List<User> getFollowers(User user){
        final List<User> followers = Connection.find(User.class, "followedUser = ?", String.valueOf(user.getId()));
        Log.i(TAG, "Followers found: " + followers.toString());
        return followers;
    }

    public static String retrievePassword(String email, String name){
        final List<User> users = User.find(User.class, "email = ? and name = ?", email, name);
        if (users == null || users.size() == 0){
            Log.i(TAG, "No users found with that email / name combinations.");
            return null;
        } else {
            return users.get(0).getPassword();
        }
    }

    public static User getCurrentUser(Context context){
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final long userId = sharedPreferences.getLong(Toolbox.CURRENT_USER, -1);
        return User.findById(User.class, userId);
    }

    public static List<User> findOtherUsers(Context context){
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final long userId = sharedPreferences.getLong(Toolbox.CURRENT_USER, -1);
        final List<User> users = User.find(User.class, "id <> ? ", String.valueOf(userId));
        Log.i(TAG, "Found users: " + users);
        return users;
    }

    private static Connection getConnection(Context context, User user){
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final long currentUserId = sharedPreferences.getLong(Toolbox.CURRENT_USER, -1);
        final List<Connection> connections = Connection.find(Connection.class, "from_user = ? and to_user = ?", String.valueOf(currentUserId), String.valueOf(user.getId()));
        if (connections == null || connections.size() == 0){
            Log.i(TAG, "No connection between the current user and " + user.getName());
            return null;
        } else {
            Log.i(TAG, "Found a connection between the current user and " + user.getName());
            return connections.get(0);
        }
    }

    public static boolean isConnected(Context context, User user){
        return getConnection(context, user) != null;
    }

    public static void connect(Context context, User user){
        if (!isConnected(context, user)){
            final Connection connection = new Connection();
            connection.setFromUser(getCurrentUser(context));
            connection.setToUser(user);
            connection.save();
        }
    }

    public static void dissconect(Context context, User user){
        final Connection connection = getConnection(context, user);
        if (connection != null){
            connection.delete();
        }
    }

}
