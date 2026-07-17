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
import java.util.concurrent.Future;

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
    public void obtenerMovimientosPorProyecto(int id_proyecto, CallbackResultado<List<Movimiento>> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
            try{
                List<Movimiento> movimientos = daoMovimiento.obtenerMovimientosPorProyecto(id_proyecto);
                mainHandler.post(() -> {
                    callback.onSuccess(movimientos);
                });
            }catch (Exception e){
                mainHandler.post(() -> {
                    callback.onError(e);
                });
            }
        });
    }
    public double obtenerGastosGenerales(int id_usuario) {
        try{
            Future<Double> future = DatabaseExecutor.EXECUTOR.submit(() -> {
                return daoMovimiento.obtenerGastosGenerales(id_usuario);
            });
            return future.get();
        }catch (Exception e){
            if(e instanceof InterruptedException){
                return 0;
            }
            return 0;
        }
    }
    public double obtenerIngresosGenerales(int id_usuario) {
        try{
            Future<Double> future = DatabaseExecutor.EXECUTOR.submit(() -> {
                return daoMovimiento.obtenerIngresosGenerales(id_usuario);
            });
            return future.get();
        }catch (Exception e){
            if(e instanceof InterruptedException){
                return 0;
            }
            return 0;
        }
    }
    public double obtenerIngresosPorProyecto(int id_proyecto) {
        try{
            Future<Double> future = DatabaseExecutor.EXECUTOR.submit(() -> {
                return daoMovimiento.obtenerIngresosPorProyecto(id_proyecto);
            });
            return future.get();
        }catch (Exception e){
            if(e instanceof InterruptedException){
                return 0;
            }
            return 0;
        }
    }
    public double obtenerGastosPorProyecto(int id_proyecto) {
        try{
            Future<Double> future = DatabaseExecutor.EXECUTOR.submit(() -> {
                return daoMovimiento.obtenerGastosPorProyecto(id_proyecto);
            });
            return future.get();
        }catch (Exception e){
            if(e instanceof InterruptedException){
                return 0;
            }
            return 0;
        }
    }
}
