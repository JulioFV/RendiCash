package com.ingjcfv.rendicash11.repositorio;

import android.content.Context;

import com.ingjcfv.rendicash11.dao.DaoProyecto;
import com.ingjcfv.rendicash11.database.AppDatabase;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.DatabaseExecutor;

import java.util.List;

public class RepoProyectos {
    private final DaoProyecto daoProyecto;
    public RepoProyectos(Context contexto) {
        daoProyecto = AppDatabase.getInstance(contexto).daoProyecto();
    }
    public void crearProyecto(Proyecto proyecto, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try{
                daoProyecto.crearProyecto(proyecto);
                callback.onSuccess("Proyecto creado");
            }catch (Exception e){
                callback.onError(e);
            }
        });
    }
    public List<Proyecto> obtenerProyectosPorUsuario(int id_usuario) {
        return daoProyecto.obtenerProyectosPorUsuario(id_usuario);
    }
}
