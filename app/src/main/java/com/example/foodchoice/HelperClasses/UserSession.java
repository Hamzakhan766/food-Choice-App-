package com.example.foodchoice.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSession  {

    SharedPreferences userSession;
    SharedPreferences.Editor createSession;
    Context context;

    private static final String IS_LOGIN = "is_logged_in";

    public static final String KEY_FULLNAME = "FullName";
    public static final String KEY_USERNAME = "UserName";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_USERPHONE = "Phone";
    public static final String KEY_PASSWORD = "Password";

    public UserSession(Context _context){
        context = _context;
        userSession = _context.getSharedPreferences("userSession",Context.MODE_PRIVATE);
        createSession = userSession.edit();
    }

    ////create login/register session/////
    public void  loginRegisterSession(String FullName, String UserName, String Email, String Phone, String Password){
        createSession.putBoolean("IS_LOGGED_IN",true);
        createSession.putString(KEY_FULLNAME,FullName);
        createSession.putString(KEY_USERNAME,UserName);
        createSession.putString(KEY_EMAIL,Email);
        createSession.putString(KEY_USERPHONE,Phone);
        createSession.putString(KEY_PASSWORD,Password);
    }

    /////get user details with session////
    public HashMap<String, String> getUserDetailFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_USERNAME, userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_USERPHONE, userSession.getString(KEY_USERPHONE, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        return userData;
    }

    /////////checking if user already logged in/////////
    public boolean checkUserLogin(){
        if(userSession.getBoolean("IS_LOGGED_IN",false)){
            return true;
        }
        return false;
    }


    public void logout() {
        createSession.clear();
        createSession.commit();
    }

}
