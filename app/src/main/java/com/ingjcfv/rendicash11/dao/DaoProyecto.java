package com.ingjcfv.rendicash11.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ingjcfv.rendicash11.modelo.Proyecto;

import java.util.List;

@Dao
public interface DaoProyecto {
    @Insert
    void crearProyecto(Proyecto proyecto);
    @Query("SELECT * FROM proyecto WHERE id_usuario=:id_usuario")
    List<Proyecto> obtenerProyectosPorUsuario(int id_usuario);
    @Query("SELECT * FROM proyecto WHERE id_usuario = :id_usuario AND status = 1 ORDER BY id DESC LIMIT 3")
    List<Proyecto> obtenerProyectosRecomendados(int id_usuario);


}
