package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static String login;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            login = inform();

            Thread th = new Thread(new GetThread());
            th.setDaemon(true);
            th.start();

            System.out.println("Enter your message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()) break;

                if (text.equals("#users")) {
                    usersList();
                }
                else if(text.equals("#status")){
                    usersStatus();
                }
                else if (text.equals("#exit")) {
                    exit();
                }else {
                    String to = toUser(text);
                    text = text(text);
                    Message m = new Message(login, to, text);
                    int res = m.send(Utils.getURL() + "/add");
                    if (res != 200) { // 200 OK
                        System.out.println("HTTP error occured: " + res);
                        return;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static String inform(){
        System.out.println("Hello!");
        System.out.println("#users - all users\n#exit - go out\n#status - status users");
        System.out.println("Enter 'sign up' if you want to create new account");
        System.out.println("Enter 'sign in' if you have an account");
        Scanner scanner = new Scanner(System.in);
        String sign = scanner.nextLine();
        if(sign.equals("sign up"))
            return newUser();
        if(sign.equals("sign in"))
            return logIn();
        System.exit(-1);
        return "";
    }

    private static String logIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login: ");
        String login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        try {
            URL obj = new URL(Utils.getURL() + "/authorization?login=" + login + "&password=" + password);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            //conn.setRequestMethod("POST");
            int res = conn.getResponseCode();
            if (res != 200) {
                System.out.println("Incorrect login or password");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    private static String newUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login: ");
        String login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        try {
            URL obj = new URL(Utils.getURL() + "/adduser?login=" + login + "&password=" + password);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            int res = conn.getResponseCode();
            if (res != 200) {
                System.out.println("Incorrect login or password");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    private static String toUser(String text) {
        if (!text.contains("@"))
            return "-";

        String user = text.substring(text.indexOf("@") + 1, text.indexOf(" ", text.indexOf("@")));
        return user;
    }

    private static String text(String text) {
        String new_text = text.substring(text.indexOf(" ", text.indexOf("@")) + 1);
        return new_text;
    }

    private static void usersList() {
        final Gson gson = new GsonBuilder().create();
        try {
            URL url = new URL(Utils.getURL() + "/users");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            InputStream is = http.getInputStream();
            try {
                byte[] buf = requestBodyToArray(is);
                String strBuf = new String(buf, StandardCharsets.UTF_8);

                List<String> list;

                Type listType = new TypeToken<ArrayList<String>>() {
                }.getType();

                list = gson.fromJson(strBuf, listType);

                if (list != null) {
                    System.out.print("users: ");
                    for(int i = 0; i<list.size()-1; i++){
                        System.out.print(list.get(i)+", ");
                    }
                    System.out.println(list.get(list.size()-1));
                }
            } finally {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }

    private static void exit(){
        try {
            URL obj = new URL(Utils.getURL() + "/exit?user=" + login);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            int res = conn.getResponseCode();
            if (res != 200) {
                System.out.println("Error exit");
                System.exit(0);
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void usersStatus() {
        final Gson gson = new GsonBuilder().create();
        try {
            URL url = new URL(Utils.getURL() + "/status");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            InputStream is = http.getInputStream();
            try {
                byte[] buf = requestBodyToArray(is);
                String strBuf = new String(buf, StandardCharsets.UTF_8);

                Map<String, Boolean> list;

                Type listType = new TypeToken<HashMap<String, Boolean>>() {
                }.getType();

                list = gson.fromJson(strBuf, listType);

                if (list != null) {
                    for(Map.Entry entry: list.entrySet()){
                        System.out.print(entry.getKey()+" ");
                        if(entry.getValue().equals(true))
                            System.out.println("online");
                        else System.out.println("offline");
                    }
                }
            } finally {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
