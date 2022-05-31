package net.foxandr.sport.universiade.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class PasswordEncoder {

    public static String getAuthToken(String username, String password) {
        byte[] data = new byte[0];
        try {
            data = (username + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

}
