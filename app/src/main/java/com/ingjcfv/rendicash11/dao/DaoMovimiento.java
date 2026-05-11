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

}
