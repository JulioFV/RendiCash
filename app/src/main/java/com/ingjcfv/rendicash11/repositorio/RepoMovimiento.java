package com.ingjcfv.rendicash11.repositorio;

import android.content.Context;

import com.ingjcfv.rendicash11.dao.DaoMovimiento;
import com.ingjcfv.rendicash11.database.AppDatabase;
import com.ingjcfv.rendicash11.modelo.Movimiento;
import com.ingjcfv.rendicash11.utils.CallbackResultado;
import com.ingjcfv.rendicash11.utils.DatabaseExecutor;

import java.util.List;

public class RepoMovimiento {
    private final DaoMovimiento daoMovimiento;
    public RepoMovimiento(Context contexto) {
        daoMovimiento = AppDatabase.getInstance(contexto).daoMovimiento();
    }
    public void crearMovimiento(Movimiento movimiento, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
           try{
               daoMovimiento.crearMovimiento(movimiento);
               callback.onSuccess("Movimiento creado");
           }catch (Exception e){
               callback.onError(e);
           }
        });
    }
    public void eliminarMovimiento(Movimiento movimiento, CallbackResultado<String> callback) {
        DatabaseExecutor.EXECUTOR.execute(() -> {
           try{
               daoMovimiento.eliminarMovimiento(movimiento);
               callback.onSuccess("Movimiento eliminado");
           }catch (Exception e){
               callback.onError(e);
           }
        });
    }
    public List<Movimiento> obtenerMovimientosPorProyecto(int id_proyecto) {
        return daoMovimiento.obtenerMovimientosPorProyecto(id_proyecto);
    }
}
