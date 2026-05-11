package com.ingjcfv.rendicash11.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ingjcfv.rendicash11.modelo.Usuario;

@Dao
public interface DaoUsuario {
    @Insert
    void crearUsuario(Usuario usuario);
    @Query("Select * from usuario WHERE correo=:correo")
    Usuario buscarUsuarioPorCorreo(String correo);
    @Update
    void actualizarUsuario(Usuario usuario);
    @Delete
    void eliminarUsuario(Usuario usuario);

}
