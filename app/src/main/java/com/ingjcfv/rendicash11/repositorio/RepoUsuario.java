package com.ingjcfv.rendicash11.repositorio;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Database;

import com.ingjcfv.rendicash11.dao.DaoUsuario;
import com.ingjcfv.rendicash11.database.AppDatabase;
import com.ingjcfv.rendicash11.modelo.Usuario;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.DatabaseExecutor;

import java.util.concurrent.Future;

import kotlin.reflect.KCallable;

public class RepoUsuario {
    private final DaoUsuario daoUsuario;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    public RepoUsuario(Context contexto){
        AppDatabase db = AppDatabase.getInstance(contexto);
        daoUsuario = db.daoUsuario();
    }
    public void crearUsuario(Usuario usuario, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try{
                daoUsuario.crearUsuario(usuario);
                mainHandler.post(() -> {
                    callback.onSuccess("Usuario creado");
                });
            }catch (Exception e){
                mainHandler.post(() -> {
                    callback.onError(e);
                });
            }
        });
    }
    public void eliminarUsuario(Usuario usuario, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try {
                daoUsuario.eliminarUsuario(usuario);
                mainHandler.post(() -> {
                    callback.onSuccess("Usuario eliminado");
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    callback.onError(e);
                });
            }
        });
    }
    public Usuario buscarUsuarioPorCorreo(String correo) {
        try{
            Future<Usuario> future = DatabaseExecutor.EXECUTOR.submit(()-> {
                return daoUsuario.buscarUsuarioPorCorreo(correo);
            });
            return future.get();
        }catch (Exception e){
         return null;
        }
    }
    public void actualizarUsuario(Usuario usuario, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() ->{
            try{
                daoUsuario.actualizarUsuario(usuario);
                mainHandler.post(() -> {
                    callback.onSuccess("Usuario actualizado");
                });
            }catch (Exception e){
                mainHandler.post(() -> {
                    callback.onError(e);
                });
            }
        });
    }
}
