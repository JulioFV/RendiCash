package com.ingjcfv.rendicash11.repositorio;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ingjcfv.rendicash11.dao.DaoMovimiento;
import com.ingjcfv.rendicash11.database.AppDatabase;
import com.ingjcfv.rendicash11.modelo.Movimiento;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.DatabaseExecutor;

import java.util.List;

public class RepoMovimiento {
    private final DaoMovimiento daoMovimiento;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public RepoMovimiento(Context contexto) {
        daoMovimiento = AppDatabase.getInstance(contexto).daoMovimiento();
    }
    public void crearMovimiento(Movimiento movimiento, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
           try{
               daoMovimiento.crearMovimiento(movimiento);
               mainHandler.post(() -> {
                   callback.onSuccess("Movimiento creado");
               });

           }catch (Exception e){
               mainHandler.post(()-> {
                   callback.onError(e);
               });
           }
        });
    }
    public void eliminarMovimiento(Movimiento movimiento, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
           try{
               daoMovimiento.eliminarMovimiento(movimiento);
               mainHandler.post(() ->{
                  callback.onSuccess("Movimiento eliminado");
               });
           }catch (Exception e){
               mainHandler.post(() -> {
                   callback.onError(e);
               });
           }
        });
    }
    public List<Movimiento> obtenerMovimientosPorProyecto(int id_proyecto) {
        return daoMovimiento.obtenerMovimientosPorProyecto(id_proyecto);
    }
    public double obtenerGastosGenerales(int id_usuario) {
        return daoMovimiento.obtenerGastosGenerales(id_usuario);
    }
    public double obtenerIngresosGenerales(int id_usuario) {
        return daoMovimiento.obtenerIngresosGenerales(id_usuario);
    }
}
