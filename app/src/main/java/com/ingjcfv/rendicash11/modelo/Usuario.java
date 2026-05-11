package com.ingjcfv.rendicash11.modelo;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Usuario",
indices = {@Index(value = {"correo"}, unique = true)})
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private String password;
    private String correo;
    public Usuario(String nombre, String password, String correo) {
        this.nombre = nombre;
        this.password = password;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
