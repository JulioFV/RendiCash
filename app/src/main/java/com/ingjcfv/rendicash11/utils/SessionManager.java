package com.ingjcfv.rendicash11.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ingjcfv.rendicash11.modelo.Usuario;

public class SessionManager {
    private static final String PREF_NAME = "usuario_session";
    private static final String KEY_USER = "datos_usuario";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson;
    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }
    public void saveSession(Usuario usuario){
        String usuarioJson = gson.toJson(usuario);
        editor.putString(KEY_USER, usuarioJson);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }
    public Usuario getSession(){
        String usuarioJson = prefs.getString(KEY_USER, null);
        if(usuarioJson != null){
            return gson.fromJson(usuarioJson, Usuario.class);
        }
        return null;
    }
    public boolean isLoggedIn(){
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public void logout(){
        editor.clear();
        editor.apply();
    }
}
