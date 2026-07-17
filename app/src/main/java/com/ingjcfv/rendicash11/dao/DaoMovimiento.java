package com.ingjcfv.rendicash11.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ingjcfv.rendicash11.modelo.Movimiento;

import java.util.List;

@Dao
public interface DaoMovimiento {
    @Insert
    void crearMovimiento(Movimiento movimiento);
    @Delete
    void eliminarMovimiento(Movimiento movimiento);
    @Query("SELECT * FROM movimiento WHERE id_proyecto=:id_proyecto")
    List<Movimiento> obtenerMovimientosPorProyecto(int id_proyecto);
    @Query("SELECT SUM(monto) FROM movimiento WHERE id_usuario = :id_usuario AND tipo = 1")
    double obtenerGastosGenerales(int id_usuario);
    @Query("SELECT SUM(monto) FROM movimiento WHERE id_usuario = :id_usuario AND tipo = 2")
    double obtenerIngresosGenerales(int id_usuario);
    @Query("SELECT SUM(monto) FROM movimiento WHERE id_proyecto = :id_proyecto AND tipo = 2")
    double obtenerIngresosPorProyecto(int id_proyecto);
    @Query("SELECT SUM(monto) FROM movimiento WHERE id_proyecto = :id_proyecto AND tipo = 1")
    double obtenerGastosPorProyecto(int id_proyecto);

}
