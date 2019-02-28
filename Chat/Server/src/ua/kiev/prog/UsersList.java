package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class UsersList {
    private static final UsersList users = new UsersList(){{add("yulia"); add("user");}};

    private final Gson gson;

    private final List<String> list = new LinkedList<String>();

    public static UsersList getInstance() {
        return users;
    }

    private UsersList() {
        gson = new GsonBuilder().create();
    }

    public synchronized void add (String login) {
        list.add(login);
    }

    public synchronized String toJSON() {
        return gson.toJson(list);
    }
}
