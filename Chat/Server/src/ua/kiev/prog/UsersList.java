package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class UsersList {
    private static final UsersList users = new UsersList();

    private final Gson gson;

    private final Map<String, String> list = new HashMap<String, String>();
    private final Map<String, Boolean> listStatus = new HashMap<String, Boolean>();

    public static UsersList getInstance() {
        return users;
    }

    private UsersList() {
        gson = new GsonBuilder().create();
    }

    public synchronized void add(String login, String password) {
        list.put(login, password);
    }

    public synchronized void addStatus(String login, Boolean status) {
        if(listStatus.containsKey(login))
            listStatus.replace(login, status);
        else listStatus.put(login, status);
    }

    public synchronized boolean check(String login, String password){
        if (list.containsKey(login)) {
            String pass = list.get(login);
            if (pass.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public synchronized String toJSON() {
        return gson.toJson(list.keySet());
    }

    public synchronized String statusToJSON() {
        return gson.toJson(listStatus);
    }
}
