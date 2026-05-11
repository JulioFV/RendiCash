package com.ingjcfv.rendicash11.repositorio;

import android.content.Context;

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
    public RepoUsuario(Context contexto){
        AppDatabase db = AppDatabase.getInstance(contexto);
        daoUsuario = db.daoUsuario();
    }
    public void crearUsuario(Usuario usuario, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try{
                daoUsuario.crearUsuario(usuario);
                callback.onSuccess("Usuario creado");
            }catch (Exception e){
                callback.onError(e);
            }
        });
    }
    public void eliminarUsuario(Usuario usuario, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try {
                daoUsuario.eliminarUsuario(usuario);
                callback.onSuccess("Usuario eliminado");
            } catch (Exception e) {
                callback.onError(e);
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
                callback.onSuccess("Usuario actualizado");
            }catch (Exception e){
                callback.onError(e);
            }
        });
    }
}
