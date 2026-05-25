package com.ingjcfv.rendicash11.repositorio;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ingjcfv.rendicash11.dao.DaoProyecto;
import com.ingjcfv.rendicash11.database.AppDatabase;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.DatabaseExecutor;

import java.util.List;

public class RepoProyectos {
    private final DaoProyecto daoProyecto;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    public RepoProyectos(Context contexto) {
        daoProyecto = AppDatabase.getInstance(contexto).daoProyecto();
    }
    public void crearProyecto(Proyecto proyecto, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try{
                daoProyecto.crearProyecto(proyecto);
                mainHandler.post(() -> {
                    callback.onSuccess("Proyecto creado");
                });
            }catch (Exception e){
                mainHandler.post(() -> {
                    callback.onError(e);
                });
            }
        });
    }
    public void obtenerProyectosPorUsuario(
            int id_usuario,
            CallbackResultado<List<Proyecto>> callback
    ) {

        DatabaseExecutor.EXECUTOR.execute(() -> {

            try {

                List<Proyecto> proyectos =
                        daoProyecto.obtenerProyectosPorUsuario(id_usuario);

                mainHandler.post(() -> {
                    callback.onSuccess(proyectos);
                });

            } catch (Exception e) {

                mainHandler.post(() -> {
                    callback.onError(e);
                });

            }

        });
    }


    public void obtenerProyectosRecomendados(int id_usuario,
            CallbackResultado<List<Proyecto>> callback
    ) {

        DatabaseExecutor.EXECUTOR.execute(() -> {

            try {

                List<Proyecto> proyectos =
                        daoProyecto.obtenerProyectosRecomendados(id_usuario);

                mainHandler.post(() -> {
                    callback.onSuccess(proyectos);
                });

            } catch (Exception e) {

                mainHandler.post(() -> {
                    callback.onError(e);
                });

            }

        });
    }
}
