package com.example.foodchoice.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final  String IsLogin = "Is_Logged_In";

    private static final  String KEY_USER_USERNAME = "userName";

    public SessionManager(Context _context){
        context = _context;
        userSession = context.getSharedPreferences("userSession",Context.MODE_PRIVATE);
        editor = userSession.edit();
    }


    public void createUserSession(String UserName){
        editor.putBoolean(IsLogin,true);
        editor.putString(KEY_USER_USERNAME,UserName);
        editor.commit();
    }



    public HashMap<String , String>  getUserDetails(){
        HashMap<String ,String> userData = new HashMap<String ,String>();
        userData.put(KEY_USER_USERNAME,userSession.getString(KEY_USER_USERNAME,null));
        return userData;
    }


    public boolean checkUserLogin(){
         if(userSession.getBoolean(IsLogin,false)){
             return true;
         }else
             return false;
    }




}
